package in.kb.main.services;

import in.kb.main.entitys.RefreshToken;
import in.kb.main.entitys.User;
import in.kb.main.reposirtory.RefreshTokenRepo;
import in.kb.main.reposirtory.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

@Service
public class RefreshTokenImp implements RefreshTokenServices{

    @Autowired
    private RefreshTokenRepo refreshTokenRepo;

    @Autowired
    private UserRepository userRepository;

//          5 Hours validity time
    private long refreshTokenValidity = 5*60*60*1000;



    @Override
    public RefreshToken createRefreshToken(String email) {

        Instant now = Instant.now();




        User user = userRepository.findByEmail(email);
        RefreshToken refreshToken = user.getRefreshToken();

        if(refreshToken == null){
            refreshToken = new RefreshToken();
            refreshToken.setRefreshToken(UUID.randomUUID().toString());

            Instant expiration = now.plusMillis(refreshTokenValidity);
            refreshToken.setExpire( LocalDateTime.now().plus(Duration.ofMillis(refreshTokenValidity)));
            refreshToken.setUser(user);

        }else {

            refreshToken.setExpire( LocalDateTime.now().plus(Duration.ofMillis(refreshTokenValidity)));

        }

        return refreshTokenRepo.save(refreshToken);
    }

    @Override
    public RefreshToken verifyRefreshToken(String refreshToken) {

        RefreshToken getRefreshToken = refreshTokenRepo.findByRefreshToken(refreshToken);

        if(getRefreshToken.getExpire().compareTo(LocalDateTime.now())< 0){
            refreshTokenRepo.delete(getRefreshToken);
            throw new RuntimeException("Refresh Token Expired !!");

        }else {
            return  getRefreshToken;
        }


    }
}
