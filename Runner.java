import island.Island;

public class Runner {
    public static void main(String[] args) {
        Island island = new Island();
        island.print();
        island.generate();
        island.print();
        island.start();
    }
}
