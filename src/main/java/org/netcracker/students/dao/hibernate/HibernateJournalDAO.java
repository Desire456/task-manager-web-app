package org.netcracker.students.dao.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.netcracker.students.dao.exceptions.journalDAO.*;
import org.netcracker.students.dao.hibernate.utils.HibernateSessionFactoryUtil;
import org.netcracker.students.dao.interfaces.JournalDAO;
import org.netcracker.students.dao.postrgresql.DAOErrorConstants;
import org.netcracker.students.factories.JournalDTOFactory;
import org.netcracker.students.factories.JournalFactory;
import org.netcracker.students.model.Journal;
import org.netcracker.students.model.dto.JournalDTO;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class HibernateJournalDAO implements JournalDAO {
    @Override
    public Journal create(String name, String description, Integer userId, Timestamp creatingDate, boolean isPrivate)
            throws CreateJournalException {
        Journal journal;
        try {
            journal = JournalFactory.createJournal(name, description, userId, creatingDate.toLocalDateTime(),
                    isPrivate);
            Session session = HibernateSessionFactoryUtil.getInstance().getSessionFactory().openSession();
            Transaction tx1 = session.beginTransaction();
            session.save(journal);
            tx1.commit();
            session.close();
        }
        catch (HibernateException e){
            throw new CreateJournalException(DAOErrorConstants.CREATE_JOURNAL_EXCEPTION_MESSAGE + e.getMessage());
        }
        return journal;
    }

    @Override
    public Journal read(int id) throws ReadJournalException {
        Journal journal;
        try {
            journal = HibernateSessionFactoryUtil.getInstance().getSessionFactory().openSession().
                    get(Journal.class, id);
        } catch (HibernateException e) {
            throw new ReadJournalException(DAOErrorConstants.READ_JOURNAL_EXCEPTION + e.getMessage());
        }
        return journal;
    }

    @Override
    public void update(Journal journal) throws UpdateJournalException {
        Session session = HibernateSessionFactoryUtil.getInstance().getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(journal);
        tx1.commit();
        session.close();
    }

    @Override
    public void delete(int id) throws DeleteJournalException {
        Session session = HibernateSessionFactoryUtil.getInstance().getSessionFactory().openSession();
        String hql = "Delete From Journal where id = :journal_id";
        Transaction tx1 = session.beginTransaction();
        Query query = session.createQuery(hql);
        query.setParameter("journal_id", id);
        query.executeUpdate();
        tx1.commit();
        session.close();
    }

    @Override
    public List<JournalDTO> getAll() throws GetAllJournalException {
        Session session = HibernateSessionFactoryUtil.getInstance().getSessionFactory().openSession();
        String hql = "From Journal";
        Transaction tx1 = session.beginTransaction();
        List<Journal> journals = new ArrayList<>();
        Query query = session.createQuery(hql);
        for (Object o : query.list()) {
            journals.add((Journal)o);
        }
        List<JournalDTO> journalDTOS = new ArrayList<>();
        for (Journal journal : journals) {
            JournalDTO journalDTO = JournalDTOFactory.createJournalDTO(journal.getId(), journal.getName(),
                    journal.getDescription(), journal.getCreationDate());
            journalDTOS.add(journalDTO);
        }
        tx1.commit();
        session.close();
        return journalDTOS;
    }

    @Override
    public List<JournalDTO> getAll(int userId) throws GetAllJournalByUserIdException {
        Session session = HibernateSessionFactoryUtil.getInstance().getSessionFactory().openSession();
        String hql = "From Journal where id = :user_id or is_private = :value ";
        Transaction tx1 = session.beginTransaction();
        List<Journal> journals = new ArrayList<>();
        Query query = session.createQuery(hql);
        query.setParameter("user_id", userId);
        query.setParameter("value", false);
        for (Object o : query.list()) {
            journals.add((Journal)o);
        }
        List<JournalDTO> journalDTOS = new ArrayList<>();
        for (Journal journal : journals) {
            JournalDTO journalDTO = JournalDTOFactory.createJournalDTO(journal.getId(), journal.getName(),
                    journal.getDescription(), journal.getCreationDate());
            journalDTOS.add(journalDTO);
        }
        tx1.commit();
        session.close();
        return journalDTOS;
    }

    @Override
    public List<JournalDTO> getSortedByCriteria(int userId, String column, String criteria) throws GetSortedByCriteriaJournalException {
        Session session = HibernateSessionFactoryUtil.getInstance().getSessionFactory().openSession();
        String hql = "From Journal where user_id = :user_id " +
                "or is_private = :value order by %s %s";
        String HQL = String.format(hql, column, criteria);
        Transaction tx1 = session.beginTransaction();
        Query query = session.createQuery(hql);
        List<Journal> journals = new ArrayList<>();
        query.setParameter("user_id", userId);
        query.setParameter("value", false);
        for (Object o : query.list()) {
            journals.add((Journal)o);
        }
        List<JournalDTO> journalDTOS = new ArrayList<>();
        for (Journal journal : journals) {
            JournalDTO journalDTO = JournalDTOFactory.createJournalDTO(journal.getId(), journal.getName(),
                    journal.getDescription(), journal.getCreationDate());
            journalDTOS.add(journalDTO);
        }
        tx1.commit();
        session.close();
        return journalDTOS;
    }

    @Override
    public List<JournalDTO> getFilteredByPattern(int userId, String column, String pattern, String criteria) throws GetFilteredByPatternJournalException {
        Session session = HibernateSessionFactoryUtil.getInstance().getSessionFactory().openSession();
        String hql = "FROM Journal WHERE (user_id = :user_id OR is_private = :value) AND (%s LIKE :pattern) " +
                "ORDER BY %s %s";
        String HQL = String.format(hql, column, column, criteria);
        Transaction tx1 = session.beginTransaction();
        Query query = session.createQuery(hql);
        List<Journal> journals = new ArrayList<>();
        query.setParameter("user_id", userId);
        query.setParameter("value", false);
        query.setParameter("pattern", pattern);
        for (Object o : query.list()) {
            journals.add((Journal)o);
        }
        List<JournalDTO> journalDTOS = new ArrayList<>();
        for (Journal journal : journals) {
            JournalDTO journalDTO = JournalDTOFactory.createJournalDTO(journal.getId(), journal.getName(),
                    journal.getDescription(), journal.getCreationDate());
            journalDTOS.add(journalDTO);
        }
        tx1.commit();
        session.close();
        return journalDTOS;
    }

    @Override
    public List<JournalDTO> getFilteredByEquals(int userId, String column, String equal, String criteria) throws GetFilteredByEqualsJournalException {
        Session session = HibernateSessionFactoryUtil.getInstance().getSessionFactory().openSession();
        String hql = "FROM Journal  WHERE (user_id = :user_id " +
                "OR is_private = :value) AND (%s = :equal) ORDER BY %s %s";
        String HQL = String.format(hql, column, column, criteria);
        Transaction tx1 = session.beginTransaction();
        Query query = session.createQuery(hql);
        List<Journal> journals = new ArrayList<>();
        query.setParameter("user_id", userId);
        query.setParameter("value", false);
        query.setParameter("equal", equal);
        for (Object o : query.list()) {
            journals.add((Journal)o);
        }
        List<JournalDTO> journalDTOS = new ArrayList<>();
        for (Journal journal : journals) {
            JournalDTO journalDTO = JournalDTOFactory.createJournalDTO(journal.getId(), journal.getName(),
                    journal.getDescription(), journal.getCreationDate());
            journalDTOS.add(journalDTO);
        }
        tx1.commit();
        session.close();
        return null;
    }
}
