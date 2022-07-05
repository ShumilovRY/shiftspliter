package utills;

import model.Directory;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static model.Directory.*;

public class SplitUtil {

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


    private static void writePartToFile(long byteSize, long position, FileChannel sourceChannel, List<Path> partFiles, String name, String ext) throws IOException {
        Path fileName = Paths.get(DIRSPLIT + name + SUFFIX + "Complete" + ext);
        try (RandomAccessFile toFile = new RandomAccessFile(fileName.toFile(), "rw");
             FileChannel toChannel = toFile.getChannel()) {
            sourceChannel.position(position);
            toChannel.transferFrom(sourceChannel, 0, byteSize);
        }
        partFiles.add(fileName);

    }

    private static void writePartToFile(long byteSize, long position, FileChannel sourceChannel, List<Path> partFiles, int count, String name, String ext) throws IOException {
        Path fileName = Paths.get(DIRSPLIT + name + SUFFIX + count + ext);
        try (RandomAccessFile toFile = new RandomAccessFile(fileName.toFile(), "rw");
             FileChannel toChannel = toFile.getChannel()) {
            sourceChannel.position(position);
            toChannel.transferFrom(sourceChannel, 0, byteSize);
        }
        partFiles.add(fileName);
    }
}
