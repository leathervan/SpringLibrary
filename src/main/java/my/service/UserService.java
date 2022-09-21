package my.service;

import my.dao.UserDao;
import my.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserService {
    private final UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void add(User user){
        userDao.add(user);
    }

    public User getUser(int id) {
        return userDao.get(id);
    }
    public User getUser(String email) {
       return userDao.get(email);
    }
}
