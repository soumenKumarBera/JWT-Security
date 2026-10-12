package in.kb.main.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;
@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String,Object>> exceptionHandler(Exception e){

        Map<String, Object> map = new HashMap<>();
        map.put("STATUS", "ERROR");
        map.put("MESSAGE", e.getMessage());

        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);

    }
}
