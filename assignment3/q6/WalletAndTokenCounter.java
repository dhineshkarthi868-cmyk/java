class Wallet {
    private final String owner;
    private int balance;
    Wallet(String owner, int balance) { this.owner = owner; this.balance = balance; }
    String getOwner() { return owner; }
    int getBalance() { return balance; }
    void debit(int amount) { balance -= amount; }
    void credit(int amount) { balance += amount; }
}
class WalletTransfer {
    // Global lock ordering by owner name prevents circular wait.
    static void transfer(Wallet a, Wallet b, int amount) {
        Wallet first = a.getOwner().compareTo(b.getOwner()) < 0 ? a : b;
        Wallet second = first == a ? b : a;
        synchronized (first) {
            synchronized (second) {
                if (a.getBalance() >= amount) {
                    a.debit(amount);
                    b.credit(amount);
                }
            }
        }
    }
}
class TokenCounter {
    private final int[] buffer = new int[5];
    private int count = 0, in = 0, out = 0;

    public synchronized void put(int token) throws InterruptedException {
        while (count == buffer.length) wait();
        buffer[in] = token;
        in = (in + 1) % buffer.length;
        count++;
        notifyAll();
    }
    public synchronized int take() throws InterruptedException {
        while (count == 0) wait();
        int token = buffer[out];
        out = (out + 1) % buffer.length;
        count--;
        notifyAll();
        return token;
    }
}
public class WalletAndTokenCounter {
    public static void main(String[] args) throws InterruptedException {
        Wallet alice = new Wallet("Alice", 1000);
        Wallet bob = new Wallet("Bob", 1000);
        Thread t1 = new Thread(() -> { for (int i=0;i<1000;i++) WalletTransfer.transfer(alice,bob,1); });
        Thread t2 = new Thread(() -> { for (int i=0;i<1000;i++) WalletTransfer.transfer(bob,alice,1); });
        t1.start(); t2.start(); t1.join(); t2.join();
        System.out.println("Wallet total: " + (alice.getBalance()+bob.getBalance()));

        TokenCounter counter = new TokenCounter();
        int[] collected = new int[2];
        Thread cook = new Thread(() -> {
            try { for (int token=1; token<=20; token++) counter.put(token); }
            catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        });
        Thread[] students = new Thread[2];
        for (int s=0;s<2;s++) {
            final int id=s;
            students[s] = new Thread(() -> {
                try { for (int k=0;k<10;k++) collected[id] += counter.take(); }
                catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            });
        }
        cook.start();
        for (Thread student : students) student.start();
        cook.join();
        for (Thread student : students) student.join();
        System.out.println("Sum of tokens collected: " + (collected[0]+collected[1]) + " (expected 210)");
    }
}
