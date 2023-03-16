package com.project.banking_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.banking_system.dto.BankAccount;


@Repository
public interface BankRepository extends JpaRepository<BankAccount, Long>{

}
