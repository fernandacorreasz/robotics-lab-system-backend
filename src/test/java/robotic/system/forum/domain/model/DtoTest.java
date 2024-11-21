package robotic.system.forum.domain.model;

import org.junit.jupiter.api.Test;
import robotic.system.forum.domain.dto.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class DtoTest {

    @Test
    void testForumCommentCreateDTO() {
        ForumCommentCreateDTO dto = new ForumCommentCreateDTO();
        UUID forumId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        dto.setContent("Test Content");
        dto.setCodeSnippet("Test Snippet");
        dto.setForumId(forumId);
        dto.setUserId(userId);

        assertEquals("Test Content", dto.getContent());
        assertEquals("Test Snippet", dto.getCodeSnippet());
        assertEquals(forumId, dto.getForumId());
        assertEquals(userId, dto.getUserId());
    }

    @Test
    void testForumCommentDTO() {
        ForumCommentDTO dto = new ForumCommentDTO();
        UUID id = UUID.randomUUID();
        Date now = new Date();

        dto.setId(id);
        dto.setContent("Comment Content");
        dto.setCreationDate(now);
        dto.setEditDate(now);
        dto.setVoteCount(5);

        assertEquals(id, dto.getId());
        assertEquals("Comment Content", dto.getContent());
        assertEquals(now, dto.getCreationDate());
        assertEquals(now, dto.getEditDate());
        assertEquals(5, dto.getVoteCount());
    }

    @Test
    void testForumCreateDTO() {
        ForumCreateDTO dto = new ForumCreateDTO();
        UUID userId = UUID.randomUUID();
        List<UUID> tagIds = List.of(UUID.randomUUID());

        dto.setTitle("Forum Title");
        dto.setDescription("Forum Description");
        dto.setCodeSnippet("Snippet");
        dto.setStatus("OPEN");
        dto.setUserId(userId);
        dto.setTagIds(tagIds);

        assertEquals("Forum Title", dto.getTitle());
        assertEquals("Forum Description", dto.getDescription());
        assertEquals("Snippet", dto.getCodeSnippet());
        assertEquals("OPEN", dto.getStatus());
        assertEquals(userId, dto.getUserId());
        assertEquals(tagIds, dto.getTagIds());
    }

    @Test
    void testForumCommentResponseDTO() {
        UUID commentId = UUID.randomUUID();
        ForumCommentResponseDTO dto = new ForumCommentResponseDTO(commentId);

        assertEquals(commentId, dto.getCommentId());

        UUID newCommentId = UUID.randomUUID();
        dto.setCommentId(newCommentId);

        assertEquals(newCommentId, dto.getCommentId());
    }

    @Test
    void testForumDTO() {
        UUID id = UUID.randomUUID();
        Date now = new Date();
        ForumDTO dto = new ForumDTO();

        dto.setId(id);
        dto.setTitle("Forum Title");
        dto.setDescription("Forum Description");
        dto.setCodeSnippet("Code Snippet");
        dto.setCreationDate(now);
        dto.setEditDate(now);
        dto.setVoteCount(10);
        dto.setUserName("User Name");
        dto.setUserId(UUID.randomUUID());

        assertEquals(id, dto.getId());
        assertEquals("Forum Title", dto.getTitle());
        assertEquals("Forum Description", dto.getDescription());
        assertEquals("Code Snippet", dto.getCodeSnippet());
        assertEquals(now, dto.getCreationDate());
        assertEquals(now, dto.getEditDate());
        assertEquals(10, dto.getVoteCount());
        assertEquals("User Name", dto.getUserName());
    }

    @Test
    void testForumResponseDTO() {
        UUID id = UUID.randomUUID();
        Date now = new Date();
        ForumResponseDTO dto = new ForumResponseDTO(id, "Title", "Description", "Snippet", "OPEN", now);

        assertEquals(id, dto.getId());
        assertEquals("Title", dto.getTitle());
        assertEquals("Description", dto.getDescription());
        assertEquals("Snippet", dto.getCodeSnippet());
        assertEquals("OPEN", dto.getStatus());
        assertEquals(now, dto.getCreationDate());
    }

    @Test
    void testTagCreateDTO() {
        TagCreateDTO dto = new TagCreateDTO();

        dto.setName("Test Tag");

        assertEquals("Test Tag", dto.getName());
    }

    @Test
    void testTagDTO() {
        UUID id = UUID.randomUUID();
        TagDTO dto = new TagDTO(id, "Tag Name");

        assertEquals(id, dto.getId());
        assertEquals("Tag Name", dto.getName());
    }

    @Test
    void testTagResponseDTO() {
        UUID tagId = UUID.randomUUID();
        TagResponseDTO dto = new TagResponseDTO(tagId);

        assertEquals(tagId, dto.getTagId());

        UUID newTagId = UUID.randomUUID();
        dto.setTagId(newTagId);

        assertEquals(newTagId, dto.getTagId());
    }
}
