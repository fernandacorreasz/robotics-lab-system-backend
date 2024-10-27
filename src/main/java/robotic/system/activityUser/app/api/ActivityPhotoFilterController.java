package robotic.system.activityUser.app.api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import robotic.system.activityUser.app.service.ActivityPhotoFilterService;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/activityphotos")
public class ActivityPhotoFilterController {

    @Autowired
    private ActivityPhotoFilterService activityPhotoFilterService;

    @GetMapping("/{photoId}")
    public ResponseEntity<byte[]> getPhoto(@PathVariable UUID photoId) throws IOException {
        return activityPhotoFilterService.getPhotoByFilename(photoId);
    }
}
