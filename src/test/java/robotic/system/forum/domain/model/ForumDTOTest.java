package robotic.system.forum.domain.model;

import org.junit.jupiter.api.Test;
import robotic.system.forum.domain.dto.*;
import robotic.system.forum.domain.en.ForumStatus;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ForumDTOTest {

    @Test
    void testForumCreateDTO() {
        UUID userId = UUID.randomUUID();
        List<UUID> tagIds = List.of(UUID.randomUUID(), UUID.randomUUID());

        ForumCreateDTO forumCreateDTO = new ForumCreateDTO(
                "Test Title",
                "Test Description",
                "Code Snippet",
                "OPEN",
                userId,
                tagIds
        );

        assertEquals("Test Title", forumCreateDTO.getTitle());
        assertEquals("Test Description", forumCreateDTO.getDescription());
        assertEquals("Code Snippet", forumCreateDTO.getCodeSnippet());
        assertEquals("OPEN", forumCreateDTO.getStatus());
        assertEquals(userId, forumCreateDTO.getUserId());
        assertEquals(tagIds, forumCreateDTO.getTagIds());

        forumCreateDTO.setTitle("Updated Title");
        assertEquals("Updated Title", forumCreateDTO.getTitle());
    }

    @Test
    void testForumCommentDTO() {
        UUID commentId = UUID.randomUUID();
        Date now = new Date();

        ForumCommentDTO forumCommentDTO = new ForumCommentDTO();
        forumCommentDTO.setId(commentId);
        forumCommentDTO.setContent("Test Comment");
        forumCommentDTO.setCreationDate(now);
        forumCommentDTO.setEditDate(now);
        forumCommentDTO.setVoteCount(10);

        assertEquals(commentId, forumCommentDTO.getId());
        assertEquals("Test Comment", forumCommentDTO.getContent());
        assertEquals(now, forumCommentDTO.getCreationDate());
        assertEquals(now, forumCommentDTO.getEditDate());
        assertEquals(10, forumCommentDTO.getVoteCount());
    }

    @Test
    void testForumCommentCreateDTO() {
        UUID userId = UUID.randomUUID();
        UUID forumId = UUID.randomUUID();

        ForumCommentCreateDTO forumCommentCreateDTO = new ForumCommentCreateDTO();
        forumCommentCreateDTO.setContent("Test Content");
        forumCommentCreateDTO.setCodeSnippet("Code Snippet");
        forumCommentCreateDTO.setUserId(userId);
        forumCommentCreateDTO.setForumId(forumId);

        assertEquals("Test Content", forumCommentCreateDTO.getContent());
        assertEquals("Code Snippet", forumCommentCreateDTO.getCodeSnippet());
        assertEquals(userId, forumCommentCreateDTO.getUserId());
        assertEquals(forumId, forumCommentCreateDTO.getForumId());
    }

    @Test
    void testForumDTO() {
        UUID forumId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        List<CommentDTO> comments = Arrays.asList(
                new CommentDTO(UUID.randomUUID(), "Comment1", "Snippet1", "User1", UUID.randomUUID()),
                new CommentDTO(UUID.randomUUID(), "Comment2", "Snippet2", "User2", UUID.randomUUID())
        );
        List<TagDTO> tags = Arrays.asList(
                new TagDTO(UUID.randomUUID(), "Tag1"),
                new TagDTO(UUID.randomUUID(), "Tag2")
        );

        ForumDTO forumDTO = new ForumDTO(
                forumId,
                "Forum Title",
                "Forum Description",
                "Code Snippet",
                ForumStatus.OPEN,
                new Date(),
                new Date(),
                15,
                "User Name",
                userId,
                comments,
                tags
        );

        assertEquals(forumId, forumDTO.getId());
        assertEquals("Forum Title", forumDTO.getTitle());
        assertEquals("Forum Description", forumDTO.getDescription());
        assertEquals("Code Snippet", forumDTO.getCodeSnippet());
        assertEquals(ForumStatus.OPEN, forumDTO.getStatus());
        assertEquals(15, forumDTO.getVoteCount());
        assertEquals("User Name", forumDTO.getUserName());
        assertEquals(userId, forumDTO.getUserId());
        assertEquals(comments, forumDTO.getComments());
        assertEquals(tags, forumDTO.getTags());

        forumDTO.setTitle("Updated Title");
        assertEquals("Updated Title", forumDTO.getTitle());
    }
}
