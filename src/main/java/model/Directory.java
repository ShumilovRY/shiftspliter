package model;

public enum Directory {
    DIR("C:\\Users\\ry.shumilov\\Desktop\\ShumilovLogs\\"),
    DIRSPLIT("C:\\Users\\ry.shumilov\\Desktop\\ShumilovLogs\\split\\"),
    DIRUNSPLIT("C:\\Users\\ry.shumilov\\Desktop\\ShumilovLogs\\unsplit\\"),
    SUFFIX("_part_");

    private final String dir;

    Directory(String dir) {
        this.dir = dir;
    }

    @Override
    public String toString() {
        return dir;
    }
}
