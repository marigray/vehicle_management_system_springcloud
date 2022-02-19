package com.dra.pojo.login;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author liuwang
 * @create 2021-08-13 14:43:34 
 * @description  
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Background implements Serializable {

	private String id;

	private String username;

	private String password;

	/**
	 * 最后一次登陆时间
	 */
	private Date lastTime;

}
