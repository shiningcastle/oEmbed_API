package com.example.demo.exception;

import com.example.demo.dto.ResponseDto;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

	// 모든 에러의 메세지 전송
	@ExceptionHandler({ Exception.class })
	public ResponseDto exceptionResponse(Exception e) {
		ResponseDto responseDto = new ResponseDto("err", e.getMessage());
		return responseDto;
	}
}