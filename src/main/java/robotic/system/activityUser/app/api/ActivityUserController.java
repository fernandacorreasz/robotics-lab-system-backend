package robotic.system.activityUser.app.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import robotic.system.activityUser.domain.dto.ActivityUserDTO;
import robotic.system.activityUser.domain.en.ActivityStatus;
import robotic.system.activityUser.domain.model.ActivityUser;
import robotic.system.util.delete.BulkDeleteService;
import robotic.system.activityUser.app.service.ActivityPhotoByActivityService;
import robotic.system.activityUser.app.service.ActivityUpdateService;
import robotic.system.activityUser.app.service.ActivityUserService;
import robotic.system.activityUser.app.service.DeleteActivityService;

import java.io.IOException;

import java.util.*;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/activities")
public class ActivityUserController {

    @Autowired
    private ActivityUserService activityUserService;

    @Autowired
    private ActivityPhotoByActivityService activityPhotoByActivityService;

    @Autowired
    private DeleteActivityService deleteActivityService;

    @Autowired
    private ActivityUpdateService activityUpdateService;

    @PostMapping(consumes = { "multipart/form-data" })
    public ResponseEntity<ActivityUser> createActivity(
            @RequestParam(value = "files", required = false) List<MultipartFile> files,
            @RequestPart("activity") String activityJson) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        ActivityUser activityUser = objectMapper.readValue(activityJson, ActivityUser.class);

        UUID userId = activityUser.getUser().getId();

        if (activityUser.getPhotos() == null) {
            activityUser.setPhotos(new ArrayList<>());
        }

        ActivityUser createdActivity = activityUserService.createActivity(activityUser, files, userId);
        return ResponseEntity.ok(createdActivity);
    }

    @PutMapping(value = "/{activityId}", consumes = { "multipart/form-data" })
    public ResponseEntity<ActivityUser> updateActivity(
            @PathVariable UUID activityId,
            @RequestParam(value = "files", required = false) List<MultipartFile> files,
            @RequestPart("activity") String activityJson,
            @RequestParam(value = "removePhotos", required = false) Boolean removeExistingPhotos) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        ActivityUser updatedActivity = objectMapper.readValue(activityJson, ActivityUser.class);

        ActivityUser updated = activityUpdateService.updateActivity(activityId, updatedActivity, files,
                removeExistingPhotos);

        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{activityId}")
    public ResponseEntity<ActivityUserDTO> getActivityByIdWithUserDetails(@PathVariable UUID activityId) {
        Optional<ActivityUserDTO> activity = activityUserService.getActivityByIdWithUserDetails(activityId);
        return activity.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public ResponseEntity<Page<ActivityUserDTO>> getFindAllActivities(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "100") int size) {

        Page<ActivityUserDTO> activitiesPage = activityUserService.listActivities(PageRequest.of(page, size));
        return ResponseEntity.ok(activitiesPage);
    }

    @GetMapping("/{activityId}/photo")
    public ResponseEntity<?> getActivityPhoto(@PathVariable UUID activityId) throws IOException {
        return activityPhotoByActivityService.getPhotoByActivityId(activityId);
    }
    
    @DeleteMapping("/delete")
    public ResponseEntity<BulkDeleteService.BulkDeleteResult> deleteActivities(@RequestBody List<String> activityIds) {
        BulkDeleteService.BulkDeleteResult result = deleteActivityService.deleteActivitiesByIds(activityIds);
        return ResponseEntity.ok(result);
    }

}
