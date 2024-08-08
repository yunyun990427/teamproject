package org.spring.service;

import javax.mail.internet.MimeMessage;

import org.spring.domain.UserDTO;
import org.spring.persistence.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
	
	@Autowired
	private UserMapper userMapper;
	
    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String content) throws Exception {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "utf-8");

        messageHelper.setFrom("yi3976997@gmail.com"); // Change to your email
        messageHelper.setTo(to);
        messageHelper.setSubject(subject);
        messageHelper.setText(content);

        mailSender.send(message);
    }

	public UserDTO selectMember(String email) {
		return userMapper.getUserByEmail(email);
	}
	
	
}
