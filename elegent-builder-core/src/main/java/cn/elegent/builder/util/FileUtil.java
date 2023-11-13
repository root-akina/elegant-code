package cn.elegent.builder.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileUtil {

    public static boolean isAsciiFile(File file) {
        try (FileInputStream fis = new FileInputStream(file)) {
            int firstByte = fis.read();
            return (firstByte >= 0 && firstByte <= 127);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
