package com.example.simple.model;

import java.io.Serializable;

/**
 * NoSerial
 * 不可序列化的对象
 * @author Mrhan
 * @date 2021/7/1 10:15
 */

public class NoSerial  {

    private int count;

    public void add() {
        this.count ++;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
