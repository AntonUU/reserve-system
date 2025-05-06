package cn.anton.reservesystem.util;

import cn.anton.commonpackage.common.constant.OSSConstant;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.EnvironmentVariableCredentialsProvider;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Calendar;

/**
 * 类说明:
 * 阿里云OSS服务
 *
 * @author 丶Anton
 * @email itanton666@gmail.com
 * @date 2024/8/1 20:51
 */
@Component
@Slf4j
public class AliOSSUtil {

    @Value("${oss.endpoint}")
    public String endpoint;

    @Value("${oss.bucket}")
    public String bucket;

    /**
     * 通过本地文件路径上传文件
     *
     * @param objectName    对象存储文件名称
     * @param localFilePath 本地文件路径
     * @throws com.aliyuncs.exceptions.ClientException
     */
    public String putFileByFilePath(String objectName, String localFilePath) throws com.aliyuncs.exceptions.ClientException, IOException {
        String obj = getObjectPath() + objectName;
        log.info("【OSS-文件上传-路径】 文件上传开始 obj = {}", obj);//        String endpoint = "https://oss-cn-guangzhou.aliyuncs.com";
        EnvironmentVariableCredentialsProvider credentialsProvider =
                CredentialsProviderFactory.newEnvironmentVariableCredentialsProvider();

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, credentialsProvider);
        PutObjectResult result = null;
        try {
            // 创建PutObjectRequest对象。
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, obj, new File(localFilePath));

            // 上传文件。
            result = ossClient.putObject(putObjectRequest);
            log.info("【OSS-文件上传-路径】 文件上传完成 obj = {}, tag = {}", obj, result.getETag());
            return obj;
        } catch (Exception e) {
            log.info("【OSS-文件上传-路径】 文件上传失败 e = {}", e.getMessage());
            return null;
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
            if (result != null) {
                result.getResponse().getContent().close();
            }
        }
    }

    /**
     * 通过流的方式上传文件
     *
     * @param objectName 对象存储文件名称
     * @param stream     文件流
     * @throws com.aliyuncs.exceptions.ClientException
     * @return 对象路径
     */
    public String putFileByStream(String objectName, InputStream stream) throws com.aliyuncs.exceptions.ClientException, IOException {
        String obj = getObjectPath() + objectName;
        log.info("【OSS-文件上传-流】 文件上传开始 obj = {}", obj);
        EnvironmentVariableCredentialsProvider credentialsProvider = CredentialsProviderFactory.newEnvironmentVariableCredentialsProvider();

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, credentialsProvider);
        PutObjectResult result = null;
        try {
            // 创建PutObjectRequest对象。
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, obj, stream);

            // 上传文件。
            result = ossClient.putObject(putObjectRequest);
            log.info("【OSS-文件上传-流】 文件上传完成 obj = {}, tag = {}", obj, result.getETag());
        } catch (Exception e) {
            log.info("【OSS-文件上传-流】 文件上传失败 e = {}", e.getMessage());
            return null;
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
            if (result != null && result.getResponse() != null) {
                result.getResponse().getContent().close();
            }
        }
        return obj;
    }

    /**
     * 通过本地时间获取对象存储路径
     */
    private String getObjectPath() {
        return OSSConstant.OBJECT_PATH + Calendar.getInstance().getWeekYear() + "/";
    }

}
