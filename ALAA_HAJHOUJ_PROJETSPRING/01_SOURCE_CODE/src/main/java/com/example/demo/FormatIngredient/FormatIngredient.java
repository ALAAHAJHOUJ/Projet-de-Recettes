package com.example.demo.FormatIngredient;


import jakarta.validation.constraints.NotNull;




public class FormatIngredient {

    @NotNull
    private String nom;

    @NotNull
    private Integer quantite;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Integer getQuantite() {
        return quantite;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }
}
