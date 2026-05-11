package com.example.demo.FormatIngredient;




import jakarta.validation.constraints.NotNull;

public class FormatIngredient {

    @NotNull(message="ce champs est requis dans la demande")
    private String nom;

    @NotNull(message="ce champs est requis dans la demande")
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
