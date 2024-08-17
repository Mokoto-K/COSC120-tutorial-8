import javax.swing.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Basic implements Subscription{

    Map<Criteria, Object> criteria = new HashMap<>();

    int numberOfSearchResultsToShow;

    public Basic(int numberOfSearchResultsToShow){

        this.numberOfSearchResultsToShow = numberOfSearchResultsToShow;
        this.criteria = new HashMap<>();
    }

    @Override
    public DreamPet getUserInput(Set<String> allBreeds, PetType petType) {

        criteria.put(Criteria.TYPE, petType);

        String breed = getBreed(allBreeds);
        if (!breed.equals("NA")) {criteria.put(Criteria.BREED, breed); }

        criteria.put(Criteria.DE_SEXED, getDeSexed());

        Sex sex = getSex();
        if (!sex.equals(Sex.NA)) {criteria.put(Criteria.SEX, getSex()); }

        double[] age = getminMaxValues("smol Age", "big age");

        JOptionPane.showMessageDialog(null, "Yo buy premium you bitch");

        return new DreamPet(criteria, age[0], age[1]);
    }

    @Override
    public Pet displayResults(List<Pet> potentialMatches, Criteria[] criteria) {
        Map<String,Pet> options = new HashMap<>();
        int matches = potentialMatches.size();
        StringBuilder infoToShow = new StringBuilder(matches + "Matches found!! The following Pets meet your criteria: \n");
        infoToShow.append("Since you're basic, you only get 3 pets to view, dont be a basic");

        for (int i=1; i <3; i++) {
            Pet potentialMatch = potentialMatches.get(i);
            infoToShow.append("\n").append(potentialMatch.toString(criteria));
            options.put(potentialMatch.name() + " (" + potentialMatch.microchipNumber() + ")", potentialMatch);
        }
        String adopt = (String) JOptionPane.showInputDialog(null,infoToShow+"\nPlease select which " +
                "Pet you'd like to adopt:",appName, JOptionPane.QUESTION_MESSAGE,icon,options.keySet().toArray(), "");
        if(adopt==null) System.exit(0);

        float discount = (float) options.get(adopt).adoptionFee() / 100;
        JOptionPane.showMessageDialog(null,"You could get " +  discount + "% off if you weren't basic, plus a hundo voucher" );
        return options.get(adopt);
    }

    @Override
    public void placeAdoptionRequest(Pet usersChoice) {
            String name = name();
            String secondemail = email();
            String phoneNumber = phoneNumber();
            Person user = new Person(name, secondemail, phoneNumber);
            writeAdoptionRequestToFile("unverified", user, usersChoice);
        }
    }

