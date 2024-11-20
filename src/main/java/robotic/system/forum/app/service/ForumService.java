package robotic.system.forum.app.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.el.stream.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import robotic.system.forum.domain.dto.*;
import robotic.system.forum.domain.en.ForumStatus;
import robotic.system.forum.domain.model.Forum;
import robotic.system.forum.domain.model.Tag;
import robotic.system.forum.repository.ForumRepository;
import robotic.system.forum.repository.TagRepository;
import robotic.system.user.domain.model.Users;
import robotic.system.user.repository.UserRepository;

@Service
public class ForumService {

   @Autowired
    private ForumRepository forumRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TagRepository tagRepository;

     // Método para criar um fórum associando ao usuário e às tags obrigatórias
 @Transactional
    public ForumResponseDTO createForum(ForumCreateDTO forumCreateDTO) {
        // Verificar se o usuário existe
        Users user = userRepository.findById(forumCreateDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));

        // Verificar se as tags fornecidas existem
        List<Tag> tags = tagRepository.findAllById(forumCreateDTO.getTagIds());
        if (tags.isEmpty() || tags.size() != forumCreateDTO.getTagIds().size()) {
            throw new IllegalArgumentException("Algumas das tags fornecidas não foram encontradas.");
        }

        // Criar o fórum
        Forum forum = new Forum();
        forum.setTitle(forumCreateDTO.getTitle());
        forum.setDescription(forumCreateDTO.getDescription());
        forum.setCodeSnippet(forumCreateDTO.getCodeSnippet());
        forum.setStatus(ForumStatus.valueOf(forumCreateDTO.getStatus()));
        forum.setUser(user);
        forum.setTags(tags);
        forum.setCreationDate(new Date());

        // Salvar o fórum
        Forum savedForum = forumRepository.save(forum);

        // Retornar o DTO com os dados do fórum criado
        return new ForumResponseDTO(
            savedForum.getId(),
            savedForum.getTitle(),
            savedForum.getDescription(),
            savedForum.getCodeSnippet(),
            savedForum.getStatus().name(),
            savedForum.getCreationDate()
        );
    }

    public ForumDTO getForumById(UUID forumId) {
        Forum forum = forumRepository.findById(forumId)
                .orElseThrow(() -> new IllegalArgumentException("Fórum não encontrado com o ID: " + forumId));

        return new ForumDTO(
                forum.getId(),
                forum.getTitle(),
                forum.getDescription(),
                forum.getCodeSnippet(),
                forum.getStatus(),
                forum.getCreationDate(),
                forum.getEditDate(),
                forum.getVoteCount(),
                forum.getUser().getName(),
                forum.getUser().getId(),
                forum.getComments().stream()
                        .map(comment -> new CommentDTO(
                                comment.getId(),
                                comment.getContent(),
                                comment.getCodeSnippet(),
                                comment.getUser().getName(),
                                comment.getUser().getId()
                        ))
                        .collect(Collectors.toList()),
                forum.getTags().stream()
                        .map(tag -> new TagDTO(tag.getId(), tag.getName()))
                        .collect(Collectors.toList())
        );
    }


    public List<Forum> getAllForums() {
        return forumRepository.findAll();
    }

    public Page<ForumDTO> filterForums(Specification<Forum> spec, Pageable pageable) {
        return forumRepository.findAll(spec, pageable).map(forum -> new ForumDTO(
                forum.getId(),
                forum.getTitle(),
                forum.getDescription(),
                forum.getCodeSnippet(),
                forum.getStatus(),
                forum.getCreationDate(),
                forum.getEditDate(),
                forum.getVoteCount(),
                forum.getUser().getName(),
                forum.getUser().getId(),
                forum.getComments().stream()
                        .map(comment -> new CommentDTO(
                                comment.getId(),
                                comment.getContent(),
                                comment.getCodeSnippet(),
                                comment.getUser().getName(),
                                comment.getUser().getId()
                        ))
                        .collect(Collectors.toList()),
                forum.getTags().stream()
                        .map(tag -> new TagDTO(tag.getId(), tag.getName()))
                        .collect(Collectors.toList())
        ));
    }

}
