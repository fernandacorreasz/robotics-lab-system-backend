package robotic.system.activityUser.app.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import robotic.system.activityUser.domain.model.ActivityUser;
import robotic.system.activityUser.domain.model.Comment;
import robotic.system.activityUser.repository.CommentRepository;
import robotic.system.activityUser.repository.ActivityUserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ActivityUserRepository activityUserRepository;

    @Transactional
    public String addCommentToActivity(UUID activityId, String text) {
        Optional<ActivityUser> activityUserOptional = activityUserRepository.findById(activityId);
        if (activityUserOptional.isPresent()) {
            ActivityUser activityUser = activityUserOptional.get();
            Comment comment = new Comment(text, activityUser);
            commentRepository.save(comment);
            return "Comentário adicionado com sucesso";
        } else {
            throw new IllegalArgumentException("Atividade com ID " + activityId + " não encontrada.");
        }
    }
}
