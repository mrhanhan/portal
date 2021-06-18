package com.portal.core.server.monitor;

import com.portal.core.connect.Connection;
import com.portal.core.server.DataHandler;
import com.portal.core.server.DataMonitorRegister;
import com.portal.core.utils.ByteVisit;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

/**
 * IConnectionMonitor
 * 连接检测，检测到写入请求就进行读取
 *
 * @author Mrhan
 * @date 2021/6/15 17:47
 */
@RequiredArgsConstructor
public class SimpleDataMonitor implements DataMonitor {

    public static final byte[] START = ByteVisit.intToBytes(0xAABB);
    @Setter
    public Consumer<Exception> exceptionHandler = (e) -> {
        e.printStackTrace();
    };
    @Getter
    public final Connection connection;
    private final DataHandler dataHandler;
    private final DataMonitorRegister register;
    private final AtomicBoolean status = new AtomicBoolean(false);

    /**
     * 接受的数据包有几种情况
     * : 粘包
     * : 截断
     */
    @Override
    public void run() {
        InputStream input = connection.getInput();
        byte[] data = new byte[512];
        // 读取数据的长度
        int readLength = 0;
        // Data数据长度
        int dataLength = 0;
        // 当前数据读取到的长度
        int dataPosition = 0;
        // 操作数据的长度
        int readPosition = 0;
        // 操作模式，false: 待读取数据，true: 读取数据中
        boolean mode = false;
        // 检测数据的长度
        int detectionLength = 8;
        // 检测数据
        byte[] detectionDataCache = null;
        status.set(true);
        try {
            ByteArrayOutputStream cache = new ByteArrayOutputStream();
            //这里的是需要判断是否瞒著于
            while (status.get() && connection.isAvailable()) {
                readLength = input.read(data);
                if (readLength == -1) {
                    // 输入输出流关闭，断开连接
                    break;
                }
                readPosition = 0;
                /*
                    读取数据模式，继续读取上次没有读取完的数据
                 */
                while (true) {
                    if (mode) {
                        // 剩余需要读取数据的长度
                        int currentLength = dataLength - dataPosition;
                        // 如果本此接受的数据，只是原来数据的一部分, 读取 本此剩下的所有数据，然后跳出本此循环，读取下次需要的数据
                        int lastLength = (readLength - readPosition);
                        if (lastLength < currentLength) {
                            dataPosition += lastLength;
                            cache.write(data, readPosition, lastLength);
                            break;
                        } else {
                            // 如果本此需要接受的数据已经完全接受完，可以完全读取，然后等待下下次检测
                            cache.write(data, readPosition, currentLength);
                            readPosition += currentLength;
                            mode = false;
                            // 触发调用
                            byte[] bytes = cache.toByteArray();
                            cache.reset();
                            onHandler(bytes);
                        }
                    }
                    /*
                    待检测状态
                    检测状态下，有两个问题，
                        1: 本次剩余的数据不足以检测，
                        2: 本次剩余的数据足以检测，
                    */
                    int lastLength = (readLength - readPosition);
                    int cacheDataLength = Objects.isNull(detectionDataCache) ? 0 : detectionDataCache.length;
                    if ((cacheDataLength + lastLength) < detectionLength) {
                        // 本此不足以检测怎么办
                        // 缓存本次数据，然后留着下次检测
                         detectionDataCache = Objects.isNull(detectionDataCache) ? ByteVisit.get(data, readPosition, lastLength) : ByteVisit.join(detectionDataCache, ByteVisit.get(data, readPosition, lastLength));
                         break;
                    } else {
                        // 算上缓存数据足够检测，和没有缓存数据足够检测
                        // 计算需要从新读取数据的地方需要在读取多长
                        int laseNeedDetectionLength = detectionLength - cacheDataLength;
                        detectionDataCache = cacheDataLength == 0 ? ByteVisit.get(data, readPosition, laseNeedDetectionLength) : ByteVisit.join(detectionDataCache, ByteVisit.get(data, readPosition, laseNeedDetectionLength));
                        readPosition += laseNeedDetectionLength;
                        // 开始检测
                        if (ByteVisit.equ(detectionDataCache, START)) {
                            detectionDataCache = null;
                            dataLength = ByteVisit.bytesToInt(ByteVisit.get(detectionDataCache, START.length, 4));
                            System.out.println("检测到数据: " + dataLength);
                            dataPosition = 0;
                            mode = true;
                        }
                        detectionDataCache = null;
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            status.compareAndSet(true, false);
            // 移除 检测
            register.removeDataMonitor(this);
        }

    }

    /**
     * 触发 数据处理器
     * @param bytes 需要处理的数据
     */
    private void onHandler(byte[] bytes) {
        try {
            dataHandler.onHandler(this, bytes);
        }catch (Exception e){
            exceptionHandler.accept(e);
        }
    }

    @Override
    public void close() throws Exception {
        status.set(false);
    }

    @Override
    public byte[] bale(byte[] data) {
        byte[] length = ByteVisit.intToBytes(data.length);
        return ByteVisit.join(START, length, data);
    }
}
