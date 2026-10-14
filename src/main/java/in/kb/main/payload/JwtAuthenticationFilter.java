package in.kb.main.payload;


import in.kb.main.payload.JwtToken;
import in.kb.main.services.MyServices;
import io.jsonwebtoken.JwtException;
import org.springframework.stereotype.Component;


import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
 private JwtToken jwtToken;

    @Autowired
    private MyServices myServices;



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = request.getHeader("Authorization");


        if(token != null && token.startsWith("Bearer")){
            token = token.substring(7);
            try{
                boolean expired = jwtToken.isExpired(token);

                if (!expired){

                    String username = jwtToken.getUsernameFromToken(token);

                    if(username != null && SecurityContextHolder.getContext().getAuthentication() == null ){

                        UserDetails userDetails =  myServices.loadUserByUsername(username);

                        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, token, userDetails.getAuthorities());

                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);


                    }else {
                        throw new JwtException("USER_NOT-FOUND");
                    }


                }else {
                    throw new JwtException("TOKEN_IS_EXPIRED");
                }






            }catch (IllegalArgumentException e){
                e.printStackTrace();

            }catch (ExpiredJwtException e){
                e.printStackTrace();
            }catch (MalformedJwtException e){
                e.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }

        }
//        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null){
//            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//            Boolean validateToken = this.jwtHelper.validateToken(token,userDetails.getUsername());
//            if(validateToken) {
//                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
//                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//            }
//        }
        filterChain.doFilter(request, response);

    }
}
