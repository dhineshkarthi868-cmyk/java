import java.util.*;

public class GenericLeaderboard {
    static final class Team {
        final String name; final int points, goalDiff;
        Team(String name,int points,int goalDiff){this.name=name;this.points=points;this.goalDiff=goalDiff;}
        public String toString(){return name+"("+points+","+goalDiff+")";}
    }
    static final class Player {
        final String name; final int points, goalDiff;
        Player(String name,int points,int goalDiff){this.name=name;this.points=points;this.goalDiff=goalDiff;}
        public String toString(){return name+"("+points+","+goalDiff+")";}
    }
    static final class Leaderboard<T> {
        private final List<T> items=new ArrayList<>();
        private final Comparator<? super T> order;
        Leaderboard(Comparator<? super T> order){this.order=Objects.requireNonNull(order);}
        void add(T item){items.add(Objects.requireNonNull(item));}
        List<T> ranking(){List<T> copy=new ArrayList<>(items); copy.sort(order); return copy;}
        void print(){System.out.println(ranking());}
    }
    public static void main(String[] args) {
        Comparator<Team> byRank=Comparator.comparingInt((Team x)->x.points).reversed()
            .thenComparing(Comparator.comparingInt((Team x)->x.goalDiff).reversed())
            .thenComparing(x->x.name,String.CASE_INSENSITIVE_ORDER);
        Leaderboard<Team> board=new Leaderboard<>(byRank);
        board.add(new Team("Panthers",9,4));
        board.add(new Team("Eagles",9,6));
        board.add(new Team("Cobras",6,1));
        board.add(new Team("Bisons",9,4));
        System.out.println("Q5 team ranking (expected Eagles, Bisons, Panthers, Cobras):");
        board.print();

        Comparator<Player> playerRank=Comparator.comparingInt((Player p)->p.points).reversed()
            .thenComparing(Comparator.comparingInt((Player p)->p.goalDiff).reversed())
            .thenComparing(p->p.name,String.CASE_INSENSITIVE_ORDER);
        Leaderboard<Player> players=new Leaderboard<>(playerRank);
        players.add(new Player("Arun",12,5)); players.add(new Player("Bala",12,3));
        System.out.println("Reusable player ranking:"); players.print();

        List<? extends Team> readOnlyView=board.ranking();
        System.out.println("Wildcard extends T allows reading ranked Team values: " + readOnlyView.size());
    }
}
