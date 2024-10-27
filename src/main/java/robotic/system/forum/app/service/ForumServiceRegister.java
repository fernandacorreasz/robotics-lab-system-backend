package robotic.system.forum.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import robotic.system.forum.domain.model.Forum;
import robotic.system.forum.repository.ForumRepository;

@Service
public class ForumServiceRegister {
    @Autowired
    private ForumRepository forumRepository;

    public Forum createForum(Forum forum) {
        return forumRepository.save(forum);
    }

    public List<Forum> getAllForums() {
        return forumRepository.findAll();
    }
}
