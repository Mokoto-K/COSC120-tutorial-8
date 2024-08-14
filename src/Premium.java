import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Premium implements Subscription{

    Map<Criteria, Object> criteria = new HashMap<>();

    String premiumUserDataFile;

    Map<String, Person> premiumUserAccounts = new HashMap<>();

    public Premium(String premiumUserDataFile){
        premiumUserDataFile = "premiumUser.txt";
        criteria.put(Criteria.TYPE, getType());
        criteria.put(Criteria.BREED, getBreed());
        criteria.put(Criteria.SEX, getSex());
        criteria.put(Criteria.DE_SEXED, getDeSexed());
        criteria.put(Criteria.PUREBRED, getPureBred());
        criteria.put(Criteria.HAIR, getHair());
    }

    @Override
    public DreamPet getUserInput(Set<String> dogBreeds, PetType petType) {
        return null;
    }

    @Override
    public Pet displayResults(List<Pet> matchedPets, Criteria[] criteria) {
        return null;
    }

    @Override
    public void placeAdoptionRequest(Pet usersChoice) {

    }
}
