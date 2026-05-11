package com.example.demo.Services;

import com.example.demo.FormatIngredient.FormatIngredient;
import com.example.demo.FormatRecette.FormatRecette;
import com.example.demo.Repository.RecetteRepo;
import com.example.demo.Repository.UserRepo;
import com.example.demo.entites.Ingredient;
import com.example.demo.entites.Recette;
import com.example.demo.entites.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;


@Service
public class RecetteService {


    @Autowired
    private RecetteRepo recetteRepo;

    @Autowired
    private IngredientService ingredientService;

    @Autowired
    private UserRepo userRepo;


    public FormatRecette enregistrerRecette(FormatRecette recette){
        Recette r1=new Recette();
        BeanUtils.copyProperties(recette,r1);

        List<Ingredient> ingredient1=recette.getListIngredient().stream().map(iDto -> {
                    Ingredient i = new Ingredient();
                    i.setQuantite(iDto.getQuantite());
                    i.setNom(iDto.getNom());
                    i.setRecette(r1);
                    return i;
                })
                .toList();

        r1.setIngredients(ingredient1);



        Authentication auth = SecurityContextHolder
                .getContext()
                .getAuthentication();

        String username = auth.getName();

        User user1=userRepo.findByUsername(username);

        r1.setUser(user1);

        recetteRepo.save(r1);

        FormatRecette r2=new FormatRecette();

        BeanUtils.copyProperties(r1,r2);


        List<FormatIngredient> liste1=new LinkedList<>();
        for(Ingredient d:r1.getIngredients()){
            FormatIngredient forme=new FormatIngredient();
            forme.setNom(d.getNom());
            forme.setQuantite(d.getQuantite());
            liste1.add(forme);
        }
        r2.setListIngredient(liste1);

        return r2;

    }





    public String modifierRecette(int id, FormatRecette recette){
        Recette r1=recetteRepo.findById(id).orElseThrow(()->new RuntimeException("recette inexistante"));

        Authentication auth = SecurityContextHolder
                .getContext()
                .getAuthentication();

        String username = auth.getName();
        System.out.println(username);

        String role = auth.getAuthorities()
                .stream()
                .findFirst()
                .get()
                .getAuthority();

        if(!role.equals("ADMIN")){
            if(!r1.getUser().getUsername().equals(username)){
                return "cette opération n'est pas autorisé";
            }
        }


        if(recette.getTitre()!=null){
             r1.setTitre(recette.getTitre());
        }

        if(recette.getDescription()!=null){
             r1.setDescription(recette.getDescription());
        }

        if(recette.getLink_Img()!=null){
             r1.setLink_Img(recette.getLink_Img());
        }

        if(recette.getTempsMinutes()!=null){
           r1.setTempsMinutes(recette.getTempsMinutes());
        }

        if(recette.getListIngredient()!=null){
            List<Ingredient> listIngr=new LinkedList<>();


            //d'abord on supprime tous les ingredients
            for(Ingredient r:r1.getIngredients()){
                String titreIngr=r.getNom();
                ingredientService.supprimerIngredient(titreIngr,id);
            }



            //ajouter Les recettes envoyés dans la demande
            for(FormatIngredient d:recette.getListIngredient()){
                Ingredient ingr1=new Ingredient();
                ingr1.setNom(d.getNom());
                ingr1.setRecette(r1);
                ingr1.setQuantite(d.getQuantite());
                listIngr.add(ingr1);
            }
            r1.setIngredients(listIngr);
        }

        recetteRepo.save(r1);



        return "succes";
    }





    public String SupprimerRecette (int id)  {

        Optional<Recette> r1=recetteRepo.findById(id);

        Authentication auth = SecurityContextHolder
                .getContext()
                .getAuthentication();

        String username = auth.getName();

        String role = auth.getAuthorities()
                .stream()
                .findFirst()
                .get()
                .getAuthority();
        System.out.println(role.equals("ADMIN"));

        if(role.equals("ADMIN"))
        {
            recetteRepo.delete(r1.get());
        }else {
            try{
                if(r1.get().getUser().getUsername().equals(username)){
                    recetteRepo.delete(r1.get());
                }else {
                    throw new IllegalAccessException("non autorisé a supprimer cet element");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return "non autorisé a supprimer cet element";
            }

        }


        return "suppression avec succes";
    }





    public FormatRecette chercher(int id){
        Recette r1=recetteRepo.findById(id).orElseThrow(()->new RuntimeException("recette introuvable"));
        FormatRecette format=new FormatRecette();
        BeanUtils.copyProperties(r1,format);
        List<FormatIngredient> list1=new LinkedList<>();

        for(Ingredient ingr:r1.getIngredients()){
            FormatIngredient forme1=new FormatIngredient();
            BeanUtils.copyProperties(ingr,forme1);
            list1.add(forme1);
        }

        format.setListIngredient(list1);
        return format;
    }



    public boolean existe(int id){
        return recetteRepo.existsById(id);
    }




    public boolean existeparTitre(String nom){
        return recetteRepo.existsByTitre(nom);
    }




    public Page<FormatRecette> RecettePagine1(int page,int size){


        Page<FormatRecette> listRecettesPagine=recetteRepo.findAll(PageRequest.of(page,size)).map((recette)->{
           FormatRecette r1=new FormatRecette();
           BeanUtils.copyProperties(recette,r1);
           List<FormatIngredient> listIngredients=new LinkedList<>();
           for(Ingredient d:recette.getIngredients()){
               FormatIngredient formeIngredient=new FormatIngredient();
               BeanUtils.copyProperties(d,formeIngredient);
               listIngredients.add(formeIngredient);
           }
           r1.setListIngredient(listIngredients);
           return r1;
        });

        return listRecettesPagine;
    }



    public Page<FormatRecette> chercher1(String nom,int page,int size){

        Pageable p = PageRequest.of(page, size);
        Page<FormatRecette> RecettePagine=recetteRepo.search(nom,p).map((recette)->{
            FormatRecette r1=new FormatRecette();
            BeanUtils.copyProperties(recette,r1);
            List<FormatIngredient> listIngredient=new LinkedList<>();
            for(Ingredient d1:recette.getIngredients()){
                FormatIngredient ingredient=new FormatIngredient();
                BeanUtils.copyProperties(d1,ingredient);
                listIngredient.add(ingredient);
            }
            r1.setListIngredient(listIngredient);
            return r1;

        });

        return RecettePagine;
    }

}
