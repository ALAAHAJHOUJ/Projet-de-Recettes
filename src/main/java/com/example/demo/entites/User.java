package com.example.demo.entites;

import jakarta.persistence.*;

import java.util.List;



@Entity
@Table(name = "Utilisateur")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    private String Username;

    private String Password;

    private String Role;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Recette> listRecettes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
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
