package com.example.rubby.Model;

public class CreditCardModel {

    public int ID;
    public String cardNumber;
    public String cardValidity;
    public String securityCode;
    public String ownerName;

    public CreditCardModel(int ID, String cardNumber , String cardValidity, String securityCode, String ownerName){
        this.ID = ID;
        this.cardNumber = cardNumber;
        this.cardValidity = cardValidity;
        this.securityCode = securityCode;
        this.ownerName = ownerName;
    }

    public String getEncodedNumber(String number){
        String cardType = "Visa ";
        String stars = "**** ";
        String numbers = number.substring(number.length()- 4);
        return cardType + stars + numbers;
    }

    public String getEncodedCardValidity(String code){
        String title = "Срок действия карты ";
        String stars = "**/";
        String numbers = code.substring(code.length()- 2);
        return title + stars + numbers;
    }

}
