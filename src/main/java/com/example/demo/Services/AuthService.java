package com.example.demo.Services;

import com.example.demo.Repository.UserRepo;
import com.example.demo.entites.User;
import com.example.demo.securite.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;


@Service
public class AuthService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JwtUtil jwtUtil;


    public String authenticate(String username, String password) {
        User user = userRepo.findByPassword(username);
        String token = null;
        if (user != null && user.getPassword().equals(password)) {
            token = jwtUtil.generateToken(username);
        }
        else
            throw new BadCredentialsException("Identifiants invalides");
        return token;
    }



}
