package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

public class MailConfig {
    private final Properties serverInfo; // 서버 정보
    private final Authenticator auth;    // 인증 정보
    private final String submitEmail = "inpo1231@gmail.com"; 
    private final String recieveEmail = "wlghks3812@gmail.com"; 

    public MailConfig() {
        // 구글 SMTP 서버 접속 정보
        serverInfo = new Properties();
        serverInfo.put("mail.smtp.host", "smtp.gmail.com");
        serverInfo.put("mail.smtp.port", "587");
        serverInfo.put("mail.smtp.auth", "true");
        serverInfo.put("mail.smtp.debug", "true");
        serverInfo.put("mail.smtp.starttls.enable", "true");

        // 사용자 인증 정보
        String user = "inpo1231@gmail.com";
        String password = "aywz alsz fwrg aevb";

        auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        };
    }

    public void sendEmail(Map<String, String> mailInfo, InputStream attachmentContent, String attachmentName) throws MessagingException, IOException {
    	if (mailInfo.get("details") == null || mailInfo.get("details").isEmpty()) {
            throw new IllegalArgumentException("Email details cannot be null or empty");
        }
        if (mailInfo.get("purpose") == null || mailInfo.get("purpose").isEmpty()) {
            throw new IllegalArgumentException("Email purpose cannot be null or empty");
        }
    	
    	Session session = Session.getInstance(serverInfo, auth);
        session.setDebug(true);

        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(submitEmail));     // 보내는 사람
        msg.addRecipient(Message.RecipientType.TO, new InternetAddress(recieveEmail));  // 받는 사람
        msg.setSubject(mailInfo.get("purpose") + " from " + mailInfo.get("userEmail"), "UTF-8");  // 제목에 사용자 이메일 포함
        
        MimeBodyPart textPart = new MimeBodyPart();
        textPart.setContent(mailInfo.get("details"), "text/plain; charset=UTF-8");

        MimeMultipart multipart = new MimeMultipart();
        multipart.addBodyPart(textPart);

        // 첨부 파일이 있는 경우 추가
        if (attachmentContent != null && attachmentName != null) {
            MimeBodyPart attachmentPart = new MimeBodyPart();
            DataSource source = new ByteArrayDataSource(attachmentContent, "application/octet-stream");
            attachmentPart.setDataHandler(new DataHandler(source));
            attachmentPart.setFileName(attachmentName);
            multipart.addBodyPart(attachmentPart);
        }

        msg.setContent(multipart);

        Transport.send(msg);
    }
}
