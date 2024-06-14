package com.interaction.interactionservice.controller;

import com.interaction.interactionservice.model.Comments;
import com.interaction.interactionservice.model.Likes;
import com.interaction.interactionservice.service.InteractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/interaction")
public class InteractionController {

    @Autowired
    private InteractionService interactionService;

    @PostMapping("/addComment")
    public Comments addComment(@RequestBody Comments comment) {
        return interactionService.addComment(comment);
    }

    @PutMapping("/updateComment")
    public ResponseEntity<Comments> updateComment(@RequestParam String id, @RequestBody Comments updatedComment) {
        Comments comment = interactionService.updateComment(id, updatedComment);
        if (comment != null) {
            return ResponseEntity.ok(comment);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/deleteComment")
    public ResponseEntity<Void> deleteComment(@RequestParam String id) {
        interactionService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/getByDiscussionId")
    public List<Comments> getCommentsByDiscussionId(@RequestParam String discussionId) {
        return interactionService.getCommentsByDiscussionId(discussionId);
    }

    @PostMapping("/replyToComment")
    public Comments replyToComment(@RequestParam String parentCommentId, @RequestBody Comments reply) {
        return interactionService.replyToComment(parentCommentId, reply);
    }

    @PostMapping("/addLike")
    public ResponseEntity<Likes> addLike(@RequestBody Likes like) {
        Likes createdLike = interactionService.addLike(like);
        return ResponseEntity.ok(createdLike);
    }

    @GetMapping("/getLikesByEmailAndDiscussionId")
    public List<Likes> getLikesByEmailAndDiscussionId(@RequestParam String email, @RequestParam String discussionId) {
        return interactionService.getLikesByEmailAndDiscussionId(email, discussionId);
    }


    @GetMapping("/countLikesByDiscussionId")
    public ResponseEntity<Long> countLikesByDiscussionId(@RequestParam String discussionId) {
        long count = interactionService.countLikesByDiscussionId(discussionId);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/countLikesByCommentId")
    public ResponseEntity<Long> countLikesByCommentId(@RequestParam String commentId) {
        long count = interactionService.countLikesByCommentId(commentId);
        return ResponseEntity.ok(count);
    }
}
