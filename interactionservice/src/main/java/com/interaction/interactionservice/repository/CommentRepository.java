package com.interaction.interactionservice.repository;

import com.interaction.interactionservice.model.Comments;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends MongoRepository<Comments,String> {
    List<Comments> findByDiscussionId(String discussionId);
    List<Comments> findByParentCommentId(String parentCommentId);
}
