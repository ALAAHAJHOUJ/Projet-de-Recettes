package com.example.demo.entites;

import jakarta.persistence.*;

import java.util.List;



@Entity
@Table(name = "Utilisateur")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false,unique = true)
    private String username;


    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String Role;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Recette> listRecettes;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    public List<Recette> getListRecettes() {
        return listRecettes;
    }

    public void setListRecettes(List<Recette> listRecettes) {
        this.listRecettes = listRecettes;
    }
}
