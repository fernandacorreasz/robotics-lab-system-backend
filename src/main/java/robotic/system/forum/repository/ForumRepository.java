package robotic.system.forum.repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import robotic.system.forum.domain.dto.ForumDTO;
import robotic.system.forum.domain.model.Forum;
import java.util.UUID;

@Repository
public interface ForumRepository extends JpaRepository<Forum, UUID>, JpaSpecificationExecutor<Forum> {

     @Query("SELECT new robotic.system.forum.domain.dto.ForumDTO(f.id, f.title, f.description, f.status, f.creationDate, f.editDate, f.voteCount) " +
           "FROM Forum f")
    Page<ForumDTO> findAllForumDTO(Pageable pageable);
}
