package com.example.demo.Services;

import com.example.demo.Repository.UserRepo;
import com.example.demo.entites.User;
import com.example.demo.securite.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder PasswordEncoder;


    public String authenticate(String username, String password) {
        User user = userRepo.findByPassword(username);
        String token = null;
        if (user != null && PasswordEncoder.matches(password,user.getPassword())) {
            token = jwtUtil.generateToken(username,user.getRole());
        }
        else
            throw new BadCredentialsException("Identifiants invalides");
        return token;
    }



}
