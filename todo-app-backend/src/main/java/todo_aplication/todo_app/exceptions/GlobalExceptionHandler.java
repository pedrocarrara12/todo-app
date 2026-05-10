package todo_aplication.todo_app.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TarefaNaoEncontradaException.class)
    public ResponseEntity<ApiError> handleTarefaNaoEncontrada(TarefaNaoEncontradaException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ApiError(exception.getMessage(), HttpStatus.NOT_FOUND.value(), LocalDateTime.now()));
    }

    @ExceptionHandler({ResponsavelException.class, IllegalArgumentException.class})
    public ResponseEntity<ApiError> handleRegraInvalida(RuntimeException exception) {
        return ResponseEntity
                .badRequest()
                .body(new ApiError(exception.getMessage(), HttpStatus.BAD_REQUEST.value(), LocalDateTime.now()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationError> handleValidacao(MethodArgumentNotValidException exception) {
        Map<String, String> errors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        fieldError -> fieldError.getField(),
                        fieldError -> fieldError.getDefaultMessage() == null
                                ? "Valor invalido"
                                : fieldError.getDefaultMessage(),
                        (first, second) -> first
                ));

        return ResponseEntity
                .badRequest()
                .body(new ValidationError("Erro de validacao", HttpStatus.BAD_REQUEST.value(), LocalDateTime.now(), errors));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiError> handleTipoInvalido(MethodArgumentTypeMismatchException exception) {
        String message = "Parametro invalido: " + exception.getName();
        return ResponseEntity
                .badRequest()
                .body(new ApiError(message, HttpStatus.BAD_REQUEST.value(), LocalDateTime.now()));
    }

    public record ApiError(String message, int status, LocalDateTime timestamp) {
    }

    public record ValidationError(
            String message,
            int status,
            LocalDateTime timestamp,
            Map<String, String> errors
    ) {
    }
}
