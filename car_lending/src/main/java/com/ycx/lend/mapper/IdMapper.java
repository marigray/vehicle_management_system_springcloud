package com.ycx.lend.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author ycx
 * @Date 2022/1/24 15:24
 * @Description
 */
@Repository
public interface IdMapper {
    List<String> getId(@Param("tableName") String tableName,
                       @Param("idName") String idName);
}
