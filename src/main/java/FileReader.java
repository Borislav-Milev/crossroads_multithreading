
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileReader {

    private static final String FILE_PATH = System.getProperty("user.dir") + "\\src\\main\\resources\\";
    private static final String roadOneFile = FILE_PATH + "Road1.txt";
    private static final String roadTwoFile = FILE_PATH + "Road2.txt";

    public String readFile(int witchFile) {
        try {
            if (witchFile == 1) {
                return Files.readString(Paths.get(roadOneFile));
            } else {
                return Files.readString(Paths.get(roadTwoFile));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
