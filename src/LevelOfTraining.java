/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

public enum LevelOfTraining {
    NONE,BASIC,INTERMEDIATE,ADVANCED,NA;

    public String toString(){
        return switch (this) {
            case NONE -> "No training at all";
            case BASIC -> "Sit, stay, lay down on instruction";
            case INTERMEDIATE -> "Play fetch, and will stop barking if instructed";
            case ADVANCED -> "perform tricks - handshake, play dead, jump over a bar";
            case NA -> "Not applicable";
        };
    }
}
