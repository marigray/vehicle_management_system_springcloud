package com.ycx.lend.mapper;

import com.github.pagehelper.Page;
import com.ycx.lend.pojo.Application;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface ApplicationMapper extends Mapper<Application> {
    Page<Application> queryAllApplication();

    List<Application> queryApplicationByUser(@Param("userId") String userId);

    @Select("select * from application where if_return=0")
    List<Application> queryNotReturnApplication();

    //修改申请表审核状态
    @Update("update application set audit_status=#{auditStatus} where application_id=#{applicationId}")
    int updateAuditStatus(@Param("applicationId") String applicationId,
                          @Param("auditStatus") Integer auditStatus);

    //查询所有申请单号
    @Select("select application_id from application")
    List<String> queryAllApplicationId();

    //修改申请的还车状态
    @Update("update application set if_return = #{ifReturn} where application_id=#{applicationId}")
    int updateIfReturn(@Param("applicationId") String applicationId,
                       @Param("ifReturn") Integer ifReturn);

    //通过车牌号和申请人查找未归还的申请单
    @Select("select * from application where car_id=#{carId} and user_id=#{userId} and if_return=0")
    Application queryByCarAndUser(@Param("carId") String carId, @Param("userId") String userId);

    //通过车牌号查询派车未归还的申请
    @Select("select * from application where car_id=#{carId} and if_return=0")
    Application queryNotReturnByCarId(@Param("carId") String carId);
}
