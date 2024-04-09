package com.it.revolution.trees.app.amazon.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.it.revolution.trees.app.amazon.settings.AWSS3Settings;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AWSS3Service implements FileService {

    private final AmazonS3Client amazonS3Client;
    private final AWSS3Settings settings;

    @Override
    public String uploadFile(MultipartFile file) {
        log.debug("Uploading file...");
        String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        String key = UUID.randomUUID() + "." + extension;

        ObjectMetadata metadata = createObjectMetadata(file);

        String bucketName = settings.getBucket().getName();
        try {
            amazonS3Client.putObject(bucketName, key, file.getInputStream(), metadata );
            log.debug("File has been successfully uploaded.");
        } catch (Exception e) {
            log.error("Couldn't upload the file.", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occupied while uploading the file");
        }

        amazonS3Client.setObjectAcl(bucketName, key, CannedAccessControlList.PublicRead);

        String resourceUrl = amazonS3Client.getResourceUrl(bucketName, key);
        log.debug("File's resourceURL: {}", resourceUrl);
        return resourceUrl;
    }

    @Override
    public void deleteFile(String fileUrl) {
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/")+1);
        amazonS3Client.deleteObject(settings.getBucket().getName(), fileName);
    }

    private ObjectMetadata createObjectMetadata(MultipartFile file) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());
        return metadata;
    }

}
