package com.example.demo.Repository;

import com.example.demo.entites.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Integer> {

     User findByPassword(String Password);
}
