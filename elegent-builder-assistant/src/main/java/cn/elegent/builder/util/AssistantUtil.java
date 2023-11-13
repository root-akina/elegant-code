package cn.elegent.builder.util;

import java.io.*;

public class AssistantUtil {

    public static boolean isAsciiFile(File file) {
        try (FileInputStream fis = new FileInputStream(file)) {
            int firstByte = fis.read();
            return (firstByte >= 0 && firstByte <= 127);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**tem
     * 获取文件内容
     *
     * @param fileName
     * @return
     */
    public static String getContent(String fileName) {
        File file = new File(fileName);
        String result = "";
        try {
            InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "UTF-8");
            BufferedReader read = new BufferedReader(isr);


            //BufferedReader br = new BufferedReader(new FileReader(file));// 构造一个BufferedReader类来读取文件

            String s = null;
            while ((s = read.readLine()) != null) {// 使用readLine方法，一次读一行
                result = result + s + "\n";
            }
            read.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }

    /**
     * 设置文件内容
     *
     * @param filePath
     *            文件存储目录
     * @param content
     *            文件内容
     */
    public static void setContent(String filePath, String content) {

        try {

            File f = new File(filePath);

            if (f.exists()) {
                f.delete();
            }
            File pathf = f.getParentFile();
            if (!pathf.exists()) {
                pathf.mkdirs();
            }

            f.createNewFile();// 不存在则创建

            //BufferedWriter output = new BufferedWriter(new FileWriter(f));

            FileOutputStream fout=new FileOutputStream(f);
            BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(fout, "UTF-8"));

            writer.write(content);
            writer.close();

            //output.write(content);
            //output.close();
            System.out.println("生成代码：" + filePath);

        } catch (Exception e) {
            e.printStackTrace();

        }
    }


    /**
     * 复制单个文件
     *
     * @param oldPath
     *            String 原文件路径 如：c:/fqf.txt
     * @param newPath
     *            String 复制后路径 如：f:/fqf.txt
     * @return boolean
     */
    public static void copyFile(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { // 文件存在时

                //创建新的文件的文件夹
                File newpath=new File(newPath);
                if(!newpath.getParentFile().exists())
                {
                    newpath.getParentFile().mkdirs();
                }

                InputStream inStream = new FileInputStream(oldPath); // 读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                //int length;
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; // 字节数 文件大小
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        } catch (Exception e) {
            System.out.println("复制单个文件操作出错!原文件:"+oldPath+" 新文件："+newPath);
            //e.printStackTrace();

        }

    }


}
