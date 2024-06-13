package com.discussion.discussionservice.service;

import com.discussion.discussionservice.Repository.DiscussionRepository;
import com.discussion.discussionservice.model.Discussions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import org.bson.types.ObjectId;

@Service
public class DiscussionService {
    @Autowired
    private final GridFsTemplate gridFsTemplate;

    @Autowired
    private DiscussionRepository discussionRepository;

    public DiscussionService(GridFsTemplate gridFsTemplate){
        this.gridFsTemplate = gridFsTemplate;
    }

    public Discussions createDiscussions(String text, MultipartFile imageFile, List<String> hashTags, String email) throws IOException {
        // Store the image file in GridFS
        ObjectId imageId = gridFsTemplate.store(imageFile.getInputStream(), imageFile.getOriginalFilename(), imageFile.getContentType());

        // Create a new discussion post
        Discussions discussion = new Discussions();
        discussion.setText(text);
        discussion.setImageId(imageId.toString());
        discussion.setHashTags(hashTags);
        discussion.setCreatedOn(new Date());
        discussion.setEmail(email); // Set email

        // Save the discussion post
        return discussionRepository.save(discussion);
    }

    public List<Discussions> getAllDiscussions() {
        return discussionRepository.findAll();
    }

    public Discussions updateDiscussions(String id, String text, MultipartFile imageFile, List<String> hashTags, String email) throws IOException {
        Discussions discussion = discussionRepository.findById(id).orElse(null);
        if (discussion != null) {
            discussion.setText(text);
            discussion.setHashTags(hashTags);
            discussion.setEmail(email); // Update email

            if (imageFile != null && !imageFile.isEmpty()) {
                // Delete old image from GridFS
                if (discussion.getImageId() != null) {
                    gridFsTemplate.delete(new Query(Criteria.where("_id").is(discussion.getImageId())));
                }

                // Store new image file in GridFS
                ObjectId imageId = gridFsTemplate.store(imageFile.getInputStream(), imageFile.getOriginalFilename(), imageFile.getContentType());
                discussion.setImageId(imageId.toString());
            }

            // Save updated discussion post
            return discussionRepository.save(discussion);
        }
        return null;
    }

    public boolean deleteDiscussions(String id) {
        Discussions discussion = discussionRepository.findById(id).orElse(null);
        if (discussion != null) {
            // Delete image from GridFS
            if (discussion.getImageId() != null) {
                gridFsTemplate.delete(new Query(Criteria.where("_id").is(discussion.getImageId())));
            }

            // Delete discussion post
            discussionRepository.deleteById(id);
            return true;
        }
        return false;
    }


    public List<Discussions> getDiscussionsByTags(List<String> hashTags) {
        return discussionRepository.findByHashTagsIn(hashTags);
    }

    public List<Discussions> getDiscussionsByText(String text) {
        return discussionRepository.findByTextContainingIgnoreCase(text);
    }
}
