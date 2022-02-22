package com.wang.vire.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "auditor")
public class Auditor implements Serializable {
  @Id
  private String auditorId;
  private String userId;
  private Integer auditorType;

}
