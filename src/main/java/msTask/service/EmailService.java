package msTask.service;

import msTask.models.EmailRequestModel;

public interface EmailService {

	void sendSimpleEmail(String to, String subject, String text);

	void sendSimpleEmail(EmailRequestModel emailRequestModel);

}
