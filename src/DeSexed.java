/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

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
