package io.github.takgeun.accessible_finance_backend.user.domain;

import java.util.Optional;
import java.util.UUID;

// 도메인 계층이 요구하는 저장소 규격
// 어플리케이션에서 필요한 기능만 정의한 도메인 저장소 인터페이스
// 이렇게 하면 도메인 계층이 Spring Data JPA에 의존하지 않게 된다. (이게 목적)
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
public interface AppUserRepository {

    AppUser save(AppUser appUser);

    Optional<AppUser> findById(UUID id);

    Optional<AppUser> findByEmail(String email);

    boolean existsByEmail(String email);
}
