package com.ycx.lend.pojo;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@ToString
@Entity
@Table(name = "car")
public class Car implements Serializable {
  @Id
  private String carId;

  private String carModel;

  private Integer carStatus;

  private Integer carType;
}
