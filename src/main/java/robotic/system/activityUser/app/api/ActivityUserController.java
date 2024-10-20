package robotic.system.activityUser.app.api;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import robotic.system.activityUser.domain.model.ActivityUser;
import robotic.system.activityUser.repository.ActivityPhotoRepository;
import robotic.system.activityUser.app.service.ActivityUserService;
import robotic.system.activityUser.domain.model.ActivityPhoto;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import java.util.List;
import java.util.ArrayList;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/activities")
public class ActivityUserController {

    @Autowired
    private ActivityUserService activityUserService;

    @Autowired
    private ActivityPhotoRepository activityPhotoRepository;

    @PostMapping(consumes = { "multipart/form-data" })
    public ResponseEntity<ActivityUser> createActivity(
            @RequestParam(value = "files") List<MultipartFile> files,
            @RequestPart("activity") String activityJson) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ActivityUser activityUser = objectMapper.readValue(activityJson, ActivityUser.class);

        if (activityUser.getPhotos() == null) {
            activityUser.setPhotos(new ArrayList<>());
        }

        if (files != null && !files.isEmpty()) {
            byte[] zipBytes = zipFiles(files);

            ActivityPhoto photo = new ActivityPhoto();
            photo.setImageFile(zipBytes);
            ActivityPhoto savedPhoto = activityPhotoRepository.save(photo);
            activityUser.getPhotos().add(savedPhoto);
        }
        ActivityUser createdActivity = activityUserService.createActivity(activityUser);

        return ResponseEntity.ok(createdActivity);
    }

    private byte[] zipFiles(List<MultipartFile> files) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream)) {
            for (MultipartFile file : files) {
                ZipEntry zipEntry = new ZipEntry(file.getOriginalFilename());
                zipOutputStream.putNextEntry(zipEntry);
                zipOutputStream.write(file.getBytes());
                zipOutputStream.closeEntry();
            }
        }
        return byteArrayOutputStream.toByteArray();
    }

}
