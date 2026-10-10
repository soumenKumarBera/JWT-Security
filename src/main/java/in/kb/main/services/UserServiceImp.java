package in.kb.main.services;

import in.kb.main.dto.UserDto;
import in.kb.main.entitys.User;
import in.kb.main.entitys.UserType;
import in.kb.main.reposirtory.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserServices{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User register(UserDto userDto) {

        User user = modelMapper.map(userDto, User.class);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        if(user.getUserType() == null){
            user.setUserType(UserType.EMPLOY);
        }

        return userRepository.save(user);
    }
}
