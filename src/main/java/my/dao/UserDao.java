package my.dao;

import my.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

@Component
public class UserDao {
    private final SessionFactory sessionFactory;

    @Autowired
    public UserDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public List<User> getAll(){
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select u from User u", User.class).getResultList();
    }

    @Transactional
    public void add(User user){
        Session session = sessionFactory.getCurrentSession();
        session.persist(user);
    }
    @Transactional
    public User get(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(User.class, id);
    }
    @Transactional
    public User get(String email) {
        Session session = sessionFactory.getCurrentSession();
        try {
            return (User) session.createQuery("from User where email = :email").setParameter("email", email).getSingleResult();
        } catch (NoResultException e){
            return null;
        }
    }
}
