package robotic.system.activityUser.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import robotic.system.activityUser.domain.dto.CommentDTO;
import robotic.system.activityUser.domain.model.Comment;

import java.util.List;
import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {
    List<Comment> findAllByActivityId(UUID activityId);

    @Query("SELECT new robotic.system.activityUser.domain.dto.CommentDTO(c.id, c.text, c.createdDate) " +
           "FROM Comment c WHERE c.activity.id = :activityId")
    List<CommentDTO> findCommentsByActivityId(@Param("activityId") UUID activityId);
}
