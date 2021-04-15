import models.Vehicle;
import repository.Repository;

import java.util.ArrayList;

public class SecondRoad implements Runnable {

    private final Crossroads crossroads;
    private Repository repository;

    public SecondRoad(Crossroads crossroads, Repository repository) {
        this.crossroads = crossroads;
        this.repository = repository;
    }

    @Override
    public void run() {
        while (true) {
            for (Vehicle vehicle : new ArrayList<>(this.repository.getSecondRoad())) {
                try {
                    this.crossroads.secondRoad(vehicle);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
