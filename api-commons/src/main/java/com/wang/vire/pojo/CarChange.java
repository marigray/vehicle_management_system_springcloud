package com.wang.vire.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "car_change")
public class CarChange implements Serializable {

  @Id
  private String carId;
  private Integer afterStatus;
  private Integer beforeStatus;
  private Date changeTime;

}
