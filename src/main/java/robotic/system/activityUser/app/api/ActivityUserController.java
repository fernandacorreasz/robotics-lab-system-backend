package robotic.system.activityUser.app.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import robotic.system.activityUser.domain.dto.ActivityUserDTO;
import robotic.system.activityUser.domain.model.ActivityUser;
import robotic.system.activityUser.app.service.ActivityUserService;

import java.io.IOException;

import java.util.*;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/activities")
public class ActivityUserController {

    @Autowired
    private ActivityUserService activityUserService;

    @PostMapping(consumes = { "multipart/form-data" })
    public ResponseEntity<ActivityUser> createActivity(
            @RequestParam(value = "files") List<MultipartFile> files,
            @RequestPart("activity") String activityJson) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ActivityUser activityUser = objectMapper.readValue(activityJson, ActivityUser.class);

        if (activityUser.getPhotos() == null) {
            activityUser.setPhotos(new ArrayList<>());
        }

        ActivityUser createdActivity = activityUserService.createActivity(activityUser, files);

        return ResponseEntity.ok(createdActivity);
    }

    @GetMapping("/{activityId}")
    public ResponseEntity<Map<String, Object>> getActivityById(@PathVariable UUID activityId) throws IOException {
        return activityUserService.getActivityById(activityId);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<ActivityUserDTO>> getFindAllActivities(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "100") int size) {
        
        Page<ActivityUserDTO> activitiesPage = activityUserService.listActivities(PageRequest.of(page, size));
        return ResponseEntity.ok(activitiesPage);
    }
    
}
