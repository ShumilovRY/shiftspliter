package utills;

import model.Directory;
import model.SplitFile;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class UnSplitUtil {

    public static void unSplitter(final String name, final String ext, final List<SplitFile> files) throws IOException {
        String outFile = Directory.DIRUNSPLIT  + name + ext;
        try (RandomAccessFile sourceFile = new RandomAccessFile(outFile, "rw");
             FileChannel sourceChannel = sourceFile.getChannel()) {
            files.forEach(s-> {
                try {
                    writeFromPart(sourceChannel,s.getName(),s.getSuffix(), s.getExt());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private static void writeFromPart(FileChannel sourceChannel, String name, String suffix, String ext) throws IOException {
        Path fileName = Paths.get(Directory.DIRSPLIT + name + suffix + ext);
        try (RandomAccessFile inFile = new RandomAccessFile(fileName.toFile(), "r");
             FileChannel toChannel = inFile.getChannel()) {
            for (long p = 0, l = toChannel.size(); p < 1; )
                p += toChannel.transferTo(p, l - p, sourceChannel);
        }
    }
}
