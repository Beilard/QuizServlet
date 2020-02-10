package ua.quiz.model.dto;

import java.util.Objects;

public class User {
    private final Long id;
    private final String email;
    private final String password;
    private final String name;
    private final String surname;
    private final Boolean isCaptain;
    private final Long teamId;
    private final Role role;

    public User(UserBuilder userBuilder) {
        this.id = userBuilder.id;
        this.email = userBuilder.email;
        this.password = userBuilder.password;
        this.name = userBuilder.name;
        this.surname = userBuilder.surname;
        this.role = userBuilder.role;
        this.teamId = userBuilder.teamId;
        this.isCaptain = userBuilder.isCaptain;
    }

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    public static UserBuilder builder(User user) {
        return new UserBuilder(user);
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

    public Role getRole() {
        return role;
    }

    public Long getTeamId() {
        return teamId;
    }

    public Boolean getCaptain() {
        return isCaptain;
    }

    public static class UserBuilder {
        private Long id;
        private String email;
        private String password;
        private String name;
        private String surname;
        private Boolean isCaptain;
        private Long teamId;
        private Role role;

        private UserBuilder() {
        }

        private UserBuilder(User user) {
            this.id = user.id;
            this.email = user.email;
            this.password = user.password;
            this.name = user.name;
            this.surname = user.surname;
            this.role = user.role;
            this.teamId = user.teamId;
            this.isCaptain = user.isCaptain;
        }

        public User build() {
            return new User(this);
        }

        public UserBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public UserBuilder withCaptain(Boolean captain) {
            isCaptain = captain;
            return this;
        }

        public UserBuilder withEmail(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder withPassword(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder withTeamId(Long teamId) {
            this.teamId = teamId;
            return this;
        }

        public UserBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public UserBuilder withSurname(String surname) {
            this.surname = surname;
            return this;
        }

        public UserBuilder withRole(Role role) {
            this.role = role;
            return this;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        User user = (User) o;
        return id.equals(user.id) &&
                Objects.equals(email, user.email) &&
                Objects.equals(password, user.password) &&
                Objects.equals(name, user.name) &&
                Objects.equals(surname, user.surname) &&
                Objects.equals(isCaptain, user.isCaptain) &&
                Objects.equals(teamId, user.teamId) &&
                role == user.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password, name, surname, isCaptain, teamId, role);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", isCaptain=" + isCaptain +
                ", teamId=" + teamId +
                ", role=" + role +
                '}';
    }
}
