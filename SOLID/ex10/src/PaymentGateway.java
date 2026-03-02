public class PaymentGateway implements PaymentChargeable {
    public String charge(String studentId, double amount) {
        return "TXN-9001";
    }
}
