package robotic.system.activityUser.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommentTest {

    @Test
    void testDefaultConstructor() {
        Comment comment = new Comment();
        assertNull(comment.getText(), "Text should be null initially");
        assertNull(comment.getActivity(), "Activity should be null initially");
        assertNotNull(comment.getCreatedDate(), "Created date should not be null");
    }
}
