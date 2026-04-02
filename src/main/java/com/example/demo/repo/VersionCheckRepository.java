package com.example.demo.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.VersionCheck;

import java.util.Optional;

@Repository
public interface VersionCheckRepository extends JpaRepository<VersionCheck, Long> {

    Optional<VersionCheck> findByCurrentVersionAndLatestVersion(String currentVersion, String latestVersion);
}
