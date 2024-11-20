package robotic.system.forum.domain.model;

import org.junit.jupiter.api.Test;
import robotic.system.user.domain.model.Users;

import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class EditHistoryTest {

    @Test
    void testEditHistoryConstructor() {
        Users editorUser = new Users();
        String previousContent = "Original content";
        String type = "Update";
        UUID referenceId = UUID.randomUUID();
        Date editDate = new Date();

        EditHistory history = new EditHistory();
        history.setId(UUID.randomUUID());
        history.setPreviousContent(previousContent);
        history.setType(type);
        history.setReferenceId(referenceId);
        history.setEditorUser(editorUser);
        history.setEditDate(editDate);

        assertNotNull(history.getId(), "ID should not be null");
        assertEquals(previousContent, history.getPreviousContent(), "Previous content should match");
        assertEquals(type, history.getType(), "Type should match");
        assertEquals(referenceId, history.getReferenceId(), "Reference ID should match");
        assertEquals(editorUser, history.getEditorUser(), "Editor user should match");
        assertEquals(editDate, history.getEditDate(), "Edit date should match");
    }
}
