package com.buyogo.demo.Service;

import com.buyogo.demo.DTO.LoginDTO;
import com.buyogo.demo.DTO.SignUpDTO;
import com.buyogo.demo.Model.User;
import com.buyogo.demo.Repo.UserRepo;
import com.buyogo.demo.Response.AuthResponse;
import com.buyogo.demo.Response.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    private Optional<User> findByEmail(String email, String password) {
        return userRepo.findByEmail(email);
    }

    private Optional<User> findByPhone(String phone, String password) {
        return userRepo.findByPhone(phone);
    }

    public ResponseEntity<?> authenticateUser(LoginDTO loginRequest){
        if (loginRequest.getEmail() != null && !loginRequest.getEmail().isEmpty()) {
            Optional<User> userOptional = findByEmail(loginRequest.getEmail(), loginRequest.getPassword());
            if (userOptional.isPresent() && loginRequest.getPassword().equals(userOptional.get().getPassword())) {
                return ResponseEntity.ok(new CustomResponse(HttpStatus.OK.value(), "email"));
            }
        } else if (loginRequest.getPhone() != null && !loginRequest.getPhone().isEmpty()) {
            Optional<User> userOptional = findByPhone(loginRequest.getPhone(), loginRequest.getPassword());
            if (userOptional.isPresent() && loginRequest.getPassword().equals(userOptional.get().getPassword())) {
                return ResponseEntity.ok(new CustomResponse(HttpStatus.OK.value(), "phone"));
            }
        }
        return ResponseEntity.ok(new CustomResponse(HttpStatus.UNAUTHORIZED.value(),null));
    }

    private boolean userExists(String email, String phone) {
        return userRepo.findByEmail(email).isPresent() || userRepo.findByPhone(phone).isPresent();
    }

    private User userSignUp(User user){
        return userRepo.save(user);
    }


    public ResponseEntity<?> createUser(SignUpDTO signupRequest){
        boolean userExists = userExists(signupRequest.getEmail(), signupRequest.getPhone());
        if (userExists) {
            return ResponseEntity.ok(new CustomResponse(HttpStatus.CONFLICT.value(), null));
        }
        User newUser = new User();
        newUser.setEmail(signupRequest.getEmail());
        newUser.setPhone(signupRequest.getPhone());
        newUser.setFullName(signupRequest.getFullName());
        newUser.setBirthDate(signupRequest.getBirthDate());
        newUser.setCity(signupRequest.getCity());
        newUser.setDesignation(signupRequest.getDesignation());
        newUser.setOrganizationId(signupRequest.getOrganizationId());
        newUser.setOrganizationName(signupRequest.getOrganizationName());
        newUser.setPincode(signupRequest.getPincode());
        newUser.setPassword(signupRequest.getPassword());
        userSignUp(newUser);
        return ResponseEntity.ok(new CustomResponse(HttpStatus.CREATED.value(), "created"));
    }

    
    public ResponseEntity<?> getUserByEmailOrPhone(String email, String phone) {
        User existedUser =  userRepo.findByEmailOrPhone(email, phone);
        if (existedUser!=null){
            return ResponseEntity.ok(new AuthResponse(HttpStatus.OK.value(), "available",existedUser));
        }
        return ResponseEntity.ok(new AuthResponse(HttpStatus.NOT_FOUND.value(), "unavailable",null));
    }

}
