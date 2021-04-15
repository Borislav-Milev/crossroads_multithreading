import models.Vehicle;
import repository.Repository;

public class SecondRoad implements Runnable {

    private final Crossroads crossroads;
    private final Repository repository;

    public SecondRoad(Crossroads crossroads, Repository repository) {
        this.crossroads = crossroads;
        this.repository = repository;
    }

    @Override
    @SuppressWarnings("InfiniteLoopStatement")
    public void run() {
        while (true) {
            for (Vehicle vehicle : this.repository.getSecondRoad()) {
                try {
                    this.crossroads.secondRoad(vehicle);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
