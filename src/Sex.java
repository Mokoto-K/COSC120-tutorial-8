

public enum Sex {

    MALE, FEMALE, NA;

    /**
     * @return a prettified version of the relevant enum constant
     */
    public String toString() {
        return switch (this) {
            case MALE -> "Male";
            case FEMALE -> "Female";
            case NA -> "Any sex will do";
        };
    }
}
