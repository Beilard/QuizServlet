package ua.quiz.model.entity;

import java.util.Objects;

public class UserEntity {
    private final Long id;
    private final String email;
    private final String password;
    private final String name;
    private final String surname;
    private final Boolean isCaptain;
    private final Long teamId;
    private final RoleEntity roleEntity;

    public UserEntity(UserEntityBuilder userEntityBuilder) {
        this.id = userEntityBuilder.id;
        this.email = userEntityBuilder.email;
        this.password = userEntityBuilder.password;
        this.name = userEntityBuilder.name;
        this.surname = userEntityBuilder.surname;
        this.roleEntity = userEntityBuilder.roleEntity;
        this.teamId = userEntityBuilder.teamId;
        this.isCaptain = userEntityBuilder.isCaptain;
    }

    public static UserEntityBuilder builder() {
        return new UserEntityBuilder();
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Long getTeamId() {
        return teamId;
    }

    public RoleEntity getRoleEntity() {
        return roleEntity;
    }

    public Boolean getCaptain() {
        return isCaptain;
    }

    public static class UserEntityBuilder {
        private Long id;
        private String email;
        private String password;
        private String name;
        private String surname;
        private Boolean isCaptain;
        private Long teamId;
        private RoleEntity roleEntity;

        private UserEntityBuilder() {
        }

        public UserEntity build() {
            return new UserEntity(this);
        }

        public UserEntityBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public UserEntityBuilder withTeamId(Long teamId) {
            this.teamId = teamId;
            return this;
        }

        public UserEntityBuilder withCaptain(Boolean captain) {
            isCaptain = captain;
            return this;
        }

        public UserEntityBuilder withRoleEntity(RoleEntity roleEntity) {
            this.roleEntity = roleEntity;
            return this;
        }

        public UserEntityBuilder withEmail(String email) {
            this.email = email;
            return this;
        }

        public UserEntityBuilder withPassword(String password) {
            this.password = password;
            return this;
        }

        public UserEntityBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public UserEntityBuilder withSurname(String surname) {
            this.surname = surname;
            return this;
        }

        public UserEntityBuilder withRole(RoleEntity roleEntity) {
            this.roleEntity = roleEntity;
            return this;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserEntity)) return false;
        UserEntity that = (UserEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(email, that.email) &&
                Objects.equals(password, that.password) &&
                Objects.equals(name, that.name) &&
                Objects.equals(surname, that.surname) &&
                Objects.equals(isCaptain, that.isCaptain) &&
                Objects.equals(teamId, that.teamId) &&
                roleEntity == that.roleEntity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password, name, surname, isCaptain, teamId, roleEntity);
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", isCaptain=" + isCaptain +
                ", teamId=" + teamId +
                ", roleEntity=" + roleEntity +
                '}';
    }
}
