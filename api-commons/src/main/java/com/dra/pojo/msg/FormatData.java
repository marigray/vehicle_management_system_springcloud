package com.dra.pojo.msg;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FormatData<T> implements Serializable {
    private T data;
    private int code;
    private String msg;
}
