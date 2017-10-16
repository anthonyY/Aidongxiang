package com.dongxiang.model.repository;

import com.dongxiang.model.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author Anthony
 * createTime 2017-08-19
 */
@Repository
public interface PostRepository extends JpaRepository<PostEntity, Integer> {


    @Modifying      // 说明该方法是修改操作
    @Transactional  // 说明该方法是事务性操作
    // 定义查询
    // @Param注解用于提取参数
    @Query("update PostEntity post set post.name=:qName, post.imagePath=:qImagePath, post.userId=:qUserId," +
            " post.content=:qContent, post.timestamp=:qTimestamp where post.id=:qId")
    public void updatePost(@Param("qName") String name, @Param("qImagePath") String imagePath,
                           @Param("qUserId") int userId, @Param("qContent") int content,
                           @Param("qTimestamp") Date timestamp, @Param("qId") Integer id);
}
