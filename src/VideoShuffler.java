import java.io.File;
import java.io.FileFilter;
import java.util.*;

/**
 * Author   : Aye Maung
 */
public class VideoShuffler {
    private static final String VLC_EXE = "C:\\Program Files (x86)\\VideoLAN\\VLC\\vlc.exe";
    private static final List<String> extensions = Arrays.asList("avi", "wmv", "mp4", "mkv", "rmvb", "mpg");

    private static VideoShuffler instance = new VideoShuffler();

    public static VideoShuffler getInstance() {
        return instance;
    }

    public void openRandomVideo(String directoryPath) {
        try {
            File root = new File(directoryPath);
            if (root == null || !root.isDirectory()) {
                System.err.println(directoryPath + " is not a folder");
                return;
            }

            System.out.println("Root Folder : " + root.getAbsolutePath());
            List<File> allInFolder = listFilesUnder(root);
            if (allInFolder.isEmpty()) {
                return;
            }

            System.out.println("Total files : " + allInFolder.size());
            Collections.shuffle(allInFolder);
            File fileOpened = allInFolder.get(0);

            ProcessBuilder pb = new ProcessBuilder(VLC_EXE, fileOpened.getAbsolutePath());
            pb.start();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private VideoShuffler() {
    }

    private List<File> listFilesUnder(final File folder) {
        List<File> retList = new ArrayList<>();
        File[] eligibleFiles = folder.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                String[] splitName = pathname.getAbsolutePath().split("\\.");
                String ext = splitName[splitName.length - 1];
                return pathname.isDirectory() || extensions.contains(ext);
            }
        });

        for (final File fileEntry : eligibleFiles) {
            if (fileEntry.isDirectory()) {
                retList.addAll(listFilesUnder(fileEntry));
            } else {
                retList.add(fileEntry);
            }
        }

        return retList;
    }
}
