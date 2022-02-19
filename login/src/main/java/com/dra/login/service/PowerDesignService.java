package com.dra.login.service;

import com.dra.pojo.login.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 安全框架数据增删改查
 */

public interface PowerDesignService {

    /**
     *角色
     */
    int addRole(Role role);
    int updateRole(Role role);
    int deleteRoleRole(String roleId);
    Role searchRoleById(String roleId);
    ArrayList<Role> searchAllRole(int pageNum,int pageSize);

    /**
     *权限
     */
    int addPower(Power power);
    int updatePower(Power power);
    int deletePower(String powerId);
    Power searchPowerById(String powerId);
    ArrayList<Power> searchAllPower(int pageNum,int pageSize);

    /**
     *页面元素（当然我也把它当url拦截使用）
     */
    int addElement(Element element);
    int updateElement(Element element);
    int deleteElement(String elementId);
    Element searchElementById(String elementId);
    ArrayList<Element> searchAllElement(int pageNum,int pageSize);

    /**
     *文件 暂时没用
     */
    int addFile(File file);
    int updateFile(File file);
    int deleteFile(String fileId);
    File searchFileById(String fileId);
    ArrayList<File> searchAllFile(int pageNum,int pageSize);

    /**
     *菜单
     */
    int addMenu(Menu menu);
    int updateMenu(Menu menu);
    int deleteMenu(String menuId);
    Menu searchMenuById(String menuId);
    ArrayList<Menu> searchAllMenu(int pageNum,int pageSize);

    /**
     *前台用户
     */
    int addUser(User user);
    int updateUser(User user);
    int deleteUser(String id);
    User searchUserById(String id);
    HashMap<String,String> searchUserByUsername(String username);
    ArrayList<User> searchAllUserByUsername(String username,int pageNum,int pageSize);

    /**
     *后台用户
     */
    int addBackground(Background background);
    int updateBackground(Background background);
    int deleteBackground(String id);
    HashMap<String, String> searchBackgroundByUsername(String username);
    HashMap<String,String> searchBackgroundById(String id);
    ArrayList<HashMap<String,String>> searchAllBackgroundByUsername(String username,int pageNum,int pageSize);
}
