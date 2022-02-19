package com.dra.login.service;

public interface LoginService {



    String userLogin(String username, String password);

    String backgroundLogin(String username, String password);
}
