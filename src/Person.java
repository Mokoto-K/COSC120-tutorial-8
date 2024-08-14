

/**
 * record to represent a Person object with name, ph num and email address
 * @param name         the program user's full name
 * @param phoneNumber  the program user's 10-digit phone number
 * @param emailAddress the program user's email address
 */
public record Person(String name, String phoneNumber, String emailAddress) {}