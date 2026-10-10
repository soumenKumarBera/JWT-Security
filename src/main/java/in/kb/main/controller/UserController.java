package in.kb.main.controller;

import in.kb.main.dto.UserDto;
import in.kb.main.entitys.User;
import in.kb.main.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserServices userServices;


    @PostMapping
    public ResponseEntity<User> register(@RequestBody UserDto userDto){

        User user = userServices.register(userDto);

        return new ResponseEntity<>(user, HttpStatus.OK);

    }




}
