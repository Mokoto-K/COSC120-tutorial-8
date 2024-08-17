
import java.util.HashMap;
import java.util.Map;

public class DreamPet {
    private final Map<Criteria,Object> petCriteria;
    private double minAge;
    private double maxAge;
    private double minFee;
    private double maxFee;

    /**
     * constructor used to create user's dream pet (not real pet)
     * @param petCriteria a mapping of criteria to values, e.g. Breed: Jack russell
     * @param minAge lowest age user would be willing to adopt
     * @param maxAge highest age user would be willing to adopt
     * @param minFee the lowest adoption fee the user is interested in
     * @param maxFee the highest adoption fee the user is willing to pay
     */
    public DreamPet(Map<Criteria,Object> petCriteria, double minAge, double maxAge, double minFee, double maxFee) {
        this.petCriteria=new HashMap<>(petCriteria);
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.minFee = minFee;
        this.maxFee = maxFee;
    }

    /**
     * constructor used to create real pet's dream pet features
     * @param petCriteria a mapping of criteria to values, e.g. Breed: Jack russell
     */
    public DreamPet(Map<Criteria,Object> petCriteria) {
        this.petCriteria = new HashMap<>(petCriteria);
    }

    public DreamPet(Map<Criteria,Object> petCriteria, double minAge, double maxAge) {
        this.petCriteria=new HashMap<>(petCriteria);
        this.minAge = minAge;
        this.maxAge = maxAge;
    }

    /**
     * access all the key-value pairs, e.g. breed: jack russell, sex: female, etc.
     * @return the entire mapping of criteria and their values
     */
    public Map<Criteria, Object> getAllPetCriteriaAndValues() {
        return new HashMap<>(petCriteria);
    }

    /**
     * @param key a criteria, e.g. breed
     * @return the value at a specified criteria, e.g. jack russell
     */
    public Object getValueAtCriteria(Criteria key){
        return getAllPetCriteriaAndValues().get(key);
    }

    /**
     * @return a 'dream' Pet's min age
     */
    public double getMinAge() {
        return minAge;
    }
    /**
     * @return a 'dream' Pet's max age
     */
    public double getMaxAge() {
        return maxAge;
    }

    /**
     * @return the lowest adoption fee the user is interested in
     */
    public double getMinFee() {
        return minFee;
    }

    /**
     * @return the highest adoption fee the user is willing to pay
     */
    public double getMaxFee() {
        return maxFee;
    }

    /**
     * method to return a description of generic DreamPet features
     * @return a formatted String description of the pet's dream features
     */
    public String getDreamPetDescription(Criteria[] criteria){
        StringBuilder description= new StringBuilder();
        for(Criteria key: criteria) description.append("\n").append(key).append(": ").append(getValueAtCriteria(key));
        return description.toString();
    }

    /**
     * method to compare two DreamPet objects against each other for compatibility
     * @param petCriteria an imaginary pet representing the user's criteria
     * @return true if matches, false if not
     */
    public boolean compareDreamPets(DreamPet petCriteria) {
        for(Criteria key : petCriteria.getAllPetCriteriaAndValues().keySet()) {
            if(!getValueAtCriteria(key).equals(petCriteria.getValueAtCriteria(key))) return false;
        }
        return true;
    }
}