package com.buyogo.demo.Controller;

import com.buyogo.demo.DTO.LoginDTO;
import com.buyogo.demo.DTO.SignUpDTO;
import com.buyogo.demo.Model.User;
import com.buyogo.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/user/")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginRequest) {
        return userService.authenticateUser(loginRequest);

    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignUpDTO signupRequest) {
        return userService.createUser(signupRequest);
    }

    @GetMapping("/get/{identifier}")
    public ResponseEntity<?> getUserByEmailPhone(@PathVariable String identifier){
        return userService.getUserByEmailOrPhone(identifier,identifier);

    }
}
