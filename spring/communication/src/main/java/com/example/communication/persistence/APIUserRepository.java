package com.example.communication.persistence;

import com.example.communication.domain.APIUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface APIUserRepository extends JpaRepository<APIUser, String > {
}
