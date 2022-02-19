package com.dra.pojo.msg;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Car implements Serializable {
  private String carId;
  private Date carProTime;//车辆数据入库时间
  private Date lastMaintain;//上次保养时间
}
