package Controller;

import Model.Card;
import Model.Cost;
import Repository.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author joj on 4/17/2019
 **/
public class Controller {
    private Repository repository;
    private Problem problem;
    private int payingCardIndex;
    private boolean payment;

    public Controller(Repository repository, Problem problem) {
        this.repository = repository;
        this.problem = problem;
        this.problem.initializeMap(repository.getCards());
        this.payingCardIndex = 1;
        this.payment = false;
    }

    public void run() {
        while(!payment) {
            Card payingCard = this.choosePayingCard();
            System.out.println("The card chosen to do the online transaction is: " + payingCard.toString());
            double total;
            do{
                total = runOneDay(payingCard);
            } while(payingCard.getCurrentAmount() < problem.getProductPrice() && total != 0);
            if (total != 0) {
                this.problem.updateTVA(payingCard);
                Map<Card, Cost> cardCostMap = problem.getCardCosts();
                System.out.println("Card Costs:");
                cardCostMap.keySet().forEach(card -> System.out.println(card + " " + problem.getCardCosts().get(card)));
                payment = true;
            }
        }
    }

    private double runOneDay(Card payingCard) {
        double totalAmountTransferred = this.getAvailableSortedCards(payingCard)
                                                    .stream()
                                                    .mapToDouble(card -> problem.getTransferableAmount(card))
                                                    .sum();
        System.out.println("Total amount to be transferred today on this card is: " + totalAmountTransferred);

        this.getAvailableSortedCards(payingCard)
                .forEach(card -> problem.transferMoney(payingCard, card));
        System.out.println("After transfers, the status of each card is:");
        this.repository.getCards().forEach(System.out::println);
        // increase current date by one day
        this.problem.addOneDay();
        return totalAmountTransferred;
    }

    /***
     * Get a list of all cards that are available for transferring money in the current day
     * @param payingCard Card
     * @return List<Card>
     */
    private List<Card> getAvailableSortedCards(Card payingCard) {
        return this.getSortedCards().stream()
                .filter(card -> card != payingCard)
                .filter(card -> card.getExpirationDate().compareTo(this.problem.getCurrentDateTime()) > 0)
                .collect(Collectors.toList());
    }

    /**
     * Choose the card that will eventually be used to make the online payment based on:
     *      • card having greatest withdrawal fee
     *          (! supposing there are no fees for online transactions
     *          but there are fees for transferring money between cards)
     *      • other cards previously chosen to be the paying card
     * @return Card
     */
    private Card choosePayingCard() {
        List<Card> cards = this.getSortedCards();
        return cards.get(cards.size() - this.payingCardIndex);
    }

    /***
     * Get list of all cards sorted ascending by fee
     * @return List<Card>
     */
    private List<Card> getSortedCards() {
        List<Card> cards = this.repository.getCards();
        cards.sort(Comparator.comparing(Card::getFee));
        return cards;
    }
}
