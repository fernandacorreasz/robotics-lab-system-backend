package robotic.system.activityUser.app.service;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import robotic.system.activityUser.app.constants.ErrorMessages;
import robotic.system.activityUser.domain.model.ActivityPhoto;
import robotic.system.activityUser.domain.model.ActivityUser;
import robotic.system.activityUser.repository.ActivityPhotoRepository;
import robotic.system.activityUser.repository.ActivityUserRepository;

import jakarta.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class ActivityUpdateService {

    @Autowired
    private ActivityUserRepository activityUserRepository;

    @Autowired
    private ActivityPhotoRepository activityPhotoRepository;

    @Transactional
    public ActivityUser updateActivity(UUID activityId, ActivityUser updatedActivity, List<MultipartFile> files, Boolean removeExistingPhotos) throws IOException {

        ActivityUser existingActivity = activityUserRepository.findById(activityId)
                .orElseThrow(() -> new IllegalArgumentException(String.format(ErrorMessages.ACTIVITY_NOT_FOUND, activityId)));

        if (updatedActivity.getActivityTitle() != null) {
            existingActivity.setActivityTitle(updatedActivity.getActivityTitle());
        }
        if (updatedActivity.getActivityDescription() != null) {
            existingActivity.setActivityDescription(updatedActivity.getActivityDescription());
        }
        if (updatedActivity.getActivityStatus() != null) {
            existingActivity.setActivityStatus(updatedActivity.getActivityStatus());
        }
        if (updatedActivity.getTimeSpent() != null) {
            existingActivity.setTimeSpent(updatedActivity.getTimeSpent());
        }
        if (updatedActivity.getStartDate() != null) {
            existingActivity.setStartDate(updatedActivity.getStartDate());
        }
        if (updatedActivity.getEndDate() != null) {
            existingActivity.setEndDate(updatedActivity.getEndDate());
        }
        if (updatedActivity.getUserCode() != null) {
            existingActivity.setUserCode(updatedActivity.getUserCode());
        }

        // Remover fotos existentes, se solicitado
        if (removeExistingPhotos != null && removeExistingPhotos) {
            activityPhotoRepository.deleteAll(existingActivity.getPhotos());
            existingActivity.getPhotos().clear();
        }

        // Adicionar novas fotos, se enviadas
        if (files != null && !files.isEmpty()) {
            for (MultipartFile file : files) {
                String contentType = file.getContentType();

                if (contentType == null) {
                    throw new IllegalArgumentException(ErrorMessages.NULL_CONTENT_TYPE);
                }

                if (!isSupportedContentType(contentType)) {
                    throw new IllegalArgumentException(ErrorMessages.UNSUPPORTED_FILE_TYPE);
                }

                byte[] zipBytes = zipFile(file);
                ActivityPhoto newPhoto = new ActivityPhoto();
                newPhoto.setImageFile(zipBytes);
                newPhoto.setFilename(file.getOriginalFilename());
                ActivityPhoto savedPhoto = activityPhotoRepository.save(newPhoto);
                existingActivity.getPhotos().add(savedPhoto);
            }
        }

        return activityUserRepository.save(existingActivity);
    }

    private boolean isSupportedContentType(String contentType) {
        return "image/jpeg".equals(contentType) || "image/png".equals(contentType) || "image/gif".equals(contentType);
    }

    private byte[] zipFile(MultipartFile file) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream)) {
            ZipEntry zipEntry = new ZipEntry(file.getOriginalFilename());
            zipOutputStream.putNextEntry(zipEntry);
            zipOutputStream.write(file.getBytes());
            zipOutputStream.closeEntry();
        }
        return byteArrayOutputStream.toByteArray();
    }
}
