package robotic.system.activityUser.app.service;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import robotic.system.activityUser.domain.model.ActivityUser;
import robotic.system.activityUser.repository.ActivityPhotoRepository;
import robotic.system.activityUser.repository.ActivityUserRepository;
import robotic.system.activityUser.app.service.ActivityUserService;
import robotic.system.activityUser.domain.dto.ActivityUserDTO;
import robotic.system.activityUser.domain.model.ActivityPhoto;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import java.util.*;

@Service
public class ActivityUserService {

    @Autowired
    private ActivityUserRepository activityUserRepository;

    @Autowired
    private ActivityPhotoRepository activityPhotoRepository;

    public ActivityUser createActivity(ActivityUser activityUser, List<MultipartFile> files) throws IOException {
        if (files != null && !files.isEmpty()) {
            byte[] zipBytes = zipFiles(files);

            ActivityPhoto photo = new ActivityPhoto();
            photo.setImageFile(zipBytes);
            ActivityPhoto savedPhoto = activityPhotoRepository.save(photo);
            activityUser.getPhotos().add(savedPhoto);
        }
        return activityUserRepository.save(activityUser);
    }

    public ResponseEntity<Map<String, Object>> getActivityById(UUID activityId) throws IOException {
        Optional<ActivityUser> activityUserOptional = activityUserRepository.findById(activityId);

        if (activityUserOptional.isPresent()) {
            ActivityUser activityUser = activityUserOptional.get();
            Map<String, Object> response = new HashMap<>();

            response.put("id", activityUser.getId());
            response.put("activityTitle", activityUser.getActivityTitle());
            response.put("activityDescription", activityUser.getActivityDescription());
            response.put("activityStatus", activityUser.getActivityStatus());
            response.put("timeSpent", activityUser.getTimeSpent());
            response.put("startDate", activityUser.getStartDate());
            response.put("endDate", activityUser.getEndDate());

            List<Map<String, String>> extractedPhotos = new ArrayList<>();

            if (activityUser.getPhotos() != null && !activityUser.getPhotos().isEmpty()) {
                for (ActivityPhoto photo : activityUser.getPhotos()) {
                    byte[] zipBytes = photo.getImageFile();
                    List<Map<String, byte[]>> files = unzipFiles(zipBytes);

                    for (Map<String, byte[]> file : files) {
                        for (Map.Entry<String, byte[]> entry : file.entrySet()) {
                            String base64String = Base64.getEncoder().encodeToString(entry.getValue());
                            Map<String, String> fileResponse = new HashMap<>();
                            fileResponse.put("fileName", entry.getKey());
                            fileResponse.put("fileData", base64String);
                            extractedPhotos.add(fileResponse);
                        }
                    }
                }
            }

            response.put("extractedPhotos", extractedPhotos);

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
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

    private List<Map<String, byte[]>> unzipFiles(byte[] zipBytes) throws IOException {
        List<Map<String, byte[]>> files = new ArrayList<>();
        try (java.util.zip.ZipInputStream zipInputStream = new java.util.zip.ZipInputStream(
                new java.io.ByteArrayInputStream(zipBytes))) {
            java.util.zip.ZipEntry entry;
            while ((entry = zipInputStream.getNextEntry()) != null) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len;
                while ((len = zipInputStream.read(buffer)) > 0) {
                    byteArrayOutputStream.write(buffer, 0, len);
                }
                Map<String, byte[]> file = new HashMap<>();
                file.put(entry.getName(), byteArrayOutputStream.toByteArray());
                files.add(file);
            }
        }
        return files;
    }

    public Page<ActivityUserDTO> listActivities(Pageable pageable) {
        return activityUserRepository.findAllActivityUserDTO(pageable);
    }
}
