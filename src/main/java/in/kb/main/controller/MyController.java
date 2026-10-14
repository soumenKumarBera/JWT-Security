package in.kb.main.controller;


import in.kb.main.dto.UserDto;
import in.kb.main.entitys.RefreshToken;
import in.kb.main.entitys.User;
import in.kb.main.payload.JwtAuthRequest;
import in.kb.main.payload.JwtResponse;
import in.kb.main.payload.JwtToken;
import in.kb.main.payload.RefreshTokenRequest;
import in.kb.main.services.CustomServiceDetails;
import in.kb.main.services.RefreshTokenServices;
import in.kb.main.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/auth")
public class MyController {

    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    private JwtToken jwtToken;

    @Autowired
    private RefreshTokenServices refreshTokenServices;

    @Autowired
    private UserServices userServices;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> loginPage(@RequestBody JwtAuthRequest jwtAuthRequest){
        String username =  jwtAuthRequest.getUsername();
        String password = jwtAuthRequest.getPassword();
        System.out.println("working");

     UserDetails userDetails = authenticate(username, password);
     //GENERATE  TOKEN ON THE BASIC OF UserDetails OBJECT

        System.out.println("medium");

       String token =  jwtToken.generatedToken(userDetails); // NEW ERA

        RefreshToken refreshToken = refreshTokenServices.createRefreshToken(userDetails.getUsername());

        JwtResponse jwtResponse = new JwtResponse();
        jwtResponse.setToken(token);
        jwtResponse.setRefreshToken(refreshToken.getRefreshToken());

        System.out.println("finsh");

        return new ResponseEntity<>(jwtResponse, HttpStatus.OK);

    }

    private UserDetails authenticate(String username, String password) {
       


            Authentication authentication =
                    authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    username, password
                            )
                    );


            return (UserDetails) authentication.getPrincipal();


    }




    @PostMapping
    public ResponseEntity<User> register(@RequestBody UserDto userDto){

        User user = userServices.register(userDto);

        return new ResponseEntity<>(user, HttpStatus.OK);

    }


    @PostMapping("/refresh")
    public JwtResponse refreshJwtToken(@RequestBody RefreshTokenRequest request){



       RefreshToken refreshToken =  refreshTokenServices.verifyRefreshToken(request.getRefreshToken());

       User user = refreshToken.getUser();


        UserDetails userDetails = new CustomServiceDetails(user.getId(),user.getName(), user.getEmail(),user.getPassword(),user.getUserType(), new ArrayList<>());

       String token = jwtToken.generatedToken(userDetails);
        System.out.println(token);

       JwtResponse jwtResponse = new JwtResponse();

       jwtResponse.setToken(token);
       jwtResponse.setRefreshToken(refreshToken.getRefreshToken());

       return jwtResponse;
    }




}
