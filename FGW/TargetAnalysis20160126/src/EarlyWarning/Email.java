package EarlyWarning;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;



public class Email {
	final Properties Pro = new Properties();
	public Email(){
	}
	
	public void set() throws MessagingException{
		
	}

	public void sendMessage(String emailAddress,String Message)throws IOException, MessagingException{
		
		Pro.setProperty("mail.transport.protocol", "smtp");  
		//表示SMTP发送邮件，需要身份验证
		Pro.put("mail.smtp.auth", "true");
		Pro.put("mail.smtp.host", "smtp.163.com");
		//发件人的帐号
		Pro.put("mail.user", "liguoyu_a@163.com");
		Pro.put("mail.password", "liguoyu123");
		Authenticator auth = new Authenticator() {
			@Override
            protected PasswordAuthentication getPasswordAuthentication() {
                // 用户名、密码
                String userName = Pro.getProperty("mail.user");
                String password = Pro.getProperty("mail.password");
                return new PasswordAuthentication(userName, password);
            }
		};
		// 使用环境属性和授权信息，创建邮件会话
        Session mailSession = Session.getInstance(Pro, auth);
        //mailSession.setDebug(true);
        // 创建邮件消息
        MimeMessage message = new MimeMessage(mailSession);
        // 设置发件人
        InternetAddress form = new InternetAddress(Pro.getProperty("mail.user"));
        message.setFrom(form);
		// 设置收件人
        InternetAddress to = new InternetAddress(emailAddress);
        message.setRecipient(RecipientType.TO, to);
        // 设置密送，其他的收件人不能看到密送的邮件地址
        InternetAddress bcc = new InternetAddress("aaaaa@163.com");
        message.setRecipient(RecipientType.CC, bcc);

        // 设置邮件标题
        message.setSubject("预警邮件");

        // 设置邮件的内容体
        message.setContent(Message, "text/html;charset=UTF-8");

        // 发送邮件
        Transport.send(message);
	}
	
	public static void main(String[] args)throws IOException, MessagingException{
		System.out.print("start......");
		long Start = System.currentTimeMillis();
		Email email = new Email();
		for(int i = 0;i < 100 ;i++){
			email.sendMessage("958186555@qq.com","Hello");
		}
		
		long End = System.currentTimeMillis();
		System.out.print("finished!timeout:"+(End - Start)/1000+"s");
	}
	
}
