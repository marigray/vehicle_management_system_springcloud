package com.ycx.lend.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author ycx
 * @Date 2022/2/19 12:59
 * @Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GpsConvert implements Serializable {
    private String locations;
    private String coordsys;
    private String output;
    private String key;
}
