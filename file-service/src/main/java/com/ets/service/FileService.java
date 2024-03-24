package com.ets.service;

import com.ets.exception.ErrorType;
import com.ets.exception.FileServiceException;
import com.ets.repository.IFileRepository;
import com.ets.repository.entity.FilesEntity;
import com.ets.utility.ServiceManager;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class FileService extends ServiceManager<FilesEntity,Long> {

    private final IFileRepository repository;

    public FileService(IFileRepository repository){
        super(repository);
        this.repository = repository;
    }

    public String saveFile(MultipartFile file) throws IOException {
        // Check Size
        if(file.getSize() > 5 * 1024 * 1024) {
            throw new IllegalArgumentException("Dosya boyutu 5mb dan fazla olamaz!");
        }

        // Check Path
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String fileExtension = getFileExtension(fileName);
        if(!isValidExtension(fileExtension)) {
            throw new IllegalArgumentException("Dosya uzantısı desteklenmiyor. Lütfen png, jpeg, jpg, docx, pdf, xls türünde dosya yükleyin");
        }

        // Save File
        String filePath = "/Users/aral/Desktop/ets-proje/" + fileName;
        java.io.File localFile = new java.io.File(filePath);
        if(localFile.exists()){
            throw new FileServiceException(ErrorType.UPLOAD_ERROR);
        }
        file.transferTo(localFile);

        // Save File information
        FilesEntity filesEntity = new FilesEntity(fileName, fileExtension, filePath, file.getSize());
        save(filesEntity);
        return "Dosya yükleme işleminiz başarıyla gerçekleşmiştir.";
    }

    public List<FilesEntity> getAllFiles() {
        return findAll();
    }

    public FilesEntity getFileById(Long id) {
        Optional<FilesEntity> optionalFilesEntity = findById(id);
        if(optionalFilesEntity.isPresent()){
            return optionalFilesEntity.get();
        }else{
            throw new IllegalStateException("Dosya bulunamadı.");
        }
    }

    public String deleteFileById(Long id) {
        Optional<FilesEntity> optionalFilesEntity = findById(id);
        if(optionalFilesEntity.isPresent()) {
            String filePath = optionalFilesEntity.get().getFilePath();
            java.io.File file = new java.io.File(filePath);
            if(file.exists()) {
                if(file.delete()) {
                    deleteById(id);
                    return "Dosya başarıyla silinmiştir.";
                }else{
                    throw new IllegalStateException("Dosya diskten silinirken bir hata oluştu.");
                }
            }else{
                throw new IllegalStateException("Dosya diskte bulunamadı.");
            }
        }else{
            throw new IllegalStateException("Dosya bulunamadı.");
        }
    }

    public byte[] getFileContent(Long id) throws IOException {
        Optional<FilesEntity> optionalFilesEntity = findById(id);
        if (optionalFilesEntity.isPresent()) {
            java.io.File file = new java.io.File(optionalFilesEntity.get().getFilePath());
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
