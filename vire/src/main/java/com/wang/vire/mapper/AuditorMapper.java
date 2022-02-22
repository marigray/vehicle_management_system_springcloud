package com.wang.vire.mapper;

import com.wang.vire.pojo.Auditor;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @Author wang
 * @Data 2022/1/22 22:17
 * @Description
 */
@Repository
public interface AuditorMapper extends Mapper<Auditor> {
    @Select("select auditor_id from auditor where auditor_type=0")
    List<String> queryNormalAuditorId();

    @Select("select auditor_id from auditor where auditor_type=1 or auditor_type=1")
    List<String> queryEndAuditorId();
}
