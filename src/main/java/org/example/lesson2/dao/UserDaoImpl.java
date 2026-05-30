package org.example.lesson2.dao;

import org.example.lesson2.config.HibernateUtil;
import org.example.lesson2.model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

@Slf4j
public class UserDaoImpl extends BaseDao implements UserDao {

    @Override
    public void save(User user) {

        Objects.requireNonNull(user, "User не может быть null");

        executeInsideTransaction(
                session -> {
                    session.persist(user);
                    log.info("Пользователь сохранен: {}", user);
                }
        );

    }

    @Override
    public User findById(Integer id) {

        Objects.requireNonNull(id, "ID не может быть null");

        return executeWithResult(
                session -> session.get(User.class, id)
        );
    }

    @Override
    public List<User> findAll() {
            log.info("Получение всех пользователей");

            return executeWithResult(
                    session ->
                        session.createQuery(
                                "from User",
                                User.class
                        )
                                .list()
            );
    }

    @Override
    public void update(User user) {

        Objects.requireNonNull(user, "User не может быть null");

        executeInsideTransaction(
                session -> {
                    session.merge(user);
                    log.info("Пользователь обновлен: {}", user);
                }
        );
    }

    @Override
    public void delete(Integer id) {

        Objects.requireNonNull(id, "ID не может быть null");

        executeInsideTransaction(session -> {
            User user = session.get(User.class, id);

            if (user != null) {
                session.remove(user);

                log.info("Пользователь удален: id={}", id);
            } else {
                log.warn("Пользователь не найден id={}", id);
            }
        });

    }
}
