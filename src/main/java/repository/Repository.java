package repository;

import models.Vehicle;

import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;

public class Repository {

    private final Set<Vehicle> firstRoad;
    private final Set<Vehicle> secondRoad;

    public Repository() {
        this.firstRoad = new CopyOnWriteArraySet<>();
        this.secondRoad = new CopyOnWriteArraySet<>();
    }


    public void addVehicleToFirstRoad(Vehicle vehicle) {
        for (Vehicle vehicle1 : firstRoad) {
            if(vehicle.getRegistrationNumber().equals(vehicle1.getRegistrationNumber())){
                return;
            }
        }
        firstRoad.add(vehicle);
    }

    public void addVehicleToSecondRoad(Vehicle vehicle) {
        for (Vehicle vehicle1 : secondRoad) {
            if(vehicle.getRegistrationNumber().equals(vehicle1.getRegistrationNumber())){
                return;
            }
        }
        secondRoad.add(vehicle);
    }

    public int getSizeFirstRoad() {
        return this.firstRoad.size();
    }

    public int getSizeSecondRoad() {
        return this.secondRoad.size();
    }

    public Set<Vehicle> getFirstRoad() {
        return firstRoad;
    }

    public Set<Vehicle> getSecondRoad() {
        return secondRoad;
    }
}
