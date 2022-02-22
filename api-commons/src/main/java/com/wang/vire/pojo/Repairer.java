package com.wang.vire.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Author wang
 * @Data 2022/2/18 21:40
 * @Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "repairer")
public class Repairer implements Serializable {
    @Id
    private String repairerId;
    private String userId;
}
