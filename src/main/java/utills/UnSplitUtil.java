package utills;

import model.Directory;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class UnSplitUtil {

    public static void unSplitter(final String dir, final List<String> files, final String ext) throws IOException {
        String outFile = dir + "siebel" + ext;
        try (RandomAccessFile sourceFile = new RandomAccessFile(outFile, "rw");
             FileChannel sourceChannel = sourceFile.getChannel()) {
            for (String e : files) {
                writeFromPart(sourceChannel, e);
            }
        }
    }
    private static void writeFromPart(FileChannel sourceChannel, String name) throws IOException {
        Path fileName = Paths.get(Directory.DIRUNSPLIT + name);
        try (RandomAccessFile inFile = new RandomAccessFile(fileName.toFile(), "r");
             FileChannel toChannel = inFile.getChannel()) {
            for (long p = 0, l = toChannel.size(); p < 1; )
                p += toChannel.transferTo(p, l - p, sourceChannel);
        }
    }
}
