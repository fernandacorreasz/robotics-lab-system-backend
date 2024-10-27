package robotic.system.forum.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import robotic.system.forum.domain.dto.ForumCommentCreateDTO;
import robotic.system.forum.domain.dto.ForumCommentResponseDTO;
import robotic.system.forum.domain.model.Forum;
import robotic.system.forum.domain.model.ForumComment;
import robotic.system.forum.repository.ForumCommentRepository;
import robotic.system.forum.repository.ForumRepository;
import robotic.system.user.domain.model.Users;
import robotic.system.user.repository.UserRepository;

import java.util.Date;
import java.util.List;

@Service
public class ForumCommentService {

    @Autowired
    private ForumCommentRepository forumCommentRepository;
    
    @Autowired
    private ForumRepository forumRepository;

    @Autowired
    private UserRepository userRepository;
    @Transactional
    public ForumCommentResponseDTO createComment(ForumCommentCreateDTO commentDTO) {
        // Verificar se o fórum existe
        Forum forum = forumRepository.findById(commentDTO.getForumId())
                .orElseThrow(() -> new IllegalArgumentException("Fórum não encontrado."));

        // Verificar se o usuário existe
        Users user = userRepository.findById(commentDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));

        // Criar o comentário
        ForumComment comment = new ForumComment();
        comment.setContent(commentDTO.getContent());
        comment.setCodeSnippet(commentDTO.getCodeSnippet());
        comment.setUser(user);
        comment.setForum(forum);
        comment.setCreationDate(new Date());

        // Salvar o comentário
        ForumComment savedComment = forumCommentRepository.save(comment);

        // Retornar apenas o ID do comentário criado
        return new ForumCommentResponseDTO(savedComment.getId());
    }
    public List<ForumComment> getCommentsByForum(Forum forum) {
        return forumCommentRepository.findAll();
    }
}
