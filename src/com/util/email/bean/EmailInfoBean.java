package com.util.email.bean;

import org.apache.commons.lang.StringUtils;

import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class EmailInfoBean implements java.io.Serializable {
    private String sendNickName;     //发件人别名
    private String sendAddress;	//发件人邮箱
    private String sendPassword;	//发件人邮件密码
    private String sendSMTPHost;	//发件人邮箱的 SMTP服务器地址

    private List<InternetAddress> toRecipient; //收件人邮箱
    private List<InternetAddress> ccRecipient;//抄送人邮箱
    private List<InternetAddress> bccRecipient;//密送人邮箱

    private String mailTopic;        // 邮件主题
    private String mailText;         // 邮件正文
    private String mailTextContentType; //邮件正文的读取方式，默认是text/html;charset=UTF-8
    private List<AttachmentBean> attachmentList;  // 附件

    public InternetAddress[] getToRecipient() {
        return null == toRecipient || toRecipient.size() == 0 ? null : (InternetAddress[])toRecipient.toArray(new InternetAddress[toRecipient.size()]);
    }

    public InternetAddress[] getCcRecipient() {
        return null == ccRecipient || ccRecipient.size() == 0 ? null : (InternetAddress[])ccRecipient.toArray(new InternetAddress[ccRecipient.size()]);
    }

    public InternetAddress[] getBccRecipient() {
        return null == bccRecipient || bccRecipient.size() == 0 ? null : (InternetAddress[])bccRecipient.toArray(new InternetAddress[bccRecipient.size()]);
    }

    public Session getSession() {
        if (StringUtils.isBlank(sendSMTPHost)) return null;
        //1.创建参数配置, 用于连接邮件服务器的参数配置
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp"); //使用的协议（JavaMail规范要求）
        props.setProperty("mail.smtp.host", sendSMTPHost); //发件人的邮箱的 SMTP 服务器地址
        props.setProperty("mail.smtp.auth", "true"); //需要请求认证
        //2.根据配置创建会话对象, 用于和邮件服务器交互
        return Session.getDefaultInstance(props);
    }

    public EmailInfoBean(String sendNickName, String sendAddress, String sendPassword, String sendSMTPHost){
        this.sendNickName = sendNickName;
        this.sendAddress = sendAddress;
        this.sendPassword = sendPassword;
        this.sendSMTPHost = sendSMTPHost;
    };

//    public EmailInfoBean(String toNickName, String toAddress, String mailTopic, String mailText){
//        this.toNickName = toNickName;
//        this.toAddress = toAddress;
//        this.mailTopic = mailTopic;
//        this.mailText = mailText;
//    };

    public void addDiskFileAttachment(String filePath, String fileName) {
        AttachmentBean attachmentBean = new AttachmentBean(filePath, fileName);
        if (null == attachmentList) attachmentList = new ArrayList<AttachmentBean>();
        attachmentList.add(attachmentBean);
    }

    public void addURLFileAttachment(String fileURL, String fileName) throws Exception {
        AttachmentBean attachmentBean = new AttachmentBean(fileName, new URL(fileURL));
        if (null == attachmentList) attachmentList = new ArrayList<AttachmentBean>();
        this.attachmentList.add(attachmentBean);
    }

    public void addInputStreamAttachment(InputStream inputStream, String contentType, String fileName) throws Exception {
        AttachmentBean attachmentBean = new AttachmentBean(fileName, inputStream, contentType);
        if (null == attachmentList) attachmentList = new ArrayList<AttachmentBean>();
        this.attachmentList.add(attachmentBean);
    }

    public String getSendNickName() {
        return sendNickName;
    }

    public String getSendAddress() {
        return sendAddress;
    }

    public String getSendPassword() {
        return sendPassword;
    }

    public String getSendSMTPHost() {
        return sendSMTPHost;
    }

    public void setToAddress(String toAddress) throws UnsupportedEncodingException {
        this.toRecipient = setInternetAddress(toRecipient, toAddress);
    }

    private List<InternetAddress> setInternetAddress(List<InternetAddress> interAddrList, String addr) throws UnsupportedEncodingException{
        if (null == interAddrList) interAddrList = new ArrayList<InternetAddress>();
        String[] arr = addr.split(";");
        for (String recip : arr) {
            InternetAddress recipientAddr = new InternetAddress();
            if (StringUtils.isNotBlank(recip)) {
                String[] rArr = recip.split("\\|");
                if (rArr.length > 1) {
                    recipientAddr.setAddress(rArr[1]);
                    recipientAddr.setPersonal(rArr[0], "UTF-8");
                } else  recipientAddr.setAddress(rArr[0]);;
            }
            interAddrList.add(recipientAddr);
        }
        return interAddrList;
    }

    public void setCcAddress(String ccAddress) throws UnsupportedEncodingException {
        this.ccRecipient = setInternetAddress(ccRecipient, ccAddress);

    }

    public void setBccAddress(String bccAddress) throws UnsupportedEncodingException{
        this.bccRecipient = setInternetAddress(bccRecipient, bccAddress);
    }

    public String getMailTopic() {
        return mailTopic;
    }

    public void setMailTopic(String mailTopic) {
        this.mailTopic = mailTopic;
    }

    public String getMailText() {
        return mailText;
    }

    public void setMailText(String mailText) {
        this.mailText = mailText;
    }

    public List<AttachmentBean> getAttachmentList() {
        return attachmentList;
    }

    public String getMailTextContentType() {
        if(StringUtils.isNotBlank(mailTextContentType)){
            return mailTextContentType;
        }
        return "text/html;charset=UTF-8";
    }

    public void setMailTextContentType(String mailTextContentType) {
        this.mailTextContentType = mailTextContentType;
    }

}