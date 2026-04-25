package in.kb.main.payload;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.*;

@Component
public class JwtToken {

    @Value("${jwt.token.validity}")
   private long JWT_VALIDITY;

    @Value("${jwt.secret}")
   private String SECRET_KEY;


    public String generatedToken(UserDetails userDetails){

        List<String> roles = new ArrayList<>();
        for(GrantedAuthority grantedAuthority: userDetails.getAuthorities()){
            roles.add(grantedAuthority.getAuthority());
        };

        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", roles);

       String token =  Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + JWT_VALIDITY * 1000 ))
               .signWith(getKey(),Jwts.SIG.HS512)
                .compact();

        return token;

    }

    private SecretKey getKey(){
        byte[] bytes = SECRET_KEY.getBytes();
        return Keys.hmacShaKeyFor(bytes);
    }
}
