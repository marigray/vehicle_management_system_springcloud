package com.dra.pojo.gps;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarGps implements Serializable {

    private String carId;
    private String gpsId;


}
