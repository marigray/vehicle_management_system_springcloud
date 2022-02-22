package com.ycx.lend.mapper;

import com.ycx.lend.pojo.ApplicationType;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface ApplicationTypeMapper extends Mapper<ApplicationType> {

    @Select("select type_name from application_type where type_num=#{typeNum}")
    String queryTypeName(@Param("typeNum") int typeNum);

}
