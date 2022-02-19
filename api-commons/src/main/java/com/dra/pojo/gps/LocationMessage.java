package com.dra.pojo.gps;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 标准数据
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationMessage {
    private String gpsId;
    private Location longitude; //经
    private Location latitude;  //纬
}
