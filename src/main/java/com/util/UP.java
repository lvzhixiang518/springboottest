package com.util;

import com.ibm.icu.text.CharsetDetector;
import com.ibm.icu.text.CharsetMatch;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UP {

    public static void main(String[] args) {
        /*try {
            getString("D:"+File.separator+"马上要用.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        System.out.println("".substring(0,2));
    }

    //读取文件夹下 指定文件的内容
    public static List<String> getString(String path) throws IOException {
        String lineTxt = null;
        String text = "";
        //Map<Object, Object> map  = new HashMap<>();
        List<String> list = new ArrayList<>();
        int i = 0;
        try {
            //String encoding = "GBK";
            File file = new File(path);
            if (file.isFile() && file.exists()) { //判断文件是否存在
                InputStreamReader read = null;//考虑到编码格式
                String ss = getFileCharsetByICU4J(file);
                try {
                    read = new InputStreamReader(new FileInputStream(file),"UTF-8");
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                BufferedReader bufferedReader = new BufferedReader(read);
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    //System.out.println(lineTxt);
                    text += lineTxt;
                    //map.put(i,lineTxt);
                    list.add(lineTxt.trim());
                    i++;
                }
                read.close();
            } else {
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
        for (int j = 0;j<list.size();j++){
            System.out.println(list.get(j));
        }
        return list;
    }

    public static String getFileCharsetByICU4J(File file) {
        String encoding = null;

        try {
            Path path = Paths.get(file.getPath());
            byte[] data = Files.readAllBytes(path);
            CharsetDetector detector = new CharsetDetector();
            detector.setText(data);
            CharsetMatch match = detector.detect();
            if (match == null) {
                return encoding;
            }

            encoding = match.getName();
        } catch (IOException var6) {
            System.out.println(var6.getCause());
        }

        return encoding;
    }

}
