package com.wang.vire.utils;

import java.util.Random;

/**
 * @Author wang
 * @Data 2022/1/23 22:07
 * @Description
 */
public class RandomNum {
    public static String randomNum(){
        return String.valueOf(Math.abs(new Random().nextInt()));
    }
}
