package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.VersionRequest;
import com.example.demo.dto.VersionResponse;
import com.example.demo.entity.VersionCheck;
import com.example.demo.repo.VersionCheckRepository;

@Service
public class VersionCheckService {

    @Autowired
    private VersionCheckRepository repository;

    public VersionResponse updateVersion(VersionRequest request) {

        VersionCheck version = repository
                .findByCurrentVersionAndLatestVersion(
                        request.getCurrentVersion(),
                        request.getLatestVersion()
                )
                .orElseThrow(() -> new RuntimeException("Version not found"));

        if ("FORCE".equalsIgnoreCase(request.getUpdateType())) {
            version.setIsForceUpdate(true);
            version.setIsSoftUpdate(false);
        } else if ("SOFT".equalsIgnoreCase(request.getUpdateType())) {
            version.setIsForceUpdate(false);
            version.setIsSoftUpdate(true);
        } else {
            throw new RuntimeException("Invalid update type");
        }

        repository.save(version);

        return new VersionResponse(
                version.getIsForceUpdate(),
                version.getIsSoftUpdate()
        );
    }
}
