package com.example.image.impl;

import com.example.image.Server;
import com.example.image.api.ImageService;
import lombok.SneakyThrows;

import javax.imageio.ImageIO;
import java.awt.Image;

/**
 * ImageServiceImpl
 *
 * @author Mrhan
 * @date 2021/7/6 10:33
 */
public class ImageServiceImpl implements ImageService {

    private final Server server;

    public ImageServiceImpl(Server server) {
        this.server = server;
    }

    @SneakyThrows
    @Override
    public Image image() {
        return ImageIO.read(getClass().getResource("/78.jpg"));
    }

    @Override
    public void close() {
        server.shutDown();
    }
}
