package com.wyj;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;


/**
 * 根据SQL生成器
 * @author W.Y.J
 * @Date 2020/10/10 10:42 下午
 * @return
 */
public class SqlGenerator {

    public static void main(String[] args) {
        //实体类所在的package在磁盘上的绝对路径
        String packageName = "/Users/wangyj/IdeaProjects/mproduct/java/new-media-common/src/main/java/com/media/common/po/reward/";
        //生成sql的文件夹、
        String filePath = "/Users/wangyj/IdeaProjects/mproduct/java/new-media-reward-api/src/main/test/sql/";
        //项目中实体类的路径
        String prefix = "com.media.common.po.reward.";
        String className = "";

        StringBuffer sqls = new StringBuffer();
        //获取包下的所有类名称
        List<String> list = getAllClasses(packageName);
        for (String str : list) {
            className = prefix + str.substring(0, str.lastIndexOf("."));
            String sql = generateSql(className, filePath);
            sqls.append(sql);
        }
//        System.out.println(sqls.toString());
        StringToSql(sqls.toString(), filePath + "report.sql");

    }

    /**
     * 根据实体类生成建表语句
     *
     * @param className 全类名
     * @param filePath  磁盘路径  如 : d:/workspace/
     * @author
     * @date 2018年4月11日
     */
    public static String generateSql(String className, String filePath) {
        try {
            Class<?> clz = Class.forName(className);
            Field[] fields = clz.getDeclaredFields();

            //拼接表头sql
            StringBuffer tableHeaderBuilder = generateHeader(clz);
            //拼接表字段sql
            StringBuffer columnSqlBuilder = generateColumn(fields);
            StringBuffer sql = new StringBuffer();
            sql.append("\n " + tableHeaderBuilder)
                    .append(" \n `id` int(11) NOT NULL,")
                    .append(columnSqlBuilder)
                    .append(" \n PRIMARY KEY (`id`) USING BTREE,")
                    .append("\n INDEX `id`(`id`) USING BTREE")
                    .append(" \n ) ENGINE = InnoDB DEFAULT CHARSET=utf8mb4;");
            return sql.toString();
        } catch (ClassNotFoundException e) {
            return null;
        }

    }

    /**
     * 拼接表头sql
     * @author W.Y.J
     */
    private static StringBuffer generateHeader(Class<?> clz) {
        boolean isTableAnnotation = clz.isAnnotationPresent(Table.class);
        String tableName = "";
        if (isTableAnnotation) {
            Table table = clz.getAnnotation(Table.class);
            tableName = table.name();
        } else {
            tableName = clz.getSimpleName();
        }
        StringBuffer tableHeaderSqlBuilder = new StringBuffer();
        tableHeaderSqlBuilder.append("\n DROP TABLE IF EXISTS `" + tableName + "`; ")
                .append(" \n CREATE TABLE `" + tableName + "`  (");
        return tableHeaderSqlBuilder;
    }

    /**
     * 生成表字段builder
     *
     * @param fields
     * @return java.lang.StringBuffer
     * @author W.Y.J
     * @Date 2020/10/11 10:03 下午
     */
    private static StringBuffer generateColumn(Field[] fields) {
        StringBuffer columnSqlBuilder = new StringBuffer();
        Map<Class, String> filedTypeMap = new HashMap<>();
        filedTypeMap.put(String.class, " varchar(255) DEFAULT NULL,");
        filedTypeMap.put(Integer.class, " int(11) DEFAULT NULL,");
        filedTypeMap.put(Date.class, " datetime DEFAULT NULL,");
        filedTypeMap.put(BigDecimal.class, " double DEFAULT NULL,");
        for (Field f : fields) {
            String filedName = "";
            boolean isColumnAnnotation = f.isAnnotationPresent(Column.class);
            if (isColumnAnnotation) {
                Column col = f.getAnnotation(Column.class);
                filedName = col.name();
            } else {
                filedName = f.getName();
            }
            String filedDesc = filedTypeMap.get(f.getType());
            filedDesc = Objects.isNull(fields) ? filedTypeMap.get(String.class) : filedDesc;
            columnSqlBuilder.append(" \n `" + filedName + "`").append(filedDesc);
        }
        return columnSqlBuilder;
    }


    /**
     * 获取包下的所有类名称,获取的结果类似于 XXX.java
     * @author W.Y.J
     * @Date 2020/10/11 10:44 下午
     * @param packageName
     * @return java.util.List<java.lang.String>
     */
    public static List<String> getAllClasses(String packageName) {
        List<String> classList = new ArrayList<String>();
        String className = "";
        File f = new File(packageName);
        if (f.exists() && f.isDirectory()) {
            File[] files = f.listFiles();
            for (File file : files) {
                className = file.getName();
                classList.add(className);
            }
            return classList;
        } else {
            logger.debug("包路径未找到！");
            return null;
        }
    }

    /**
     * sql写入文件
     * @author W.Y.J
     * @Date 2020/10/11 10:44 下午
     * @param str
     * @param path
     * @return void
     */
    public static void StringToSql(String str, String path) {
        byte[] sourceByte = str.getBytes();
        if (null != sourceByte) {
            try {
                File file = new File(path);     //文件路径（路径+文件名）
                if (!file.exists()) {   //文件不存在则创建文件，先创建目录
                    File dir = new File(file.getParent());
                    dir.mkdirs();
                    file.createNewFile();
                }
                FileOutputStream outStream = new FileOutputStream(file);    //文件输出流用于将数据写入文件
                outStream.write(sourceByte);
                outStream.flush();
                outStream.close();  //关闭文件输出流
                System.out.println("生成成功");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}