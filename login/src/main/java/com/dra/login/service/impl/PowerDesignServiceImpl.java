package com.dra.login.service.impl;

import com.dra.login.dao.*;
import com.dra.login.service.PowerDesignService;
import com.dra.pojo.login.*;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class PowerDesignServiceImpl implements PowerDesignService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private BackgroundMapper backgroundMapper;

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private PowerMapper powerMapper;

    @Resource
    private ElementMapper elementMapper;

    @Resource
    private FileMapper fileMapper;

    @Resource
    private MenuMapper menuMapper;

    @Test
    public void test() {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-tx.xml");
        PowerDesignServiceImpl powerDesignServiceImpl =
                (PowerDesignServiceImpl) context.getBean("powerDesignServiceImpl");
        User user = new User();
        user.setId("11629397199132");
        user.setLastTime(new Date());

        powerDesignServiceImpl.updateUser(user);
    }


    @Override
    public int addRole(Role role) {

        if (role == null) {
            return 0;
        }
        if (role.getRoleId() == null || role.getRoleId().equals("") ||
                role.getRoleName() == null || role.getRoleName().equals(""))
            return 0;
            return roleMapper.addRole(role);
    }

    @Override
    public int updateRole(Role role) {
        return roleMapper.updateRole(role);
    }

    @Override
    public int deleteRoleRole(String roleId) {
        if (roleId==null||roleId.equals(""))
            return 0;
        return roleMapper.deleteRole(roleId);
    }

    @Override
    public Role searchRoleById(String roleId) {
        if (roleId==null||roleId.equals(""))
            return null;
        return roleMapper.searchRoleById(roleId);
    }

    @Override
    public ArrayList<Role> searchAllRole(int pageNum,int pageSize) {
        PageHelper.startPage(pageNum,pageSize);

        return roleMapper.searchAllRole();
    }


    @Override
    public int addPower(Power power) {
        if (power == null) {
            return 0;
        }
        if (power.getPowerId() == null || power.getPowerId().equals("") ||
                power.getPowerType() == null || power.getPowerType().equals(""))
            return 0;
        return powerMapper.addPower(power);
    }

    @Override
    public int updatePower(Power power) {
        return powerMapper.updatePower(power);
    }

    @Override
    public int deletePower(String powerId) {
        if (powerId==null||powerId.equals(""))
            return 0;
        return powerMapper.deletePower(powerId);
    }

    @Override
    public Power searchPowerById(String powerId) {
        if (powerId==null||powerId.equals(""))
            return null;
        return powerMapper.searchPowerById(powerId);
    }

    @Override
    public ArrayList<Power> searchAllPower(int pageNum,int pageSize) {
        PageHelper.startPage(pageNum,pageSize);


        return powerMapper.searchAllPowerByUsername();
    }


    @Override
    public int addElement(Element element) {
        if (element == null) {
            return 0;
        }
        if (element.getElementId() == null || element.getElementId().equals("") ||
                element.getElementEncode() == null || element.getElementEncode().equals(""))
            return 0;
        return elementMapper.addElement(element);
    }

    @Override
    public int updateElement(Element element) {
        return elementMapper.updateElement(element);
    }

    @Override
    public int deleteElement(String elementId) {
        if (elementId==null||elementId.equals(""))
            return 0;
        return elementMapper.deleteElement(elementId);
    }

    @Override
    public Element searchElementById(String elementId) {
        if (elementId==null||elementId.equals(""))
            return null;
        return elementMapper.searchElementById(elementId);
    }

    @Override
    public ArrayList<Element> searchAllElement(int pageNum,int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return elementMapper.searchAllElementByUsername();
    }


    @Override
    public int addFile(File file) {
        if (file == null) {
            return 0;
        }
        if (file.getFileId() == null || file.getFileId().equals("") ||
                file.getFileName() == null || file.getFileName().equals("") ||
                file.getFileUrl() == null || file.getFileUrl().equals(""))
            return 0;
        return fileMapper.addFile(file);
    }

    @Override
    public int updateFile(File file) {
        return fileMapper.updateFile(file);
    }

    @Override
    public int deleteFile(String fileId) {
        if (fileId==null||fileId.equals(""))
            return 0;

        return fileMapper.deleteFile(fileId);
    }

    @Override
    public File searchFileById(String fileId) {
        if (fileId==null||fileId.equals(""))
            return null;
        return fileMapper.searchFileById(fileId);
    }

    @Override
    public ArrayList<File> searchAllFile(int pageNum,int pageSize) {
        PageHelper.startPage(pageNum,pageSize);

        return fileMapper.searchAllFileByUsername();
    }


    @Override
    public int addMenu(Menu menu) {
        if (menu == null) {
            return 0;
        }
        if (menu.getMenuId() == null || menu.getMenuId().equals("") ||
                menu.getMenuName() == null || menu.getMenuName().equals("") ||
                menu.getMenuUrl() == null || menu.getMenuUrl().equals(""))
            return 0;
        return menuMapper.addMenu(menu);
    }

    @Override
    public int updateMenu(Menu menu) {
        return menuMapper.updateMenu(menu);
    }

    @Override
    public int deleteMenu(String menuId) {
        if (menuId==null||menuId.equals(""))
            return 0;
        return menuMapper.deleteMenu(menuId);
    }

    @Override
    public Menu searchMenuById(String menuId) {
        if (menuId==null||menuId.equals(""))
            return null;
        return menuMapper.searchMenuById(menuId);
    }

    @Override
    public ArrayList<Menu> searchAllMenu(int pageNum,int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return menuMapper.searchAllMenuByUsername();
    }


    @Override
    public int addUser(User user) {
       return 0;
    }

    @Override
    public int updateUser(User user) {
        return userMapper.updateUser(user);
    }

    @Override
    public int deleteUser(String id) {
        if (id==null||id.equals(""))
            return 0;
        return userMapper.deleteUser(id);
    }

    @Override
    public User searchUserById(String id) {
        if (id==null||id.equals(""))
            return null;
        return userMapper.searchUserById(id);
    }
    @Override
    public HashMap<String,String> searchUserByUsername(String username) {
        if (username==null||username.equals(""))
            return null;
        return userMapper.searchUserByUsername(username);
    }
    @Override
    public ArrayList<User> searchAllUserByUsername(String username,int pageNum,int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return userMapper.searchAllUserByUsername(username);
    }


    @Override
    public int addBackground(Background background) {
        return 0;
    }

    @Override
    public int updateBackground(Background background) {
        return backgroundMapper.updateBackground(background);
    }

    @Override
    public int deleteBackground(String id) {
        if (id==null||id.equals(""))
            return 0;
        return backgroundMapper.deleteBackground(id);
    }

    @Override
    public HashMap<String,String> searchBackgroundByUsername(String username) {
        if (username==null||username.equals(""))
            return null;
        return backgroundMapper.searchBackgroundByUsername(username);
    }

    @Override
    public HashMap<String, String> searchBackgroundById(String id) {
        if (id==null||id.equals(""))
            return null;
        return backgroundMapper.searchBackgroundById(id);
    }

    @Override
    public ArrayList<HashMap<String, String>> searchAllBackgroundByUsername(String username,int pageNum,int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        Page<HashMap<String, String>> hashMaps = backgroundMapper.searchAllBackgroundByUsername(username);
        List<HashMap<String, String>> result = hashMaps.getResult();
        for (HashMap<String,String> r : result)
            System.out.println(r);
        System.out.println();
        System.out.println("data"+backgroundMapper.searchAllBackgroundByUsername(username));
        return backgroundMapper.searchAllBackgroundByUsername(username);
    }
}
