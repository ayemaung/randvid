import java.io.File;
import java.nio.file.Path;
import java.util.*;

/**
 * Created by Aye on 2/4/14.
 */
public class VideoBrowser {

    private static final List<String> extensions = new ArrayList<String>(
            Arrays.asList("avi", "wmv", "mp4", "mkv" ,"rmvb", "mpg"));

    public static void openRandomVideo(String directoryPath) {
        try {
            File root = new File(directoryPath);
            if (root == null || !root.isDirectory()) {
                System.err.println(directoryPath + " is not a folder");
            }

            System.out.println("Root Folder : " + root.getAbsolutePath());
            List<File> allInFolder = listFilesForFolder(root);
            Collections.shuffle(allInFolder);

            Random seeder = new Random();
            int seed = seeder.nextInt(1000000000);
            int rand = new Random(seed).nextInt(allInFolder.size() - 1);
            File fileOpened = allInFolder.get(rand);

            ProcessBuilder pb = new ProcessBuilder("C:\\Program Files (x86)\\VideoLAN\\VLC\\vlc.exe",
                    fileOpened.getAbsolutePath());
            Process start = pb.start();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public static List<File> listFilesForFolder(final File folder) {
        List<File> retList = new ArrayList<File>();
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                retList.addAll(listFilesForFolder(fileEntry));
            } else {
                String[] splitName = fileEntry.getName().split("\\.");
                String ext = splitName[splitName.length - 1];
                if (extensions.contains(ext.toLowerCase())) {
                    retList.add(fileEntry);
                }
            }
        }

        return retList;
    }
}
