package in.kb.main.config;

import in.kb.main.entitys.Customer;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class UserDetailsImp implements UserDetails {

    private final Customer customer;

    public UserDetailsImp(Customer customer){
        this.customer = customer;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        HashSet<GrantedAuthority> set = new HashSet<>();
        set.add(new SimpleGrantedAuthority(customer.getRole()));
        return set;
    }

    @Override
    public @Nullable String getPassword() {
        return customer.getPassword();
    }

    @Override
    public String getUsername() {
        return customer.getUsername();
    }
}
