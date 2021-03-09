package com.wyj;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;


/**
 * 直接调用系统的复制命令进行文件拷贝
 * @author W.Y.J
 * @Date 2021/2/19 16:44
 */
public class CommandCopyFileUtils {

    final static String WINDOWS_COMMAND = "cmd /c copy {0} {1}";
    final static String LINUX_COMMAND = "cp {0} {1}";


    public static void main(String[] args) {

        String s = "D:\\测试素材\\video\\f4af11bbcad4457d8a941384eb147cfd.mp4";
        String t = "D:\\测试素材\\video2\\f4af11bbcad4457d8a941384eb147cfde.mp4";

        try {
             copyFile(s, t);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 复制文件 默认超时时间5min
     *
     * @param sourcePath 源文件绝对路径
     * @param targetPath 目标文件绝对路径
     * @return boolean      复制结果
     * @author W.Y.J
     * @Date 2021/2/19 16:24
     */
    public static boolean copyFile(String sourcePath, String targetPath) throws Exception {
        return copyFile(sourcePath, targetPath, 300);
    }


    /**
     * 复制文件
     *
     * @param sourcePath 源文件绝对路径
     * @param targetPath 目标文件绝对路径
     * @param sec        超时时间，单位秒
     * @return boolean      复制结果
     * @author W.Y.J
     * @Date 2021/2/19 16:24
     */
    public static boolean copyFile(String sourcePath, String targetPath, int sec) throws Exception {
        String cm = MessageFormat.format(getEvnCommand(), sourcePath, targetPath);
        System.out.println(cm);
        //如果目标路径目录不存在，则创建
        File dir = new File(getFileDir(targetPath));
        if (!dir.exists()) {
            dir.mkdirs();
        }
        Process process = Runtime.getRuntime().exec(cm);
        //最多阻塞5分钟
        boolean result = process.waitFor(sec, TimeUnit.SECONDS);
        if (result) {
            System.out.println("Success!");
        } else {
            //abnormal...
            System.out.println("abnormal!");
        }

        return result;
    }


    /**
     * 根据环境获取命令
     */
    private static String getEvnCommand() {
        String os = System.getProperty("os.name");
        if (os == null || os.length() <= 0)
            return "";
        os = os.toLowerCase();
        if (os.indexOf("linux") > -1) {
            return LINUX_COMMAND;
        }
        if (os.indexOf("windows") > -1) {
            return WINDOWS_COMMAND;
        }
        return "";

    }

    /**
     * 获取文件所在目录
     *
     */
    protected static String getFileDir(String path) {
        if (StringUtils.isBlank(path)) return "";
        int idx = StringUtils.lastIndexOf(path, "\\");
        return idx == 1 ? path : path.substring(0, idx);
    }

}
