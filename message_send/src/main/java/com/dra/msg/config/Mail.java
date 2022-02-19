package com.dra.msg.config;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

@Component
@ConfigurationProperties(prefix = "mymail")
@Data
@Slf4j
public class Mail {

    //发件人地址
    private String senderAddress;
    //发件人账户名
    private String senderAccount;
    //发件人账户密码
    private String senderPassword;

    public void send(String msgStr,String subject,String recipientAddress) throws Exception{
        System.out.println("senderAddress:"+senderAddress);
        System.out.println("senderAccount:"+senderAccount);
        System.out.println("senderPassword:"+senderPassword);
        Properties props = new Properties();
        //设置邮件地址
        props.put("mail.smtp.host", "smtp.163.com");
        //开启认证
        props.put("mail.smtp.auth", "true");
        Session session = Session.getDefaultInstance(props, null);
        Transport transport = session.getTransport();
        //授权码
        transport.connect(senderAddress, senderPassword);
        //创建邮件消息
        MimeMessage msg = new MimeMessage(session);
        msg.setSentDate(new Date());
        //邮件发送人
        InternetAddress fromAddress = new InternetAddress(senderAddress, senderAccount);
        msg.setFrom(fromAddress);
        //邮件接收人
        InternetAddress[] toAddress = new InternetAddress[]{new InternetAddress(recipientAddress)};
        msg.setRecipients(Message.RecipientType.TO, toAddress);
        //邮件主题
        msg.setSubject(subject, "UTF-8");
        //邮件内容和格式
        msg.setContent(msgStr, "text/html;charset=UTF-8");
        msg.saveChanges();
        //发送
        transport.sendMessage(msg,toAddress);
    }
    public boolean send1(String msgStr, String subject, String[] recipientAddress){
        // 发送email
        HtmlEmail email = new HtmlEmail();
        try {
            // 这里是SMTP发送服务器的名字：163的如下："smtp.163.com"
//            email.setSSLOnConnect(false);
            email.setHostName("smtp.qq.com");
            email.setSmtpPort(587);
            // 字符编码集的设置
            email.setCharset("UTF-8");
            // 收件人的邮箱
            email.addTo(recipientAddress);
            // 发送人的邮箱
            email.setFrom(senderAddress, senderAccount);
            // 如果需要认证信息的话，设置认证：用户名-密码。分别为发件人在邮件服务器上的注册名称和密码
            email.setAuthentication(senderAddress, senderPassword);
            // 要发送的邮件主题
            email.setSubject(subject);
            // 要发送的信息，由于使用了HtmlEmail，可以在邮件内容中使用HTML标签
            email.setMsg(msgStr);
            email.send();
            if (log.isDebugEnabled()) {
                log.debug(senderAddress + " 发送邮件到 " + recipientAddress);
            }
            return true;
        } catch (EmailException e) {
            log.error(senderAddress + " 发送邮件到 " + recipientAddress
                    + " 失败",e);
            return false;
        }

    }

    public boolean send1(String msgStr, String subject, String recipientAddress){
        String[] str = {recipientAddress};
        return send1(msgStr,subject,str);
    }
    public static void main(String[] args) throws Exception{
//        new Mail().send1("测试","测试","draso@vip.qq.com");
//        new Mail().send1(mailData.toString(), "请注意以下车辆近期需要保养", mails);
//        new Mail().send1("", "请注意以下车辆近期需要保养", new String[]{"draso@vip.qq.com"});
    }
}
