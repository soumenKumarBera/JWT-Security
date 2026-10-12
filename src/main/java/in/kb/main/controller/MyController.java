package in.kb.main.controller;


import in.kb.main.payload.JwtAuthRequest;
import in.kb.main.payload.JwtResponse;
import in.kb.main.payload.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class MyController {

    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    private JwtToken jwtToken;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> loginPage(@RequestBody JwtAuthRequest jwtAuthRequest){
        String username =  jwtAuthRequest.getUsername();
        String password = jwtAuthRequest.getPassword();
        System.out.println("working");

     UserDetails userDetails = authenticate(username, password);
     //GENERATE  TOKEN ON THE BASIC OF UserDetails OBJECT

        System.out.println("medium");

       String token =  jwtToken.generatedToken(userDetails); // NEW ERA
        JwtResponse jwtResponse = new JwtResponse();
        jwtResponse.setToken(token);

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





}
