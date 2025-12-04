package infrastructure.security;

import domain.entity.value.UserId;

import java.time.Instant;

public class AuditLogger {

    public void logLogin(UserId userId) {
        System.out.println("[AUDIT] LOGIN user=" + userId.value() + " at " + Instant.now());
    }

    public void logAction(UserId userId, String action, String target) {
        System.out.println("[AUDIT] user=" + userId.value() +
                " action=" + action +
                " target=" + target +
                " at " + Instant.now());
    }
}
