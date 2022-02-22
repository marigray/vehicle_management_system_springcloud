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
@Table(name = "audit_end")
public class AuditEnd implements Serializable {
  @Id
  private String auditId;
  private String auditorId;
  private String applicationId;
  private Integer endStatus;
  private Date endTime;

}
