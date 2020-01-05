package com.vabait.common.util;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.*;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;

public class FileUtil {

    public static boolean write(String path, byte[] bytes) {
        boolean ret = false;

        File file = new File(path);
        BufferedOutputStream outputStream = null;
        try {
            outputStream = new BufferedOutputStream(new FileOutputStream(file));
            outputStream.write(bytes);
            outputStream.close();
            ret = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ret;
    }

    public static void append(String file, String conent) {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true)));
            out.write(conent);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static byte[] read(String path) {
        File file = new File(path);
        byte[] data = null;
        if (file.exists()) {
            try {
                FileInputStream inputStream = new FileInputStream(file);
                data = new byte[(int) file.length()];
                inputStream.read(data);
                inputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return data;
    }

    public static boolean delete(String path) {
        File file = new File(path);
        if (file != null && file.exists()) {
            return file.delete();
        }
        return false;
    }

    public static String readStaticResource(String resourceName) {
        /*
        Classpath resource not found when running as jar
        resource.getFile() expects the resource itself to be available on the file system, i.e.
        it can't be nested inside a jar file.
        This is why it works when you run your application in STS but doesn't work once you've built your application
        and run it from the executable jar.
        Rather than using getFile() to access the resource's contents,
        I'd recommend using getInputStream() instead.
        That'll allow you to read the resource's content irrespective of where it's located.
        http://stackoverflow.com/questions/25869428/classpath-resource-not-found-when-running-as-jar
        make sure you are using resource.getInputStream() not resource.getFile() when loading from inside a jar file.
        http://stackoverflow.com/questions/14876836/file-inside-jar-is-not-visible-for-spring
        */

        ResourceLoader loader = new DefaultResourceLoader();
        Resource resource = loader.getResource("classpath:/static/" + resourceName);

        StringBuffer buffer = new StringBuffer();
        try {
            InputStream stream = resource.getInputStream();
            Reader reader = new InputStreamReader(stream, "utf-8");
            BufferedReader br = new BufferedReader(reader);

            String str = null;
            while ((str = br.readLine()) != null) {
                buffer.append(str);
            }

            br.close();
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return buffer.toString();
    }

    public static byte[] readStaticResourceBytes(String resourceName) {
        byte[] bytes = null;
        ResourceLoader loader = new DefaultResourceLoader();
        Resource resource = loader.getResource("classpath:/static/" + resourceName);

        try {
            InputStream stream = resource.getInputStream();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            final int len = 1024;
            byte tmp[] = new byte[len];
            int i;
            while ((i = stream.read(tmp, 0, len)) > 0) {
                baos.write(tmp, 0, i);
            }
            bytes = baos.toByteArray();
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bytes;
    }

    //Java文件的MD5校验和CRC校验 https://blog.csdn.net/mfcing/article/details/48180271
    public static String md5(String filePath) {
        String str = null;

        File file = new File(filePath);
        if (file.exists()) {
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(file);
                MessageDigest md5 = MessageDigest.getInstance("MD5");
                int nBuffLen = 10 * 1024; //10KB
                byte[] buff = new byte[nBuffLen];
                while (true) {
                    int nRead = fis.read(buff);
                    if (nRead > 0)
                        md5.update(buff, 0, nRead);
                    if (nRead < nBuffLen)
                        break;
                }
                byte[] bMd5 = md5.digest();
                str = BytesUtil.bytes2HexString(bMd5, false);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
            } catch (IOException e) {
            } finally {
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return str;
    }

    /**
     * 获取服务器IP地址
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public static String getServerIp() {
        String SERVER_IP = null;
        try {
            Enumeration netInterfaces = NetworkInterface.getNetworkInterfaces();
            while (netInterfaces.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) netInterfaces.nextElement();
                InetAddress ip = ni.getInetAddresses().nextElement();
                if (!ip.isSiteLocalAddress() && !ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1) {
                    SERVER_IP = ip.getHostAddress();
                    break;
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }

        return SERVER_IP;
    }

    /***
     * 删除指定文件夹下所有文件
     *
     * @param path 文件夹完整绝对路径
     * @return
     */
    public static boolean deleteAllFiles(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                deleteAllFiles(path + "/" + tempList[i]);// 先删除文件夹里面的文件
                deleteFolder(path + "/" + tempList[i]);// 再删除空文件夹
                flag = true;
            }
        }

        return flag;
    }

    /***
     * 删除文件夹
     *
     * @param folderPath 文件夹完整绝对路径
     */
    public static void deleteFolder(String folderPath) {
        try {
            deleteAllFiles(folderPath); // 删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
            File myFilePath = new File(filePath);
            myFilePath.delete(); // 删除空文件夹
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 复制文件
    public static boolean copyStaticResource(String resourceName, String destPathName) {
        boolean ret = false;
        ResourceLoader loader = new DefaultResourceLoader();
        Resource resource = loader.getResource("classpath:/static/" + resourceName);

        try {
            InputStream input = resource.getInputStream();
            // 新建文件输入流并对它进行缓冲
            //FileInputStream input = new FileInputStream(sourceFile);
            BufferedInputStream inBuff = new BufferedInputStream(input);

            // 新建文件输出流并对它进行缓冲
            FileOutputStream output = new FileOutputStream(new File(destPathName));
            BufferedOutputStream outBuff = new BufferedOutputStream(output);

            // 缓冲数组
            byte[] b = new byte[1024 * 5];
            int len;
            while ((len = inBuff.read(b)) != -1) {
                outBuff.write(b, 0, len);
            }
            // 刷新此缓冲的输出流
            outBuff.flush();

            //关闭流
            inBuff.close();
            outBuff.close();
            output.close();
            input.close();

            ret = true;
        } catch (IOException e) {

        }

        return ret;
    }

    // 复制文件
    public static boolean copyResource(String resourceName, String destPathName) {
        boolean ret = false;
        ResourceLoader loader = new DefaultResourceLoader();
        Resource resource = loader.getResource("classpath:/" + resourceName);

        try {
            InputStream input = resource.getInputStream();
            // 新建文件输入流并对它进行缓冲
            //FileInputStream input = new FileInputStream(sourceFile);
            BufferedInputStream inBuff = new BufferedInputStream(input);

            // 新建文件输出流并对它进行缓冲
            FileOutputStream output = new FileOutputStream(new File(destPathName));
            BufferedOutputStream outBuff = new BufferedOutputStream(output);

            // 缓冲数组
            byte[] b = new byte[1024 * 5];
            int len;
            while ((len = inBuff.read(b)) != -1) {
                outBuff.write(b, 0, len);
            }
            // 刷新此缓冲的输出流
            outBuff.flush();

            //关闭流
            inBuff.close();
            outBuff.close();
            output.close();
            input.close();

            ret = true;
        } catch (IOException e) {

        }

        return ret;
    }
}
