package com.dra.login.service;

import java.util.ArrayList;
import java.util.HashMap;

public interface PowerAssignmentService {
    /**
     * 添加相关属性
     */

    int addRoleToUser(String id,
                      String roleId);

    int addPowerToRole(String roleId,
                       String powerId);

    int addElementToPower(String powerId,
                          String elementId);

    int addFileToPower(String powerId,
                       String fileId);

    int addMenuToPower(String powerId,
                       String menuId);


    /**
     * 移除相关属性
     */
    int deleteRoleFromUser(String id,
                           String roleId);

    int deletePowerFromRole(String roleId,
                            String powerId);

    int deleteElementFromRole(String powerId,
                              String elementId);

    int deleteFileFromRole(String powerId,
                           String fileId);

    int deleteMenuFromRole(String powerId,
                           String menuId);

    /**
     * 查询相关属性
     */

    ArrayList<HashMap<String, String>> searchRoleByUsername(String id,String type);

    ArrayList<HashMap<String, String>> searchPowerByRoleId(String roleId);

    ArrayList<HashMap<String, String>> searchElementByPowerId(String powerId);

    ArrayList<HashMap<String, String>> searchFileByPowerId(String powerId);

    ArrayList<HashMap<String, String>> searchMenuByPowerId(String powerId);
}
