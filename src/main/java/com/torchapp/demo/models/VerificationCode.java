package com.torchapp.demo.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "verification_code")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VerificationCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    private String code;

    private LocalDateTime expiresAt;

    private int attempts;

    private boolean verified;

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiresAt);
    }
}
