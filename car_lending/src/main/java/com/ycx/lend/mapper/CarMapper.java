package com.ycx.lend.mapper;

import com.ycx.lend.pojo.Car;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @Author ycx
 * @Date 2022/1/22 20:32
 * @Description
 */
@Repository
public interface CarMapper extends Mapper<Car> {

    @Select("select * from car where car_status = 1")
    List<Car> queryIdleCar();

    Car queryCarByAuditEndId(@Param("auditId") String auditId);
}
