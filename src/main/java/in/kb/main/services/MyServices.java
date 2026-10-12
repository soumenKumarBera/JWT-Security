package in.kb.main.services;

import in.kb.main.entitys.User;
import in.kb.main.reposirtory.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MyServices implements UserDetailsService {


    private final UserRepository userRepository;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {



            User opt = userRepository.findByEmail(username);



            if (opt == null) {
                throw new UsernameNotFoundException("UserName is wrong......." + username);


            } else {
            return new CustomServiceDetails(opt.getId(), opt.getName(), opt.getEmail(),opt.getPassword(),opt.getUserType(), new ArrayList<>());

            }




    }
}
