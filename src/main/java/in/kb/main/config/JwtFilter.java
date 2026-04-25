package in.kb.main.config;

import in.kb.main.payload.JwtToken;
import in.kb.main.services.MyServices;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtToken jwtToken;

    @Autowired
    private MyServices myServices;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

      String token=  request.getHeader("Authorization");

      if (token != null && token.startsWith("Bearer")){
          // token processing

          token = token.substring(7);
        Boolean expired =  jwtToken.isExpired(token);

        if (!expired){
            String username = jwtToken.getUsernameFromToken(token);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null){

                UserDetails ud =  myServices.loadUserByUsername(username);
                if (ud != null){
               UsernamePasswordAuthenticationToken UPA =    new UsernamePasswordAuthenticationToken(ud, token, ud.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(UPA);
                    //EVERY NEW REQUEST -> securityContextHolder(EMPTY)
                }

            }else {
                System.out.println("username not found  in the token");
            }

        }else {
            System.out.println("token is expired");


        }




      }

        filterChain.doFilter(request, response);
    }
}
