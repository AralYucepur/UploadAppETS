package com.ets.controller;

import com.ets.repository.entity.FilesEntity;
import com.ets.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.ets.constants.RestApi.*;

@RestController
@RequestMapping(FILE)
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping(value = "/upload", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    @Operation(summary = "Upload File", description = "Uploads a file as multipart form data.")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
            return  ResponseEntity.ok(fileService.saveFile(file));

    }

    @GetMapping("/list")
    @Operation(summary = "List All Files", description = "Lists all files in the system.")
    //Authorize can be added this way.
    //@PreAuthorize("hasAuthority('ADMINISTRATOR')")
    public ResponseEntity<List<FilesEntity>>getAllFiles(){
        return ResponseEntity.ok(fileService.getAllFiles());
    }

    @GetMapping("/find_file")
    @Operation(summary = "Find File by ID", description = "Finds a file by the given ID.")
    public ResponseEntity<FilesEntity> getFileById(@RequestParam Long id){
        return ResponseEntity.ok(fileService.getFileById(id));
    }
    @DeleteMapping("/delete_file")
    @Operation(summary = "Delete File by ID", description = "Deletes a file by the given ID.")
    public ResponseEntity<String> deleteFileById(@RequestParam Long id){
        return ResponseEntity.ok(fileService.deleteFileById(id));
    }

    @GetMapping("/find_byte_file")
    @Operation(summary = "Get File Content by ID", description = "Gets the content of a file by the given ID as byte array.")
    public ResponseEntity<byte[]> getFileContent(@RequestParam Long id) throws IOException {
        return ResponseEntity.ok(fileService.getFileContent(id));
    }
}
