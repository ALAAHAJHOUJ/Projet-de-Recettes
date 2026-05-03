package com.example.demo.Repository;

import com.example.demo.entites.Recette;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;



@Repository
public interface RecetteRepo extends JpaRepository<Recette,Integer> {

    Recette findByTitre(String titre);

    @Query("""
        SELECT DISTINCT r FROM Recette r
        JOIN Ingredient i on r.id=i.recette.id
        WHERE LOWER(r.titre) = LOWER(:mot)
        OR LOWER(r.description) LIKE LOWER(CONCAT('%', :mot, '%'))
        OR LOWER(i.nom) = LOWER(:mot)
 """)
    Page<Recette> search(@Param("mot") String mot, Pageable pageable);
}
