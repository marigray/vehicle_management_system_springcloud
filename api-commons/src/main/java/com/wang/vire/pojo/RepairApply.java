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
@Table(name = "repair_apply")
public class RepairApply implements Serializable {

  @Id
  private String applyId;
  private String applicantId;
  private String carId;
  private String repairReasons;
  private Date applyTime;
  private Integer applyStatus;
}
