package com.example.demo.Services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.Repository.ingredientRepo;

@Service
public class IngredientService {

    @Autowired
    private ingredientRepo ingredientRepo;

    public void supprimerIngredient(String titre,int id){
        ingredientRepo.deleteByNomAndRecetteId(titre,id);
    }

}
