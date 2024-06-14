package com.interaction.interactionservice.repository;

import com.interaction.interactionservice.model.Likes;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends MongoRepository<Likes,String> {
    long countByDiscussionId(String discussionId);
    long countByCommentId(String commentId);
    List<Likes> findByEmailAndDiscussionId(String email, String discussionId);
}
