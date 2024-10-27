package robotic.system.activityUser.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import robotic.system.activityUser.repository.ActivityUserRepository;
import robotic.system.util.delete.BulkDeleteService;

import java.util.List;
import java.util.UUID;

@Service
public class DeleteActivityService {

    @Autowired
    private ActivityUserRepository activityUserRepository;

    @Autowired
    private BulkDeleteService bulkDeleteService;

    @Transactional
    public BulkDeleteService.BulkDeleteResult deleteActivitiesByIds(List<String> activityIds) {
        return bulkDeleteService.bulkDeleteByField(
            activityIds,
            this::findActivityById, 
            this::deleteActivity
        );
    }

    private Object findActivityById(String id) {
        return activityUserRepository.findById(UUID.fromString(id)).orElse(null);
    }

    private Boolean deleteActivity(Object entity) {
        try {
            activityUserRepository.delete((robotic.system.activityUser.domain.model.ActivityUser) entity);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
