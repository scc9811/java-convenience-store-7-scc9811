package store.entity;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class FileReader {
    public List<String> readFile(String fileName) {
        try {
            Path path = Paths.get("src/main/resources/" + fileName);
            return Files.readAllLines(path);
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }
}
