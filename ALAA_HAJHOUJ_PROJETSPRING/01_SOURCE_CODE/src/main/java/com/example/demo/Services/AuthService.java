package com.example.demo.Services;

import com.example.demo.ModelUser.ModelUser;
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
        User user = userRepo.findByUsername(username);
        String token = null;

        if(user!=null){
            System.out.println(user.getRole());
        }


        if (user != null && PasswordEncoder.matches(password,user.getPassword())) {
            token = jwtUtil.generateToken(username,user.getRole());
        }
        else
            throw new BadCredentialsException("Identifiants invalides");
        return token;
    }





    public String Inscription(ModelUser user){

        User user1=new User();
        String hash = PasswordEncoder.encode(user.getPassword());
        String nom=user.getUsername();
        user1.setPassword(hash);
        user1.setUsername(nom);
        user1.setRole(user.getRole());

        user1.setListRecettes(null);

            userRepo.save(user1);

        return "succes";

    }


}
