package com.ycx.lend.pojo;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@ToString
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
