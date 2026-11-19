import java.util.Scanner;

class InvalidAmountException extends Exception {
    InvalidAmountException(String message) { super(message); }
}
class InsufficientFundsException extends Exception {
    InsufficientFundsException(String message) { super(message); }
}
class CardReaderException extends Exception {
    CardReaderException(String message) { super(message); }
}

class ATM {
    private double balance = 5000.0;
    private boolean cardReaderAvailable = true;

    double withdraw(String accountNumber, double amount)
            throws InvalidAmountException, InsufficientFundsException, CardReaderException {
        if (!cardReaderAvailable) throw new CardReaderException("Card reader file/device is unavailable");
        if (accountNumber == null || !accountNumber.matches("\\d{6,12}"))
            throw new IllegalArgumentException("Account number must contain 6 to 12 digits");
        if (amount <= 0) throw new InvalidAmountException("Withdrawal amount must be positive");
        if (amount > balance) throw new InsufficientFundsException("Insufficient account balance");
        balance -= amount;
        return balance;
    }

    void setCardReaderAvailable(boolean available) { cardReaderAvailable = available; }
}

public class ATMExceptionHandling {
    public static void main(String[] args) {
        ATM atm = new ATM();
        String[] accounts = {"123456", "123456", "123456", "12A456", "123456"};
        double[] amounts = {1000, -50, 9000, 100, 0};

        for (int i = 0; i < amounts.length; i++) {
            System.out.println("\\nTest " + (i + 1));
            try {
                double remaining = atm.withdraw(accounts[i], amounts[i]);
                System.out.printf("Withdrawal successful. Balance: %.2f%n", remaining);
            } catch (CardReaderException e) {
                System.out.println("Card/device error: " + e.getMessage());
            } catch (InvalidAmountException e) {
                System.out.println("Invalid amount: " + e.getMessage());
            } catch (InsufficientFundsException e) {
                System.out.println("Transaction declined: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Input error: " + e.getMessage());
            } finally {
                System.out.println("Transaction attempt completed; resources/status must be reset.");
            }
        }
    }
}
