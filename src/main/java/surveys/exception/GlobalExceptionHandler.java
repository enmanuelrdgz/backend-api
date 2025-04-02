package surveys.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import surveys.dto.ApiResponseDTO;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Esta exception se puede lanzar si un usuario intenta votar
    // en una encuesta en la que ya ha votado antes
    @ExceptionHandler(DataIntegrityViolationException.class)
    public String DataIntegrityViolationExceptionHandler() {
        return "DataIntegrityViolationException";
    }

    // Esta exception se puede lanzar si hay un error de connexion con la base de datos
    // esperemos que eso no pase ;)
    @ExceptionHandler(JpaSystemException.class)
    public String JpaSystemExceptionHandler() {
        return "DataIntegrityViolationException";
    }

    // Esta exception se puede lanzar si el cuerpo de una solicitud (@RequestBody)
    // no cumple con las restricciones establecidas al momento de deserializarse.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseDTO<Void>> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {
        return new ResponseEntity<>(
                new ApiResponseDTO<>(400, ex.getMessage(), null),
                HttpStatus.BAD_REQUEST
        );
    }

    // Esta exception se puede lanzar si Spring no puede deserializar el
    // cuerpo de la solicitud debido a un mal formato
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponseDTO<Void>> HttpMessageNotReadableExceptionHandler(MethodArgumentNotValidException ex) {
        return new ResponseEntity<>(
                new ApiResponseDTO<>(400, ex.getMessage(), null),
                HttpStatus.BAD_REQUEST
        );
    }

    // Esta exception se puede lanzar si se proporciona un ID incorrecto
    // al momento de obtener una entidad de la base de datos
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponseDTO<Void>> ResourceNotFoundExceptionHandler(ResourceNotFoundException ex) {
        return new ResponseEntity<>(
                new ApiResponseDTO<>(404, ex.getMessage(), null),
                HttpStatus.NOT_FOUND
        );
    }
}

