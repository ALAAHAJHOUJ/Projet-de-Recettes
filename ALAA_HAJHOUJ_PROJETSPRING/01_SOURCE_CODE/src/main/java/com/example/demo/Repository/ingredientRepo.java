package com.example.demo.Repository;

import com.example.demo.entites.Ingredient;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ingredientRepo extends JpaRepository<Ingredient,Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Ingredient e WHERE e.nom = :nom AND e.recette.id = :id")
    int deleteByNomAndRecetteId(@Param("nom") String nom,
                                @Param("id") int id);

}
