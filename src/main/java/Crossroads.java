import models.Truck;
import models.Vehicle;
import repository.Repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Crossroads extends ReentrantLock {
    private final Lock lock;
    private final Timer timer;
    private boolean switchLights;
    private final Condition cond1;
    private final Condition cond2;
    private final Repository repository;
    private final List<String> removedVehiclesFirstRoad;
    private final List<String> removedVehiclesSecondRoad;

    public Crossroads(Repository repository) {
        this.lock = new ReentrantLock(false);
        this.timer = new Timer();
        this.cond1 = this.lock.newCondition();
        this.cond2 = this.lock.newCondition();
        this.repository = repository;
        this.removedVehiclesFirstRoad = new CopyOnWriteArrayList<>();
        this.removedVehiclesSecondRoad = new CopyOnWriteArrayList<>();
        this.timer();
    }

    public void firstRoad(Vehicle vehicle) throws InterruptedException {
        this.lock.lock();
        try {
            while (this.switchLights){
                this.cond2.await();
            }
            this.cond1.signal();
            this.repository.getFirstRoad().remove(vehicle);
            this.removedVehiclesFirstRoad.add(vehicle.getRegistrationNumber());
            vehiclePassing(vehicle, 1);

        }finally {
            this.lock.unlock();
        }
    }

    public void secondRoad(Vehicle vehicle) throws InterruptedException {
        this.lock.lock();
        try{
            while (!this.switchLights){
                this.cond1.await();
            }
            vehiclePassing(vehicle, 2);
            this.repository.getSecondRoad().remove(vehicle);
            this.removedVehiclesSecondRoad.add(vehicle.getRegistrationNumber());
            this.cond2.signal();
        } finally {
            this.lock.unlock();
        }
    }

    private void vehiclePassing(Vehicle vehicle, int road) throws InterruptedException {
        String vehicleType = vehicle.getClass().getSimpleName();
        System.out.printf("Road %d: %s %s is passing...\n", road, vehicleType, vehicle.getRegistrationNumber());
        if (vehicleType.equals(Truck.class.getSimpleName())) {
            Thread.sleep(7000);
        } else {
            Thread.sleep(5000);
        }
        System.out.printf("%s -- Road %d: %s %s passed\n", getTime(), road, vehicleType, vehicle.getRegistrationNumber());
    }

    private void timer() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                switchLights = !switchLights;
                if (switchLights) {
                    System.out.println(getTime() + "--Traffic light: road1 -> RED");
                    System.out.println(getTime() + "--Traffic light: road2 -> GREEN");
                } else {
                    System.out.println(getTime() + "--Traffic light: road1 -> GREEN");
                    System.out.println(getTime() + "--Traffic light: road2 -> RED");
                }
            }
        };
        this.timer.scheduleAtFixedRate(timerTask, 0, 10000);
    }
    private String getTime(){
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
    }

    public List<String> getRemovedVehiclesFirstRoad() {
        return removedVehiclesFirstRoad;
    }

    public List<String> getRemovedVehiclesSecondRoad() {
        return removedVehiclesSecondRoad;
    }
}
