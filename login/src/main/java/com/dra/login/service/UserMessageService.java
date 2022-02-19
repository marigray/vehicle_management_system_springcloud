package com.dra.login.service;

import com.dra.pojo.login.UserMessage;

public interface UserMessageService {
    UserMessage findUser(String id, String type);
}
