package com.javachinna.repo;

import com.javachinna.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {

	AppUser findByEmail(String email);

	boolean existsByEmail(String email);

}
