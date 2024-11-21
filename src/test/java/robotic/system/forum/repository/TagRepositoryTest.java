package robotic.system.forum.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import robotic.system.forum.domain.model.Tag;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TagRepositoryTest {

    @Mock
    private TagRepository tagRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindById() {
        when(tagRepository.findById(any(UUID.class))).thenReturn(Optional.of(mock(Tag.class)));

        Optional<Tag> result = tagRepository.findById(UUID.randomUUID());

        assertNotNull(result, "Result should not be null");
        verify(tagRepository).findById(any(UUID.class));
    }

    @Test
    void testSave() {
        Tag tag = mock(Tag.class);
        when(tagRepository.save(any(Tag.class))).thenReturn(tag);

        Tag result = tagRepository.save(tag);

        assertNotNull(result, "Saved Tag should not be null");
        verify(tagRepository).save(any(Tag.class));
    }

    @Test
    void testDeleteById() {
        doNothing().when(tagRepository).deleteById(any(UUID.class));

        tagRepository.deleteById(UUID.randomUUID());

        verify(tagRepository).deleteById(any(UUID.class));
    }
}
