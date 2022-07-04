import model.SplitFile;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class ShiftSplitter {
    private static final String dir = "C:\\Users\\ry.shumilov\\Desktop\\ShumilovLogs\\";
    private static final String dirsplit = "C:\\Users\\ry.shumilov\\Desktop\\ShumilovLogs\\split\\";
    private static final String dirunsplit = "C:\\Users\\ry.shumilov\\Desktop\\ShumilovLogs\\unsplit\\";
    private static final String suffix = "_part_";

    public static void main(String[] args) throws IOException {
        List<SplitFile> allFiles = getFileDir(dir);

        //allFiles.forEach(s -> System.out.println(s.toString()));
        //Map<String, String> name1 = getFileDir(dir);
        //List<String> name2 = getSplitFileDir(dirsplit);
        //unSplitter(dirunsplit, name2, ".log");
        /*allFiles.forEach(s-> {
            try {
                splitter(dir,50, s.getName(), s.getExt());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });*/
        //List<String> name2 = getSplitFileDir(dirsplit);
        //name2.forEach(s-> splitFiles.add(new SplitFile(s)));
        List<SplitFile> splitFiles = getSplitFileDir(dirsplit);
        splitFiles.forEach(s-> System.out.println(s.toString()));
        //splitFiles.forEach(s -> System.out.println(s.getNamesSuffix(suffix + "Complete")));
    }


    public static List<Path> splitter(final String dir, final int mBperSplit, final String name, final String ext) throws IOException {
        String fileName = dir + name + ext;
        if (mBperSplit <= 0) {
            throw new IllegalArgumentException("mBperSplit must be more than zero");
        }

        List<Path> partFiles = new ArrayList<>();
        final long sourceSize = Files.size(Paths.get(fileName));
        final long bytesPerSplit = 1024L * 1024L * mBperSplit;
        final long numSplits = sourceSize / bytesPerSplit;
        final long remainingBytes = sourceSize % bytesPerSplit;
        int position = 0;
        int startCnt = 0;
        try (RandomAccessFile sourceFile = new RandomAccessFile(fileName, "r");
             FileChannel sourceChannel = sourceFile.getChannel()) {

            for (; position < numSplits; position++) {
                writePartToFile(bytesPerSplit, position * bytesPerSplit, sourceChannel, partFiles, startCnt, name, ext);
                startCnt++;
            }

            if (remainingBytes > 0) {
                writePartToFile(remainingBytes, position * bytesPerSplit, sourceChannel, partFiles, name, ext);
            }
        }
        return partFiles;
    }

    public static void unSplitter(final String dir, final List<String> files, final String ext) throws IOException {
        String outFile = dir + "siebel" + ext;
        try (RandomAccessFile sourceFile = new RandomAccessFile(outFile, "rw");
             FileChannel sourceChannel = sourceFile.getChannel()) {
            for (String e : files) {
                writeFromPart(sourceChannel, e);
            }
        }
    }

    private static void writePartToFile(long byteSize, long position, FileChannel sourceChannel, List<Path> partFiles, String name, String ext) throws IOException {
        Path fileName = Paths.get(dirsplit + name + suffix + "Complete" + ext);
        try (RandomAccessFile toFile = new RandomAccessFile(fileName.toFile(), "rw");
             FileChannel toChannel = toFile.getChannel()) {
            sourceChannel.position(position);
            toChannel.transferFrom(sourceChannel, 0, byteSize);
        }
        partFiles.add(fileName);

    }

    private static void writePartToFile(long byteSize, long position, FileChannel sourceChannel, List<Path> partFiles, int count, String name, String ext) throws IOException {
        Path fileName = Paths.get(dirsplit + name + suffix + count + ext);
        try (RandomAccessFile toFile = new RandomAccessFile(fileName.toFile(), "rw");
             FileChannel toChannel = toFile.getChannel()) {
            sourceChannel.position(position);
            toChannel.transferFrom(sourceChannel, 0, byteSize);
        }
        partFiles.add(fileName);
    }

    private static void writeFromPart(FileChannel sourceChannel, String name) throws IOException {
        Path fileName = Paths.get(dirsplit + name);
        try (RandomAccessFile inFile = new RandomAccessFile(fileName.toFile(), "r");
             FileChannel toChannel = inFile.getChannel()) {
            for (long p = 0, l = toChannel.size(); p < 1; )
                p += toChannel.transferTo(p, l - p, sourceChannel);
        }
    }

    // private static Map<String, String> getFileDir(String dir) throws IOException {
    private static List<SplitFile> getFileDir(String dir) throws IOException {
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

    private static List<SplitFile> getSplitFileDir(String dir) throws IOException {
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
