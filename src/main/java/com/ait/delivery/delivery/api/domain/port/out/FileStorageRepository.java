package com.ait.delivery.delivery.api.domain.port.out;

import com.ait.delivery.delivery.api.domain.model.FileUpload;

public interface FileStorageRepository {
    String store(FileUpload file);
}
