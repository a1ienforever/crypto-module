package com.example.cryptomodule.dao;

import com.example.cryptomodule.models.Key;
import com.example.cryptomodule.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class KeyDAO {

    // Create a new Key
    public void saveKey(Key key) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(key);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    // Read a Key by ID
    public Key getKeyById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Key.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Update an existing Key
    public void updateKey(Key key) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(key);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    // Delete a Key
    public void deleteKey(int id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Key key = session.get(Key.class, id);
            if (key != null) {
                session.delete(key);
                transaction.commit();
            }
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    // List all Keys
    public List<Key> getAllKeys() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Key> query = session.createQuery("FROM Key", Key.class);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
