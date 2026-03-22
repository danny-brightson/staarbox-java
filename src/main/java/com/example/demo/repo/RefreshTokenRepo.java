package com.example.demo.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.RefreshToken;

@Repository
public interface RefreshTokenRepo extends JpaRepository<RefreshToken, Long>  {

	Optional<RefreshToken> findByResfreshToken(String refreshToken);
	
	Optional<RefreshToken> findTopByPhoneNumberOrderByCreatedTimeDesc(String phoneNumber);

}
