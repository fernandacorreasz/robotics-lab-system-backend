package robotic.system.forum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import robotic.system.forum.domain.model.Forum;
import java.util.UUID;

@Repository
public interface ForumRepository extends JpaRepository<Forum, UUID> {
}
