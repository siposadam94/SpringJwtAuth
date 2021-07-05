package sAdam.RSJwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sAdam.RSJwt.entity.User;

@Repository
public interface UserDetailsRepository extends JpaRepository<User, Long> {
	User findByUsername(String username);
}

