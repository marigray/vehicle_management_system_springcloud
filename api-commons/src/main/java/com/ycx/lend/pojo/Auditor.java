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
@Table(name = "auditor")
public class Auditor implements Serializable {
    @Id
    private String auditorId;

    private String userId;

    private Integer auditorType;

}
