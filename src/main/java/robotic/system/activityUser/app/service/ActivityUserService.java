package robotic.system.activityUser.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import robotic.system.activityUser.domain.model.ActivityUser;
import robotic.system.activityUser.repository.ActivityUserRepository;

@Service
public class ActivityUserService {

    @Autowired
    private ActivityUserRepository activityUserRepository;

    public ActivityUser createActivity(ActivityUser activityUser) {
        // Salva a atividade com suas fotos, se houver
        return activityUserRepository.save(activityUser);
    }
}
