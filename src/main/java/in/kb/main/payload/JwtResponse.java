package in.kb.main.payload;

import lombok.Data;
import org.springframework.stereotype.Service;

@Data
public class JwtResponse {
    private String token;
    private String refreshToken;
}
