package robotic.system.forum.app.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import robotic.system.forum.domain.dto.ForumCreateDTO;
import robotic.system.forum.domain.dto.ForumDTO;
import robotic.system.forum.domain.dto.ForumResponseDTO;
import robotic.system.forum.domain.dto.ForumUpdateDTO;
import robotic.system.forum.domain.en.ForumStatus;
import robotic.system.forum.domain.model.Forum;
import robotic.system.forum.domain.model.Tag;
import robotic.system.forum.repository.ForumRepository;
import robotic.system.forum.repository.TagRepository;
import robotic.system.user.domain.model.Users;
import robotic.system.user.repository.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class ForumServicesTest {

    @Mock
    private ForumRepository forumRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TagRepository tagRepository;

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

    @Test
    void testCreateForum_Success() {
        ForumCreateDTO forumCreateDTO = new ForumCreateDTO(
                "Test Title", "Test Description", "Code Snippet", "OPEN",
                UUID.randomUUID(), List.of(UUID.randomUUID())
        );

        Users mockUser = mock(Users.class);
        Tag mockTag = mock(Tag.class);

        when(userRepository.findById(forumCreateDTO.getUserId())).thenReturn(Optional.of(mockUser));
        when(tagRepository.findAllById(forumCreateDTO.getTagIds())).thenReturn(List.of(mockTag));

        Forum savedForum = mock(Forum.class);
        when(savedForum.getStatus()).thenReturn(ForumStatus.OPEN); // Garantir que o status está configurado
        when(forumRepository.save(any(Forum.class))).thenReturn(savedForum);

        ForumResponseDTO response = forumService.createForum(forumCreateDTO);

        assertNotNull(response);
        assertEquals("OPEN", response.getStatus()); // Validar o status esperado
        verify(forumRepository).save(any(Forum.class));
    }


    @Test
    void testGetForumById_Success() {
        UUID forumId = UUID.randomUUID();
        Forum mockForum = mock(Forum.class);
        when(mockForum.getUser()).thenReturn(mock(Users.class));
        when(mockForum.getComments()).thenReturn(Collections.emptyList());
        when(mockForum.getTags()).thenReturn(Collections.emptyList());
        when(forumRepository.findById(forumId)).thenReturn(Optional.of(mockForum));

        ForumDTO result = forumService.getForumById(forumId);

        assertNotNull(result);
        verify(forumRepository).findById(forumId);
    }

    @Test
    void testGetForumById_NotFound() {
        UUID forumId = UUID.randomUUID();
        when(forumRepository.findById(forumId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                forumService.getForumById(forumId));

        assertEquals("Fórum não encontrado com o ID: " + forumId, exception.getMessage());
    }

    @Test
    void testGetAllForums() {
        List<Forum> mockForums = List.of(mock(Forum.class));
        when(forumRepository.findAll()).thenReturn(mockForums);

        List<Forum> result = forumService.getAllForums();

        assertNotNull(result);
        assertEquals(mockForums.size(), result.size());
        verify(forumRepository).findAll();
    }

    @Test
    void testUpdateForum_Success() {
        ForumUpdateDTO updateDTO = new ForumUpdateDTO();
        updateDTO.setId(UUID.randomUUID());
        updateDTO.setTitle("Updated Title");
        updateDTO.setDescription("Updated Description");

        Forum mockForum = mock(Forum.class);
        when(mockForum.getStatus()).thenReturn(ForumStatus.OPEN);
        when(forumRepository.findById(updateDTO.getId())).thenReturn(Optional.of(mockForum));
        when(forumRepository.save(any(Forum.class))).thenReturn(mockForum);

        ForumResponseDTO response = forumService.updateForum(updateDTO);

        assertEquals(null, response.getTitle());
        assertEquals(null, response.getDescription());
        verify(forumRepository).save(any(Forum.class));
    }


    @Test
    void testUpdateForum_NotFound() {
        ForumUpdateDTO updateDTO = new ForumUpdateDTO();
        updateDTO.setId(UUID.randomUUID());

        when(forumRepository.findById(updateDTO.getId())).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                forumService.updateForum(updateDTO));

        assertEquals("Fórum não encontrado com o ID: " + updateDTO.getId(), exception.getMessage());
    }
}