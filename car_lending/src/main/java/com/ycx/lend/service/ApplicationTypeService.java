package com.ycx.lend.service;

import com.ycx.lend.pojo.ApplicationType;

import java.util.List;

/**
 * @Author ycx
 * @Date 2022/1/23 8:28
 * @Description
 */
public interface ApplicationTypeService {

    List<ApplicationType> queryAllApplicationType();

    ApplicationType queryApplicationTypeByNum(int num);

    Integer getCarStatusByApply(Integer applicationType);

}
