package com.example.image.api;

import java.awt.Image;

/**
 * ImageService
 *
 * @author Mrhan
 * @date 2021/7/6 10:32
 */
public interface ImageService {
    /**
     * 获取图片信息
     * @return  获取图片信息
     */
    Image image();

    /**
     * 关闭服务器
     */
    void close();
}
