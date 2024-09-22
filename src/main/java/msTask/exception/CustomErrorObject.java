package msTask.exception;

import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class CustomErrorObject {

	private HttpStatus httpStatus;
	private String message;
	private List<String> errors;
	
	public CustomErrorObject(HttpStatus httpStatus, String message, List<String> errors) {
		super();
		this.httpStatus = httpStatus;
		this.message = message;
		this.errors = errors;
	}
	
	public CustomErrorObject(HttpStatus httpStatus, String message, String error) {
		super();
		this.httpStatus = httpStatus;
		this.message = message;
		this.errors = Collections.singletonList(error);
	}

    public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

    public void setMessage(String message) {
		this.message = message;
	}

    public void setErrors(List<String> errors) {
		this.errors = errors;
	}
}
