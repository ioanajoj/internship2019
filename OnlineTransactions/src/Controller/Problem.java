package Controller;

import Model.Card;
import Model.Cost;
import Model.Exceptions.DateTimeParseException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author joj on 4/19/2019
 **/
public class Problem {
    private double productPrice;
    private double TVA;
    private Date currentDateTime;
    private Map<Card, Cost> cardCosts;

    public Problem(String fileName) throws DateTimeParseException {
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String[] parameters = bufferedReader.readLine().split("=");
            this.productPrice = Double.parseDouble(parameters[1]);

            parameters = bufferedReader.readLine().split("=");
            this.TVA = Double.parseDouble(parameters[1]);

            parameters = bufferedReader.readLine().split("=");
            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy hh:mm");
            this.currentDateTime = formatter.parse(parameters[1]);
        } catch (ParseException e) {
        throw new DateTimeParseException("Current Date and Time do not match the format (dd.MM.yyyy hh:mm).");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initialize map of costs for given list of cards
     * @param cardList List<Card>
     */
    void initializeMap(List<Card> cardList) {
        cardCosts = new HashMap<>();
        cardList.forEach(card -> this.cardCosts.put(card, new Cost(0, 0)));
    }

    /**
     * Here is me over-engineering stuff, trying to keep the logic out of the model :)
     * Get the maximum transferable amount from the given card in one day
     * @return double
     */
    double getTransferableAmount(Card card) {
        if(card.getLimit() + card.getFee() * card.getLimit() > card.getCurrentAmount())
            return card.getCurrentAmount() - card.getFee() * card.getCurrentAmount();
        else
            return card.getLimit();
    }

    /**
     * Update the cost in the map of costs for given card
     * @param payer Card
     * @param transferableAmount double
     */
    private void updateFee(Card payer, double transferableAmount) {
        double oldFee = cardCosts.get(payer).getFee();
        double newFee = oldFee + transferableAmount * payer.getFee();
        this.cardCosts.replace(payer, new Cost(newFee, 0));
    }

    /**
     * Update the TVA amount for the given card
     * @param card Card
     */
    void updateTVA(Card card) {
        double TVAamount = (this.TVA * this.productPrice) / 100;
        this.cardCosts.replace(card, new Cost(0, TVAamount));
    }

    /**
     * Given a payer and a paying card, transfer the maximum transferable amount of money
     * @param recipient Card
     * @param payer Card
     */
    void transferMoney(Card recipient, Card payer) {
        double transferableAmount = this.getTransferableAmount(payer);
        // set new amount for payer
        payer.setCurrentAmount(payer.getCurrentAmount() - (transferableAmount + payer.getFee() * transferableAmount));
        // set new amount for paying
        double currentRecipientAmount = recipient.getCurrentAmount();
        currentRecipientAmount += transferableAmount;
        recipient.setCurrentAmount(currentRecipientAmount);

        updateFee(payer, transferableAmount);
    }

    Map<Card, Cost> getCardCosts() {
        return cardCosts;
    }

    /**
     * Add one day to the current date time
     */
    void addOneDay() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(this.currentDateTime);
        cal.add(Calendar.DATE, 1);
    }

    double getProductPrice() {
        return productPrice;
    }

    void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    double getTVA() {
        return TVA;
    }

    void setTVA(int TVA) {
        this.TVA = TVA;
    }

    Date getCurrentDateTime() {
        return currentDateTime;
    }

    void setCurrentDateTime(Date currentDateTime) {
        this.currentDateTime = currentDateTime;
    }
}
