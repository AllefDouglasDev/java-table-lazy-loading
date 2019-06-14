package controllers;

import dao.UserDAO;
import models.Paginate;
import models.User;

public class UserController {
    private UserDAO userDAO;

    public UserController() {
        userDAO = new UserDAO();
    }

    public Paginate<User> getUsers(int perPage, int page) {
        return userDAO.getUsers(perPage, page);
    }
}
