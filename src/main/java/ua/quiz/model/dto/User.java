package ua.quiz.model.dto;

import java.util.Objects;

public class User {
    private final Long id;
    private final String email;
    private final String password;
    private final String name;
    private final String surname;
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
    }

    public static UserBuilder builder(){
        return new UserBuilder();
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

    public static class UserBuilder {
        private Long id;
        private String email;
        private String password;
        private String name;
        private String surname;
        private Long teamId;
        private Role role;

        private UserBuilder(){
        }

        public User build(){
            return new User(this);
        }

        public UserBuilder withId(Long id) {
            this.id = id;
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
                Objects.equals(teamId, user.teamId) &&
                Objects.equals(surname, user.surname) &&
                role == user.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password, name, surname, teamId, role);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", teamId=" + teamId +
                ", role=" + role +
                '}';
    }
}
