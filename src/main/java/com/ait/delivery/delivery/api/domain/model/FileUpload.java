package com.ait.delivery.delivery.api.domain.model;

import java.io.InputStream;

public record FileUpload(String filename, String contentType, InputStream content) {
}
