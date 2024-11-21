package robotic.system.forum.app.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import robotic.system.forum.domain.dto.ForumCreateDTO;
import robotic.system.forum.repository.TagRepository;
import robotic.system.user.domain.model.Users;
import robotic.system.user.repository.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class ForumServicesTest {

    @Mock
    private TagRepository tagRepository;

    @Mock
    private UserRepository userRepository;


    @InjectMocks
    private ForumService forumService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateForum_UserNotFound() {
        UUID userId = UUID.randomUUID();
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        ForumCreateDTO forumCreateDTO = new ForumCreateDTO(
                "Test Title", "Test Description", "Snippet", "OPEN", userId, List.of()
        );

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                forumService.createForum(forumCreateDTO));

        assertTrue(exception.getMessage().contains("Usuário não encontrado"));
        verify(userRepository).findById(userId);
    }

    @Test
    void testCreateForum_TagsNotFound() {
        UUID userId = UUID.randomUUID();
        Users user = new Users();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(tagRepository.findAllById(any())).thenReturn(Collections.emptyList());

        ForumCreateDTO forumCreateDTO = new ForumCreateDTO(
                "Test Title", "Test Description", "Snippet", "OPEN", userId, List.of(UUID.randomUUID())
        );

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                forumService.createForum(forumCreateDTO));

        assertTrue(exception.getMessage().contains("Algumas das tags fornecidas não foram encontradas"));
        verify(userRepository).findById(userId);
        verify(tagRepository).findAllById(any());
    }
}
