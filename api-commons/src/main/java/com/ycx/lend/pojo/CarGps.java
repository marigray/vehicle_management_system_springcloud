package com.ycx.lend.pojo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author ycx
 * @Date 2022/3/4 15:35
 * @Description
 */

@Data
public class CarGps implements Serializable {
    private double positionX;
    private double positionY;
}
