package msTask.service.impl;

import msTask.models.EmailRequestModel;
import msTask.service.EmailService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

	private final JavaMailSender mailSender;

	public EmailServiceImpl(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	@Override
	public void sendSimpleEmail(String to, String subject, String text) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);
		message.setFrom("zstefchev2@gmail.com"); //FIXME ...
		mailSender.send(message);
	}

	@Override
	public void sendSimpleEmail(EmailRequestModel emailRequestModel) {
		this.sendSimpleEmail(emailRequestModel.getTo(), emailRequestModel.getSubject(), emailRequestModel.getText());
	}
}
