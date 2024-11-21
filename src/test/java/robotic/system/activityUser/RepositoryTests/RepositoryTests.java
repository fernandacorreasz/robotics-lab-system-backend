package robotic.system.activityUser.RepositoryTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import robotic.system.activityUser.domain.dto.ActivityUserDTO;
import robotic.system.activityUser.domain.dto.CommentDTO;
import robotic.system.activityUser.domain.model.ActivityPhoto;
import robotic.system.activityUser.domain.model.Comment;
import robotic.system.activityUser.repository.ActivityPhotoRepository;
import robotic.system.activityUser.repository.ActivityUserRepository;
import robotic.system.activityUser.repository.CommentRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RepositoryTests {

    @Mock
    private ActivityUserRepository activityUserRepository;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private ActivityPhotoRepository activityPhotoRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Inicializa os mocks
    }

    @Test
    void testFindAllActivityUserDTO() {
        PageRequest pageable = PageRequest.of(0, 10);
        List<ActivityUserDTO> activityUserDTOList = List.of(new ActivityUserDTO(UUID.randomUUID(), "Title", "Description", null, 10, null, null, UUID.randomUUID(), "user@example.com"));
        Page<ActivityUserDTO> page = new PageImpl<>(activityUserDTOList);

        when(activityUserRepository.findAllActivityUserDTO(pageable)).thenReturn(page);

        Page<ActivityUserDTO> result = activityUserRepository.findAllActivityUserDTO(pageable);

        assertEquals(page, result, "Result should match the mocked page");
        verify(activityUserRepository).findAllActivityUserDTO(pageable);
    }

    @Test
    void testFindActivityByIdWithUserDetails() {
        UUID activityId = UUID.randomUUID();
        ActivityUserDTO dto = new ActivityUserDTO(activityId, "Title", "Description", null, 10, null, null, UUID.randomUUID(), "user@example.com");

        when(activityUserRepository.findActivityByIdWithUserDetails(activityId)).thenReturn(Optional.of(dto));

        Optional<ActivityUserDTO> result = activityUserRepository.findActivityByIdWithUserDetails(activityId);

        assertEquals(Optional.of(dto), result, "Result should match the mocked DTO");
        verify(activityUserRepository).findActivityByIdWithUserDetails(activityId);
    }

    @Test
    void testFindAllActivityUserDTOByUserId() {
        UUID userId = UUID.randomUUID();
        PageRequest pageable = PageRequest.of(0, 10);
        List<ActivityUserDTO> activityUserDTOList = List.of(new ActivityUserDTO(UUID.randomUUID(), "Title", "Description", null, 10, null, null, userId, "user@example.com"));
        Page<ActivityUserDTO> page = new PageImpl<>(activityUserDTOList);

        when(activityUserRepository.findAllActivityUserDTOByUserId(userId, pageable)).thenReturn(page);

        Page<ActivityUserDTO> result = activityUserRepository.findAllActivityUserDTOByUserId(userId, pageable);

        assertEquals(page, result, "Result should match the mocked page");
        verify(activityUserRepository).findAllActivityUserDTOByUserId(userId, pageable);
    }

    @Test
    void testFindAllByActivityId() {
        UUID activityId = UUID.randomUUID();
        List<Comment> comments = List.of(new Comment());

        when(commentRepository.findAllByActivityId(activityId)).thenReturn(comments);

        List<Comment> result = commentRepository.findAllByActivityId(activityId);

        assertEquals(comments, result, "Result should match the mocked comments");
        verify(commentRepository).findAllByActivityId(activityId);
    }

    @Test
    void testFindCommentsByActivityId() {
        UUID activityId = UUID.randomUUID();
        List<CommentDTO> commentDTOs = List.of(new CommentDTO(UUID.randomUUID(), "Comment text", null));

        when(commentRepository.findCommentsByActivityId(activityId)).thenReturn(commentDTOs);

        List<CommentDTO> result = commentRepository.findCommentsByActivityId(activityId);

        assertEquals(commentDTOs, result, "Result should match the mocked comment DTOs");
        verify(commentRepository).findCommentsByActivityId(activityId);
    }

    @Test
    void testActivityPhotoRepository() {
        UUID photoId = UUID.randomUUID();
        ActivityPhoto photo = new ActivityPhoto();
        photo.setId(photoId);

        when(activityPhotoRepository.findById(photoId)).thenReturn(Optional.of(photo));

        Optional<ActivityPhoto> result = activityPhotoRepository.findById(photoId);

        assertEquals(Optional.of(photo), result, "Result should match the mocked photo");
        verify(activityPhotoRepository).findById(photoId);
    }
}
