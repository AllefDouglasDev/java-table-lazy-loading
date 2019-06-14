package dao;

import models.Paginate;
import models.User;

import java.util.ArrayList;

public class UserDAO {

    public int getUsersSize() {
        return 200;
    }

    public Paginate<User> getUsers(int perPage, int page) {
        int usersSize = getUsersSize();
        int totalPages = (int) Math.ceil(usersSize / perPage);
        ArrayList<User> users = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            users.add(new User(
                            generateId(),
                            "Pessoa " + generateId(),
                            "Meu phone " + generateId(),
                            generateId() > 5 ? "Open" : "Close"
                    )
            );
        }

        return new Paginate<>(totalPages, page, perPage, users);
    }

    private int generateId() {
        return (int)(Math.random() * 10);
    }
}
