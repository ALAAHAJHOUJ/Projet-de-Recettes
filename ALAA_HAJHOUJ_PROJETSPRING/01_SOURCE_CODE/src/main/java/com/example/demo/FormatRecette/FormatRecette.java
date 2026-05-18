package com.example.demo.FormatRecette;

import com.example.demo.FormatIngredient.FormatIngredient;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class FormatRecette {

    @NotNull
    private String titre;

    @NotNull
    private String description;

    @NotNull
    private String link_Img;

    @NotNull
    private Integer tempsMinutes=null;

    @NotNull
    @NotEmpty
    private List<@Valid FormatIngredient> listIngredient;


    public List<FormatIngredient> getListIngredient() {
        return listIngredient;
    }

    public void setListIngredient(List<FormatIngredient> listIngredient) {
        this.listIngredient = listIngredient;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink_Img() {
        return link_Img;
    }

    public void setLink_Img(String link_Img) {
        this.link_Img = link_Img;
    }

    public Integer getTempsMinutes() {
        return tempsMinutes;
    }

    public void setTempsMinutes(Integer tempsMinutes) {
        this.tempsMinutes = tempsMinutes;
    }

}
