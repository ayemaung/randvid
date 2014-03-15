import java.io.File;

/**
 * Created by Aye on 2/4/14.
 */
public class main {
    public static void main(String[] args) {

        File currentDir = new File(".");
        VideoBrowser.openRandomVideo(currentDir.getAbsolutePath());
    }
}
