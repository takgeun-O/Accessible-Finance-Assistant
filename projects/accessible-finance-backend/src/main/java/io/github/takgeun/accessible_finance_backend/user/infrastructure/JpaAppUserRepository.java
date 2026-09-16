package io.github.takgeun.accessible_finance_backend.user.infrastructure;

import io.github.takgeun.accessible_finance_backend.user.domain.AppUser;
import io.github.takgeun.accessible_finance_backend.user.domain.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * AppUserService
 *       ↓ 의존
 * AppUserRepository
 *       ↑ 구현
 * JpaAppUserRepository
 *       ↓ 위임
 * SpringDataAppUserRepository
 *       ↓ 자동 구현
 * PostgreSQL
 */
@Repository
@RequiredArgsConstructor
public class JpaAppUserRepository implements AppUserRepository {

    private final SpringDataAppUserRepository springDataRepository;

    @Override
    public AppUser save(AppUser appUser) {
        Objects.requireNonNull(appUser, "저장할 사용자는 필수입니다.");
        return springDataRepository.save(appUser);
    }

    @Override
    public Optional<AppUser> findById(UUID id) {
        Objects.requireNonNull(id, "사용자 ID는 필수입니다.");
        return springDataRepository.findById(id);
    }

    @Override
    public Optional<AppUser> findByEmail(String email) {
        String normalizedEmail = AppUser.normalizeEmail(email);
        return springDataRepository.findByEmail(normalizedEmail);
    }

    @Override
    public boolean existsByEmail(String email) {
        String normalizedEmail = AppUser.normalizeEmail(email);
        return springDataRepository.existsByEmail(normalizedEmail);
    }
}
