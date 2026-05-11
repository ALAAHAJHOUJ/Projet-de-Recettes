package com.example.demo.Controller;


import com.example.demo.FormatRecette.FormatRecette;
import com.example.demo.ModelUser.ModelUser;
import com.example.demo.Pagination.PagedResponse;
import com.example.demo.Services.AuthService;
import com.example.demo.Services.RecetteService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.LinkedList;
import java.util.List;


@RestController

public class ControllerRecette {


    @Autowired
    private RecetteService recetteService;


    @Autowired
    private AuthService authService;


    @PostMapping("/api/recettes")
    public ResponseEntity<?> AjouterRecette(@RequestBody @Valid FormatRecette recette1){
        if(!recetteService.existeparTitre(recette1.getTitre())){
            FormatRecette r1=recetteService.enregistrerRecette(recette1);
            return ResponseEntity.status(HttpStatus.CREATED).body(r1);
        }
        else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("titre deja existant");
        }
    }





    @PutMapping("/api/recettes/{id}")
    public ResponseEntity<?> ModifierRecette(@PathVariable int id,@RequestBody FormatRecette recette){
        if(recetteService.existe(id)){
            recetteService.modifierRecette(id,recette);
            return ResponseEntity.noContent().build();
        }else {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body("recette introuvable");
        }

    }




    @DeleteMapping("/api/recettes/{id}")
    public ResponseEntity<?> SupprimerRecette(@PathVariable int id){
         if(recetteService.existe(id)){
             recetteService.SupprimerRecette(id);
             return ResponseEntity.noContent().build();
         }else {
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body("recette introuvable");
         }
    }




    @GetMapping("/api/recettes/{id}")
    public ResponseEntity<?> Consulter(@PathVariable int id){
        boolean exister=recetteService.existe(id);
        if (!exister) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("recette introuvable");
        }

        FormatRecette r1=recetteService.chercher(id);
        return ResponseEntity.status(HttpStatus.OK).body(r1);
    }




    @GetMapping("/api/recettes")
    public ResponseEntity<PagedResponse<FormatRecette>> pagination1(@RequestParam(defaultValue = "0")int page,
                                                               @RequestParam(defaultValue = "2") int size){

        Page<FormatRecette> Recettes1=recetteService.RecettePagine1(page,size);
        List<FormatRecette> RecettesRespone=new LinkedList<>();

        Recettes1.forEach((recette)->{
            FormatRecette recette2=new FormatRecette();
            BeanUtils.copyProperties(recette,recette2);
            RecettesRespone.add(recette2);
        });

        PagedResponse<FormatRecette> PagedResponse=new PagedResponse<>(Recettes1.getTotalPages()
                ,Recettes1.getSize()
                ,Recettes1.getTotalElements()
                ,Recettes1.getNumber()+1
                ,RecettesRespone

        );
        return ResponseEntity.status(HttpStatus.OK).body(PagedResponse);
    }





    @GetMapping("/api/recettes/search")
    public ResponseEntity<PagedResponse<FormatRecette>> pagination2(@RequestParam(defaultValue = "") String nom,
                                                           @RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "2") int size){

        Page<FormatRecette> Recettes1=recetteService.chercher1(nom,page,size);
        List<FormatRecette> RecettesRespone=new LinkedList<>();

        Recettes1.forEach((recette)->{
            FormatRecette recette2=new FormatRecette();
            BeanUtils.copyProperties(recette,recette2);
            RecettesRespone.add(recette2);
        });

        PagedResponse<FormatRecette> PagedResponse=new PagedResponse<>(Recettes1.getTotalPages()
                ,Recettes1.getSize()
                ,Recettes1.getTotalElements()
                ,Recettes1.getNumber()+1
                ,RecettesRespone

        );


        return ResponseEntity.status(HttpStatus.OK).body(PagedResponse);
    }



    @PostMapping("/Login")
    public ResponseEntity<String> Login(@RequestBody @Valid ModelUser user){

        String token=authService.authenticate(user.getUsername(),user.getPassword());

        return ResponseEntity.status(HttpStatus.OK).body(token);
    }



    @PostMapping("/inscription")
    public ResponseEntity<?> Inscription(@RequestBody @Valid ModelUser user1){

       authService.Inscription(user1);

       return ResponseEntity.status(HttpStatus.OK).body("inscription avec succes");
    }



}
