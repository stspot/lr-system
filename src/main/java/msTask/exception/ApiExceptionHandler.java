package msTask.exception;

import static msTask.constants.CommonConstants.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler({ Exception.class })
	public ResponseEntity<Object> handleAll(Exception exception, WebRequest request) {
		CustomErrorObject customErrorObject = new CustomErrorObject(HttpStatus.INTERNAL_SERVER_ERROR,
				exception.getLocalizedMessage(), COMMON_EXCEPTION_TEXT);
		return new ResponseEntity<Object>(customErrorObject, customErrorObject.getHttpStatus());
	}
}
