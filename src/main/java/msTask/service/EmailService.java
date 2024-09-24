package msTask.service;

import msTask.models.EmailModel;

public interface EmailService {

	void sendSimpleEmail(String to, String subject, String text);

	void sendSimpleEmail(EmailModel emailModel);

}
