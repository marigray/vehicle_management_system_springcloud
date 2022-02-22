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
@Table(name = "audit")
public class Audit implements Serializable {
    @Id
    private String auditId;

    private String applicationId;

    private String auditorId;

    private Integer auditStatus;

    private Date auditTime;

}
