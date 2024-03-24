package com.ets.repository.entity;

import lombok.Data;

import javax.persistence.*;


@Data
@Table(name = "Files")
@Entity
public class FilesEntity {
    public FilesEntity() {
    }

    public FilesEntity(String name, String extension, String filePath, Long size) {
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
