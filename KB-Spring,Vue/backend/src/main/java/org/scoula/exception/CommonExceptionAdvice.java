package org.scoula.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import lombok.extern.log4j.Log4j2;

@Log4j2
@ControllerAdvice
@Order(1)
public class CommonExceptionAdvice {
	// @ExceptionHandler(Exception.class)
	// public String except(Exception ex, Model model) {
	// 	log.error("Exception ......." + ex.getMessage());
	// 	model.addAttribute("exception", ex);
	// 	log.error(model);
	// 	return "error_page";
	// }
	@ExceptionHandler(NoHandlerFoundException.class)
	public String handle404(NoHandlerFoundException ex) {
		return "/resources/index.html";
	}
}
