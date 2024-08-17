

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

    private final static String filePath = "./allPets.txt";
    private static AllPets allPets;

    /**
     * main method used to allow the user to search Pinkman's database of Pets, and place an adoption request
     *
     * @param args none required
     */
    public static void main(String[] args) {
        allPets = loadPets();
        JOptionPane.showMessageDialog(null, "Welcome to Pinkman's Pets Pet Finder!\n\tTo start, click OK.", Subscription.appName, JOptionPane.QUESTION_MESSAGE, Subscription.icon);
        String account = (String) JOptionPane.showInputDialog("You got an account brah");

        Subscription user;
        if (account.equalsIgnoreCase("yes")) user = new Premium("./premiumUser.txt");
        else user = new Basic(3);

        PetType type = user.getType();

        DreamPet petCriteria = user.getUserInput(allPets.getAllBreeds(type), type);

        processSearchResults(petCriteria, user);
        System.exit(0);
    }

    /**
     * method to load all Pet data from file, storing it as Pet objects in an instance of AllPets
     *
     * @return an AllPets object - functions as database of Pets, with associated methods
     */
    private static AllPets loadPets() {
        AllPets allPets = new AllPets();
        Path path = Path.of(filePath);

        List<String> petData = null;
        try {
            petData = Files.readAllLines(path);
        } catch (IOException io) {
            System.out.println("Could not load the file. \nError message: " + io.getMessage());
            System.exit(0);
        }
        for (int i = 1; i < petData.size(); i++) {
            String[] elements = petData.get(i).split(",");
            PetType type = null;
            try {
                type = PetType.valueOf(elements[0].toUpperCase().replace(" ", "_"));
            } catch (IllegalArgumentException e) {
                System.out.println("Error in file. Type of pet data could not be parsed for pet on line " + (i + 1) + ". Terminating. \nError message: " + e.getMessage());
                System.exit(0);
            }

            String name = elements[1];
            long microchipNumber = 0;
            try {
                microchipNumber = Long.parseLong(elements[2]);
            } catch (NumberFormatException n) {
                System.out.println("Error in file. Microchip number could not be parsed for Pet on line " + (i + 1) + ". Terminating. \nError message: " + n.getMessage());
                System.exit(0);
            }

            Sex sex = Sex.valueOf(elements[3].toUpperCase());
            DeSexed deSexed = DeSexed.valueOf(elements[4].toUpperCase()); //add exception handling here

            int age = 0;
            try {
                age = Integer.parseInt(elements[5]);
            } catch (NumberFormatException n) {
                System.out.println("Error in file. Age could not be parsed for Pet on line " + (i + 1) + ". Terminating. \nError message: " + n.getMessage());
                System.exit(0);
            }

            String breed = elements[6].toLowerCase();
            Purebred purebred = Purebred.valueOf(elements[7].toUpperCase()); //add exception handling here

            double adoptionFee = 0;
            try {
                adoptionFee = Double.parseDouble(elements[8]);
            } catch (NumberFormatException n) {
                System.out.println("Error in file. Adoption fee could not be parsed for Pet on line " + (i + 1) + ". Terminating. \nError message: " + n.getMessage());
                System.exit(0);
            }

            Hair hair = Hair.valueOf(elements[9].toUpperCase()); //add exception handling here
            LevelOfTraining trainingLevel = LevelOfTraining.valueOf(elements[10].toUpperCase()); //add exception handling here
            int dailyExercise = 0;
            if (!elements[11].equalsIgnoreCase("NA"))
                try {
                    dailyExercise = Integer.parseInt(elements[11]);
                } catch (NumberFormatException n) {
                    System.out.println("Error in file. Exercise minutes could not be parsed for Pet on line " + (i + 1) + ". Terminating. \nError message: " + n.getMessage());
                    System.exit(0);
                }

            Map<Criteria, Object> petCriteria = new HashMap<>();
            petCriteria.put(Criteria.TYPE, type);
            petCriteria.put(Criteria.SEX, sex);
            petCriteria.put(Criteria.DE_SEXED, deSexed);
            petCriteria.put(Criteria.BREED, breed);
            petCriteria.put(Criteria.PUREBRED, purebred);
            petCriteria.put(Criteria.HAIR, hair);
            petCriteria.put(Criteria.TRAINING_LEVEL, trainingLevel);
            petCriteria.put(Criteria.DAILY_EXERCISE, dailyExercise);

            DreamPet dreamPet = new DreamPet(petCriteria);
            Pet Pet = new Pet(name, microchipNumber, age, adoptionFee, dreamPet);

            allPets.addPet(Pet);
        }
        return allPets;
    }

    /**
     * method to display  results (if there are any) to the user in the form of a drop-down list
     * allowing them to select and adopt a pet of their choice.
     *
     * @param dreamPet a DreamPet object representing the user's selections
     */
    private static void processSearchResults(DreamPet dreamPet, Subscription user) {
        List<Pet> potentialMatches = allPets.findMatch(dreamPet);
        if (!potentialMatches.isEmpty()) {
            Pet chosenPet = user.displayResults(potentialMatches, Criteria.values());
            user.placeAdoptionRequest(chosenPet);
        } else {
            JOptionPane.showMessageDialog(null, "Unfortunately none of our pets meet your criteria :(" +
                    "\n\tTo exit, click OK.", Subscription.appName, JOptionPane.QUESTION_MESSAGE, Subscription.icon);
        }
    }
}

