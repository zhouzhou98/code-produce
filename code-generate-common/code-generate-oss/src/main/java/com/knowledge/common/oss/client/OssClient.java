package com.knowledge.common.oss.client;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.PutObjectResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;

/**
 * oss client
 * @author suyuzhou
 * @date 2022-07-15
 */
@Slf4j
public class OssClient {
    /**
     * oss配置
     */
    private OSS client;
    /**
     * 包名
     */
    private String bucketName;

    /**
     * 根目录
     */
    private String dir;

    private String endPoint;

    /**
     * oss配置
     */
    public OssClient(String bucketName, String endPoint, String accessKeyId, String accessKeySecret, String dir) {
        this.client = new OSSClientBuilder().build(endPoint,accessKeyId,accessKeySecret);
        this.bucketName = bucketName;
        this.dir = dir;
        this.endPoint = endPoint;
    }

    /**
     * 上传文件
     */
    public String uploadImg(MultipartFile file, String dir) {
        //获取文件原始名称 xxx.jpg
        String originalFilename = file.getOriginalFilename();

        //jdk8语法日期格式
        LocalDateTime ldt = LocalDateTime.now();
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy/MM/dd");

        //user/2022/12/12/sdsdwe/
        String folder = pattern.format(ldt);
        String fileName = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 32);
        String extendsion = originalFilename.substring(originalFilename.lastIndexOf("."));

        //在oss上的bucket创建文件夹
        String newFilename = dir + "/" + folder + "/" + fileName + extendsion;
        try {
            PutObjectResult putObjectResult = this.client.putObject(bucketName, newFilename, file.getInputStream());
            //拼装返回路径
            if(putObjectResult!=null){
                String imgUrl = "https://"+ bucketName +"." + this.endPoint + "/" + newFilename;
                return imgUrl;
            }

        } catch (IOException e) {
            log.error("文件上传失败:{}",e.getMessage());
        }finally {
            this.client.shutdown();
        }
        return null;
    }


    public BufferedImage getFileBufferedImage(String filePath)throws IOException {
        OSSObject ossRes = this.client.getObject(this.bucketName, filePath);
        InputStream out = ossRes.getObjectContent();
        byte[] bytes = this.toByteArray(out);
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        in.close();
        out.close();
        return ImageIO.read(in);
    }

    public BufferedImage getBufferedImage(String faceImage) throws IOException {
        String img = "faceImage" + faceImage.split("faceImage")[1];
        return this.getFileBufferedImage(img);
    }

    public String uploadInputStream(InputStream in, String dir, String format) {
        // 获取文件名
        String filename = getPath(format, dir);
        // 上传文件
        this.client.putObject(this.bucketName, filename, in);
        // 返回文件名
        return "https://" + this.bucketName + ".oss-cn-guangzhou.aliyuncs.com/" + filename;
    }

    /**
     * 获取文件名
     *
     * @param ext
     * @param dir
     * @return
     */
    private String getPath(String ext, String dir) {
        return dir + "/" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + "/"
                + UUID.randomUUID().toString() + "." + ext;
    }
    /**
     * InputStream流转byte数组
     *
     * @param input
     * @return
     * @throws IOException
     */
    private byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[input.available()];
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
        return output.toByteArray();
    }
}
