
public enum DeSexed {
    YES,NO;

    /**
     * here's another way the switch statement can be used
     * @return a prettified version of the relevant enum constant
     */
    public String toString() {
        String prettified = "NA";
        switch (this) {
            case YES -> prettified =  "Yes";
            case NO -> prettified = "No";
        }
        return prettified;
    }
}
