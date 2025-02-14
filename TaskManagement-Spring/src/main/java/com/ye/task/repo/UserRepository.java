package com.ye.task.repo;

import com.ye.task.entity.User;
import com.ye.task.enums.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User , Long> {

    Optional<User> findFirstByEmail(String email);

    Optional<User> findByUserrole(UserRoles userRoles);
}
