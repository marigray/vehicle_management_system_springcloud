package com.dra.msg.service;

import com.dra.pojo.msg.PushMail;
import com.github.pagehelper.Page;

public interface PushMailService {

    int addPushMail(PushMail pushMail);

    int delPushMail(String id);

    int updatePushMail(PushMail pushMail);

    Page<PushMail> selectAll();
}
