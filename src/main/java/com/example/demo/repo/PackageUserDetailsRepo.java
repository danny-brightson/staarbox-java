package com.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.PackageType;
import com.example.demo.entity.PackageUserDetails;

@Repository
public interface PackageUserDetailsRepo extends JpaRepository<PackageUserDetails , Long> {

	PackageUserDetails findByUserNameAndPassword(String userName, String passWord);

}
