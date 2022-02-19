package com.dra.msg.service.impl;

import com.dra.msg.mapper.PushMailMapper;
import com.dra.msg.service.PushMailService;
import com.dra.pojo.msg.PushMail;
import com.github.pagehelper.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PushMailServiceImpl implements PushMailService {
    @Resource
    private PushMailMapper pushMailMapper;


    @Override
    public int addPushMail(PushMail pushMail) {
        if (pushMail.getMail()==null||pushMail.getMail().equals(""))
            return 0;
        return pushMailMapper.addPushMail(pushMail);
    }

    @Override
    public int delPushMail(String id) {
        if (id==null||id.equals(""))
            return 0;
        return pushMailMapper.delPushMail(id);
    }

    @Override
    public int updatePushMail(PushMail pushMail) {
        return pushMailMapper.updatePushMail(pushMail);
    }

    @Override
    public Page<PushMail> selectAll() {
        return pushMailMapper.selectAll();
    }
}
