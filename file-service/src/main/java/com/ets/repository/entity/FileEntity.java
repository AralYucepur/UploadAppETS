package com.ets.repository.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Data
@Builder
@Table(name = "Files")
@Entity
public class FileEntity {
    public FileEntity() {
    }

    public FileEntity(String name, String extension, String filePath, Long size) {
        this.name = name;
        this.extension = extension;
        this.filePath = filePath;
        this.size = size;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    String extension;
    String filePath;
    Long size;
}
