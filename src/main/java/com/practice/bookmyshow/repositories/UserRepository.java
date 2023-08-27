package com.practice.bookmyshow.repositories;

import com.practice.bookmyshow.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
