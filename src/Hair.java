
public enum Hair {
    HAIRLESS,LONG,SHORT,NA;

    public String toString(){
        return switch (this) {
            case HAIRLESS -> "Hairless";
            case LONG -> "Long-haired";
            case SHORT -> "Short-haired";
            default -> "Not Applicable";
        };
    }

}