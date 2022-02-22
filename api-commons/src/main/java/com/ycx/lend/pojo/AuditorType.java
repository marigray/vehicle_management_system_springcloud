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
@Table(name = "auditor_type")
public class AuditorType implements Serializable {
  @Id
  private Integer typeNum;

  private String typeName;

}
