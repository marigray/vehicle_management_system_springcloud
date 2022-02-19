package com.dra.pojo.gps;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 原始数据 用于接受GPS数据
 *
 * {
 *   "gpsId":"123",
 *   "longitude":"2318.13307",
 *   "latitude":"11319.72036"
 * }
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationOriginal {
    private String gpsId;
    private String longitude;
    private String latitude;
}
