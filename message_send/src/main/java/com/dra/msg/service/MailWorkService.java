package com.dra.msg.service;

public interface MailWorkService {
    Object sendCheckCode(String recipientAddress) throws Exception;
}
