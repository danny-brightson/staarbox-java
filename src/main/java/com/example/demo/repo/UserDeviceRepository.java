package com.example.demo.repo;

import com.example.demo.entity.UserDevice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserDeviceRepository extends JpaRepository<UserDevice, Long> {

    List<UserDevice> findByUserId(Long userId);
}