package com.wang.vire.web;

import com.github.pagehelper.Page;
import com.wang.vire.exception.ParamException;
import com.wang.vire.pojo.FormatData;
import com.wang.vire.pojo.Repair;
import com.wang.vire.service.RepairService;
import com.wang.vire.utils.WebCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author wang
 * @Data 2022/2/15 21:25
 * @Description
 */
@RestController
@RequestMapping("/repair")
public class RepairController {
    @Autowired
    RepairService repairService;

    /**
     * 添加维修信息表
     * @param repair 维修信息表对象（除去createTime）
     * @return 是否成功信息
     */
    @RequestMapping("/add.do")
    public FormatData<Object> addRepair(@RequestBody(required=false) Repair repair)throws ParamException{
        int i=repairService.addRepair(repair);
        WebCheck.isError(i);
        return new FormatData<>(i);
    }

    /**
     * 删除维修信息表
     * @param repairId 维修信息表ID
     * @return 是否成功信息
     */
    @RequestMapping("/del.do")
    public FormatData<Object> delRepair(String repairId)throws ParamException{
        int i=repairService.delRepair(repairId);
        WebCheck.isError(i);
        return new FormatData<>(i);
    }

    /**
     * 修改维修信息表信息
     * @param repair 维修信息表对象
     * @return 是否成功信息
     */
    @RequestMapping("/update.do")
    public FormatData<Object> updateRepair(@RequestBody(required=false) Repair repair)throws ParamException{
        int i=repairService.updateRepair(repair);
        WebCheck.isError(i);
        return new FormatData<>(i);
    }

    /**
     * 根据维修信息表ID查询维修信息表
     * @param repairId 维修信息表ID
     * @return 对应的维修信息表
     */
    @RequestMapping("/queryById.do")
    public FormatData<Object> queryRepairById(String repairId) {
        Repair repair = repairService.queryRepairById(repairId);
        return new FormatData<>(repair);
    }
    /**
     * 根据维修申请表ID查询维修信息表
     * @param applyId 维修申请表ID
     * @return 对应的维修信息表
     */
    @RequestMapping("/queryByApplyId.do")
    public FormatData<Object> queryByApplyId(String applyId) {
        Page<Repair> repairs = repairService.queryRepairByApplyId(applyId);
        return new FormatData<>(repairs);
    }

    /**
     * 根据汽车ID查询维修信息表信息
     * @param carId 车辆ID
     * @return 该车辆对应的所有维修信息表
     */
    @RequestMapping("/queryByCar.do")
    public FormatData<Object> queryRepairApplyByCar(String carId) {
        Page<Repair> repairs = repairService.queryRepairByCarId(carId);
        return new FormatData<>(repairs);
    }

    /**
     * 根据用户ID查询维修信息表信息
     * @param repairerId 维修人ID
     * @return 该用户所对应的所有维修信息表
     */
    @RequestMapping("/queryByUser.do")
    public FormatData<Object> queryByUser(String repairerId) {
        Page<Repair> repairs = repairService.queryRepairByUserId(repairerId);
        return new FormatData<>(repairs);
    }

    /**
     * 查询所有维修信息表
     * @param pageNum 页数
     * @param pageSize 每页大小
     * @return 全部维修信息表
     */
    @RequestMapping("/queryAll.do")
    public FormatData<Object> queryAllApplication(@RequestParam("pageNum") int pageNum,
                                                  @RequestParam("pageSize")int pageSize) throws ParamException {
        Page<Repair> repairs = repairService.queryAllRepair(pageNum,pageSize);
        //System.out.println(repairApplies);
        return new FormatData<>(repairs);
    }
}
