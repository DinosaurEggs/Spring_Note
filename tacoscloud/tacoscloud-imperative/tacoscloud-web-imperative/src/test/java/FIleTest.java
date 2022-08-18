import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.util.FileSystemUtils;

import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
public class FIleTest
{
    @Test
    public void fileRead()
    {
        Path path = Paths.get("D:/a.png");
        log.info(path.toString());
    
//        Files.copy(path, Paths.get("D:/Cache/a.png"));
        FileSystemUtils.deleteRecursively(Paths.get("D:/Cache/a.png").toFile());
    }
}
