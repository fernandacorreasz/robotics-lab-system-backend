package robotic.system.forum.domain.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TagTest {

    @Test
    void testTagConstructor() {
        String name = "Java";
        List<Forum> forums = new ArrayList<>();

        Tag tag = new Tag();
        tag.setId(UUID.randomUUID());
        tag.setName(name);
        tag.setForums(forums);

        assertNotNull(tag.getId(), "ID should not be null");
        assertEquals(name, tag.getName(), "Name should match");
        assertEquals(forums, tag.getForums(), "Forums should match");
    }
}
