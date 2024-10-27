package robotic.system.forum.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import robotic.system.forum.domain.model.Forum;
import robotic.system.forum.domain.model.ForumComment;
import robotic.system.forum.repository.ForumCommentRepository;

import java.util.List;

@Service
public class ForumCommentService {

    @Autowired
    private ForumCommentRepository forumCommentRepository;

    public ForumComment createComment(ForumComment comment) {
        return forumCommentRepository.save(comment);
    }

    public List<ForumComment> getCommentsByForum(Forum forum) {
        return forumCommentRepository.findAll();
    }
}
