package com.example.ctdt.controller;

import com.example.ctdt.dto.UserDTO;
import com.example.ctdt.service.UserService;
import com.example.ctdt.model.User;
import com.example.ctdt.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Controller xử lý các yêu cầu liên quan đến người dùng.
 */
@RestController
@RequestMapping("/api/nguoi-dung")
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Lấy tất cả người dùng
    @GetMapping
    public ResponseEntity<List<UserDTO>> layTatCaNguoiDung() {
        List<UserDTO> danhSach = service.layTatCaNguoiDung();
        return ResponseEntity.ok(danhSach);
    }

    // Lấy người dùng theo ID
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> layNguoiDungTheoId(@PathVariable Integer id) {
        UserDTO dto = service.layNguoiDungTheoId(id);
        return ResponseEntity.ok(dto);
    }

    // Tạo mới người dùng
    @PostMapping
    public ResponseEntity<UserDTO> taoNguoiDung(@Valid @RequestBody UserDTO dto) {
        UserDTO savedDto = service.taoNguoiDung(dto);
        if (dto.getTrangThai() == null) {
            dto.setTrangThai((byte) 1);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDto);
    }

    // Cập nhật người dùng
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> capNhatNguoiDung(@PathVariable Integer id, @Valid @RequestBody UserDTO dto) {
        UserDTO updatedDto = service.capNhatNguoiDung(id, dto);
        return ResponseEntity.ok(updatedDto);
    }

    // Xóa người dùng
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> xoaNguoiDung(@PathVariable Integer id) {
        service.xoaNguoiDung(id);
        return ResponseEntity.noContent().build();
    }

    // API đăng nhập
    @PostMapping("/login")
    public ResponseEntity<?> dangNhap(@RequestBody Map<String, String> loginData) {
        String username = loginData.get("username");
        String password = loginData.get("password");
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Tài khoản không tồn tại.");
        }
        User user = userOpt.get();
        if (!passwordEncoder.matches(password, user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Mật khẩu không đúng.");
        }
        return ResponseEntity.ok(service.layNguoiDungTheoId(user.getId()));
    }
}