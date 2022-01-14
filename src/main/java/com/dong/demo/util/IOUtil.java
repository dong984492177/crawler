package com.dong.demo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * io流的一些工具类
 *
 * @author Dong_Jia_Qi on 2021/6/29
 */

public class IOUtil {
    static Logger log = LoggerFactory.getLogger(IOUtil.class);

    /**
     * 序列化文件通用功能
     * @param object 要序列化的对象
     * @param filePath 输出文件路径 String
     * @param aClass 类的CLASS,用来记录日志谁调用的
     * @return 是否序列化成功 ,有的时候序列化只是为了备份,可有可无,所以有异常也不影响整个流程的继续.
     * 所以异常直接处理掉,但要返回状态,以便必须序列化的功能抓到状态
     */
    public static boolean serializationFile(Object object, String filePath, Class aClass) {
        ObjectOutputStream objectOutputStream = null ;
        File boss = new File(filePath);
        try {
            objectOutputStream = new ObjectOutputStream(new FileOutputStream(boss));
            objectOutputStream.writeObject(object);
            log.debug("{}->序列化至文件({})完成",aClass.getName(),boss.getName());
            return true;
        } catch (IOException e) {
            log.debug("{}->序列化至文件({})失败,异常原因 {}",aClass.getName(),boss.getName(),e.getMessage());
            log.error(e.getMessage(),e);
            e.printStackTrace();
            return false ;
        } finally {
            try {
                if (objectOutputStream != null) {
                    objectOutputStream.close();
                }
            } catch (IOException e) {
                log.error("{}->序列化关闭文件流异常,异常原因 {}",aClass.getName(),e.getMessage());
            }
        }

    }

    /**
     * 序列化文件通用功能
     * @param object 要序列化的对象
     * @param file 输出文件文件
     * @param aClass 的CLASS,用来记录日志谁调用的
     * @return 是否序列化成功 ,有的时候序列化只是为了备份,可有可无,所以有异常也不影响整个流程的继续.
     * 所以异常直接处理掉,但要返回状态,以便必须序列化的功能抓到状态
     */
    public static boolean serializationFile(Object object, File file, Class aClass) {
        ObjectOutputStream objectOutputStream = null ;
        try {
            objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
            objectOutputStream.writeObject(object);
            log.debug("{}->序列化至文件({})完成",aClass.getName(),file.getName());
            return true;
        } catch (Exception e) {
            log.debug("{}->序列化至文件({})失败,异常原因 {}",aClass.getName(),file.getName(),e.getMessage());
            log.error(e.getMessage(),e);
//            e.printStackTrace();
            return false ;
        } finally {
            try {
                if (objectOutputStream != null) {
                    objectOutputStream.close();
                }
            } catch (IOException e) {
                log.debug("{}->序列化关闭文件流异常,异常原因 {}",aClass.getName(),e.getMessage());
                log.error(e.getMessage(),e);
            }
        }

    }

    /**
     * 反序列化文件
     * @param filePath 文件路径
     * @param aClass 调用此方法的类
     * @return  返回反序列化的对象 可能因为异常原因返回为null
     */
    public static Object deserialization(String filePath, Class aClass){
        ObjectInputStream ojObjectInputStream = null ;
        File file = new File(filePath);
        try {
            ojObjectInputStream = new ObjectInputStream(new FileInputStream(file));
            Object o = ojObjectInputStream.readObject();
            log.debug("{}->序列化文件({})完成",aClass.getName(),file.getName());
            return  o;
        } catch (IOException | ClassNotFoundException e) {
            log.debug("{}->反序列化文件({})失败,异常原因 {}",aClass.getName(),file.getName(),e.getMessage());
            log.error(e.getMessage(),e);
            return null ;
        }finally {
            try {
                if (ojObjectInputStream!=null) {
                    ojObjectInputStream.close();
                }
            } catch (IOException e) {
                log.debug("{}->反序列化关闭文件流异常,异常原因 {}",aClass.getName(),e.getMessage());
                log.error(e.getMessage(),e);
            }
        }
    }

    /**
     * 反序列化文件
     *
     * @param file 系列化的文件
     * @param aClass 调用此方法的类
     * @return 返回反序列化的对象 可能因为异常原因返回为null
     */
    public static Object deserialization(File file, Class aClass){
        ObjectInputStream ojObjectInputStream = null ;
        try {
            ojObjectInputStream = new ObjectInputStream(new FileInputStream(file));
            Object o = ojObjectInputStream.readObject();
            log.debug("{}->反序列化文件({})完成",aClass.getName(),file.getName());
            return  o;
        } catch (IOException | ClassNotFoundException e) {
            log.debug("{}->反序列化文件({})失败,异常原因 {}",aClass.getName(),file.getName(),e.getMessage());
            log.error(e.getMessage(),e);
            return null ;
        }finally {
            try {
                if (ojObjectInputStream!=null) {
                    ojObjectInputStream.close();
                }
            } catch (IOException e) {
                log.debug("{}->反序列化关闭文件流异常,异常原因 {}",aClass.getName(),e.getMessage());
                log.error(e.getMessage(),e);
            }
        }
    }

}
