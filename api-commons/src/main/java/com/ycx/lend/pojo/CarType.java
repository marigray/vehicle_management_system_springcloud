package com.ycx.lend.pojo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Author ycx
 * @Date 2022/1/22 21:01
 * @Description
 */
@Data
@Entity
@Table(name = "car_type")
public class CarType implements Serializable {
    @Id
    private Integer typeNum;

    private String typeName;

}