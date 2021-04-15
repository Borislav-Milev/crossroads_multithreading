
import models.Car;
import models.Truck;
import models.Vehicle;
import repository.Repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;

public class FileUploader {

    private final Timer timer;
    private final Repository repository;
    private final Crossroads crossroads;
    private final FileReader fileReader;

    public FileUploader(Repository repository, Crossroads crossroads) {
        this.timer = new Timer();
        this.repository = repository;
        this.crossroads = crossroads;
        this.fileReader = new FileReader();
    }

    public void fillRepository() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                checkSize();
                fillRoad(1);
                fillRoad(2);
            }
        };
        this.timer.scheduleAtFixedRate(timerTask, 0, 15000);
    }

    private void fillRoad(int road) {
        String[] roadFile = this.fileReader.readFile(road).split(System.lineSeparator());
        for (String s : roadFile) {
            String[] line = s.split("-");
            String type = line[0].replaceAll("[^a-zA-Z]", "");
            String regNum = line[1].replaceAll("[^a-zA-Z0-9]", "");
            this.createVehicle(type, regNum, road);
        }
    }

    private void createVehicle(String type, String reg, int road) {
        Vehicle vehicle;
        if (type.equals(Car.class.getSimpleName())) {
            vehicle = new Car(reg);
        } else {
            vehicle = new Truck(reg);
        }
        if (road == 1) {
            for (String removedReg : this.crossroads.getRemovedVehiclesFirstRoad()) {
                if(removedReg.equals(reg)){
                    return;
                }
            }
            this.repository.addVehicleToFirstRoad(vehicle);
        } else {
            for (String removedReg : this.crossroads.getRemovedVehiclesSecondRoad()) {
                if(removedReg.equals(reg)){
                    return;
                }
            }
            this.repository.addVehicleToSecondRoad(vehicle);
        }
    }


    private void checkSize() {
        if (this.repository.getSizeFirstRoad() > 20) {
            System.out.printf("%s--Road%d: There is a traffic jam, vehicles waiting %d\n",
                    getTime(), 1, this.repository.getSizeFirstRoad());
        }
        if (this.repository.getSizeSecondRoad() > 20) {
            System.out.printf("%s--Road%d: There is a traffic jam, vehicles waiting %d\n",
                    getTime(), 2, this.repository.getSizeSecondRoad());
        }
    }

    private String getTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
    }
}
