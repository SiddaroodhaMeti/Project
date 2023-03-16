package com.project.banking_system.repository;

import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.banking_system.dto.Management;
@Repository
public interface ManagementRepository extends JpaRepository<Management, Integer> {

	

	Management findByEmail(String email);

}
