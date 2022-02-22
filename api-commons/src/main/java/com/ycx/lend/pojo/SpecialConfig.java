package com.ycx.lend.pojo;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Author ycx
 * @Date 2022/2/21 14:38
 * @Description
 */
@Data
@ToString
@Entity
@Table(name = "special_conf")
public class SpecialConfig implements Serializable {
    @Id
    private String key;

    private String value;
}
