/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */
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