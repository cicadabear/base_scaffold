package cn.iutils.common.utils;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;

import java.net.URL;

/**
 * 邮件工具
 * @author iutils.cn
 * @version 1.0
 */
public class JMailUtils extends BaseUtils {

    /**
     * 测试
     * @param args
     */
    public static void main(String[] args){
        //JMailUtils.sendHtmlEmail(new JMailUtils.Email(JMailUtils.smtp163,"hc279520@163.com","123456","305627617@qq.com","hc279520@163.com","程序员工具","来自程序员工具测试信","你好<br> 程序员工具"));
    }

    /**
     * 发送带附件的邮件
     * @param email 邮件对象
     * @param isURL 是否远程
     * @return
     */
    public static boolean sendMultiPartEmail(Email email,boolean isURL){
        boolean flag = false;
        try{
            MultiPartEmail multiPartEmail = new MultiPartEmail();
            multiPartEmail.setHostName(email.getHostName());
            multiPartEmail.setAuthentication(email.getUsername(), email.getPassword());//邮件服务器验证：用户名/密码
            multiPartEmail.setCharset("UTF-8");// 必须放在前面，否则乱码
            multiPartEmail.addTo(email.getTo());
            multiPartEmail.setFrom(email.getFrom(), email.getFromName());
            multiPartEmail.setSubject(email.getSubject());
            multiPartEmail.setMsg(email.getMsg());

            EmailAttachment attachment = new EmailAttachment();
            if(isURL){
                attachment.setURL(new URL(email.getAttachmentPath()));//远程文件
            }else{
                attachment.setPath(email.getAttachmentPath());// 本地文件
            }
            attachment.setDisposition(EmailAttachment.ATTACHMENT);
            attachment.setDescription(email.getAttachmentDescription());
            attachment.setName(email.getAttachmentName());

            multiPartEmail.attach(attachment);
            multiPartEmail.send();
        }catch (Exception e){
            logger.debug("邮件发送失败",e.fillInStackTrace());
        }
        return flag;
    }

    /**
     * 发送Html格式邮件
     * @param email 邮件对象
     * @return
     */
    public static boolean sendHtmlEmail(Email email){
        boolean flag = false;
        try {
            HtmlEmail htmlEmail = new HtmlEmail();
            htmlEmail.setHostName(email.getHostName());
            htmlEmail.setAuthentication(email.getUsername(), email.getPassword());//邮件服务器验证：用户名/密码
            htmlEmail.setCharset("UTF-8");// 必须放在前面，否则乱码
            htmlEmail.addTo(email.getTo());
            htmlEmail.setFrom(email.getFrom(), email.getFromName());
            htmlEmail.setSubject(email.getSubject());
            htmlEmail.setMsg(email.getMsg());
            htmlEmail.send();
            flag = true;
        }catch (Exception e) {
            logger.debug("邮件发送失败",e.fillInStackTrace());
        }
        return flag;
    }

    /**
     * 发送简单的邮件
     * @param email 邮件对象
     * @return
     */
    public static boolean sendSimpleMail(Email email){
        boolean flag = false;
        try {
            SimpleEmail simpleEmail = new SimpleEmail();
            simpleEmail.setHostName(email.getHostName());
            simpleEmail.setAuthentication(email.getUsername(), email.getPassword());//邮件服务器验证：用户名/密码
            simpleEmail.setCharset("UTF-8");// 必须放在前面，否则乱码
            simpleEmail.addTo(email.getTo());
            simpleEmail.setFrom(email.getFrom(), email.getFromName());
            simpleEmail.setSubject(email.getSubject());
            simpleEmail.setMsg(email.getMsg());
            simpleEmail.send();
            flag = true;
        } catch (Exception e) {
            logger.debug("邮件发送失败",e.fillInStackTrace());
        }
        return flag;
    }

    /**
     * QQ主机
     */
    public static final String smtpqq = "smtp.qq.com";
    /**
     * 163主机
     */
    public static final String smtp163 = "smtp.163.com";
    /**
     * 126主机
     */
    public static final String smtp126 = "smtp.126.com";
    /**
     * sina主机
     */
    public static final String smtpsina = "smtp.sina.com.cn";
    /**
     * yahoo主机
     */
    public static final String smtpyahoo = "smtp.mail.yahoo.com";


    /**
     * 邮件类
     */
    public static class Email{
        String hostName;//SMTP主机地址
        String username;//用户名
        String password;//密码
        String to;//收件人
        String from;//发件人
        String fromName;//发件人昵称
        String subject;//邮件主题
        String msg;//邮件内容

        String attachmentPath;//附件地址
        String attachmentName;//附件名称
        String attachmentDescription;//附件描述

        public Email(){}

        /**
         * @param hostName SMTP主机地址
         * @param username 用户名
         * @param password 密码
         * @param to 收件人
         * @param from 发件人
         * @param fromName 发件人昵称
         * @param subject 邮件主题
         * @param msg 邮件内容
         */
        public Email(String hostName,String username,String password,String to,String from,String fromName,String subject,String msg){
            this.hostName = hostName;
            this.username = username;
            this.password = password;
            this.to = to;
            this.from = from;
            this.fromName = fromName;
            this.subject = subject;
            this.msg = msg;
        }

        public String getHostName() {
            return hostName;
        }

        public void setHostName(String hostName) {
            this.hostName = hostName;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getTo() {
            return to;
        }

        public void setTo(String to) {
            this.to = to;
        }

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getFromName() {
            return fromName;
        }

        public void setFromName(String fromName) {
            this.fromName = fromName;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getAttachmentPath() {
            return attachmentPath;
        }

        public void setAttachmentPath(String attachmentPath) {
            this.attachmentPath = attachmentPath;
        }

        public String getAttachmentName() {
            return attachmentName;
        }

        public void setAttachmentName(String attachmentName) {
            this.attachmentName = attachmentName;
        }

        public String getAttachmentDescription() {
            return attachmentDescription;
        }

        public void setAttachmentDescription(String attachmentDescription) {
            this.attachmentDescription = attachmentDescription;
        }
    }

}
