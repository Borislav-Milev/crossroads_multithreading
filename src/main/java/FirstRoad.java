import models.Vehicle;
import repository.Repository;


public class FirstRoad implements Runnable {

    private final Crossroads crossroads;
    private final Repository repository;

    public FirstRoad(Crossroads crossroads, Repository repository) {
        this.crossroads = crossroads;
        this.repository = repository;
    }

    @Override
    @SuppressWarnings("InfiniteLoopStatement")
    public void run() {
        while (true) {
            for (Vehicle vehicle : this.repository.getFirstRoad()) {
                try {
                    this.crossroads.firstRoad(vehicle);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
