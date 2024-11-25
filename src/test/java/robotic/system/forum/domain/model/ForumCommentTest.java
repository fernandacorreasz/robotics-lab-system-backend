package robotic.system.forum.domain.model;

import org.junit.jupiter.api.Test;
import robotic.system.user.domain.model.Users;

import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ForumCommentTest {

    @Test
    void testForumCommentEntity() {
        // Criar instância completa da classe
        Forum forum = new Forum();
        Users user = new Users();
        ForumComment comment = new ForumComment();

        UUID id = UUID.randomUUID();
        Date creationDate = new Date();
        Date editDate = new Date();

        // Configurar atributos
        comment.setId(id);
        comment.setContent("Test content");
        comment.setCodeSnippet("Test code snippet");
        comment.setCreationDate(creationDate);
        comment.setEditDate(editDate);
        comment.setVoteCount(5);
        comment.setForum(forum);
        comment.setUser(user);

        // Validações
        assertNotNull(comment);
        assertEquals(id, comment.getId());
        assertEquals("Test content", comment.getContent());
        assertEquals("Test code snippet", comment.getCodeSnippet());
        assertEquals(creationDate, comment.getCreationDate());
        assertEquals(editDate, comment.getEditDate());
        assertEquals(5, comment.getVoteCount());
        assertEquals(forum, comment.getForum());
        assertEquals(user, comment.getUser());
    }
}