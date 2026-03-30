package movie_ticket_booking;

public class CardPayment implements PaymentProcessor {
    private String cardNumber;

    public CardPayment(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public boolean pay(double amount) {
        System.out.println("Card payment of Rs." + amount + " from card ending " + cardNumber.substring(cardNumber.length() - 4) + " — SUCCESS");
        return true;
    }

    public boolean refund(double amount) {
        System.out.println("Card refund of Rs." + amount + " to card ending " + cardNumber.substring(cardNumber.length() - 4) + " — SUCCESS");
        return true;
    }

    public PaymentMode getMode() { return PaymentMode.CARD; }
}
