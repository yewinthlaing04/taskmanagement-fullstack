package com.ye.task.repo;

import com.ye.task.dto.CommentDto;
import com.ye.task.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment  , Long > {
    List<Comment> findAllByTaskId(Long taskId);
}
