package com.dongxiang.model.repository;

import com.dongxiang.model.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


@Repository
public interface UploadFileRepository extends JpaRepository<FileEntity, Integer> {


    @Modifying      // 说明该方法是修改操作
    @Transactional  // 说明该方法是事务性操作
    // 定义查询
    // @Param注解用于提取参数
    @Query("update FileEntity file set file.name=:qName, file.md5=:qMd5, file.timestamp=:qTimestamp where file.id=:qId")
    public void updateUser(@Param("qName") String name, @Param("qMd5") String md5,
                           @Param("qTimestamp") Date timestamp, @Param("qId") Integer id);


    @Query("select file from FileEntity file where file.md5 in(:qMd5s) ")
    public List<FileEntity> findByMd5s(@Param("qMd5s") List<String> md5s);
    @Query("select file from FileEntity file where file.md5 =:qMd5 ")
    public List<FileEntity> findByMd5(@Param("qMd5") String md5);
}
