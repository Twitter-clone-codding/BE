package com.twitter.clone.twitterclone.global.util;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.twitter.clone.twitterclone.global.execption.type.FileErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class S3Util {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    /**
     * 이미지 파일 확장자
     * @param originalFileName
     * @return
     */
    private String[] extensionList = {
            ".jpeg",
            ".jpg",
            ".png"
    };

    /**
     * 이미지 파일 이름 생성
     * @param multipartFile
     * @param filename
     * @return
     */
    public String saveFile(MultipartFile multipartFile, String filename) {

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());

        try {
            amazonS3.putObject(bucket, filename, multipartFile.getInputStream(), metadata);
        } catch (IOException e) {
            System.out.println(e);
        }
        
        return amazonS3.getUrl(bucket, filename).toString();
    }
    /**
     * 이미지 파일 s3에 저장 후 Dto 리스트에 담아서 반환
     * @param multipartFile
     * @return
     */
    public List<String> saveListFile(List<MultipartFile> multipartFile) {
        List<String> imgUrlList = new ArrayList<>();

        // forEach 구문을 통해 multipartFile로 넘어온 파일들 하나씩 fileNameList에 추가
        for (MultipartFile file : multipartFile) {
            String fileName = getImageName(file.getOriginalFilename());
            saveFile(file, fileName);
            imgUrlList.add(fileName);
        }
        return imgUrlList;
    }
    /**
     * 이미지 삭제
     * @param filename
     */
    public void deleteImage(String filename) {
        amazonS3.deleteObject(bucket, filename);
    }
    /**
     * 이미지 이름 생성
     * @param getOriginalFilename
     * @return
     */
    private String getImageName(String getOriginalFilename) {
        if (getOriginalFilename.length() == 0) {
            throw new IllegalArgumentException(FileErrorCode.NO_FILE_NAME.getErrorMsg());
        }
        int dotIndex = getOriginalFilename.lastIndexOf('.');
        String extension = getOriginalFilename.substring(dotIndex);
        return System.nanoTime() + getFileExtension(extension);
    }
    /**
     * 이미지 파일인지 확인
     * @param extension
     * @return
     */
    private String getFileExtension(String extension) {
        for (String extensions : extensionList) {
            if (extension.equals(extensions)) {
                return extension;
            }
        }
        throw new IllegalArgumentException(FileErrorCode.NO_IMAGEFILE.getErrorMsg());
    }
}
