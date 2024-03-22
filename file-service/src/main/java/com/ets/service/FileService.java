package com.ets.service;

import com.ets.repository.IFileRepository;
import com.ets.repository.entity.FileEntity;
import com.ets.utility.ServiceManager;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class FileService extends ServiceManager<FileEntity,Long> {

    private final IFileRepository repository;

    public FileService(IFileRepository repository){
        super(repository);
        this.repository = repository;
    }

    public void saveFile(MultipartFile file) throws IOException {
        // Check Size
        if (file.getSize() > 5 * 1024 * 1024) {
            throw new IllegalArgumentException("");
        }

        // Check Path
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String fileExtension = getFileExtension(fileName);
        if (!isValidExtension(fileExtension)) {
            throw new IllegalArgumentException("");
        }

        // Save File
        String filePath = "" + fileName;
        java.io.File localFile = new java.io.File(filePath);
        file.transferTo(localFile);

        // Save File information
        FileEntity fileEntity = new FileEntity(fileName, fileExtension, filePath, file.getSize());
        save(fileEntity);
    }

    public List<FileEntity> getAllFiles() {
        return findAll();
    }

    public Optional<FileEntity> getFileById(Long id) {
        return findById(id);
    }

    public void deleteFile(Long id) {
        deleteById(id);
    }

    public byte[] getFileContent(Long id) throws IOException {
        Optional<FileEntity> fileEntityOptional = findById(id);
        if (fileEntityOptional.isPresent()) {
            java.io.File file = new java.io.File(fileEntityOptional.get().getFilePath());
            return java.nio.file.Files.readAllBytes(file.toPath());
        } else {
            throw new IllegalArgumentException("Dosya bulunamadı.");
        }
    }

    private String getFileExtension(String fileName) {
        int lastIndexOf = fileName.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return "";
        }
        return fileName.substring(lastIndexOf + 1).toLowerCase();
    }

    private boolean isValidExtension(String extension) {
        List<String> supportedExtensions = List.of("png", "jpeg", "jpg", "docx", "pdf", "xlsx");
        return supportedExtensions.contains(extension);
    }
}
