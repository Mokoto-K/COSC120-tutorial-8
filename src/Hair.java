/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

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