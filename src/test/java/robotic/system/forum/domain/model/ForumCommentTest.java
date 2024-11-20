package robotic.system.forum.domain.model;

import org.junit.jupiter.api.Test;
import robotic.system.user.domain.model.Users;

import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ForumCommentTest {

    @Test
    void testForumCommentConstructor() {
        Users user = new Users();
        Forum forum = new Forum();
        String content = "This is a comment";
        String codeSnippet = "int x = 0;";
        Date creationDate = new Date();
        Date editDate = new Date();

        ForumComment comment = new ForumComment();
        comment.setId(UUID.randomUUID());
        comment.setContent(content);
        comment.setCodeSnippet(codeSnippet);
        comment.setUser(user);
        comment.setForum(forum);
        comment.setCreationDate(creationDate);
        comment.setEditDate(editDate);
        comment.setVoteCount(5);

        assertNotNull(comment.getId(), "ID should not be null");
        assertEquals(content, comment.getContent(), "Content should match");
        assertEquals(codeSnippet, comment.getCodeSnippet(), "Code snippet should match");
        assertEquals(user, comment.getUser(), "User should match");
        assertEquals(forum, comment.getForum(), "Forum should match");
        assertEquals(creationDate, comment.getCreationDate(), "Creation date should match");
        assertEquals(editDate, comment.getEditDate(), "Edit date should match");
        assertEquals(5, comment.getVoteCount(), "Vote count should match");
    }
}
