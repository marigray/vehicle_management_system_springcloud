package com.ycx.lend.service.impl;

import com.ycx.lend.mapper.ApplicationTypeMapper;
import com.ycx.lend.pojo.ApplicationType;
import com.ycx.lend.service.ApplicationTypeService;
import com.ycx.lend.utils.EmptyChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author ycx
 * @Date 2022/1/23 8:31
 * @Description
 */
@Service
public class ApplicationTypeServiceImpl implements ApplicationTypeService {
    @Autowired
    ApplicationTypeMapper applicationTypeMapper;


    @Override
    public List<ApplicationType> queryAllApplicationType() {
        return applicationTypeMapper.selectAll();
    }

    @Override
    public ApplicationType queryApplicationTypeByNum(int typeNum) {
        return applicationTypeMapper.selectByPrimaryKey(typeNum);
    }

    @Override
    public Integer getCarStatusByApply(Integer applicationType) {
        if (EmptyChecker.isEmpty(applicationType)) {
            return 0;
        }
        String s = applicationTypeMapper.queryTypeName(applicationType);
        if (s.equals("派车")) {
            return 3;
        }
        if (s.equals("借车")) {
            return 2;
        }
        if (s.equals("还车")) {
            return 1;
        }
        return null;
    }
}
