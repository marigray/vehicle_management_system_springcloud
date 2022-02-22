package com.wang.vire.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author wang
 * @Data 2022/2/20 19:43
 * @Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VioMessage {
    private String carId;
    private String vioTime;
    private String vioPlace;
    private String vioAction;
    private int vioScore;
    private String vioCost;
    private String organization;
}
