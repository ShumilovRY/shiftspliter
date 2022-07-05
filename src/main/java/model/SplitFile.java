package model;

import java.util.Objects;

public class SplitFile {
    private String name;
    private String suffix;
    private String ext;

    public String getName() {
        return name;
    }

    public String getExt() {
        return ext;
    }

    public String getSuffix() {
        return suffix;
    }

    public String getNamesSuffix(String suffix) {
        if (this.getSuffix().equals(suffix)) {
            return this.name;
        }
        else return "";
    }
     @Override
    public String toString() {
        return name + suffix + ext;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SplitFile splitFile = (SplitFile) o;
        return name.equals(splitFile.name) && Objects.equals(suffix, splitFile.suffix) && Objects.equals(ext, splitFile.ext);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, suffix, ext);
    }

    public SplitFile(String file) {
        int posE = file.lastIndexOf('.');
        int posS = file.lastIndexOf("_part_");
        if (posE == -1 && posS == -1) {
            this.name = file;
            }
        else if (posE == -1){
            this.suffix = file.substring(posS);
            this.name = file.substring(0, posS);
            }
        else if (posS == -1){
            this.name = file.substring(0, posE);
            this.ext = file.substring(posE);
        }
        else {
            this.name = file.substring(0, posS);
            this.suffix = file.substring(posS,posE);
            this.ext = file.substring(posE);
        }
    }
}
