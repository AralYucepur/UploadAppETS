package com.ets.repository;

import com.ets.repository.entity.FilesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IFileRepository extends JpaRepository<FilesEntity,Long>{

}
