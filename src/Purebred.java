

public enum Purebred {
    YES,NO,NA;
    public String toString(){
        return switch (this) {
            case YES -> "Yes";
            case NO -> "No";
            case NA -> "Not applicable";
        };
    }
}