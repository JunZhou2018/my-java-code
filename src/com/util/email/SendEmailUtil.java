package com.util.email;

import com.util.email.bean.AttachmentBean;
import com.util.email.bean.EmailInfoBean;
import org.apache.commons.lang.StringUtils;

import javax.activation.DataHandler;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;
import java.util.Date;

public  class SendEmailUtil {
    public static boolean sendComplexMail(EmailInfoBean emailInfo) throws Exception {
        //1.校验发件人信息
        if (StringUtils.isBlank(emailInfo.getSendAddress()) || StringUtils.isBlank(emailInfo.getSendPassword())
                || StringUtils.isBlank(emailInfo.getSendSMTPHost()))
            return false;

        //2.校验收件人、主题
        if(null == emailInfo.getToRecipient() || StringUtils.isBlank(emailInfo.getMailTopic()))
            return false;

        if (null == emailInfo.getSession()) return false;

        Transport transport = null;
        boolean isSucc = false;
        try {
            Session session = emailInfo.getSession();
            //3.创建一封邮件
            MimeMessage mimeMessage = createMimeMessage(session, emailInfo);

            //4.根据Session获取邮箱传输对象
            transport = session.getTransport();

            //5.使用邮箱账号和密码连接邮箱服务器
            //这里认证的邮箱必须与 message 中的发件人邮箱一致，否则报错
            transport.connect(emailInfo.getSendAddress(), emailInfo.getSendPassword());

            //6.发送邮件，发到所有的收件地址，message.getAllRecipients()
            // 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
            transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());

            isSucc = true;
        } catch (Exception e){
            throw e;
        } finally {
            // 7. 关闭连接
            if (null != transport)  transport.close();
        }
        return isSucc;
    }

    private static MimeMessage createMimeMessage(Session session, EmailInfoBean emailInfo) throws Exception {
        //1.创建邮件对象
        MimeMessage message = new MimeMessage(session);
        //2.发件人
        message.setFrom(new InternetAddress(emailInfo.getSendAddress(), emailInfo.getSendNickName(), "UTF-8"));
        //3.收件人
        message.setRecipients(Message.RecipientType.TO, emailInfo.getToRecipient());
        //4.抄送人
        if (null != emailInfo.getCcRecipient())
            message.setRecipients(Message.RecipientType.CC, emailInfo.getCcRecipient());
        //5.密送人
        if (null != emailInfo.getBccRecipient())
            message.setRecipients(Message.RecipientType.BCC, emailInfo.getBccRecipient());
        //6.邮件主题
        message.setSubject(emailInfo.getMailTopic(), "UTF-8");

        MimeMultipart mm = new MimeMultipart();
        //7.创建文本节点
        MimeBodyPart text = new MimeBodyPart();
        //8.设置邮件正文
        text.setContent(emailInfo.getMailText(), emailInfo.getMailTextContentType());
        mm.addBodyPart(text);

        //9.创建附件节点,如果附件不为空则创建附件节点
        if (null != emailInfo.getAttachmentList()) {
            for (AttachmentBean attach : emailInfo.getAttachmentList()) {
                MimeBodyPart attachment = new MimeBodyPart();
                DataHandler dh = new DataHandler(attach.getAttachmentDs());
                attachment.setDataHandler(dh);	//将附件数据添加到“节点”
                attachment.setFileName(MimeUtility.encodeText(attach.getFileName())); //设置附件的文件名（需要编码）
                mm.addBodyPart(attachment); //如果有多个附件，可以创建多个多次添加
            }
        }

        //7.设置文本和附件的关系（合成一个大的混合“节点”）
        mm.setSubType("mixed"); //混合关系
        //8.设置整个邮件的关系（将最终的混合“节点”作为邮件的内容添加到邮件对象）
        message.setContent(mm);
        //9.设置发件时间
        message.setSentDate(new Date());
        //10.保存上面的所有设置
        message.saveChanges();
        return message;

    }

    public static void main(String[] args) throws Exception {
        EmailInfoBean emailInfo = new EmailInfoBean("嘿，阅读", "heiyuedu@126.com",
                "I2019666", "smtp.126.com");
        emailInfo.setToAddress("zhoujun8066@126.com");
        emailInfo.setMailTopic("测试");
        emailInfo.setMailText("aaaaaaaa");
        emailInfo.addURLFileAttachment("http://cpro.baidustatic.com/cpro/exp/closead/img/bd_logo.png",
                "百度LOGO.png");
//        CmsFileUtil.FileInfo fileInfo1 = CmsFileUtil.getDownloadUrl(
//                "20180604edda884ede1f4e769d4fafb068fab264", "group_apply_annex", "", "");
//        GetMethod method1 = HttpUtils.doGet(fileInfo1.getDownloadUrl(), "GBK");
//
//        emailInfo.addInputStreamAttachment(method1.getResponseBodyAsStream(),
//                method1.getResponseHeader("Content-Type").getValue(), fileInfo1.getFileName());
        //System.out.println(sendComplexMail(emailInfo));
        System.out.println("测试乱码");
    }
}
