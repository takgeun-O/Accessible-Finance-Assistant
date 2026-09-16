package io.github.takgeun.accessible_finance_backend.user.infrastructure;

import io.github.takgeun.accessible_finance_backend.user.domain.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

// Spring Data JPA가 실제 DB 접근 코드를 생성하기 위한 인터페이스
// Spring Data JPA가 실제 구현 객체를 자동으로 만들어주는 기술 인터페이스
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
interface SpringDataAppUserRepository extends JpaRepository<AppUser, UUID> {

    Optional<AppUser> findByEmail(String email);

    boolean existsByEmail(String email);
}
