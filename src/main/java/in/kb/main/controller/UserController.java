package in.kb.main.controller;

import in.kb.main.dto.UserDto;
import in.kb.main.entitys.User;
import in.kb.main.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserServices userServices;


   @GetMapping
    public ResponseEntity<List<User>> getAllUser(){

        return new ResponseEntity<>(userServices.getAllUser(), HttpStatus.OK);

    }



}
