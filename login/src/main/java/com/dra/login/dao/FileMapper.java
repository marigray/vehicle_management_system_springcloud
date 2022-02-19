package com.dra.login.dao;

import com.dra.pojo.login.File;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface FileMapper {
    Page<File> findFile(@Param("powerId") String powerId);

    int addFile(File file);
    int updateFile(File file);
    int deleteFile(@Param("fileId") String fileId);
    File searchFileById(@Param("fileId") String fileId);
    Page<File> searchAllFileByUsername();
}
