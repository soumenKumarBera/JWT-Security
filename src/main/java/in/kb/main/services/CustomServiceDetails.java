package in.kb.main.services;

import in.kb.main.entitys.User;
import in.kb.main.entitys.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;


@AllArgsConstructor
@Data
public class CustomServiceDetails implements UserDetails {

  private Long id;
  private String name;
  private String username;
  private String password;
  private UserType userType;
  private Collection<?extends GrantedAuthority> authorities;
}
