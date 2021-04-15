package repository;

import models.Vehicle;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Repository {

    private List<Vehicle> firstRoad;
    private List<Vehicle> secondRoad;
    private Lock lock = new ReentrantLock();
    private Lock lock2 = new ReentrantLock();

    public Repository() {
        this.firstRoad = new CopyOnWriteArrayList<>();
        this.secondRoad = new CopyOnWriteArrayList<>();
    }

    public List<Vehicle> getFirstRoad() {
        return firstRoad;
    }

    public List<Vehicle> getSecondRoad() {
        return secondRoad;
    }

    public void addVehicleToFirstRoad(Vehicle vehicle) {
        if(!firstRoad.isEmpty())
        System.out.println(firstRoad.get(0).getRegistrationNumber());
    }

    public void addVehicleToSecondRoad(Vehicle vehicle) {
    }

    public int getSizeFirstRoad() {
        return this.firstRoad.size();
    }

    public int getSizeSecondRoad() {
        return this.secondRoad.size();
    }
}
