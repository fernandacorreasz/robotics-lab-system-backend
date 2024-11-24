package robotic.system.forum.app.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import robotic.system.forum.app.service.*;
import robotic.system.forum.domain.dto.*;
import robotic.system.forum.domain.model.EditHistory;
import robotic.system.forum.domain.model.Forum;
import robotic.system.forum.domain.model.Tag;
import robotic.system.util.delete.BulkDeleteService;
import robotic.system.util.filter.FilterRequest;
import robotic.system.util.filter.FilterUtil;

import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/forum")
public class ForumController {

    @Autowired
    private TagService tagService;

    @Autowired
    private ForumService forumService;

    @Autowired
    private ForumCommentService forumCommentService;

    @Autowired
    private EditHistoryService editHistoryService;

    @Autowired
    private ForumBulkDeleteService forumBulkDeleteService;


    @PostMapping("/tags")
    public ResponseEntity<TagResponseDTO> createTag(@RequestBody TagCreateDTO tagCreateDTO) {
        TagResponseDTO responseDTO = tagService.createTag(tagCreateDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/tags")
    public ResponseEntity<List<TagDTO>> getAllTags() {
        List<Tag> tags = tagService.getAllTags();

        List<TagDTO> tagDTOs = tags.stream()
                .map(tag -> new TagDTO(tag.getId(), tag.getName()))
                .toList();
        return ResponseEntity.ok(tagDTOs);
    }

    @PostMapping("/create")
    public ResponseEntity<ForumResponseDTO> createForum(@RequestBody ForumCreateDTO forumCreateDTO) {
        ForumResponseDTO responseDTO = forumService.createForum(forumCreateDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/{forumId}/comments")
    public ResponseEntity<ForumCommentResponseDTO> createComment(@RequestBody ForumCommentCreateDTO commentDTO) {
        ForumCommentResponseDTO responseDTO = forumCommentService.createComment(commentDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/{forumId}/edit-history")
    public ResponseEntity<EditHistory> createEditHistory(@PathVariable UUID forumId,
                                                         @RequestBody EditHistory editHistory) {
        Forum forum = forumService.getAllForums().stream().filter(f -> f.getId().equals(forumId)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Forum not found"));

        editHistory.setReferenceId(forum.getId());
        EditHistory createdEditHistory = editHistoryService.createEditHistory(editHistory);
        return ResponseEntity.ok(createdEditHistory);
    }

    @GetMapping("/{forumId}")
    public ResponseEntity<ForumDTO> getForumById(@PathVariable UUID forumId) {
        ForumDTO forumDTO = forumService.getForumById(forumId);
        return ResponseEntity.ok(forumDTO);
    }

    @PostMapping("/filter")
    public ResponseEntity<Page<ForumDTO>> filterForums(
            @RequestBody List<FilterRequest> filters,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Specification<Forum> spec = FilterUtil.byFilters(filters);
        Page<ForumDTO> filteredForums = forumService.filterForums(spec, pageable);
        return ResponseEntity.ok(filteredForums);
    }


    @DeleteMapping("/delete")
    public ResponseEntity<BulkDeleteService.BulkDeleteResult> deleteForums(@RequestBody List<String> forumIds) {
        BulkDeleteService.BulkDeleteResult result = forumBulkDeleteService.deleteForumsByIds(forumIds);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/update")
    public ResponseEntity<ForumResponseDTO> updateForum(@RequestBody ForumUpdateDTO forumUpdateDTO) {
        ForumResponseDTO responseDTO = forumService.updateForum(forumUpdateDTO);
        return ResponseEntity.ok(responseDTO);
    }
}
