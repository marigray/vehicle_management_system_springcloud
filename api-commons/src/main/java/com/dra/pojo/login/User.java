package com.dra.pojo.login;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

	private String id;

	private String username;

	/**
	 * 真实姓名
	 */
	private String name;

	private String password;

	private String idNum;

	private Date lastTime;

	private String phoneNum;

	private String eMail;



}
