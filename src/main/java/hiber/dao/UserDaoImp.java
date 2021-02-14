package hiber.dao;

import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void add(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    @Transactional
    public User getUserByCar(String model, int series) {
        String hqlQuery = "select users from User users inner join Car cars on users.userCar = cars.id where cars.model = :model and cars.series =:series";
        User user = sessionFactory.getCurrentSession().createQuery(hqlQuery, User.class).setParameter("model", model)
                .setParameter("series", series).uniqueResult();
        return user;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
        return query.getResultList();
    }

    @Override
    @Transactional
    public void deleteAllUser() {
        sessionFactory.getCurrentSession().createNativeQuery("delete from users").executeUpdate();
    }

    @Override
    @Transactional
    public void deleteAllCar() {
        sessionFactory.getCurrentSession().createNativeQuery("delete from cars").executeUpdate();
    }
}