package in.kb.main.services;

import in.kb.main.dto.UserDto;
import in.kb.main.entitys.User;

import java.util.List;

public interface UserServices {

    User register(UserDto userDto);
    List<User> getAllUser();



}
