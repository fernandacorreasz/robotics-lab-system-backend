package robotic.system.forum.app.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import robotic.system.forum.app.service.EditHistoryService;
import robotic.system.forum.app.service.ForumCommentService;
import robotic.system.forum.app.service.ForumServiceRegister;
import robotic.system.forum.app.service.TagService;
import robotic.system.forum.domain.model.*;


import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/forum")
public class ForumController {

    @Autowired
    private TagService tagService;

    @Autowired
    private ForumServiceRegister forumService;

    @Autowired
    private ForumCommentService forumCommentService;

    @Autowired
    private EditHistoryService editHistoryService;

    @PostMapping("/tags")
    public ResponseEntity<Tag> createTag(@RequestBody Tag tag) {
        Tag createdTag = tagService.createTag(tag);
        return ResponseEntity.ok(createdTag);
    }

    @PostMapping("/create")
    public ResponseEntity<Forum> createForum(@RequestBody Forum forum) {
        Forum createdForum = forumService.createForum(forum);
        return ResponseEntity.ok(createdForum);
    }

    @PostMapping("/{forumId}/comments")
    public ResponseEntity<ForumComment> createComment(@PathVariable UUID forumId, @RequestBody ForumComment comment) {
        Forum forum = forumService.getAllForums().stream().filter(f -> f.getId().equals(forumId)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Forum not found"));

        comment.setForum(forum);
        ForumComment createdComment = forumCommentService.createComment(comment);
        return ResponseEntity.ok(createdComment);
    }

    @PostMapping("/{forumId}/edit-history")
    public ResponseEntity<EditHistory> createEditHistory(@PathVariable UUID forumId, @RequestBody EditHistory editHistory) {
        Forum forum = forumService.getAllForums().stream().filter(f -> f.getId().equals(forumId)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Forum not found"));

        editHistory.setReferenceId(forum.getId());
        EditHistory createdEditHistory = editHistoryService.createEditHistory(editHistory);
        return ResponseEntity.ok(createdEditHistory);
    }
}
