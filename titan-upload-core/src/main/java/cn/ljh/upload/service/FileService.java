package cn.ljh.upload.service;

import cn.ljh.upload.entity.FileUploadPreprocessResponse;
import cn.ljh.upload.entity.ReturnVO;

import java.io.InputStream;

/**
 * 文件服务
 *
 * @author ljh
 * @version 1.0
 * @since 2024/1/5 16:19
 */
public interface FileService {

    /**
     * 预处理文件上传
     *
     * @param sha256     文件的SHA256
     * @param totalBytes 文件总字节数
     * @return 文件上传预处理响应
     */
    ReturnVO<FileUploadPreprocessResponse> preprocessFileUpload(String sha256, long totalBytes) throws Exception;

    /**
     * 上传文件
     *
     * @param sha256     文件的SHA256
     * @param file       文件
     * @param startByte  开始字节
     * @param totalBytes 文件总字节数
     */
    ReturnVO<String> uploadFile(String sha256, InputStream file, long startByte, long totalBytes);
}
