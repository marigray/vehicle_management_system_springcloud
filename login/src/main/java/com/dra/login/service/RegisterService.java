package com.dra.login.service;


import com.dra.pojo.login.Background;
import com.dra.pojo.login.User;

public interface RegisterService {

    int registerUser(User user);
    int registerBackground(Background background);

}
