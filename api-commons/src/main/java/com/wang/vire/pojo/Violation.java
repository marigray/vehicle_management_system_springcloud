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
@Table(name = "violation")
public class Violation implements Serializable {
  @Id
  private String vioId;
  private String carId;
  private Date vioTime;
  private String vioPlace;
  private String vioAction;
  private Integer vioScore;
  private String vioCost;
  private String organization;
  private Integer vioStatus;
  private String violatorId;

}
