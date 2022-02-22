package com.ycx.lend.service;

import com.github.pagehelper.Page;
import com.ycx.lend.pojo.Application;

import java.util.List;

public interface ApplicationService {

    //添加申请，对申请进行分类处理
    int addApplication(Application application);

    //添加申请完毕后进行审核分配
    int allotApplication(String applicationId);

    //连同删除审核表中数据
    int delApplication(String applicationId);


    int updateApplication(Application application);

    Page<Application> queryAllApplication(int pageNum,int pageSize);

    Application queryApplicationById(String applicationId);

    //查询该用户的申请
    List<Application> queryApplicationByUser(String userId);

    //查询未归还的申请
    List<Application> queryNotReturnApplication();
}
