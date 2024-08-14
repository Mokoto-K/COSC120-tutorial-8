
public enum PetType {
    DOG,CAT,GUINEA_PIG;

    @Override
    public String toString() {
        return switch(this){
            case DOG -> "Dog";
            case CAT -> "Cat";
            case GUINEA_PIG -> "Guinea pig";
        };
    }
}
