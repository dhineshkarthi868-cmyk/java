public class ScannerStations {
    static class Station extends Thread {
        private final int stationId;
        private final int scripts;
        private int processed;

        Station(int stationId, int scripts) {
            super("Scanner-" + stationId);
            this.stationId = stationId;
            this.scripts = scripts;
        }
        @Override public void run() {
            for (int i = 1; i <= scripts; i++) {
                try { Thread.sleep(30 + stationId * 10); }
                catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println(getName() + " interrupted");
                    return;
                }
                processed++;
            }
            System.out.println(getName() + " processed " + processed + " scripts");
        }
        int getProcessed() { return processed; }
    }

    static class RunnableStation implements Runnable {
        private final int stationId;
        RunnableStation(int stationId) { this.stationId = stationId; }
        @Override public void run() {
            try {
                Thread.sleep(25L * stationId);
                System.out.println("Runnable station " + stationId + " completed");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Station[] stations = new Station[4];
        for (int i = 0; i < stations.length; i++) {
            stations[i] = new Station(i + 1, 10);
            stations[i].start();
        }
        for (Station station : stations) station.join();
        int total = 0;
        for (Station station : stations) {
            total += station.getProcessed();
            System.out.println(station.getName() + " alive after join? " + station.isAlive());
        }
        System.out.println("Total scripts processed: " + total);

        // The same station task can also be supplied through Runnable.
        Thread[] runnableStations = new Thread[4];
        for (int i = 0; i < runnableStations.length; i++) {
            runnableStations[i] = new Thread(new RunnableStation(i + 1));
            runnableStations[i].start();
        }
        for (Thread station : runnableStations) station.join();
        System.out.println("All Runnable-based stations completed.");
    }
}
