package com.dra.pojo.gps;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GpsLog implements Serializable {

  private String carId;
  private String gpsId;
  private Date time;
  private double positionX;
  private double positionY;


}
