/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

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
     * a very simple regex for full name in Firstname Surname format
     * @param fullName the candidate full name entered by the user
     * @return true if name matches regex/false if not
     */
    public static boolean isValidFullName(String fullName) {
        String regex = "^[A-Z][a-z]+\\s[A-Z][a-zA-Z]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(fullName);
        return matcher.matches();
    }

    /**
     * a regex matcher that ensures that the user's entry starts with a 0 and is followed by 9 digits
     * @param phoneNumber the candidate phone number entered by the user
     * @return true if phone number matches regex/false if not
     */
    public static boolean isValidPhoneNumber(String phoneNumber) {
        Pattern pattern = Pattern.compile("^0\\d{9}$");
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }

    /**
     * a regex matcher that ensures that the user's entry complies with RFC 5322
     * source: <a href="https://www.baeldung.com/java-email-validation-regex">...</a>
     * @param email the candidate email entered by the user
     * @return true if email matches regex/false if not
     */
    public static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * generates JOptionPanes requesting user input for Pet breed, sex, de-sexed status and age
     * @return a DreamPet object representing the user's desired Pet criteria
     */
    private static DreamPet getUserCriteria(){
        PetType type = (PetType) JOptionPane.showInputDialog(null,"Please select the type of pet you'd like to adopt.",appName, JOptionPane.QUESTION_MESSAGE,icon,PetType.values(), PetType.DOG);
        if(type==null) System.exit(0);

        String breed  = (String) JOptionPane.showInputDialog(null,"Please select your preferred breed.",appName, JOptionPane.QUESTION_MESSAGE,icon,allPets.getAllBreeds(type).toArray(),"");
        if(breed==null) System.exit(0);

        Sex sex = (Sex) JOptionPane.showInputDialog(null,"Please select your preferred sex:",appName, JOptionPane.QUESTION_MESSAGE,icon,Sex.values(),Sex.FEMALE);
        if(sex==null) System.exit(0);
        DeSexed deSexed = (DeSexed) JOptionPane.showInputDialog(null,"Would you like your Pet to be de-sexed or not?",appName, JOptionPane.QUESTION_MESSAGE,icon,DeSexed.values(),DeSexed.YES);
        if(deSexed==null) System.exit(0);
        Purebred purebred  = (Purebred) JOptionPane.showInputDialog(null,"Would you like the Pet to be a purebred?",appName, JOptionPane.QUESTION_MESSAGE,null,Purebred.values(), "");
        if(purebred==null) System.exit(0);

        double[] ageRange = minMaxValues("What is the age (years) of the youngest Pet you'd like to adopt?","What is the age (years) of the oldest Pet you'd be willing to adopt?");

        Map<Criteria,Object> desiredFeatures = new HashMap<>();
        desiredFeatures.put(Criteria.TYPE, type);
        if(!breed.equals("NA")) desiredFeatures.put(Criteria.BREED,breed);
        if(!sex.equals(Sex.NA)) desiredFeatures.put(Criteria.SEX,sex);
        desiredFeatures.put(Criteria.DE_SEXED,deSexed);
        if(!purebred.equals(Purebred.NA)) desiredFeatures.put(Criteria.PUREBRED,purebred);

        if(type.equals(PetType.CAT)||type.equals(PetType.GUINEA_PIG)){
            Hair hair  = (Hair) JOptionPane.showInputDialog(null,"Please select from the following options","Pinkman's Pet Finder", JOptionPane.QUESTION_MESSAGE,null,Hair.values(), "");
            if(hair==null) System.exit(0);
            if(!hair.equals(Hair.NA)) desiredFeatures.put(Criteria.HAIR,hair);
        }
        double[] feeRange = minMaxValues("What is the lowest adoption fee you're interested in? ","What is the max. adoption fee you're willing to pay?");
        return new DreamPet(desiredFeatures,ageRange[0],ageRange[1],feeRange[0],feeRange[1]);
    }

    /**
     * a method to get the user to enter a value range (min - max)
     * @param minMessage the message to the user asking them to input a min value
     * @param maxMessage the message to the user asking them to input a max value
     * @return an int[] array where [0] is min and [1] is max
     */
    public static double[] minMaxValues(String minMessage, String maxMessage){
        double[] range = {-1,-1};
        while(range[0]<0) {
            String input = JOptionPane.showInputDialog(null, minMessage, appName, JOptionPane.QUESTION_MESSAGE);
            if(input==null) System.exit(0);
            try {
                range[0] = Double.parseDouble(input);
                if(range[0]<0) JOptionPane.showMessageDialog(null,"Min. must be >= 0.",appName, JOptionPane.ERROR_MESSAGE);
            }
            catch (NumberFormatException e){
                JOptionPane.showMessageDialog(null,"Invalid input. Please try again.", appName, JOptionPane.ERROR_MESSAGE);
            }
        }
        while(range[1]<range[0]) {
            String input = JOptionPane.showInputDialog(null, maxMessage, appName, JOptionPane.QUESTION_MESSAGE);
            if(input==null) System.exit(0);
            try {
                range[1] = Double.parseDouble(input);
                if(range[1]<range[0]) JOptionPane.showMessageDialog(null,"Max must be >= "+range[0],appName, JOptionPane.ERROR_MESSAGE);
            }
            catch (NumberFormatException e){
                JOptionPane.showMessageDialog(null,"Invalid input. Please try again.", appName, JOptionPane.ERROR_MESSAGE);
            }
        }
        return range;
    }

    /**
     * method to display  results (if there are any) to the user in the form of a drop-down list
     * allowing them to select and adopt a pet of their choice.
     * @param dreamPet a DreamPet object representing the user's selections
     */
    private static void processSearchResults(DreamPet dreamPet){
        List<Pet> potentialMatches = allPets.findMatch(dreamPet);
        if(potentialMatches.size()>0){
            Map<String,Pet> options = new HashMap<>();
            StringBuilder infoToShow = new StringBuilder("Matches found!! The following Pets meet your criteria: \n");
            for (Pet potentialMatch : potentialMatches) {
                infoToShow.append("\n").append(potentialMatch.toString(potentialMatch.dreamPet().getAllPetCriteriaAndValues()));
                options.put(potentialMatch.name() + " (" + potentialMatch.microchipNumber() + ")", potentialMatch);
            }
            String adopt = (String) JOptionPane.showInputDialog(null,infoToShow+"\nPlease select which " +
                    "Pet you'd like to adopt:",appName, JOptionPane.QUESTION_MESSAGE,icon,options.keySet().toArray(), "");
            if(adopt==null) System.exit(0);
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

    /**
     * provides Pinkman's Pets with a file containing the user's adoption request
     * @param person a Person object representing the user
     * @param Pet a Pet object representing the Pet that the user wants to adopt
     */
    private static void writeAdoptionRequestToFile(Person person, Pet Pet) {
        String filePath = person.name().replace(" ","_")+"_adoption_request.txt";
        Path path = Path.of(filePath);
        String lineToWrite = person.name()+" wishes to adopt "+Pet.name()+" ("+Pet.microchipNumber()+
                "). Their phone number is "+person.phoneNumber()+" and their email address is "+person.emailAddress();
        try {
            Files.writeString(path, lineToWrite);
        }catch (IOException io){
            System.out.println("File could not be written. \nError message: "+io.getMessage());
            System.exit(0);
        }
    }
}
