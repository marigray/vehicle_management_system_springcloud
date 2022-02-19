package com.dra.pojo.login;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author liuwang
 * @create 2021-08-13 14:43:34 
 * @description  
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Power implements Serializable {

	private String powerId;

	private String powerType;

	private List<Element> elements;

	private List<Menu> menus;

	private List<File> files;
}
