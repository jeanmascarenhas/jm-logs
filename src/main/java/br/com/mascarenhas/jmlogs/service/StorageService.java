package br.com.mascarenhas.jmlogs.service;

import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface StorageService {

    void store(MultipartFile file);

    Path load(String filename);

}
