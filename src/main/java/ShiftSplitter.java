import model.Directory;
import model.SplitFile;
import utills.DirUtil;
import utills.SplitUtil;
import utills.UnSplitUtil;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


public class ShiftSplitter {

    public static void main(String[] args) throws IOException {
//        List<SplitFile> allFiles = DirUtil.getFileDir(Directory.DIR.toString());
//        allFiles.forEach(s -> System.out.println(s.toString()));
//        //List<String> name2 = getSplitFileDir(dirsplit);
//        //unSplitter(dirunsplit, name2, ".log");
//        allFiles.forEach(s-> {
//            try {
//                SplitUtil.splitter(Directory.DIR.toString(),50, s.getName(), s.getExt());
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
//        });
        //List<String> name2 = getSplitFileDir(dirsplit);
        //name2.forEach(s-> splitFiles.add(new SplitFile(s)));
        List<SplitFile> splitFiles = DirUtil.getSplitFileDir(Directory.DIRSPLIT.toString());
        List<SplitFile> result = splitFiles.stream()
                .filter(s-> s.getName().equals(s.getNamesSuffix(Directory.SUFFIX + "Complete")))
                .collect(Collectors.toList());
        result.forEach(s-> {
            try {
                UnSplitUtil.unSplitter(s.getName(), s.getExt(), DirUtil.listUnSplit(s.getName(), splitFiles));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

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
