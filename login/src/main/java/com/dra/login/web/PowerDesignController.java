package com.dra.login.web;


import com.dra.login.exception.ParamException;
import com.dra.login.service.PowerDesignService;
import com.dra.pojo.login.*;
import com.dra.utils.ResponseFormat;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * 绿色
 */
@RestController
@RequestMapping("/pedit")
public class PowerDesignController {
    @Resource
    private PowerDesignService powerDesignService;

    private final Object success = new ResponseFormat().getSuccess("success", 250, null);

    private final ResponseFormat successData = new ResponseFormat();




    @RequestMapping(value = "/addrole.do",method = RequestMethod.POST)
    public Object addRole(@RequestBody Role role) throws ParamException {
        int i = powerDesignService.addRole(role);
        if (i == 0)
            throw new ParamException();
        return success;
    }

    @RequestMapping(value = "/uprole.do",method = RequestMethod.POST)
    public Object updateRole(Role role) throws ParamException {
        int i = powerDesignService.updateRole(role);
        if (i == 0)
            throw new ParamException();
        return success;
    }

    @RequestMapping(value = "/delrole.do")
    public Object deleteRoleRole(String roleId) throws ParamException {
        int i = powerDesignService.deleteRoleRole(roleId);
        if (i == 0)
            throw new ParamException();
        return success;
    }

    @RequestMapping(value = "/getrole.do")
    public Object searchRoleById(String roleId) throws ParamException {
        Role role = powerDesignService.searchRoleById(roleId);
        return successData.getSuccess("success", 250, role);
    }

    @RequestMapping(value = "/getsrole.do")
    public Object searchAllRole(int pageNum, int pageSize) {

        ArrayList<Role> roles = powerDesignService.searchAllRole(pageNum, pageSize);
        System.out.println(roles);
        return successData.getSuccess("success", 250, roles);
    }


    @RequestMapping(value = "/addpower.do",method = RequestMethod.POST)
    public Object addPower(@RequestBody Power power) throws ParamException {
        int i = powerDesignService.addPower(power);
        if (i == 0)
            throw new ParamException();
        return success;
    }

    @RequestMapping(value = "/uppower.do",method = RequestMethod.POST)
    public Object updatePower(Power power) throws ParamException {
        int i = powerDesignService.updatePower(power);
        if (i == 0)
            throw new ParamException();
        return success;
    }

    @RequestMapping(value = "/delpower.do")
    public Object deletePower(String powerId) throws ParamException {
        int i = powerDesignService.deletePower(powerId);
        if (i == 0)
            throw new ParamException();
        return success;
    }

    @RequestMapping(value = "/getpower.do")
    public Object searchPowerById(String powerId) throws ParamException {
        Power power = powerDesignService.searchPowerById(powerId);
        return successData.getSuccess("success", 250, power);
    }

    @RequestMapping(value = "/getspower.do")
    public Object searchAllPower(int pageNum, int pageSize) {
        ArrayList<Power> powers = powerDesignService.searchAllPower(pageNum, pageSize);
        return successData.getSuccess("success", 250, powers);
    }


    @RequestMapping(value = "/addele.do",method = RequestMethod.POST)
    public Object addElement(@RequestBody Element element) throws ParamException {
        int i = powerDesignService.addElement(element);
        if (i == 0) {
            throw new ParamException();
        }
        return success;
    }

    @RequestMapping(value = "/upele.do",method = RequestMethod.POST)
    public Object updateElement(@RequestBody Element element) throws ParamException {
        int i = powerDesignService.updateElement(element);
        if (i == 0) {
            throw new ParamException();
        }
        return success;
    }

    @RequestMapping(value = "/delele.do")
    public Object deleteElement(String elementId) throws ParamException {
        int i = powerDesignService.deleteElement(elementId);
        if (i == 0) {
            throw new ParamException();
        }
        return success;
    }

    @RequestMapping(value = "/getele.do")
    public Object searchElementById(String elementId) throws ParamException {
        Element element = powerDesignService.searchElementById(elementId);
        return successData.getSuccess("success", 250, element);
    }

    @RequestMapping(value = "/getsele.do")
    public Object searchAllElement(int pageNum, int pageSize) {
        ArrayList<Element> elements = powerDesignService.searchAllElement(pageNum, pageSize);
        return successData.getSuccess("success", 250, elements);
    }


    @RequestMapping(value = "/addfile.do",method = RequestMethod.POST)
    public Object addFile(@RequestBody File file) throws ParamException {
        int i = powerDesignService.addFile(file);
        if (i == 0)
            throw new ParamException();
        return success;
    }

    @RequestMapping(value = "/upfile.do",method = RequestMethod.POST)
    public Object updateFile(@RequestBody File file) throws ParamException {
        int i = powerDesignService.updateFile(file);
        if (i == 0)
            throw new ParamException();
        return success;
    }

