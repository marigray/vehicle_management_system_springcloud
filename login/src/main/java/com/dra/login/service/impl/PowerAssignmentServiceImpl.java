package com.dra.login.service.impl;

import com.dra.login.dao.BackgroundMapper;
import com.dra.login.dao.PowerAssignmentMapper;
import com.dra.login.dao.UserMapper;
import com.dra.login.service.PowerAssignmentService;
import com.dra.pojo.login.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class PowerAssignmentServiceImpl implements PowerAssignmentService {

    @Resource
    private PowerAssignmentMapper powerAssignmentMapper;

//    @Resource
//    private RoleMapper roleMapper;
    @Resource
    private UserMapper userMapper;

    @Resource
    private BackgroundMapper backgroundMapper;

    private boolean isEmpty(String s1, String s2) {
        if (s1 == null || s1.equals("") || s2 == null || s2.equals("")) {
            return true;
        }
        return false;
    }


    public boolean isExist(String var1, String var2, Object o) {
        boolean is;
        String key = "";
        List<HashMap<String, String>> list;
        if (o instanceof Role) {
            list = powerAssignmentMapper.searchRoleByUsername(var1);
            key = "roleId";
        } else if (o instanceof Power) {
            list = powerAssignmentMapper.searchPowerByRoleId(var1);
            key = "powerId";
        } else if (o instanceof Element) {
            list = powerAssignmentMapper.searchElementByPowerId(var1);
            key = "elementId";
        } else if (o instanceof File) {
            list = powerAssignmentMapper.searchFileByPowerId(var1);
            key = "fileId";
        } else if (o instanceof Menu) {
            list = powerAssignmentMapper.searchMenuByPowerId(var1);
            key = "menuId";
        } else
            return true;
        for (HashMap<String, String> stringStringHashMap : list) {
//            System.out.println(stringStringHashMap);
            String s = stringStringHashMap.get(key);

            return s != null && s.equals(var2);
        }
        return false;
    }

//    @Test
//    public void test(){
////        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext-service.xml");
//        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext-tx.xml");
//        PowerAssignmentServiceImpl powerAssignmentServiceImpl = (PowerAssignmentServiceImpl) applicationContext.getBean("powerAssignmentServiceImpl");
//        System.out.println(powerAssignmentServiceImpl.isExist("1", "1", new Role()));
////        isExist("","",new IsType<Role>() );
//    }

    @Override
    public int addRoleToUser(String id, String roleId) {

        if (isEmpty(id, roleId))
            return 0;
        if (isExist(id,roleId,new Role()))
            return 0;
        return powerAssignmentMapper.addRoleToUser(id, roleId);
    }

    @Override
    public int addPowerToRole(String roleId, String powerId) {
        if (isEmpty(roleId, powerId))
            return 0;
        if (isExist(roleId,powerId,new Power()))
            return 0;
        return powerAssignmentMapper.addPowerToRole(roleId, powerId);
    }

    @Override
    public int addElementToPower(String powerId, String elementId) {
        if (isEmpty(powerId, elementId))
            return 0;
        if (isExist(powerId,elementId,new Power()))
            return 0;
        return powerAssignmentMapper.addElementToPower(powerId, elementId);
    }

    @Override
    public int addFileToPower(String powerId, String fileId) {
        if (isEmpty(powerId, fileId))
            return 0;
        if (isExist(powerId,fileId,new File()))
            return 0;
        return powerAssignmentMapper.addFileToPower(powerId, fileId);
    }

    @Override
    public int addMenuToPower(String powerId, String menuId) {
        if (isEmpty(powerId, menuId))
            return 0;
        if (isExist(powerId,menuId,new Menu()))
            return 0;
        return powerAssignmentMapper.addMenuToPower(powerId, menuId);
    }


    @Override
    public int deleteRoleFromUser(String id, String roleId) {
        if (isEmpty(id, roleId))
            return 0;
        return powerAssignmentMapper.deleteRoleFromUser(id, roleId);
    }

    @Override
    public int deletePowerFromRole(String roleId, String powerId) {
        if (isEmpty(roleId, powerId))
            return 0;
        return powerAssignmentMapper.deletePowerFromRole(roleId, powerId);
    }

    @Override
    public int deleteElementFromRole(String powerId, String elementId) {
        if (isEmpty(powerId, elementId))
            return 0;
        return powerAssignmentMapper.deleteElementFromRole(powerId, elementId);
    }

    @Override
    public int deleteFileFromRole(String powerId, String fileId) {
        if (isEmpty(powerId, fileId))
            return 0;
        return powerAssignmentMapper.deleteFileFromRole(powerId, fileId);
    }

    @Override
    public int deleteMenuFromRole(String powerId, String menuId) {
        if (isEmpty(powerId, menuId))
            return 0;
        return powerAssignmentMapper.deleteMenuFromRole(powerId, menuId);
    }


    @Override
    public ArrayList<HashMap<String, String>> searchRoleByUsername(String username, String type) {
        String userid;
        if (type.equals("user")) {
            userid = userMapper.searchByUsername(username).getId();
            System.out.println("userid===" + userid);
        } else if (type.equals("background"))
            userid = backgroundMapper.searchBackgroundByUsername(username).get("id");
        else
            return null;
        return powerAssignmentMapper.searchRoleByUsername(userid);
    }

    @Override
    public ArrayList<HashMap<String, String>> searchPowerByRoleId(String roleId) {
        return powerAssignmentMapper.searchPowerByRoleId(roleId);
    }

    @Override
    public ArrayList<HashMap<String, String>> searchElementByPowerId(String powerId) {
        return powerAssignmentMapper.searchElementByPowerId(powerId);
    }

    @Override
    public ArrayList<HashMap<String, String>> searchFileByPowerId(String powerId) {
        return powerAssignmentMapper.searchFileByPowerId(powerId);
    }

    @Override
    public ArrayList<HashMap<String, String>> searchMenuByPowerId(String powerId) {
        return powerAssignmentMapper.searchMenuByPowerId(powerId);
    }
}

