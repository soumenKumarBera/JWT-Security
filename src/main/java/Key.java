import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Base64;

public class Key {
    public static void main(String[] args) {
        SecretKey key = Jwts.SIG.HS512.key().build();
        String encode = Base64.getEncoder().encodeToString(key.getEncoded());
        System.out.println(encode);
    }
}
