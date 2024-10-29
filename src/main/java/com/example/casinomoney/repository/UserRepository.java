package com.example.casinomoney.repository;

import com.example.casinomoney.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
