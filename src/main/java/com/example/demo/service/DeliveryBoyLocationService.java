package com.example.demo.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.example.demo.dto.LocationRequestDto;
import com.example.demo.dto.LocationResponseDto;
import com.example.demo.entity.DeliveryBoyLocation;
import com.example.demo.repo.DeliveryboylocationRepo;

@Service
public class DeliveryBoyLocationService {

    @Autowired
    private DeliveryboylocationRepo repo;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void updateLocation(LocationRequestDto dto) {
        if (repo.existsByDeliveryBoyPhoneNumber(dto.getDeliveryBoyPhoneNumber())) {
            repo.updateLocation(dto.getDeliveryBoyPhoneNumber(), dto.getLatitude(), dto.getLongitude(), LocalDateTime.now());
        } else {
            DeliveryBoyLocation loc = new DeliveryBoyLocation();
            loc.setDeliveryBoyPhoneNumber(dto.getDeliveryBoyPhoneNumber());
            loc.setLatitude(dto.getLatitude());
            loc.setLongitude(dto.getLongitude());
            loc.setUpdatedAt(LocalDateTime.now());
            repo.save(loc);
        }

        LocationResponseDto response = new LocationResponseDto(
            dto.getDeliveryBoyPhoneNumber(), dto.getLatitude(), dto.getLongitude(), LocalDateTime.now()
        );

        messagingTemplate.convertAndSend("/topic/location-updates/" + dto.getDeliveryBoyPhoneNumber(), response);
    }

    public LocationResponseDto getLocation(String phoneNumber) {
        DeliveryBoyLocation location = repo.findTopByDeliveryBoyPhoneNumberOrderByUpdatedAtDesc(phoneNumber)
            .orElseThrow(() -> new RuntimeException("Location not found"));

        return new LocationResponseDto(
            location.getDeliveryBoyPhoneNumber(),
            location.getLatitude(),
            location.getLongitude(),
            location.getUpdatedAt()
        );
    }
}
