package com.portal.core.utils;

/**
 * Snowflake算法实现
 * @author 77
 * @date 2019/7/21 17:20
 */
public class Snowflake {

    private static final int MAX_WORKD_ID = 1024;
    private static final int MAX_SEQUENCE_ID = 4096;
    /**
     * 时间位
     */
    private int timeBit = 41;
    /**
     * Work ID
     */
    private int workBit = 10;
    /**
     * 序列号
     */
    private int serialBit = 12;

    /**
     * 最后一次生成的时间
     */
    private long lastTime = 0;
    /**
     * 工作ID
     */
    private int workId = 0;

    private int sequence = 0;


    /**
     *
     * @param workId
     * @param sequence
     * @param lastTime
     */
    public Snowflake(int workId , int sequence , long lastTime){
        this.workId = workId;
        this.sequence = sequence;
        this.lastTime = lastTime;
    }

    /**
     *
     * @param wordId
     * @param sequence
     */
    public Snowflake(int wordId , int sequence){
       this(wordId , sequence , System.currentTimeMillis());
    }

    /**
     *
     * @param workId
     */
    public Snowflake(int workId){
        this(workId , 1);
    }

    /**
     * 生成UID
     * @return
     */
    public synchronized long generate(){
        long uid = 0;
        long nowTime = getTime();
        if (nowTime < lastTime){
            throw new RuntimeException("UID 的时间反生回流，无法生成唯一UID");
        }
        if (nowTime == lastTime){
            sequence ++ ;
            // 判断一毫秒内是否超过4096个序列，如果超出，则会溢出，需要等待到下一秒
            if (sequence >= MAX_SEQUENCE_ID){
                sequence = 1;
                nowTime = waitNextTime(nowTime);
            }
        }else{
            sequence = 1;
        }
        lastTime = nowTime;
        uid <<= timeBit;
        uid |= nowTime;
        uid <<= workBit;
        uid |= workId;
        uid <<= serialBit;
        uid |= sequence;

        return uid;
    }

    // ================================================= 私有方法 ==============================================


    /**
     * 获取时间
     * @return
     */
    private long getTime(){
        return System.currentTimeMillis();
    }

    /**
     * 等待获取到下一个时间
     * @return
     */
    private long waitNextTime(long nowTime){
        long nextTime = getTime();

        while (nowTime == nextTime){
            nextTime = getTime();
        }
        return nextTime;
    }
}

