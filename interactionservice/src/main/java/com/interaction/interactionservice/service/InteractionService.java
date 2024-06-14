package com.interaction.interactionservice.service;

import com.interaction.interactionservice.model.Comments;
import com.interaction.interactionservice.model.Likes;
import com.interaction.interactionservice.repository.CommentRepository;
import com.interaction.interactionservice.repository.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InteractionService {
    @Autowired
    private CommentRepository commentsRepository;

    @Autowired
    private LikeRepository likesRepository;

    public List<Comments> getAllComments() {
        return commentsRepository.findAll();
    }

    public Optional<Comments> getCommentById(String id) {
        return commentsRepository.findById(id);
    }

    public Comments addComment(Comments comment) {
        return commentsRepository.save(comment);
    }

    public Comments updateComment(String id, Comments updatedComment) {
        Optional<Comments> existingComment = commentsRepository.findById(id);
        if (existingComment.isPresent()) {
            Comments comment = existingComment.get();
            comment.setComment_text(updatedComment.getComment_text());
            return commentsRepository.save(comment);
        }
        return null;
    }

    public void deleteComment(String id) {
        commentsRepository.deleteById(id);
    }

    public List<Comments> getCommentsByDiscussionId(String discussionId) {
        return commentsRepository.findByDiscussionId(discussionId);
    }

    public List<Comments> getRepliesByCommentId(String parentCommentId) {
        return commentsRepository.findByParentCommentId(parentCommentId);
    }

    public Comments replyToComment(String parentCommentId, Comments reply) {
        reply.setParentCommentId(parentCommentId);
        return commentsRepository.save(reply);
    }


    public Likes addLike(Likes like) {
        return likesRepository.save(like);
    }

    public List<Likes> getLikesByEmailAndDiscussionId(String email, String discussionId) {  // Add this method
        return likesRepository.findByEmailAndDiscussionId(email, discussionId);
    }

    public boolean deleteLike(String id) {
        if (likesRepository.existsById(id)) {
            likesRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public long countLikesByDiscussionId(String discussionId) {
        return likesRepository.countByDiscussionId(discussionId);
    }

    public long countLikesByCommentId(String commentId) {
        return likesRepository.countByCommentId(commentId);
    }

}
