package robotic.system.activityUser.app.service;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.transaction.Transactional;
import robotic.system.activityUser.domain.model.ActivityUser;
import robotic.system.activityUser.domain.model.Comment;
import robotic.system.activityUser.repository.ActivityPhotoRepository;
import robotic.system.activityUser.repository.ActivityUserRepository;
import robotic.system.inventory.domain.dto.ComponentResponseDTO;
import robotic.system.user.domain.model.Users;
import robotic.system.user.repository.UserRepository;
import robotic.system.activityUser.app.service.ActivityUserService;
import robotic.system.activityUser.domain.dto.ActivityUserDTO;
import robotic.system.activityUser.domain.dto.ActivityWithCommentsDTO;
import robotic.system.activityUser.domain.dto.CommentDTO;
import robotic.system.activityUser.domain.model.ActivityPhoto;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ActivityUserService {

    @Autowired
    private ActivityUserRepository activityUserRepository;

    @Autowired
    private ActivityPhotoRepository activityPhotoRepository;

     @Autowired
    private UserRepository userRepository;

    @Transactional
    public ActivityUser createActivity(ActivityUser activityUser, List<MultipartFile> files, UUID userId)
            throws IOException {
    
        // Verificar se o userId foi fornecido
        if (userId == null) {
            throw new IllegalArgumentException("O ID do usuário é obrigatório.");
        }
    
        // Buscar o usuário no banco de dados pelo userId
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário com id " + userId + " não encontrado."));
        
        // Associar o usuário encontrado à atividade
        activityUser.setUser(user);
    
        // Processar as fotos, se houver
        if (files != null && !files.isEmpty()) {
            for (MultipartFile file : files) {
                String contentType = file.getContentType();
                if (!isSupportedContentType(contentType)) {
                    throw new IllegalArgumentException("Tipo de arquivo não suportado. Somente JPG, PNG e GIF são permitidos.");
                }
    
                byte[] zipBytes = zipFiles(files);
    
                ActivityPhoto photo = new ActivityPhoto();
                photo.setImageFile(zipBytes);
                photo.setFilename(file.getOriginalFilename());
                ActivityPhoto savedPhoto = activityPhotoRepository.save(photo);
                activityUser.getPhotos().add(savedPhoto);
            }
        }
    
        return activityUserRepository.save(activityUser);
    }
    
    private boolean isSupportedContentType(String contentType) {
        return contentType.equals("image/jpeg") || contentType.equals("image/png") || contentType.equals("image/gif");
    }

 
    // Métodos auxiliares para lidar com arquivos zip e fotos
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

            // Extração de fotos, se houver
            List<Map<String, String>> extractedPhotos = extractPhotos(activityUser);
            response.put("extractedPhotos", extractedPhotos);

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
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

    private List<Map<String, String>> extractPhotos(ActivityUser activityUser) throws IOException {
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
        return extractedPhotos;
    }


    public Page<ActivityUserDTO> listActivities(Pageable pageable) {
        return activityUserRepository.findAllActivityUserDTO(pageable);
    }

    public Page<ActivityUserDTO> listActivitiesByUserId(UUID userId, Pageable pageable) {
        return activityUserRepository.findAllActivityUserDTOByUserId(userId, pageable);
    }
    

    public Optional<ActivityUserDTO> getActivityByIdWithUserDetails(UUID activityId) {
        return activityUserRepository.findActivityByIdWithUserDetails(activityId);
    }

    public Optional<ActivityWithCommentsDTO> getActivityWithCommentsById(UUID activityId) {
        Optional<ActivityUser> activityUser = activityUserRepository.findById(activityId);
        if (activityUser.isPresent()) {
            ActivityUser activity = activityUser.get();

            List<CommentDTO> commentDTOs = activity.getComments().stream()
                    .map(comment -> new CommentDTO(comment.getId(), comment.getText(), comment.getCreatedDate()))
                    .collect(Collectors.toList());

            // Mapear os componentes usados para ComponentResponseDTO
            List<ComponentResponseDTO> componentDTOs = activity.getComponentsUsed().stream()
                    .map(component -> new ComponentResponseDTO(component.getId(), component.getName()))
                    .collect(Collectors.toList());

            return Optional.of(new ActivityWithCommentsDTO(
                    activity.getId(),
                    activity.getActivityTitle(),
                    activity.getActivityDescription(),
                    activity.getActivityStatus(),
                    activity.getTimeSpent(),
                    activity.getStartDate(),
                    activity.getEndDate(),
                    activity.getUser().getId(),
                    activity.getUser().getEmail(),
                    commentDTOs,
                    componentDTOs // Incluindo os componentes no DTO
            ));
        }
        return Optional.empty();
    }


    


}
