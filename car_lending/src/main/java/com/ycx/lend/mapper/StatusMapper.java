package com.ycx.lend.mapper;

import com.ycx.lend.pojo.Status;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * @Author ycx
 * @Date 2022/1/30 16:26
 * @Description
 */
@Repository
public interface StatusMapper extends Mapper<Status> {
    @Select("select status_name  from status where status_num = #{carStatus}")
    String queryCarStatusName(@Param("carStatus") Integer carStatus);
}
