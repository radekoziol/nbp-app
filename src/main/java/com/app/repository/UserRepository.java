package com.app.repository;

import com.app.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    boolean existsByUsername(String email);

//    User findById(long id);

    Optional<User> findByUsername(String username);

}
