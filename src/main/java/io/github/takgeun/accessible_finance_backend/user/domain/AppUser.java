package io.github.takgeun.accessible_finance_backend.user.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "app_users")
// JPA 엔티티의 생성, 수정 시점을 감지하여 감사(Auditing) 정보를 자동으로 넣어주는 리스너를 등록하는 애노테이션
/**
 * repository.save(appUser)
 *         ↓
 * 엔티티 생성 또는 수정 감지
 *         ↓
 * AuditingEntityListener 실행
 *         ↓
 * @CreatedDate / @LastModifiedDate 필드에 시간 입력
 *         ↓
 * INSERT 또는 UPDATE 실행
 */
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AppUser {

    private static final int EMAIL_MAX_LENGTH = 255;
    private static final int PASSWORD_HASH_MAX_LENGTH = 255;
    private static final int NAME_MAX_LENGTH = 100;

    @Id
    @Column(nullable = false, updatable = false)
    private UUID id;

    @Column(nullable = false, length = EMAIL_MAX_LENGTH, unique = true)
    private String email;

    @Column(name = "password_hash", nullable = false, length = PASSWORD_HASH_MAX_LENGTH)
    private String passwordHash;

    @Column(nullable = false, length = NAME_MAX_LENGTH)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AppUserRole role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AppUserStatus status;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    private AppUser(
            UUID id,
            String email,
            String passwordHash,
            String name,
            AppUserRole role
    ) {
        this.id = Objects.requireNonNull(id, "사용자 ID는 필수입니다.");
        this.email = email;
        this.passwordHash = passwordHash;
        this.name = name;
        this.role = role;
    }

    public void lock() {
        requireStatus(AppUserStatus.ACTIVE);
        this.status = AppUserStatus.LOCKED;
    }

    public void unlock() {
        requireStatus(AppUserStatus.LOCKED);
        this.status = AppUserStatus.ACTIVE;
    }

    public void disable() {
        if (this.status == AppUserStatus.DISABLED) {
            throw new IllegalStateException("이미 비활성화된 사용자입니다.");
        }

        this.status = AppUserStatus.DISABLED;
    }

    public boolean isActive() {
        return this.status == AppUserStatus.ACTIVE;
    }

    public boolean isCustomer() {
        return this.role == AppUserRole.CUSTOMER;
    }

    public boolean isStaff() {
        return this.role == AppUserRole.STAFF;
    }

    public static String normalizeEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("이메일은 필수입니다.");
        }

        String normalizedEmail = email.trim().toLowerCase(Locale.ROOT);

        if (normalizedEmail.length() > EMAIL_MAX_LENGTH) {
            throw new IllegalArgumentException(
                    "이메일은 " + EMAIL_MAX_LENGTH + "자를 초과할 수 없습니다."
            );
        }

        return normalizedEmail;
    }

    private static String validatePasswordHash(String passwordHash) {
        if (passwordHash == null || passwordHash.isBlank()) {
            throw new IllegalArgumentException("비밀번호 해시는 필수입니다.");
        }

        if (passwordHash.length() > PASSWORD_HASH_MAX_LENGTH) {
            throw new IllegalArgumentException(
                    "비밀번호 해시는 "
                            + PASSWORD_HASH_MAX_LENGTH
                            + "자를 초과할 수 없습니다."
            );
        }

        return passwordHash;
    }

    private static String normalizeName(String name) {
        if(name == null || name.isBlank()) {
            throw new IllegalArgumentException("이름은 필수입니다.");
        }

        String normalizedName = name.trim();

        if(normalizedName.length() > NAME_MAX_LENGTH) {
            throw new IllegalArgumentException(
                    "이름은 " + NAME_MAX_LENGTH + "자를 초과할 수 없습니다."
            );
        }

        return normalizedName;
    }

    private void requireStatus(AppUserStatus requiredStatus) {
        if(this.status != requiredStatus) {
            throw new IllegalStateException(
                    "현재 상태에서는 요청한 계정 상태 변경을 수행할 수 없습니다."
            );
        }
    }
}
