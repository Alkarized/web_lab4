package ru.itmo.alkarized.lab4.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.itmo.alkarized.lab4.entities.User;
import ru.itmo.alkarized.lab4.repositories.UserRepository;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping(value = "/logout")
    public ResponseEntity<?> logout(@RequestBody User user){
        SecurityContextHolder.clearContext();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody User user){
        try {
            if (user.getUsername() == null || user.getUsername().equals("")
                    || user.getPassword() == null || user.getPassword().equals("")) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            User DBUser = userRepository.findByUsername(user.getUsername());
            if (DBUser == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (AuthenticationException ex) {
            System.out.print("User can't login");
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }


    @PostMapping(value = "/registration")
    public ResponseEntity<?> register(@RequestBody User user){
        User DBUser = userRepository.findByUsername(user.getUsername());

        if (DBUser != null) {
            System.out.println("User alredy exists!");
            return new ResponseEntity<>(HttpStatus.IM_USED);
        }

        if (user.getUsername().trim().equals("") || user.getPassword().trim().equals("")) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); //на всякий случай...
        }

        user.setPassword(passwordEncoder.encode(user.getPassword().trim()));
        user.setUsername(user.getUsername().trim());
        userRepository.save(user);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/login")
    public ResponseEntity<String> loginPage(){
        return new ResponseEntity<String>("login", HttpStatus.OK);
    }

    @GetMapping(value = "/registration")
    public ResponseEntity<String> registerPage(){
        return new ResponseEntity<String>("registration", HttpStatus.OK);
    }

    @GetMapping(value = "/logout")
    public ResponseEntity<String> logoutPage(){
        return new ResponseEntity<String>("logout", HttpStatus.OK);
    }

    @GetMapping(value = "/")
    public ResponseEntity<String> mainPage(){
        return new ResponseEntity<String>("redirect:/login", HttpStatus.OK);
    }

    @GetMapping(value = "/error")
    public ResponseEntity<String> errorPage(){
        return new ResponseEntity<String>("error has occured", HttpStatus.OK);
    }

}
