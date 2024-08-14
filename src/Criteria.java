
public enum Criteria {
    TYPE, SEX, DE_SEXED, BREED, PUREBRED, HAIR, TRAINING_LEVEL, DAILY_EXERCISE;

    public String toString() {
        return switch (this) {
            case TYPE -> "Type";
            case SEX -> "Sex";
            case DE_SEXED -> "De-sexed";
            case BREED -> "Breed";
            case PUREBRED -> "Purebred";
            case HAIR -> "Type of hair";
            case TRAINING_LEVEL -> "Level of training";
            case DAILY_EXERCISE -> "Daily exercise (minutes)";
        };
    }
}