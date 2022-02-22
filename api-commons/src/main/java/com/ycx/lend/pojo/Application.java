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
@Table(name = "application")
public class Application implements Serializable{
    @Id
    private String applicationId;

    private String userId;

    private String carId;

    private String reason;

    private Integer type;

    private Integer auditStatus;

    private Date applicationTime;

    private Integer ifReturn;

}
