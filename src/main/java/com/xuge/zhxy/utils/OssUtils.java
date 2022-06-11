package com.xuge.zhxy.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.OSSObject;
import com.aliyuncs.exceptions.ClientException;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;

/**
 * author: yjx
 * Date :2022/6/1120:01
 **/
public class OssUtils {
  public static void upload(MultipartFile multipartFile){
    // yourEndpoint填写Bucket所在地域对应的Endpoint。以华东1（杭州）为例，Endpoint填写为https://oss-cn-hangzhou.aliyuncs.com。
    String endpoint = "oss-cn-beijing.aliyuncs.com";
// 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
    String accessKeyId = "LTAI5tNf4mAyWoNHn5A86CZB";
    String accessKeySecret = "rBgxvni0xTCjkrvd0zLSud6FbiDpGW";
// 填写Bucket名称，例如examplebucket。
    String bucketName = "edu-xuge-1001";
// 填写文件名。文件名包含路径，不包含Bucket名称。例如exampledir/exampleobject.txt。
    //使用UUID随机生成文件名
    String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
    //生成新的文件名字
    String filename = "2020/6/11"+uuid.concat(multipartFile.getOriginalFilename());
    //生成文件的保存路径(实际生产环境这里会使用真正的文件存储服务器)

// 创建OSSClient实例。
    OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

    String content = "Hello OSS";
    ossClient.putObject(bucketName, filename, new ByteArrayInputStream(content.getBytes()));

// 关闭OSSClient。
    ossClient.shutdown();
  }

  public static void download () {
    String endpoint = "oss-cn-beijing.aliyuncs.com";
// 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
    String accessKeyId = "LTAI5tNf4mAyWoNHn5A86CZB";
    String accessKeySecret = "rBgxvni0xTCjkrvd0zLSud6FbiDpGW";
// 填写Bucket名称，例如examplebucket。
    String bucketName = "edu-xuge-1001";
    // 填写Object完整路径，例如exampledir/exampleobject.txt。Object完整路径中不能包含Bucket名称。
    String objectName = "exampledir/exampleobject.txt";

    // 创建OSSClient实例。
    OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

    try {
      // 调用ossClient.getObject返回一个OSSObject实例，该实例包含文件内容及文件元信息。
      OSSObject ossObject = ossClient.getObject(bucketName, objectName);
      // 调用ossObject.getObjectContent获取文件输入流，可读取此输入流获取其内容。
      InputStream content = ossObject.getObjectContent();
      if (content != null) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(content));
        while (true) {
          String line = reader.readLine();
          if (line == null) break;
          System.out.println("\n" + line);
        }
        // 数据读取完成后，获取的流必须关闭，否则会造成连接泄漏，导致请求无连接可用，程序无法正常工作。
        content.close();
      }
    } catch (OSSException oe) {
      System.out.println("Caught an OSSException, which means your request made it to OSS, "
              + "but was rejected with an error response for some reason.");
      System.out.println("Error Message:" + oe.getErrorMessage());
      System.out.println("Error Code:" + oe.getErrorCode());
      System.out.println("Request ID:" + oe.getRequestId());
      System.out.println("Host ID:" + oe.getHostId());
    } catch (Exception ce) {
      System.out.println("Caught an ClientException, which means the client encountered "
              + "a serious internal problem while trying to communicate with OSS, "
              + "such as not being able to access the network.");
      System.out.println("Error Message:" + ce.getMessage());

    } finally {
      if (ossClient != null) {
        ossClient.shutdown();
      }
    }
  }
}
