import repository.Repository;

public class Main {
    public static void main(String[] args) {
        Repository repository = new Repository();
        Crossroads crossroads = new Crossroads(repository);
        FileUploader fileUploader = new FileUploader(repository, crossroads);
        fileUploader.fillRepository();


        Thread firstRoadThread = new Thread(new FirstRoad(crossroads, repository));
        Thread secondRoadThread = new Thread(new SecondRoad(crossroads, repository));
        firstRoadThread.start();
        secondRoadThread.start();
    }
}
