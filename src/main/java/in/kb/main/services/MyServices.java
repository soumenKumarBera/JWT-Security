package in.kb.main.services;

import in.kb.main.config.UserDetailsImp;
import in.kb.main.entitys.Customer;
import in.kb.main.reposirtory.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MyServices implements UserDetailsService {

    private final CustomerRepository customerRepository;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Customer> opt = customerRepository.findByUsername(username);

        if (opt.isPresent()){

            Customer customer = opt.get();

            return new UserDetailsImp(customer);

        }else {
            throw new UsernameNotFoundException("UserName is wrong.......");
        }




    }
}