    @RequestMapping(value = "/delfile.do")
    public Object deleteFile(String fileId) throws ParamException {
        int i = powerDesignService.deleteFile(fileId);
        if (i == 0)
            throw new ParamException();
        return success;
    }

    @RequestMapping(value = "/getfile.do")
    public Object searchFileById(String fileId) throws ParamException {
        File file = powerDesignService.searchFileById(fileId);
        return successData.getSuccess("success", 250, file);
    }

    @RequestMapping(value = "/getsfile.do")
    public Object searchAllFile(int pageNum, int pageSize) {
        ArrayList<File> files = powerDesignService.searchAllFile(pageNum, pageSize);
        return successData.getSuccess("success", 250, files);
    }


    @RequestMapping(value = "/addmenu.do",method = RequestMethod.POST)
    public Object addMenu(@RequestBody Menu menu) throws ParamException {
        int i = powerDesignService.addMenu(menu);
        if (i == 0)
            throw new ParamException();
        return success;
    }

    @RequestMapping(value = "/upmenu.do",method = RequestMethod.POST)
    public Object updateMenu(@RequestBody Menu menu) throws ParamException {
        int i = powerDesignService.updateMenu(menu);
        if (i == 0)
            throw new ParamException();
        return success;
    }

    @RequestMapping(value = "/delmenu.do")
    public Object deleteMenu(String menuId) throws ParamException {
        int i = powerDesignService.deleteMenu(menuId);
        if (i == 0)
            throw new ParamException();
        return success;
    }

    @RequestMapping(value = "/getmenu.do")
    public Object searchMenuById(String menuId) throws ParamException {
        Menu menu = powerDesignService.searchMenuById(menuId);
        return successData.getSuccess("success", 250, menu);
    }

    @RequestMapping(value = "/getsmenu.do")
    public Object searchAllMenu(int pageNum, int pageSize) {
        ArrayList<Menu> menus = powerDesignService.searchAllMenu(pageNum, pageSize);
        return successData.getSuccess("success", 250, menus);
    }



    @RequestMapping(value = "/upuser.do",method = RequestMethod.POST)
//    @com.dra.annotation.User
    public Object updateUser(@RequestBody User user) throws ParamException {
        int i = powerDesignService.updateUser(user);
        if (i == 0)
            throw new ParamException();
        return success;
    }

    @RequestMapping(value = "/deluser.do")
    public Object deleteUser(String id) throws ParamException {
        int i = powerDesignService.deleteUser(id);
        if (i == 0)
            throw new ParamException();
        return success;
    }

    @RequestMapping(value = "/getuser.do")
    public Object searchUserById(String id) throws ParamException {
        User user = powerDesignService.searchUserById(id);
        return successData.getSuccess("success", 250, user);
    }
    @RequestMapping(value = "/getusername.do")
//    @com.dra.annotation.User
    public Object searchUserByUsername(String username) throws ParamException {
        HashMap<String, String> user = powerDesignService.searchUserByUsername(username);
        return successData.getSuccess("success", 250, user);
    }

    @RequestMapping(value = "/getsuser.do")
    public Object searchAllUserByUsername(String username, int pageNum, int pageSize) {
        ArrayList<User> users = powerDesignService.searchAllUserByUsername(username, pageNum, pageSize);
        return successData.getSuccess("success", 250, users);
    }


    @RequestMapping(value = "/upbg.do",method = RequestMethod.POST)
    public Object updateBackground(@RequestBody Background background) throws ParamException {
        int i = powerDesignService.updateBackground(background);
        if (i == 0)
            throw new ParamException();
        return success;
    }

    @RequestMapping(value = "/delbg.do")
    public Object deleteBackground(String id) throws ParamException {
        int i = powerDesignService.deleteBackground(id);
        if (i == 0)
            throw new ParamException();
        return success;
    }

    @RequestMapping(value = "/getbg.do")
    public Object searchBackgroundById(String id) throws ParamException {
        HashMap<String, String> stringStringHashMap = powerDesignService.searchBackgroundById(id);
        return successData.getSuccess("success", 250, stringStringHashMap);
    }

    @RequestMapping(value = "/getbgname.do")
    public Object searchBackgroundByUsername(String username) throws ParamException {
        HashMap<String, String> stringStringHashMap = powerDesignService.searchBackgroundByUsername(username);
        return successData.getSuccess("success", 250, stringStringHashMap);
    }

    @RequestMapping(value = "/getsbg.do")
    public Object searchAllBackgroundByUsername(String username, int pageNum, int pageSize) {
        ArrayList<HashMap<String, String>> hashMaps = powerDesignService.searchAllBackgroundByUsername(username, pageNum, pageSize);
        return successData.getSuccess("success", 250, hashMaps);
    }
}
