package com.amin.backenddevelopertask.service.cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class CloudinaryService {

    private static final Logger logger = LoggerFactory.getLogger(CloudinaryService.class);
    private static final List<String> ALLOWED_CONTENT_TYPES = List.of(
            "image/jpeg",
            "image/png",
            "image/gif",
            "image/jpg"
    );
    private static final long MAX_FILE_SIZE = 5_242_880; // 5MB
    private static final int MAX_MULTIPLE_FILES = 10;

    private final Cloudinary cloudinary;

    @Autowired
    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public String uploadFile(MultipartFile file) throws IOException {
        validateFile(file);

        try {
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(),
                    ObjectUtils.asMap(
                            "folder", "products",
                            "resource_type", "auto"
                    ));

            String url = uploadResult.get("url").toString();
            logger.info("File uploaded successfully: {}", url);
            return url;
        } catch (Exception e) {
            logger.error("File upload failed: {}", e.getMessage());
            throw new IOException("Fayl yüklənərkən xəta baş verdi", e);
        }
    }


    public void deleteFile(String publicId) throws IOException {
        try {
            Map result = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            if (!"ok".equals(result.get("result"))) {
                throw new IOException("Fayl silinmədi: " + result.get("result"));
            }
            logger.info("File deleted successfully: {}", publicId);
        } catch (Exception e) {
            logger.error("File deletion failed: {}", e.getMessage());
            throw new IOException("Fayl silinərkən xəta baş verdi", e);
        }
    }

    public List<String> uploadMultipleFiles(List<MultipartFile> files) throws IOException {
        if (files == null || files.isEmpty()) {
            throw new IllegalArgumentException("Fayl listi boş ola bilməz");
        }
        if (files.size() > MAX_MULTIPLE_FILES) {
            throw new IllegalArgumentException("Maksimum " + MAX_MULTIPLE_FILES + " fayl yükləyə bilərsiniz");
        }

        List<String> urls = new ArrayList<>();
        for (MultipartFile file : files) {
            try {
                urls.add(uploadFile(file));
            } catch (IOException e) {
                logger.warn("Failed to upload file: {}", file.getOriginalFilename());
                urls.add("Xəta: " + file.getOriginalFilename());
            }
        }
        return urls;
    }

    private void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Yüklənəcək fayl boş ola bilməz");
        }

        String filename = file.getOriginalFilename();
        if (filename == null || !filename.matches("(?i).+\\.(jpg|jpeg|png|gif)$")) {
            throw new IllegalArgumentException("Yalnız JPG, JPEG, PNG və GIF faylları qəbul edilir");
        }

        String contentType = file.getContentType();
        if (contentType == null) {
            throw new IllegalArgumentException("Fayl tipi müəyyən edilə bilmədi");
        }

        contentType = contentType.toLowerCase();
        if (!ALLOWED_CONTENT_TYPES.contains(contentType)) {
            throw new IllegalArgumentException(
                    "Yalnız " + ALLOWED_CONTENT_TYPES + " fayl növləri qəbul edilir. Göndərilən tip: " + contentType
            );
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new IllegalArgumentException(
                    "Fayl ölçüsü " + (MAX_FILE_SIZE/1024/1024) + "MB-dan çox ola bilməz"
            );
        }
    }
}