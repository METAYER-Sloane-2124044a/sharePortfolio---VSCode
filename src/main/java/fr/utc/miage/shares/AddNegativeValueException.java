package fr.utc.miage.shares;
public class AddNegativeValueException extends Exception{

    private String message;

    public AddNegativeValueException(String message){
        this.message = message;
    }  
}