import model.Directory;
import model.SplitFile;
import utills.DirUtil;
import utills.SplitUtil;

import java.io.IOException;
import java.util.List;


public class ShiftSplitter {

    public static void main(String[] args) throws IOException {
        List<SplitFile> allFiles = DirUtil.getFileDir(Directory.DIR.toString());

        allFiles.forEach(s -> System.out.println(s.toString()));
        //Map<String, String> name1 = getFileDir(dir);
        //List<String> name2 = getSplitFileDir(dirsplit);
        //unSplitter(dirunsplit, name2, ".log");
        allFiles.forEach(s-> {
            try {
                SplitUtil.splitter(Directory.DIR.toString(),50, s.getName(), s.getExt());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        //List<String> name2 = getSplitFileDir(dirsplit);
        //name2.forEach(s-> splitFiles.add(new SplitFile(s)));
        //List<SplitFile> splitFiles = DirUtil.getSplitFileDir(dirsplit);
        //splitFiles.forEach(s-> {
//            String check = s.getNamesSuffix(suffix + "Complete");
//            if(!check.equals("")){
//
//                //unSplitter(dirunsplit, unSplit, s.getExt());
//            }
//        });
        //splitFiles.forEach(s-> System.out.println(s.toString()));
        //splitFiles.forEach(s -> System.out.println(s.getNamesSuffix(suffix + "Complete")));
    }
}
