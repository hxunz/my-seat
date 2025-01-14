package com.codesoom.myseat.services;

import com.codesoom.myseat.domain.Role;
import com.codesoom.myseat.domain.User;
import com.codesoom.myseat.repositories.RoleRepository;
import com.codesoom.myseat.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 회원 가입 서비스
 */
@Service
@Slf4j
public class SignupService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepo;
    private final RoleRepository roleRepo;

    public SignupService(
            UserRepository userRepo, 
            RoleRepository roleRepo,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 생성된 회원 엔티티를 반환합니다.
     * 
     * @param name 이름
     * @param email 이메일
     * @param password 비밀번호
     * @return 회원
     */
    public User createUser(
            String name,
            String email,
            String password
    ) {
        Role role = Role.builder()
                .email(email)
                .roleName("USER")
                .build();
        
        roleRepo.save(role);

        return userRepo.save(User.builder()
                .name(name)
                .email(email)
                .password(passwordEncoder.encode(password))
                .build()
        );
    }
}
