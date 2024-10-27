package robotic.system.forum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import robotic.system.forum.domain.model.Tag;
import java.util.UUID;

@Repository
public interface TagRepository extends JpaRepository<Tag, UUID> {
}
