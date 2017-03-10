package config;
/**
 * @author Vlad Shevchenko
 * @version 1.0
 * @since 10.03.17
 */

public class AppConfig {

    private static final String FILE_DIRECTORY = "/home/vshevchenko/project/words/src/main/resources/words.txt" ;

    public static String getFileDirectory() {
        return FILE_DIRECTORY;
    }
}
