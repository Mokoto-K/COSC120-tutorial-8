

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FindAPet {

    private final static String appName = "Pinkman's Pets Pet Finder";
    private final static String filePath = "./allPets.txt";
    private final static String iconPath = "./icon.jpg";
    private static final ImageIcon icon = new ImageIcon(iconPath);
    private static AllPets allPets;

    /**
     * main method used to allow the user to search Pinkman's database of Pets, and place an adoption request
     * @param args none required
     */
    public static void main(String[] args) {
        allPets = loadPets();
        JOptionPane.showMessageDialog(null, "Welcome to Pinkman's Pets Pet Finder!\n\tTo start, click OK.", appName, JOptionPane.QUESTION_MESSAGE, icon);
        DreamPet petCriteria = getUserCriteria();
        processSearchResults(petCriteria);
        System.exit(0);
    }

    /**
     * method to load all Pet data from file, storing it as Pet objects in an instance of AllPets
     * @return an AllPets object - functions as database of Pets, with associated methods
     */
    private static AllPets loadPets() {
        AllPets allPets = new AllPets();
        Path path = Path.of(filePath);

        List<String> petData = null;
        try{
            petData = Files.readAllLines(path);
        }catch (IOException io){
            System.out.println("Could not load the file. \nError message: "+io.getMessage());
            System.exit(0);
        }
        for (int i=1;i<petData.size();i++) {
            String[] elements = petData.get(i).split(",");
            PetType type = null;
            try {
                type = PetType.valueOf(elements[0].toUpperCase().replace(" ","_"));
            }catch (IllegalArgumentException e){
                System.out.println("Error in file. Type of pet data could not be parsed for pet on line "+(i+1)+ ". Terminating. \nError message: "+e.getMessage());
                System.exit(0);
            }

            String name = elements[1];
            long microchipNumber = 0;
            try{
                microchipNumber = Long.parseLong(elements[2]);
            }
            catch (NumberFormatException n){
                System.out.println("Error in file. Microchip number could not be parsed for Pet on line "+(i+1)+". Terminating. \nError message: "+n.getMessage());
                System.exit(0);
            }

            Sex sex = Sex.valueOf(elements[3].toUpperCase());
            DeSexed deSexed = DeSexed.valueOf(elements[4].toUpperCase()); //add exception handling here

            int age = 0;
            try{
                age = Integer.parseInt(elements[5]);
            }catch (NumberFormatException n){
                System.out.println("Error in file. Age could not be parsed for Pet on line "+(i+1)+". Terminating. \nError message: "+n.getMessage());
                System.exit(0);
            }

            String breed = elements[6].toLowerCase();
            Purebred purebred = Purebred.valueOf(elements[7].toUpperCase()); //add exception handling here

            double adoptionFee = 0;
            try{
                adoptionFee = Double.parseDouble(elements[8]);
            }catch (NumberFormatException n){
                System.out.println("Error in file. Adoption fee could not be parsed for Pet on line "+(i+1)+". Terminating. \nError message: "+n.getMessage());
                System.exit(0);
            }

            Hair hair  = Hair.valueOf(elements[9].toUpperCase()); //add exception handling here
            LevelOfTraining trainingLevel = LevelOfTraining.valueOf(elements[10].toUpperCase()); //add exception handling here
            int dailyExercise = 0;
            if(!elements[11].equalsIgnoreCase("NA"))
                try{
                    dailyExercise = Integer.parseInt(elements[11]);
                }catch (NumberFormatException n){
                  System.out.println("Error in file. Exercise minutes could not be parsed for Pet on line "+(i+1)+". Terminating. \nError message: "+n.getMessage());
                  System.exit(0);
                }

            Map<Criteria,Object> petCriteria = new HashMap<>();
            petCriteria.put(Criteria.TYPE,type);
            petCriteria.put(Criteria.SEX,sex);
            petCriteria.put(Criteria.DE_SEXED,deSexed);
            petCriteria.put(Criteria.BREED,breed);
            petCriteria.put(Criteria.PUREBRED,purebred);
            petCriteria.put(Criteria.HAIR,hair);
            petCriteria.put(Criteria.TRAINING_LEVEL,trainingLevel);
            petCriteria.put(Criteria.DAILY_EXERCISE,dailyExercise);

            DreamPet dreamPet = new DreamPet(petCriteria);
            Pet Pet = new Pet(name, microchipNumber,age, adoptionFee,dreamPet);

            allPets.addPet(Pet);
        }
        return allPets;
    }

    /**
     * generates JOptionPanes requesting user input for Pet breed, sex, de-sexed status and age
     * @return a DreamPet object representing the user's desired Pet criteria
     */
    private static DreamPet getUserCriteria(){


        Map<Criteria,Object> desiredFeatures = new HashMap<>();
        desiredFeatures.put(Criteria.TYPE, type);
        if(!breed.equals("NA")) desiredFeatures.put(Criteria.BREED,breed);
        if(!sex.equals(Sex.NA)) desiredFeatures.put(Criteria.SEX,sex);
        desiredFeatures.put(Criteria.DE_SEXED,deSexed);
        if(!purebred.equals(Purebred.NA)) desiredFeatures.put(Criteria.PUREBRED,purebred);

        if(type.equals(PetType.CAT)||type.equals(PetType.GUINEA_PIG)){

            if(!hair.equals(Hair.NA)) desiredFeatures.put(Criteria.HAIR,hair);
        }

        return new DreamPet(desiredFeatures,ageRange[0],ageRange[1],feeRange[0],feeRange[1]);
    }

    /**
     * method to get user to input name, ph num and email, with appropriate input validation
     * @return a Person object representing the user of the program
     */
    private static Person getUserDetails(){
        String name;
        do {
            name = JOptionPane.showInputDialog(null, "Please enter your full name.", appName, JOptionPane.QUESTION_MESSAGE);
            if(name==null) System.exit(0);
        } while(!isValidFullName(name));

        String phoneNumber;
        do{
            phoneNumber = JOptionPane.showInputDialog("Please enter your phone number (10-digit number in the format 0412345678): ");
            if(phoneNumber==null) System.exit(0);}
        while(!isValidPhoneNumber(phoneNumber));

        String email;
        do {
            email = JOptionPane.showInputDialog(null, "Please enter your email address.", appName, JOptionPane.QUESTION_MESSAGE);
            if (email == null) System.exit(0);
        }while(!isValidEmail(email));
        return new Person(name, phoneNumber, email);
    }

    /**
     * method to display  results (if there are any) to the user in the form of a drop-down list
     * allowing them to select and adopt a pet of their choice.
     * @param dreamPet a DreamPet object representing the user's selections
     */
    private static void processSearchResults(DreamPet dreamPet){
        List<Pet> potentialMatches = allPets.findMatch(dreamPet);


            else{
                Pet chosenPet = options.get(adopt);
                Person applicant = getUserDetails();
                writeAdoptionRequestToFile(applicant, chosenPet);
                JOptionPane.showMessageDialog(null, "Thank you! Your adoption request has been submitted. \n" +
                        "One of our friendly staff will be in touch shortly.", appName, JOptionPane.QUESTION_MESSAGE, icon);
            }
        } else JOptionPane.showMessageDialog(null, "Unfortunately none of our pets meet your criteria :(" +
                "\n\tTo exit, click OK.", appName, JOptionPane.QUESTION_MESSAGE, icon);
    }


}
