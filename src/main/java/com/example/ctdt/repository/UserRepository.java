package com.example.ctdt.repository;

import com.example.ctdt.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository cho quản lý người dùng.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    // Tìm người dùng theo tên đăng nhập
    Optional<User> findByUsername(String username);

    // Tìm người dùng theo vai trò
    Iterable<User> findByVaiTro(String vaiTro);
}