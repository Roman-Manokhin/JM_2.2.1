package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    private final SessionFactory sessionFactory;

    @Autowired
    public UserDaoImp(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void add(User user) {
        sessionFactory.getCurrentSession().save(user);
    }


    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
        return query.getResultList();
    }

    @Override
    public User getUserFromCarParameters(String model, int series) {
        String HQL = "from Car car LEFT OUTER JOIN FETCH car.user WHERE car.model=:model and car.series=:series";

        Car car = sessionFactory.getCurrentSession()
                    .createQuery(HQL, Car.class)
                    .setParameter("model", model)
                    .setParameter("series", series)
                    .uniqueResult();

        return car.getUser();
    }


}