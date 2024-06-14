package com.discussion.discussionservice.controller;

import com.discussion.discussionservice.model.Discussions;
import com.discussion.discussionservice.service.DiscussionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/discussion")
public class DiscussionController {
    @Autowired
    private DiscussionService discussionService;

    @PostMapping("/addDiscussion")
    public ResponseEntity<Discussions> createDiscussions(@RequestParam String text,
                                                         @RequestParam MultipartFile imageFile,
                                                         @RequestParam List<String> hashTags,
                                                         @RequestParam String email) {
        try {
            Discussions discussion = discussionService.createDiscussions(text, imageFile, hashTags, email);
            return new ResponseEntity<>(discussion, HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/getDiscussions")
    public ResponseEntity<List<Discussions>> getAllDiscussions() {
        List<Discussions> discussions = discussionService.getAllDiscussions();
        return new ResponseEntity<>(discussions, HttpStatus.OK);
    }

    @PutMapping("/updateDiscussion")
    public ResponseEntity<Discussions> updateDiscussions(@RequestParam String id,
                                                         @RequestParam String text,
                                                         @RequestParam(required = false) MultipartFile imageFile,
                                                         @RequestParam List<String> hashTags,
                                                         @RequestParam String email) {
        try {
            Discussions updatedDiscussions = discussionService.updateDiscussions(id, text, imageFile, hashTags, email);
            return updatedDiscussions != null ? new ResponseEntity<>(updatedDiscussions, HttpStatus.OK) : new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteDiscussion")
    public ResponseEntity<Void> deleteDiscussions(@RequestParam String id) {
        boolean isDeleted = discussionService.deleteDiscussions(id);
        return isDeleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/tags")
    public ResponseEntity<List<Discussions>> getDiscussionsByTags(@RequestParam List<String> hashTags) {
        List<Discussions> discussions = discussionService.getDiscussionsByTags(hashTags);
        return new ResponseEntity<>(discussions, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Discussions>> getDiscussionsByText(@RequestParam String text) {
        List<Discussions> discussions = discussionService.getDiscussionsByText(text);
        return new ResponseEntity<>(discussions, HttpStatus.OK);
    }

    @GetMapping("/viewCounts")
    public ResponseEntity<Integer> getViewCounts(@RequestParam String id) {
        int vc = discussionService.getViewCounts(id);
        return new ResponseEntity<>(vc, HttpStatus.OK);
    }
}
