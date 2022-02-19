package com.dra.pojo.gps;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Gps implements Serializable {

  private String gpsId;
  private String gpsName;
  private String gpsType;
}
