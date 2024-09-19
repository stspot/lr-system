package msTask.service.impl;

import msTask.models.EmailRequestModel;
import msTask.service.EmailService;

import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
	
	private final Environment environment;

	private final JavaMailSender mailSender;

	public EmailServiceImpl(JavaMailSender mailSender, Environment environment) {
		this.environment = environment;
		this.mailSender = mailSender;
	}

	@Async
	@Override
	public void sendSimpleEmail(String to, String subject, String text) {
		
		String emailOfSender = this.environment.getProperty("email.sender");
		
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);
		message.setFrom(emailOfSender);
		//TODO...
		mailSender.send(message);
	}

	@Async
	@Override
	public void sendSimpleEmail(EmailRequestModel emailRequestModel) {
		this.sendSimpleEmail(emailRequestModel.getTo(), emailRequestModel.getSubject(), emailRequestModel.getText());
	}
}
