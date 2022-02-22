package com.wang.vire.mapper;

import com.wang.vire.pojo.Car;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @Author wang
 * @Data 2022/1/24 14:01
 * @Description
 */
@Repository
public interface CarMapper extends Mapper<Car> {
    @Select("select * from car where car_status = 1")
    List<Car> queryIdleCar();

}
