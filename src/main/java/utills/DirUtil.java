package utills;

import model.SplitFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DirUtil {

    public static List<SplitFile> getFileDir(String dir) throws IOException {
        try (Stream<Path> stream = Files.list(Paths.get(dir))) {
            return stream
                    .filter(file -> !Files.isDirectory(file))
                    .map(Path::getFileName)
                    .map(path -> new SplitFile(path.toString()))
                    .collect(Collectors.toList());
                    /*.map(Path::toString)
                    .collect(Collectors.toMap(s -> s.substring(0, s.lastIndexOf('.')), s -> s.substring(s.lastIndexOf('.'))));*/
        }
    }

    public static List<SplitFile> getSplitFileDir(String dir) throws IOException {
        try (Stream<Path> stream = Files.list(Paths.get(dir))) {
            return stream
                    .filter(file -> !Files.isDirectory(file))
                    .map(Path::getFileName)
                    .map(path -> new SplitFile(path.toString()))
                    .sorted(Comparator.comparing(SplitFile::getName))
                    .collect(Collectors.toList());
        }
    }

}
