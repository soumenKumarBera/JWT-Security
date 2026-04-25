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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    private JwtToken jwtToken;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> loginPage(@RequestBody JwtAuthRequest jwtAuthRequest){
        String username =  jwtAuthRequest.getUsername();
        String password = jwtAuthRequest.getPassword();

     UserDetails userDetails = authenticate(username, password);
     //GENERATE  TOKEN ON THE BASIC OF UserDetails OBJECT

       String token =  jwtToken.generatedToken(userDetails); // NEW ERA
        JwtResponse jwtResponse = new JwtResponse();
        jwtResponse.setToken(token);

        return new ResponseEntity<>(jwtResponse, HttpStatus.OK);



    }

    private UserDetails authenticate(String username, String password) {

        UsernamePasswordAuthenticationToken userpass = new UsernamePasswordAuthenticationToken(username, password);
          Authentication authentication =  authenticationManager.authenticate(userpass);
        return (UserDetails) authentication.getPrincipal();
    }

    @GetMapping("/admin")
    public void adminPage(){



    }

    @GetMapping("/member")
    public void memberPage(){



    }



}
