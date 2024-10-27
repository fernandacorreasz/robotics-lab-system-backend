package robotic.system.activityUser.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import robotic.system.activityUser.domain.model.ActivityPhoto;
import robotic.system.activityUser.repository.ActivityPhotoRepository;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Service
public class ActivityPhotoFilterService {

    @Autowired
    private ActivityPhotoRepository activityPhotoRepository;

    // Método para buscar a imagem pelo filename e descompactar
    public ResponseEntity<byte[]> getPhotoByFilename(UUID photoId) throws IOException {
        Optional<ActivityPhoto> photoOptional = activityPhotoRepository.findById(photoId);
        
        if (!photoOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ActivityPhoto photo = photoOptional.get();
        byte[] zipBytes = photo.getImageFile();
        String filename = photo.getFilename();
        
        // Descompactar o arquivo ZIP e retornar o arquivo original
        byte[] originalFileBytes = unzipFile(zipBytes, filename);

        // Definir o tipo de conteúdo com base na extensão do arquivo
        String contentType = getContentTypeFromFilename(filename);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", contentType);
        headers.set("Content-Disposition", "inline; filename=\"" + filename + "\"");

        return new ResponseEntity<>(originalFileBytes, headers, HttpStatus.OK);
    }

    // Método auxiliar para descompactar um arquivo ZIP
    private byte[] unzipFile(byte[] zipBytes, String filename) throws IOException {
        try (ZipInputStream zipInputStream = new ZipInputStream(new ByteArrayInputStream(zipBytes))) {
            ZipEntry entry;
            while ((entry = zipInputStream.getNextEntry()) != null) {
                if (entry.getName().equals(filename)) {
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = zipInputStream.read(buffer)) > 0) {
                        outputStream.write(buffer, 0, len);
                    }
                    return outputStream.toByteArray();
                }
            }
        }
        throw new IOException("File not found in the ZIP: " + filename);
    }

    // Método auxiliar para determinar o tipo de conteúdo com base no nome do arquivo
    private String getContentTypeFromFilename(String filename) {
        if (filename.endsWith(".jpg") || filename.endsWith(".jpeg")) {
            return "image/jpeg";
        } else if (filename.endsWith(".png")) {
            return "image/png";
        } else if (filename.endsWith(".gif")) {
            return "image/gif";
        }
        return "application/octet-stream"; // Tipo genérico
    }

    
}
