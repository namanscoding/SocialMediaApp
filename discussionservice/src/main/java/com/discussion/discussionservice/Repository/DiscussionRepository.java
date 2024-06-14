package com.discussion.discussionservice.Repository;

import com.discussion.discussionservice.model.Discussions;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiscussionRepository extends MongoRepository<Discussions, String> {
    List<Discussions> findByHashTagsIn(List<String> hashTags);
    List<Discussions> findByTextContainingIgnoreCase(String text);
}
