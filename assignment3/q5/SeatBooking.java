import java.util.concurrent.CountDownLatch;

public class SeatBooking {
    private final boolean[] booked;
    private int bookingsMade = 0;

    SeatBooking(int seats) { booked = new boolean[seats]; }

    // The synchronized method makes check-then-book one atomic operation.
    public synchronized boolean book(int seat) {
        if (seat < 0 || seat >= booked.length || booked[seat]) return false;
        booked[seat] = true;
        bookingsMade++;
        return true;
    }
    public synchronized int getBookingsMade() { return bookingsMade; }

    public static void main(String[] args) throws InterruptedException {
        SeatBooking screen = new SeatBooking(10);
        int usersCount = 100;
        Thread[] users = new Thread[usersCount];
        CountDownLatch ready = new CountDownLatch(usersCount);
        CountDownLatch start = new CountDownLatch(1);

        for (int i = 0; i < users.length; i++) {
            final int seat = i % 10;
            users[i] = new Thread(() -> {
                ready.countDown();
                try {
                    start.await();
                    screen.book(seat);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
            users[i].start();
        }
        ready.await();
        start.countDown();
        for (Thread user : users) user.join();
        System.out.println("Seats sold: " + screen.getBookingsMade());
    }
}
