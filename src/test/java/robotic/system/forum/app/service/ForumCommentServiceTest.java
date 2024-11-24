package robotic.system.forum.app.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import robotic.system.forum.domain.dto.ForumCommentCreateDTO;
import robotic.system.forum.domain.dto.ForumCommentResponseDTO;
import robotic.system.forum.domain.model.Forum;
import robotic.system.forum.domain.model.ForumComment;
import robotic.system.forum.repository.ForumCommentRepository;
import robotic.system.forum.repository.ForumRepository;
import robotic.system.user.domain.model.Users;
import robotic.system.user.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ForumCommentServiceTest {

    @Mock
    private ForumCommentRepository forumCommentRepository;

    @Mock
    private ForumRepository forumRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ForumCommentService forumCommentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateComment_Success() {
        UUID forumId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        Forum mockForum = mock(Forum.class);
        Users mockUser = mock(Users.class);
        ForumComment mockComment = mock(ForumComment.class);

        ForumCommentCreateDTO commentDTO = new ForumCommentCreateDTO();
        commentDTO.setForumId(forumId);
        commentDTO.setUserId(userId);
        commentDTO.setContent("Test content");
        commentDTO.setCodeSnippet("Test code snippet");

        when(forumRepository.findById(forumId)).thenReturn(Optional.of(mockForum));
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));
        when(forumCommentRepository.save(any(ForumComment.class))).thenReturn(mockComment);
        when(mockComment.getId()).thenReturn(UUID.randomUUID());

        ForumCommentResponseDTO response = forumCommentService.createComment(commentDTO);

        assertNotNull(response);
        verify(forumRepository).findById(forumId);
        verify(userRepository).findById(userId);
        verify(forumCommentRepository).save(any(ForumComment.class));
    }

    @Test
    void testCreateComment_ForumNotFound() {
        UUID forumId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        ForumCommentCreateDTO commentDTO = new ForumCommentCreateDTO();
        commentDTO.setForumId(forumId);
        commentDTO.setUserId(userId);
        commentDTO.setContent("Test content");
        commentDTO.setCodeSnippet("Test code snippet");

        when(forumRepository.findById(forumId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                forumCommentService.createComment(commentDTO));

        assertEquals("Fórum não encontrado.", exception.getMessage());
        verify(forumRepository).findById(forumId);
        verifyNoInteractions(userRepository);
        verifyNoInteractions(forumCommentRepository);
    }

    @Test
    void testCreateComment_UserNotFound() {
        UUID forumId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        Forum mockForum = mock(Forum.class);

        ForumCommentCreateDTO commentDTO = new ForumCommentCreateDTO();
        commentDTO.setForumId(forumId);
        commentDTO.setUserId(userId);
        commentDTO.setContent("Test content");
        commentDTO.setCodeSnippet("Test code snippet");

        when(forumRepository.findById(forumId)).thenReturn(Optional.of(mockForum));
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                forumCommentService.createComment(commentDTO));

        assertEquals("Usuário não encontrado.", exception.getMessage());
        verify(forumRepository).findById(forumId);
        verify(userRepository).findById(userId);
        verifyNoInteractions(forumCommentRepository);
    }

    @Test
    void testGetCommentsByForum_Success() {
        Forum mockForum = mock(Forum.class);
        List<ForumComment> mockComments = List.of(mock(ForumComment.class), mock(ForumComment.class));

        when(forumCommentRepository.findAll()).thenReturn(mockComments);

        List<ForumComment> result = forumCommentService.getCommentsByForum(mockForum);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(forumCommentRepository).findAll();
    }
}
