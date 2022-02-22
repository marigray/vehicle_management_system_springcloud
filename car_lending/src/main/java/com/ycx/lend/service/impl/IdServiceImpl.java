package com.ycx.lend.service.impl;

import com.ycx.lend.mapper.IdMapper;
import com.ycx.lend.service.IdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author ycx
 * @Date 2022/1/24 19:24
 * @Description
 */
@Service
public class IdServiceImpl implements IdService {
    @Autowired
    IdMapper idMapper;

    @Override
    public String getMaxId(String tableName, String idName) {
        int j = 0;
        for (String id : idMapper.getId(tableName, idName)) {
            int i = Integer.parseInt(id);
            if (i > j) {
                j = i;
            }
        }
        return String.valueOf(j);
    }

    @Override
    public String getMinId(String tableName, String idName) {
        int j = Integer.parseInt(getMaxId(tableName, idName));
        for (String id : idMapper.getId(tableName, idName)) {
            int i = Integer.parseInt(id);
            if (i < j) {
                j = i;
            }
        }
        return String.valueOf(j);
    }
}
