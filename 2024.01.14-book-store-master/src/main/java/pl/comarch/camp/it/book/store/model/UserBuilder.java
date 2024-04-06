package pl.comarch.camp.it.book.store.model;

public class UserBuilder {
    User user = new User();

    public UserBuilder id(int id) {
        user.setId(id);
        return this;
    }

    public UserBuilder login(String login) {
        user.setLogin(login);
        return this;
    }

    public UserBuilder password(String password) {
        user.setPassword(password);
        return this;
    }

    public UserBuilder name(String name) {
        user.setName(name);
        return this;
    }

    public UserBuilder surname(String surname) {
        user.setSurname(surname);
        return this;
    }

    public UserBuilder role(User.Role role) {
        user.setRole(role);
        return this;
    }

    public User build() {
        return this.user;
    }
}
