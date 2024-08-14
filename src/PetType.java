/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */
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
