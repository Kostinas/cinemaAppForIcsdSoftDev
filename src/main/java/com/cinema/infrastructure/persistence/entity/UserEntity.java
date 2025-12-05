package com.cinema.infrastructure.persistence.entity;


import com.cinema.domain.enums.BaseRole;
import jakarta.persistence.*;

@Entity
@Table(
        name = "users",
        indexes = {
                @Index(name = "uk_users_username", columnList = "username", unique = true)
        }
)


public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 64)
    private String username;

    @Column(name = "passwordHash", nullable = false, length = 1000)
    private String passwordHash;

    @Column(name = "fullName", nullable = false, length = 120)
    private String fullName;

    @Enumerated(EnumType.STRING)
    @Column(name = "baseRole",nullable = false, length = 20)
    private BaseRole baseRole;

    @Column(nullable = false)
    private boolean active;

    @Column(name = "failedAttempts", nullable = false)
    private int failedAttempts;


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public BaseRole getBaseRole() { return baseRole; }
    public void setBaseRole(BaseRole baseRole) { this.baseRole = baseRole; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    public int getFailedAttempts() { return failedAttempts; }
    public void setFailedAttempts(int failedAttempts) { this.failedAttempts = failedAttempts; }



}
