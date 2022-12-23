package com.knowledge.server.infrastructure.manager;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Component
public class ZipManager {
    /**
     * 压缩文件
     *
     * @param srcfile
     */
    public void zipFiles(File srcfile, File targetFile) {

        ZipOutputStream out = null;
        try {
            out = new ZipOutputStream(new FileOutputStream(targetFile));

            if (srcfile.isFile()) {
                zipFile(srcfile, out, "");
            } else {
                File[] list = srcfile.listFiles();
                for (int i = 0; i < list.length; i++) {
                    compress(list[i], out, "");
                }
            }

            System.out.println("压缩完毕");
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

    /**
     * 压缩文件夹里的文件
     * 起初不知道是文件还是文件夹--- 统一调用该方法
     *
     * @param file
     * @param out
     * @param basedir
     */
    private void compress(File file, ZipOutputStream out, String basedir) {
        /* 判断是目录还是文件 */
        if (file.isDirectory()) {
            zipDirectory(file, out, basedir);
        } else {
            zipFile(file, out, basedir);
        }
    }

    /**
     * 压缩单个文件
     *
     * @param srcfile
     */
    public void zipFile(File srcfile, ZipOutputStream out, String basedir) {
        if (!srcfile.exists()) {
            return;
        }
        byte[] buf = new byte[1024];
        FileInputStream in = null;

        try {
            int len;
            in = new FileInputStream(srcfile);
            out.putNextEntry(new ZipEntry(basedir + srcfile.getName()));

            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.closeEntry();
                }

                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 压缩文件夹
     *
     * @param dir
     * @param out
     * @param basedir
     */
    public void zipDirectory(File dir, ZipOutputStream out, String basedir) {
        if (!dir.exists()) {
            return;
        }
        File[] files = dir.listFiles();
        for (int i = 0; i < files.length; i++) {
            /* 递归 */
            compress(files[i], out, basedir + dir.getName() + "/");
        }
    }

    @Async("threadPoolTaskExecutor")
    public void deleteFile(File file) {
        // 判断文件是否存在
        if (file.exists()) {
            // 判断是否是文件
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                // 否则如果它是一个目录
                // 声明目录下所有的文件 files[];
                File files[] = file.listFiles();
                // 遍历目录下所有的文件
                for (int i = 0; i < files.length; i++) {
                    // 把每个文件 用这个方法进行迭代
                    deleteFile(files[i]);
                }
            }
            file.delete();
        }
    }
}
