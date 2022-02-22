package com.ycx.lend.service;

import java.util.HashMap;

/**
 * @Author ycx
 * @Date 2022/2/19 12:40
 * @Description
 */

public interface CompanyService {
    //设置公司位置
    public int setCompanyLocation(String Location);

    //获取公司位置经纬度
    public  HashMap<String,Double> getCompanyLocation();
}
