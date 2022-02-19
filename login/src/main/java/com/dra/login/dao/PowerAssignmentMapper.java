package com.dra.login.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.HashMap;
@Mapper
public interface PowerAssignmentMapper {

    /**
     * 添加相关属性
     */

    int addRoleToUser(@Param("id") String id,
                      @Param("roleId") String roleId);

    int addPowerToRole(@Param("roleId") String roleId,
                       @Param("powerId") String powerId);

    int addElementToPower(@Param("powerId") String powerId,
                          @Param("elementId") String elementId);

    int addFileToPower(@Param("powerId") String powerId,
                       @Param("fileId") String fileId);

    int addMenuToPower(@Param("powerId") String powerId,
                       @Param("menuId") String menuId);


    /**
     * 移除相关属性
     */
    int deleteRoleFromUser(@Param("id") String id,
                           @Param("roleId") String roleId);

    int deletePowerFromRole(@Param("roleId") String roleId,
                            @Param("powerId") String powerId);

    int deleteElementFromRole(@Param("powerId") String powerId,
                              @Param("elementId") String elementId);

    int deleteFileFromRole(@Param("powerId") String powerId,
                           @Param("fileId") String fileId);

    int deleteMenuFromRole(@Param("powerId") String powerId,
                           @Param("menuId") String menuId);

    /**
     * 查询相关属性
     */

    ArrayList<HashMap<String, String>> searchRoleByUsername(@Param("id") String id);

    ArrayList<HashMap<String, String>>  searchPowerByRoleId(@Param("roleId") String roleId);

    ArrayList<HashMap<String, String>>  searchElementByPowerId(@Param("powerId") String powerId);

    ArrayList<HashMap<String, String>>  searchFileByPowerId(@Param("powerId") String powerId);

    ArrayList<HashMap<String, String>>  searchMenuByPowerId(@Param("powerId") String powerId);
}
