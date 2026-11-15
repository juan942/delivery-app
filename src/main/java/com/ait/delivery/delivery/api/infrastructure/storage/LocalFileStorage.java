package com.ait.delivery.delivery.api.infrastructure.storage;

import com.ait.delivery.delivery.api.domain.model.FileUpload;
import com.ait.delivery.delivery.api.domain.port.out.FileStorageRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Component
public class LocalFileStorage implements FileStorageRepository {

    private final Path uploadDir;

    public LocalFileStorage(@Value("${app.upload-dir:uploads}") String uploadDir) {
        this.uploadDir = Path.of(uploadDir).toAbsolutePath().normalize();
    }

    @Override
    public String store(FileUpload file) {
        try {
            Files.createDirectories(uploadDir);
            String name = file.filename();
            String extension = name.substring(name.lastIndexOf(".")).toLowerCase();
            Path target = uploadDir.resolve(UUID.randomUUID() + extension);
            Files.copy(file.content(), target);
            return target.toString();
        } catch (IOException e) {
            throw new UncheckedIOException("Could not upload file", e);
        }
    }
}
