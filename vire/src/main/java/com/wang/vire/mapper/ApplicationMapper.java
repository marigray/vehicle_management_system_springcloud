package com.wang.vire.mapper;

import com.wang.vire.pojo.Application;
import io.lettuce.core.dynamic.annotation.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;
import java.util.List;

/**
 * @Author wang
 * @Data 2022/2/15 19:42
 * @Description
 */
@Repository
public interface ApplicationMapper extends Mapper<Application> {
//    @Select("select * from application where car_id=#{carId}")
//    List<Application> queryApplicationByCarId(@Param("carId")String carId);
    List<Application> queryApplicationByTime(@Param("carId")String carId,@Param("applicationTime")Date applicationTime);

}
