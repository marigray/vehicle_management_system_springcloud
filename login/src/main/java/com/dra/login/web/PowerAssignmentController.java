package com.dra.login.web;


import com.dra.login.exception.ParamException;
import com.dra.login.service.PowerAssignmentService;
import com.dra.utils.ResponseFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 红色
 */
@RestController
@RequestMapping("/pas")

public class PowerAssignmentController {

    @Resource
    private PowerAssignmentService powerAssignmentService;

    private final Object success = new ResponseFormat().getSuccess("success", 250, null);

    private final ResponseFormat successData = new ResponseFormat();



    private void isError(int i) throws ParamException {
        if (i==0)
            throw new ParamException("参数有误或数据重复",400);
    }

    @RequestMapping(value = "/addRoleToUser.do")
    public Object addRoleToUser(String id, String roleId)  throws ParamException {
        int i = powerAssignmentService.addRoleToUser(id, roleId);
        isError(i);
        return success;
    }

    @RequestMapping(value = "/addPowerToRole.do")
    public Object addPowerToRole(String roleId, String powerId) throws ParamException  {
        int i = powerAssignmentService.addPowerToRole(roleId, powerId);
        isError(i);
        return success;
    }

    @RequestMapping(value = "/addElementToPower.do")
    public Object addElementToPower(String powerId, String elementId) throws ParamException  {
        int i = powerAssignmentService.addElementToPower(powerId, elementId);
        isError(i);
        return success;
    }

    @RequestMapping(value = "/addFileToPower.do")
    public Object addFileToPower(String powerId, String fileId)  throws ParamException {
        int i = powerAssignmentService.addFileToPower(powerId, fileId);
        isError(i);
        return success;
    }

    @RequestMapping(value = "/addMenuToPower.do")
    public Object addMenuToPower(String powerId, String menuId)  throws ParamException {
        int i = powerAssignmentService.addMenuToPower(powerId, menuId);
        isError(i);
        return success;
    }




    @RequestMapping(value = "/deleteRoleFromUser.do")
    public Object deleteRoleFromUser(String id, String roleId)  throws ParamException {
        int i = powerAssignmentService.deletePowerFromRole(id, roleId);
        isError(i);
        return success;
    }

    @RequestMapping(value = "/deletePowerFromRole.do")
    public Object deletePowerFromRole(String roleId, String powerId)  throws ParamException {
        System.out.println("into");
        int i = powerAssignmentService.deletePowerFromRole(roleId, powerId);
        isError(i);
        return success;
    }

    @RequestMapping(value = "/deleteElementFromRole.do")
    public Object deleteElementFromRole(String powerId, String elementId)  throws ParamException {
        int i = powerAssignmentService.deleteElementFromRole(powerId, elementId);
        isError(i);
        return success;
    }

    @RequestMapping(value = "/deleteFileFromRole.do")
    public Object deleteFileFromRole(String powerId, String fileId)  throws ParamException {
        int i = powerAssignmentService.deleteFileFromRole(powerId, fileId);
        isError(i);
        return success;
    }

    @RequestMapping(value = "/deleteMenuFromRole.do")
    public Object deleteMenuFromRole(String powerId, String menuId)  throws ParamException {
        int i = powerAssignmentService.deleteMenuFromRole(powerId, menuId);
        isError(i);
        return success;
    }





    @RequestMapping(value = "/searchRoleByUsername.do")
//    @User
    public Object searchRoleByUsername(String username, String type) {

        return successData.getSuccess("success",250,powerAssignmentService.searchRoleByUsername(username, type));
    }

    @RequestMapping(value = "/searchPowerByRoleId.do")
    public Object searchPowerByRoleId(String roleId) {
        return successData.getSuccess("success",250,powerAssignmentService.searchPowerByRoleId(roleId));
    }

    @RequestMapping(value = "/searchElementByPowerId.do")
    public Object searchElementByPowerId(String powerId) {
        return successData.getSuccess("success",250,powerAssignmentService.searchElementByPowerId(powerId));
    }

    @RequestMapping(value = "/searchFileByPowerId.do")
    public Object searchFileByPowerId(String powerId) {
        return successData.getSuccess("success",250,powerAssignmentService.searchFileByPowerId(powerId));
    }

    @RequestMapping(value = "/searchMenuByPowerId.do")
    public Object searchMenuByPowerId(String powerId) {
        return successData.getSuccess("success",250,powerAssignmentService.searchMenuByPowerId(powerId));
    }
}
