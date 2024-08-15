import javax.swing.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Premium implements Subscription{

    Map<Criteria, Object> criteria = new HashMap<>();

    String premiumUserDataFile;

    Map<String, Person> premiumUserAccounts = new HashMap<>();

    public Premium(String premiumUserDataFile){
        this.premiumUserDataFile = "premiumUser.txt";
        this.criteria = new HashMap<>();
        this.premiumUserAccounts = new HashMap<>();

    }

    @Override
    public DreamPet getUserInput(Set<String> allBreeds, PetType petType) {

        criteria.put(Criteria.TYPE, petType);

        String breed = getBreed(allBreeds);
        if (!breed.equals("NA")) {criteria.put(Criteria.BREED, breed); }

        Sex sex = getSex();
        if (!sex.equals(Sex.NA)) {criteria.put(Criteria.SEX, getSex()); }

        Purebred purebred = getPureBred();
        if (!purebred.equals(Purebred.NA)) {criteria.put(Criteria.PUREBRED, getPureBred()); }

        criteria.put(Criteria.DE_SEXED, getDeSexed());
        if (!petType.equals(PetType.DOG)){criteria.put(Criteria.HAIR, getHair()); }

        double[] price = getminMaxValues("Min", "Max");
        double[] age = getminMaxValues("smol Age", "big age");

        return new DreamPet(criteria, price[0], price[1], age[0], age[1]);
    }

    @Override
    public Pet displayResults(List<Pet> potentialMatches, Criteria[] criteria) {

        Map<String,Pet> options = new HashMap<>();

        StringBuilder infoToShow = new StringBuilder("Matches found!! The following Pets meet your criteria: \n");
        for (Pet potentialMatch : potentialMatches) {
            infoToShow.append("\n").append(potentialMatch.toString(criteria));
            options.put(potentialMatch.name() + " (" + potentialMatch.microchipNumber() + ")", potentialMatch);
        }
        String adopt = (String) JOptionPane.showInputDialog(null,infoToShow+"\nPlease select which " +
                "Pet you'd like to adopt:",appName, JOptionPane.QUESTION_MESSAGE,icon,options.keySet().toArray(), "");
        if(adopt==null) System.exit(0);

        return options.get(adopt);
    }

    public boolean loadPremiumData() {
        Path path = Path.of(premiumUserAccounts.toString());

        List<String> userData = null;
        try {
             userData = Files.readAllLines(path);
        } catch (Exception e) {
            System.out.println("It broke" + e);
            return false;
        }
        for (String s : userData){
            String[] temp = s.split(",");

            premiumUserAccounts.put(temp[0], new Person(temp[1], temp[2], temp[0]));
        }
        return true;
        }

    @Override
    public void placeAdoptionRequest(Pet usersChoice) {
        String email = JOptionPane.showInputDialog("Enter email");

        if (loadPremiumData()) {
            if (!premiumUserAccounts.containsKey(email)) {
                JOptionPane.showMessageDialog(null, "You dont exist");
                System.exit(0);
            }
            Person user = premiumUserAccounts.get(email);
            JOptionPane.showMessageDialog(null, "You get free monies and discount if you buy");
            writeAdoptionRequestToFile("premium_user", user, usersChoice);
        } else {
            String name = name();
            String secondemail = email();
            String phoneNumber = phoneNumber();
            Person user = new Person(name, secondemail, phoneNumber);
            writeAdoptionRequestToFile("unverified", user, usersChoice);
        }

    }
}
