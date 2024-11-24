package robotic.system.forum.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import robotic.system.forum.domain.model.Forum;
import robotic.system.forum.repository.ForumRepository;
import robotic.system.user.domain.model.Users;
import robotic.system.util.delete.BulkDeleteService;

import java.util.List;
import java.util.UUID;

@Service
public class ForumBulkDeleteService {

    @Autowired
    private ForumRepository forumRepository;

    @Autowired
    private BulkDeleteService bulkDeleteService;

    @Transactional
    public BulkDeleteService.BulkDeleteResult deleteForumsByIds(List<String> forumIds) {
        return bulkDeleteService.bulkDeleteByField(
                forumIds,
                this::findForumByIdAndCheckOwnership,
                this::removeAndDeleteForum
        );
    }

    // Buscar o fórum pelo ID e verificar se o usuário autenticado é o dono
    private Forum findForumByIdAndCheckOwnership(String forumId) {
        try {

            Forum forum = forumRepository.findById(UUID.fromString(forumId)).orElse(null);

            if (forum == null) {
                throw new IllegalArgumentException("Fórum não encontrado: " + forumId);
            }

            if (forum.getUser() == null) {
                throw new IllegalArgumentException("O fórum não tem um usuário associado: " + forumId);
            }

            return forum;

        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private Boolean removeAndDeleteForum(Forum forum) {
        try {
            forumRepository.delete(forum);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
