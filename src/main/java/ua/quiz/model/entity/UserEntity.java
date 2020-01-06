package ua.quiz.model.entity;

import java.util.Objects;

public class UserEntity {
    private final Long id;
    private final String email;
    private final String password;
    private final String name;
    private final String surname;
    private final RoleEntity roleEntity;

    public UserEntity(UserEntityBuilder userEntityBuilder) {
        this.id = userEntityBuilder.id;
        this.email = userEntityBuilder.email;
        this.password = userEntityBuilder.password;
        this.name = userEntityBuilder.name;
        this.surname = userEntityBuilder.surname;
        this.roleEntity = userEntityBuilder.roleEntity;
    }

    public static UserEntityBuilder builder(){
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

    public RoleEntity getRoleEntity() {
        return roleEntity;
    }

    public static class UserEntityBuilder {
        private Long id;
        private String email;
        private String password;
        private String name;
        private String surname;
        private RoleEntity roleEntity;

        private UserEntityBuilder(){
        }

        public UserEntity build(){
            return new UserEntity(this);
        }

        public UserEntityBuilder withId(Long id) {
            this.id = id;
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
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserEntity)) {
            return false;
        }
        UserEntity userEntity = (UserEntity) o;
        return id.equals(userEntity.id) &&
                Objects.equals(email, userEntity.email) &&
                Objects.equals(password, userEntity.password) &&
                Objects.equals(name, userEntity.name) &&
                Objects.equals(surname, userEntity.surname) &&
                roleEntity == userEntity.roleEntity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password, name, surname, roleEntity);
    }

    @Override
    public String toString() {
        return  "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", role=" + roleEntity +
                '}';
    }
}
