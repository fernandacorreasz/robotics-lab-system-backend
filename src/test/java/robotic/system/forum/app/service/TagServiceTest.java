package robotic.system.forum.app.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import robotic.system.forum.domain.model.Tag;
import robotic.system.forum.repository.TagRepository;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TagServiceTest {

    @Mock
    private TagRepository tagRepository;

    @InjectMocks
    private TagService tagService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllTags_EmptyList() {
        when(tagRepository.findAll()).thenReturn(Collections.emptyList());

        List<Tag> tags = tagService.getAllTags();

        assertNotNull(tags);
        assertTrue(tags.isEmpty(), "The list of tags should be empty");
        verify(tagRepository).findAll();
    }

    @Test
    void testGetAllTags_NonEmptyList() {
        Tag tag = new Tag();
        tag.setId(UUID.randomUUID());
        tag.setName("Test Tag");

        when(tagRepository.findAll()).thenReturn(List.of(tag));

        List<Tag> tags = tagService.getAllTags();
        assertNotNull(tags);
        assertEquals(1, tags.size(), "The list of tags should contain one element");
        assertEquals("Test Tag", tags.get(0).getName(), "The name of the tag should match");
        verify(tagRepository).findAll();
    }
}
