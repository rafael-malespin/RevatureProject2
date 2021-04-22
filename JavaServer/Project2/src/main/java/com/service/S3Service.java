package com.service;

import java.io.File;

public interface S3Service {

    public void uploadFile(String keyName, File file);
}
