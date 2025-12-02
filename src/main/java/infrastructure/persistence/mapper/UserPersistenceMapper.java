package infrastructure.persistence.mapper;

import domain.entity.User;
import domain.entity.value.HashedPassword;
import domain.entity.value.UserId;
import domain.entity.value.Username;
import infrastructure.persistence.entity.UserEntity;

public class UserPersistenceMapper {

    public UserEntity toEntity(User user) {
        UserEntity e = new UserEntity();
        if (user.id() != null) {
            e.setId(user.id().value());
        }
        e.setUsername(user.username().value());
        e.setPasswordHash(user.password().value());
        e.setFullName(user.fullName());
        e.setBaseRole(user.baseRole());
        e.setActive(user.isActive());
        e.setFailedAttempts(user.failedAttempts());

        return e;
    }

    public User toDomain(UserEntity e) {
        return new User(
                e.getId() != null ? new UserId(e.getId()) : null,
                new Username(e.getUsername()),
                new HashedPassword(e.getPasswordHash()),
                e.getFullName(),
                e.getBaseRole(),
                e.isActive(),
                e.getFailedAttempts()
        );

    }
}