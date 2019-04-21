package Model;

import Model.Exceptions.DateTimeParseException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author joj on 4/19/2019
 **/
public class Card {
    private String type;
    private double fee;
    private int limit;
    private Date expirationDate;
    private double currentAmount;

    public Card(String type, double fee, int limit, Date expirationDate, int currentAmount) {
        this.type = type;
        this.fee = fee;
        this.limit = limit;
        this.expirationDate = expirationDate;
        this.currentAmount = currentAmount;
    }

    public Card(String fileRow) throws DateTimeParseException {
        String[] cardInfo = fileRow.split(",");
        this.type = cardInfo[0];
        this.fee = Double.parseDouble(cardInfo[1]);
        this.limit = Integer.parseInt(cardInfo[2]);
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        try {
            this.expirationDate = formatter.parse(cardInfo[3]);
            this.currentAmount = Integer.parseInt(cardInfo[4]);
        } catch (ParseException e) {
            throw new DateTimeParseException("Card expiration date does not match the format (dd.MM.yyyy).");
        }
    }

    @Override
    public String toString() {
        return "Card{" +
                "type='" + type + '\'' +
                ", fee=" + fee +
                ", limit=" + limit +
                ", expirationDate=" + expirationDate +
                ", currentAmount=" + currentAmount +
                '}';
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public double getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(double currentAmount) {
        this.currentAmount = currentAmount;
    }
}
