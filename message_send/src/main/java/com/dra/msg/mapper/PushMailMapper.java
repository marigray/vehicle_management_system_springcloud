package com.dra.msg.mapper;

import com.dra.pojo.msg.PushMail;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PushMailMapper {
    int addPushMail(PushMail pushMail);
    int delPushMail(@Param("id") String id);
    int updatePushMail(PushMail pushMail);
    Page<PushMail> selectAll();
}
