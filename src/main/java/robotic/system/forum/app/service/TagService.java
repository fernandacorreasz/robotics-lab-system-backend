package robotic.system.forum.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import robotic.system.forum.domain.dto.TagCreateDTO;
import robotic.system.forum.domain.dto.TagResponseDTO;
import robotic.system.forum.domain.model.Tag;
import robotic.system.forum.repository.TagRepository;

import java.util.List;

@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;

   @Transactional
    public TagResponseDTO createTag(TagCreateDTO tagCreateDTO) {
        Tag tag = new Tag();
        tag.setName(tagCreateDTO.getName());
        Tag savedTag = tagRepository.save(tag);
        return new TagResponseDTO(savedTag.getId());
    }

    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }
}
