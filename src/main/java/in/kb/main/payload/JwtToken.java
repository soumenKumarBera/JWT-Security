package in.kb.main.payload;

import io.jsonwebtoken.*;
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

        // one user multiple role  identifier
        List<String> roles = new ArrayList<>();
        for(GrantedAuthority grantedAuthority: userDetails.getAuthorities()){
            roles.add(grantedAuthority.getAuthority());
        };

        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", roles);

       String token =  Jwts.builder()
                .claims(claims) // setRoll
                .subject(userDetails.getUsername()) // setUserName
                .issuedAt(new Date(System.currentTimeMillis())) //issueDate
                .expiration(new Date(System.currentTimeMillis() + JWT_VALIDITY * 1000 )) //expiredTime
               .signWith(getKey(),Jwts.SIG.HS512)  //secret key
                .compact();


        return token;

    }

    private SecretKey getKey(){
        byte[] bytes = SECRET_KEY.getBytes();
        return Keys.hmacShaKeyFor(bytes);
    }


    //================================================================
    private Jws<Claims>  getAllDetailsFromToken(String token){
        JwtParserBuilder parser = Jwts.parser();

        return parser.verifyWith(getKey()).build().parseSignedClaims(token);

    };

    public String getUsernameFromToken(String token){

       return getAllDetailsFromToken(token).getPayload().getSubject();

    }

    public boolean isExpired(String token){
       Date expired =  getAllDetailsFromToken(token).getPayload().getExpiration();
        return expired.before(new Date()); //true => account is expired;
                                          //false => account is not expired;

    }



}
