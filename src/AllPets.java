/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

import java.util.*;

public class AllPets {

    private final List<Pet> allPets = new ArrayList<>();
    /**
     * method to add a Pet object to the database (allPets)
     * @param pet a Pet object
     */
    public void addPet(Pet pet){
        this.allPets.add(pet);
    }

    /**
     * a method to return a set of all cat/dog breeds in the dataset (no duplicates)
     * @param type a String representing the type of pet the user is interested in (Cat or Dog)
     * @return Set</String> of available breeds
     */
    public Set<String> getAllBreeds(PetType type){
        Set<String> allBreeds = new TreeSet<>();
        for(Pet p: allPets){
            if(type.equals(p.dreamPet().getValueAtCriteria(Criteria.TYPE)))
                allBreeds.add((String) p.dreamPet().getValueAtCriteria(Criteria.BREED));
        }
        allBreeds.add("NA");
        return allBreeds;
    }

    /**
     * returns a collection of Pet objects that meet all the user's requirements
     * @param petCriteria a DreamPet object representing a user's preferred Pet
     * @return a Pet object
     */
     public List<Pet> findMatch(DreamPet petCriteria){
        List<Pet> compatiblePets = new ArrayList<>();
        for(Pet pet: this.allPets){
            if(!pet.dreamPet().compareDreamPets(petCriteria)) continue;
            if(pet.age()<petCriteria.getMinAge() || pet.age()> petCriteria.getMaxAge()) continue;
            if(pet.adoptionFee()<petCriteria.getMinFee() || pet.adoptionFee()> petCriteria.getMaxFee()) continue;
            compatiblePets.add(pet);
        }
         return compatiblePets;
    }

}
