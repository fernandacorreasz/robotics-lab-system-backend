package robotic.system.activityUser.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import robotic.system.activityUser.domain.dto.ActivityUserDTO;
import robotic.system.activityUser.domain.model.ActivityUser;
import java.util.UUID;

@Repository
public interface ActivityUserRepository extends JpaRepository<ActivityUser, UUID> {

    @Query("SELECT new robotic.system.activityUser.domain.dto.ActivityUserDTO(a.id, a.activityTitle, a.activityDescription, a.activityStatus, a.timeSpent, a.startDate, a.endDate) " +
           "FROM ActivityUser a")
    Page<ActivityUserDTO> findAllActivityUserDTO(Pageable pageable);
}
