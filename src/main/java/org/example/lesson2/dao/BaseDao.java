package org.example.lesson2.dao;

import lombok.extern.slf4j.Slf4j;
import org.example.lesson2.config.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.function.Consumer;
import java.util.function.Function;

@Slf4j
public abstract class BaseDao {
    protected void executeInsideTransaction(
            Consumer<Session> action
    ) {

        Transaction transaction = null;

        try (Session session =
                     HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            action.accept(session);

            transaction.commit();

        } catch (Exception e) {

            if (transaction != null) {
                transaction.rollback();
            }

            log.error("Ошибка транзакции", e);

            throw e;
        }
    }

    protected  <T> T executeWithResult(
            Function<Session, T> action
    ) {

        try (Session session =
                     HibernateUtil.getSessionFactory().openSession()) {

            return action.apply(session);

        } catch (Exception e) {

            log.error("Ошибка выполнения запроса", e);

            throw e;
        }
    }
}
