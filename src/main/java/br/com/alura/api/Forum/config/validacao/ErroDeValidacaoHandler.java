package br.com.alura.api.Forum.config.validacao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErroDeValidacaoHandler {
	
	@Autowired
	private MessageSource MessageSource;

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<ErrorDeFormularioDto> handle(MethodArgumentNotValidException exception) {
		 List<ErrorDeFormularioDto> dto = new ArrayList<>();
		 
		 List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
		 fieldErrors.forEach(e -> {
			 String mensagem = MessageSource.getMessage(e,  LocaleContextHolder.getLocale());
			 ErrorDeFormularioDto erro = new ErrorDeFormularioDto(e.getField(), mensagem);
			 dto.add(erro);
		 });
		 
		 return dto;
	}
}
