package com.dra.pojo.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthMessage {
    private HttpServletRequest request;
    private HttpServletResponse response;
    private boolean isPublic;
    private boolean isUser;
}
