package model;

import java.util.Objects;

public class SplitFile {
    private String name;
    private String suffix;
   // private int count=0;
    private String ext;

    public void setName(String name) {
        this.name = name;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getName() {
        return name;
    }

    public String getExt() {
        return ext;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
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
    /* @Override
    public String toString() {
        return name + suffix + ext;
    }*/

    @Override
    public String toString() {
        return "SplitFile{" +
                "name='" + name + '\'' +
                ", suffix='" + suffix + '\'' +
                ", ext='" + ext + '\'' +
               // ", count'" + count + '\'' +
                '}';
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
            //int lenS = "_part_".length();
            this.name = file.substring(0, posS);
            this.suffix = file.substring(posS,posE);
           /* if (suffix.contains("Complete")){
                this.count = 0;
            }
            else {
                int posC = posS + lenS;
                String check = suffix.substring(posC, posE);
                this.count = Integer.parseInt(check)+1;
            }*/
            this.ext = file.substring(posE);
        }
    }
}
