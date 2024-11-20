package robotic.system.forum.domain.model;

import org.junit.jupiter.api.Test;
import robotic.system.forum.domain.en.ForumStatus;
import robotic.system.user.domain.model.Users;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ForumTest {

    @Test
    void testForumBuilder() {
        String title = "Test Forum";
        String description = "Forum description";
        String codeSnippet = "public static void main(String[] args) {}";
        ForumStatus status = ForumStatus.OPEN;
        Users user = new Users();
        Date creationDate = new Date();
        Date editDate = new Date();
        int voteCount = 10;
        List<ForumComment> comments = new ArrayList<>();
        List<Tag> tags = new ArrayList<>();

        Forum forum = new ForumBuilder()
                .withTitle(title)
                .withDescription(description)
                .withCodeSnippet(codeSnippet)
                .withStatus(status)
                .withUser(user)
                .withCreationDate(creationDate)
                .withEditDate(editDate)
                .withVoteCount(voteCount)
                .withComments(comments)
                .withTags(tags)
                .build();

        assertNotNull(forum.getId(), "ID should not be null");
        assertEquals(title, forum.getTitle(), "Title should match");
        assertEquals(description, forum.getDescription(), "Description should match");
        assertEquals(codeSnippet, forum.getCodeSnippet(), "Code snippet should match");
        assertEquals(status, forum.getStatus(), "Status should match");
        assertEquals(user, forum.getUser(), "User should match");
        assertEquals(creationDate, forum.getCreationDate(), "Creation date should match");
        assertEquals(editDate, forum.getEditDate(), "Edit date should match");
        assertEquals(voteCount, forum.getVoteCount(), "Vote count should match");
        assertEquals(comments, forum.getComments(), "Comments should match");
        assertEquals(tags, forum.getTags(), "Tags should match");
    }
}
