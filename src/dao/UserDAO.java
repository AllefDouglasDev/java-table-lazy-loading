package dao;

import models.Paginate;
import models.User;

import java.util.ArrayList;

public class UserDAO {

    public int getUsersSize() {
        return 200;
    }

    public Paginate<User> getUsers(int perPage, int page) {
        ArrayList<User> users = new ArrayList<>();
        int usersSize = getUsersSize();
        int sizeToLoad = Paginate.calculateSizeToLoad(perPage, page, usersSize);
        if (sizeToLoad > 0) {
            for (int i = 0; i < sizeToLoad; i++) {
                users.add(new User(
                        generateId(),
                        "Pessoa " + generateId(),
                        "Meu phone " + generateId(),
                        generateId() > 5 ? "Open" : "Close"
                    )
                );
            }
        }

        return new Paginate<>(usersSize, page, perPage, users);
    }

    private int generateId() {
        return (int)(Math.random() * 10);
    }
}
