import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface Subscription {

    // Moving app name that is used by all j option panes into the interface
    String appName = "Pinkman's Pets Pet Finder";

    // Moving the icon to be used by all j option panes into the interface
    ImageIcon icon = new ImageIcon("./icon.jpg");

    // price for premium subscription
    int premiumSubscriptionFee =0;

    // free voucher for sign up ammount
    int voucher = 100;

    // discount to be applied if you sign up
    float discount = 0.10f;

    // Gets all breeds for a type of animal
    DreamPet getUserInput(Set<String> allBreeds, PetType petType);

    // Gets a list of pets that marches the user criteria
    Pet displayResults(List<Pet> matchedPets, Criteria[] criteria);

    // Read the method name retard
    void placeAdoptionRequest(Pet usersChoice);

    default PetType getType() {
        PetType type = (PetType) JOptionPane.showInputDialog(null,"Please select the type of pet " +
                "you'd like to adopt.",appName, JOptionPane.QUESTION_MESSAGE,icon,PetType.values(), PetType.DOG);
        if(type==null) System.exit(0);
        return type;
    }

    default String getBreed(Set<String> allbreeds){
        String breed  = (String) JOptionPane.showInputDialog(null,"Please select your preferred " +
                "breed.",appName, JOptionPane.QUESTION_MESSAGE,icon,allPets.getAllBreeds(type).toArray(),"");
        if(breed==null) System.exit(0);
        return breed;
    }

    default Sex getSex(){
        Sex sex = (Sex) JOptionPane.showInputDialog(null,"Please select your preferred sex:"
                ,appName, JOptionPane.QUESTION_MESSAGE,icon,Sex.values(),Sex.FEMALE);
        if(sex==null) System.exit(0);
        return sex;
    }

    default DeSexed getDeSexed(){
        DeSexed deSexed = (DeSexed) JOptionPane.showInputDialog(null,"Would you like your Pet" +
                " to be de-sexed or not?",appName, JOptionPane.QUESTION_MESSAGE,icon,DeSexed.values(),DeSexed.YES);
        if(deSexed==null) System.exit(0);
        return deSexed;
    }

    default Purebred getPureBred(){
        Purebred purebred  = (Purebred) JOptionPane.showInputDialog(null,"Would you like the " +
                "Pet to be a purebred?",appName, JOptionPane.QUESTION_MESSAGE,null,Purebred.values(), "");
        if(purebred==null) System.exit(0);
        return purebred;
    }

    default Hair getHair(){
        Hair hair  = (Hair) JOptionPane.showInputDialog(null,"Please select from the following" +
                " options","Pinkman's Pet Finder", JOptionPane.QUESTION_MESSAGE,null,Hair.values(), "");
        if(hair==null) System.exit(0);
        return hair;
    }

    default double[] ageRange(){
        double[] ageRange = getminMaxValues("What is the age (years) of the youngest Pet you'd like to adopt?","What is the" +
                " age (years) of the oldest Pet you'd be willing to adopt?");
        return ageRange;
    }

    default double[] feeRange(){
        double[] feeRange = getminMaxValues("What is the lowest adoption fee you're interested in? ","What is the max. adoption" +
                " fee you're willing to pay?");
        return feeRange;
    }

    /**
     * a method to get the user to enter a value range (min - max)
     * @param minMessage the message to the user asking them to input a min value
     * @param maxMessage the message to the user asking them to input a max value
     * @return an int[] array where [0] is min and [1] is max
     */
    default double[] getminMaxValues(String minMessage, String maxMessage){
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

    default String name(){
        String name = JOptionPane.showInputDialog(null, "Please enter your full name.", appName, JOptionPane.QUESTION_MESSAGE);
        if(name==null) System.exit(0);
        return name;
    }

    default String phoneNumber(){
        String phoneNumber = JOptionPane.showInputDialog("Please enter your phone number (10-digit number in the format 0412345678): ");
        if(phoneNumber==null) System.exit(0);
        return phoneNumber;
    }

    default String email(){
        String email = JOptionPane.showInputDialog(null, "Please enter your email address.", appName, JOptionPane.QUESTION_MESSAGE);
        if (email == null) System.exit(0);
        return email;
    }

    /**
     * provides Pinkman's Pets with a file containing the user's adoption request
     * @param person a Person object representing the user
     * @param pet a Pet object representing the Pet that the user wants to adopt
     */
    default void writeAdoptionRequestToFile(String preface, Person person, Pet pet) {
        String filePath = preface+person.name().replace(" ","_")+"_adoption_request.txt";
        Path path = Path.of(filePath);
        String lineToWrite = person.name()+" wishes to adopt "+pet.name()+" ("+pet.microchipNumber()+
                "). Their phone number is "+person.phoneNumber()+" and their email address is "+person.emailAddress();
        try {
            Files.writeString(path, lineToWrite);
        }catch (IOException io){
            System.out.println("File could not be written. \nError message: "+io.getMessage());
            System.exit(0);
        }
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

}
