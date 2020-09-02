package com.util;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import org.thymeleaf.util.StringUtils;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * TODO 请详细说明该类的作用
 *
 * @author lvzhixiang
 * @since 2020/4/22
 */
public class ZipTest {

    public static void main(String[] args) {
        /*String a = "D:"+File.separator+"bootstrop.zip";
        String s = "D:"+File.separator+"sssss";
        try {
            unzip(a,s,"123");
        } catch (ZipException e) {
            e.printStackTrace();
        }*/
        String s = "D:"+File.separator+"sssss"+File.separator+"bootstrop"+File.separator+"interface中转真假开关语句.txt";
        try {
            List<String> list = getString(s);
            for (String ss : list){
                System.out.println(ss);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //读取文件夹下 指定文件的内容
    public static List<String> getString(String path) throws IOException {
        String lineTxt = null;
        String text = "";
        List<String> list = new ArrayList<>();
        int i = 0;
        try {
            String encoding = "GBK";
            File file = new File(path);
            if (file.isFile() && file.exists()) { //判断文件是否存在
                InputStreamReader read = null;//考虑到编码格式
                try {
                    read = new InputStreamReader(new FileInputStream(file), encoding);
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
        return list;
    }

    /**
     * 使用给定密码解压指定的ZIP压缩文件到指定目录
     * <p>
     * 如果指定目录不存在,可以自动创建,不合法的路径将导致异常被抛出
     * @param zip 指定的ZIP压缩文件
     * @param dest 解压目录
     * @param passwd ZIP文件的密码
     * @return 解压后文件数组
     * @throws ZipException 压缩文件有损坏或者解压缩失败抛出
     */
    public static File[] unzip(String zip, String dest, String passwd) throws ZipException {
        File zipFile = new File(zip);
        return unzip(zipFile, dest, passwd);
    }

    /**
     * 使用给定密码解压指定的ZIP压缩文件到当前目录
     * @param zip 指定的ZIP压缩文件
     * @param passwd ZIP文件的密码
     * @return  解压后文件数组
     * @throws ZipException 压缩文件有损坏或者解压缩失败抛出
     */
    public static File [] unzip(String zip, String passwd) throws ZipException {
        File zipFile = new File(zip);
        File parentDir = zipFile.getParentFile();
        return unzip(zipFile, parentDir.getAbsolutePath(), passwd);
    }

    /**
     * 使用给定密码解压指定的ZIP压缩文件到指定目录
     *      * <p>
     * 如果指定目录不存在,可以自动创建,不合法的路径将导致异常被抛出
     * @param zipFile 指定的ZIP压缩文件
     * @param dest 解压目录
     * @param passwd ZIP文件的密码
     * @return  解压后文件数组
     * @throws ZipException 压缩文件有损坏或者解压缩失败抛出
     */
    public static File [] unzip(File zipFile, String dest, String passwd) throws ZipException {
        ZipFile zFile = new ZipFile(zipFile);
        zFile.setFileNameCharset("GBK");//如果将要解压的压缩文件中的文件名含有中文，解压时需要注意一点，就是设置文件名字符集方法
        if (!zFile.isValidZipFile()) {//判断压缩文件是否合法
            throw new ZipException("压缩文件不合法,可能被损坏.");
        }
        File destDir = new File(dest);//指定要解压到的文件夹路径
        if (destDir.isDirectory() && !destDir.exists()) {//检查一个对象是否是文件夹 && 判断文件是否存在
            destDir.mkdir();//mkdirs()这个方法可以在不知道有没有父类文件夹的情况下，创建文件夹;而mkdir()必须在有父类的文件夹下创建文件
        }
        if (zFile.isEncrypted()) {//判断文件加密
            zFile.setPassword(passwd.toCharArray());
        }
        zFile.extractAll(dest);//解压zip文档中的所有文件到当前目录

        List<FileHeader> headerList = zFile.getFileHeaders();//获取文件头信息
        List<File> extractedFileList = new ArrayList<File>();
        for(FileHeader fileHeader : headerList) {
            if (!fileHeader.isDirectory()) {
                extractedFileList.add(new File(destDir,fileHeader.getFileName()));
            }
        }
        File [] extractedFiles = new File[extractedFileList.size()];
        extractedFileList.toArray(extractedFiles);
        return extractedFiles;
    }

    /**
     * 压缩指定文件到当前文件夹
     * @param src 要压缩的指定文件
     * @return 最终的压缩文件存放的绝对路径,如果为null则说明压缩失败.
     */
    public static String zip(String src) {
        return zip(src,null);
    }

    /**
     * 使用给定密码压缩指定文件或文件夹到当前目录
     * @param src 要压缩的文件
     * @param passwd 压缩使用的密码
     * @return 最终的压缩文件存放的绝对路径,如果为null则说明压缩失败.
     */
    public static String zip(String src, String passwd) {
        return zip(src, null, passwd);
    }

    /**
     * 使用给定密码压缩指定文件或文件夹到当前目录
     * @param src 要压缩的文件
     * @param dest 压缩文件存放路径
     * @param passwd 压缩使用的密码
     * @return 最终的压缩文件存放的绝对路径,如果为null则说明压缩失败.
     */
    public static String zip(String src, String dest, String passwd) {
        return zip(src, dest, true, passwd);
    }

    /**
     * 使用给定密码压缩指定文件或文件夹到指定位置.
     * <p>
     * dest可传最终压缩文件存放的绝对路径,也可以传存放目录,也可以传null或者"".<br />
     * 如果传null或者""则将压缩文件存放在当前目录,即跟源文件同目录,压缩文件名取源文件名,以.zip为后缀;<br />
     * 如果以路径分隔符(File.separator)结尾,则视为目录,压缩文件名取源文件名,以.zip为后缀,否则视为文件名.
     * @param src 要压缩的文件或文件夹路径
     * @param dest 压缩文件存放路径
     * @param isCreateDir 是否在压缩文件里创建目录,仅在压缩文件为目录时有效.<br />
     * 如果为false,将直接压缩目录下文件到压缩文件.
     * @param passwd 压缩使用的密码
     * @return 最终的压缩文件存放的绝对路径,如果为null则说明压缩失败.
     */
    public static String zip(String src, String dest, boolean isCreateDir, String passwd) {
        File srcFile = new File(src);
        dest = buildDestinationZipFilePath(srcFile, dest);
        ZipParameters parameters = new ZipParameters();
        parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);           // 压缩方式
        parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);    // 压缩级别
        if (!StringUtils.isEmpty(passwd)) {
            parameters.setEncryptFiles(true);
            parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD); // 加密方式
            parameters.setPassword(passwd.toCharArray());
        }
        try {
            ZipFile zipFile = new ZipFile(dest);
            if (srcFile.isDirectory()) {
                // 如果不创建目录的话,将直接把给定目录下的文件压缩到压缩文件,即没有目录结构
                if (!isCreateDir) {
                    File [] subFiles = srcFile.listFiles();
                    ArrayList<File> temp = new ArrayList<File>();
                    Collections.addAll(temp, subFiles);
                    zipFile.addFiles(temp, parameters);
                    return dest;
                }
                zipFile.addFolder(srcFile, parameters);
            } else {
                zipFile.addFile(srcFile, parameters);
            }
            return dest;
        } catch (ZipException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 构建压缩文件存放路径,如果不存在将会创建
     * 传入的可能是文件名或者目录,也可能不传,此方法用以转换最终压缩文件的存放路径
     * @param srcFile 源文件
     * @param destParam 压缩目标路径
     * @return 正确的压缩文件存放路径
     */
    private static String buildDestinationZipFilePath(File srcFile,String destParam) {
        if (StringUtils.isEmpty(destParam)) {
            if (srcFile.isDirectory()) {
                destParam = srcFile.getParent() + File.separator + srcFile.getName() + ".zip";
            } else {
                String fileName = srcFile.getName().substring(0, srcFile.getName().lastIndexOf("."));
                destParam = srcFile.getParent() + File.separator + fileName + ".zip";
            }
        } else {
            createDestDirectoryIfNecessary(destParam);  // 在指定路径不存在的情况下将其创建出来
            if (destParam.endsWith(File.separator)) {
                String fileName = "";
                if (srcFile.isDirectory()) {
                    fileName = srcFile.getName();
                } else {
                    fileName = srcFile.getName().substring(0, srcFile.getName().lastIndexOf("."));
                }
                destParam += fileName + ".zip";
            }
        }
        return destParam;
    }

    /**
     * 在必要的情况下创建压缩文件存放目录,比如指定的存放路径并没有被创建
     * @param destParam 指定的存放路径,有可能该路径并没有被创建
     */
    private static void createDestDirectoryIfNecessary(String destParam) {
        File destDir = null;
        if (destParam.endsWith(File.separator)) {
            destDir = new File(destParam);
        } else {
            destDir = new File(destParam.substring(0, destParam.lastIndexOf(File.separator)));
        }
        if (!destDir.exists()) {
            destDir.mkdirs();
        }
    }

    //public static void main(String[] args) {
        //zip("d:\\test\\cc", "d:\\test\\cc.zip", "11");
//      try {
//          File[] files = unzip("d:\\test\\汉字.zip", "aa");
//          for (int i = 0; i < files.length; i++) {
//              System.out.println(files[i]);
//          }
//      } catch (ZipException e) {
//          e.printStackTrace();
//      }}
}
