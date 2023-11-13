package cn.elegent.builder.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 压缩工具包
 */
public class ZipUtil {

    public static void compressFile(File file, ZipOutputStream zipOutputStream) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        ZipEntry zipEntry = new ZipEntry(file.getName());
        zipOutputStream.putNextEntry(zipEntry);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = fis.read(buffer)) > 0) {
            zipOutputStream.write(buffer, 0, length);
        }

        fis.close();
        zipOutputStream.closeEntry();
    }

    public static void compressFolder(File folder, ZipOutputStream zipOutputStream) throws IOException {
        for (File file : folder.listFiles()) {
            if (file.isFile()) {
                compressFile(file, zipOutputStream);
            } else if (file.isDirectory()) {
                compressFolder(file, zipOutputStream);
            }
        }
    }

    public static void createZip(File source, File destination) throws IOException {
        FileOutputStream fos = new FileOutputStream(destination);
        ZipOutputStream zipOutputStream = new ZipOutputStream(fos);

        if (source.isFile()) {
            compressFile(source, zipOutputStream);
        } else if (source.isDirectory()) {
            compressFolder(source, zipOutputStream);
        }

        zipOutputStream.close();
        fos.close();
    }

}