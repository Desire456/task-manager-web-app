package org.netcracker.students.dao.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.netcracker.students.dao.exceptions.NameAlreadyExistException;
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
    private Journal getByName(String name, int userId) {
        Session session = HibernateSessionFactoryUtil.getInstance().getSessionFactory().openSession();
        String hql = "From Journal Where name = :name And user_id = :user_id";
        Journal journal = null;
        Transaction tx1 = session.beginTransaction();
        List<Journal> journals = new ArrayList<>();
        Query query = session.createQuery(hql);
        query.setParameter(HibernateDAOConstants.NAME, name);
        query.setParameter(HibernateDAOConstants.USER_ID, userId);
        for (Object o : query.list()) {
            journals.add((Journal)o);
        }
        journal = journals.size() == 0 ? null : journals.get(0);
        tx1.commit();
        session.close();
        return journal;
    }

    @Override
    public Journal create(String name, String description, Integer userId, Timestamp creatingDate, boolean isPrivate)
            throws CreateJournalException, NameAlreadyExistException {
        Journal journal;
        try {
            journal = getByName(name, userId);
            if(journal != null) {
                throw new CreateJournalException();
            }
            else {
                journal = JournalFactory.createJournal(name, description, userId, creatingDate.toLocalDateTime(),
                        isPrivate);
            }
            Session session = HibernateSessionFactoryUtil.getInstance().getSessionFactory().openSession();
            Transaction tx1 = session.beginTransaction();
            session.save(journal);
            tx1.commit();
            session.close();
        }
        catch (HibernateException e){
            throw new CreateJournalException(DAOErrorConstants.CREATE_JOURNAL_EXCEPTION_MESSAGE + e.getMessage());
        }
        catch (CreateJournalException e) {
            throw new NameAlreadyExistException(String.format(DAOErrorConstants.
                    NAME_ALREADY_EXIST_JOURNAL_EXCEPTION_MESSAGE, name));
        }
        return journal;
    }

    @Override
    public Journal create(int id, String name, String description, Integer userId, Timestamp creationDate, boolean isPrivate) throws CreateJournalException, NameAlreadyExistException, JournalIdAlreadyExistException {
        Journal journal;
        try {
            journal = getByName(name, userId);
            if(journal != null) {
                throw new CreateJournalException();
            }
            journal = read(id);
            if(journal != null) {
                throw new CreateJournalByIdException();
            }
            journal = JournalFactory.createJournal(name, description, userId, creationDate.toLocalDateTime(),
                    isPrivate);
            Session session = HibernateSessionFactoryUtil.getInstance().getSessionFactory().openSession();
            Transaction tx1 = session.beginTransaction();
            session.save(journal);
            tx1.commit();
            session.close();
        }
        catch (HibernateException | ReadJournalException e){
            throw new CreateJournalException(DAOErrorConstants.CREATE_JOURNAL_EXCEPTION_MESSAGE + e.getMessage());
        }
        catch (CreateJournalException e) {
            throw new NameAlreadyExistException(String.format(DAOErrorConstants.
                    NAME_ALREADY_EXIST_JOURNAL_EXCEPTION_MESSAGE, name));
        } catch (CreateJournalByIdException e) {
            throw new JournalIdAlreadyExistException(DAOErrorConstants.JOURNAL_ID_ALREADY_EXIST_JOURNAL_EXCEPTION_MESSAGE + id);
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
        try {
            Session session = HibernateSessionFactoryUtil.getInstance().getSessionFactory().openSession();
            Transaction tx1 = session.beginTransaction();
            session.update(journal);
            tx1.commit();
            session.close();
        }
        catch (HibernateException e){
            throw new UpdateJournalException(DAOErrorConstants.UPDATE_JOURNAL_EXCEPTION + e.getMessage());
        }
    }

    @Override
    public void delete(int id) throws DeleteJournalException {
        try {
            Session session = HibernateSessionFactoryUtil.getInstance().getSessionFactory().openSession();
            String hql = "Delete From Journal where journal_id = :journal_id";
            Transaction tx1 = session.beginTransaction();
            Query query = session.createQuery(hql);
            query.setParameter(HibernateDAOConstants.JOURNAL_ID, id);
            query.executeUpdate();
            tx1.commit();
            session.close();
        }
        catch (HibernateException e){
            throw new DeleteJournalException(DAOErrorConstants.DELETE_JOURNAL_EXCEPTION_MESSAGE + e.getMessage());
        }
    }

    @Override
    public List<JournalDTO> getAll() throws GetAllJournalException {
        List<JournalDTO> journalDTOS = new ArrayList<>();
        try {
            Session session = HibernateSessionFactoryUtil.getInstance().getSessionFactory().openSession();
            String hql = "From Journal";
            Transaction tx1 = session.beginTransaction();
            List<Journal> journals = new ArrayList<>();
            Query query = session.createQuery(hql);
            for (Object o : query.list()) {
                journals.add((Journal)o);
            }
            for (Journal journal : journals) {
                JournalDTO journalDTO = JournalDTOFactory.createJournalDTO(journal.getId(), journal.getName(),
                        journal.getDescription(), journal.getCreationDate());
                journalDTOS.add(journalDTO);
            }
            tx1.commit();
            session.close();
        }
        catch (HibernateException e){
            throw new GetAllJournalException(DAOErrorConstants.GET_ALL_JOURNAL_EXCEPTION_MESSAGE + e.getMessage());
        }
        return journalDTOS;
    }

    @Override
    public List<JournalDTO> getAll(int userId) throws GetAllJournalByUserIdException {
        List<JournalDTO> journalDTOS = new ArrayList<>();
        try {
            Session session = HibernateSessionFactoryUtil.getInstance().getSessionFactory().openSession();
            String hql = "From Journal where user_id = :user_id or is_private = :value";
            Transaction tx1 = session.beginTransaction();
            List<Journal> journals = new ArrayList<>();
            Query query = session.createQuery(hql);
            query.setParameter(HibernateDAOConstants.USER_ID, userId);
            query.setParameter(HibernateDAOConstants.VALUE, false);
            for (Object o : query.list()) {
                journals.add((Journal)o);
            }
            for (Journal journal : journals) {
                JournalDTO journalDTO = JournalDTOFactory.createJournalDTO(journal.getId(), journal.getName(),
                        journal.getDescription(), journal.getCreationDate());
                journalDTOS.add(journalDTO);
            }
            tx1.commit();
            session.close();
        }
        catch (HibernateException e){
            throw new GetAllJournalByUserIdException(DAOErrorConstants.GET_ALL_JOURNAL_BY_USER_ID_EXCEPTION_MESSAGE + e.getMessage());
        }
        return journalDTOS;
    }

    @Override
    public List<JournalDTO> getSortedByCriteria(int userId, String column, String criteria) throws GetSortedByCriteriaJournalException {
        List<JournalDTO> journalDTOS = new ArrayList<>();
        try {
            Session session = HibernateSessionFactoryUtil.getInstance().getSessionFactory().openSession();
            String hql = "From Journal where user_id = :user_id " +
                    "or is_private = :value order by %s %s";
            hql = String.format(hql, column, criteria);
            Transaction tx1 = session.beginTransaction();
            Query query = session.createQuery(hql);
            List<Journal> journals = new ArrayList<>();
            query.setParameter(HibernateDAOConstants.USER_ID, userId);
            query.setParameter(HibernateDAOConstants.VALUE, false);
            for (Object o : query.list()) {
                journals.add((Journal)o);
            }
            for (Journal journal : journals) {
                JournalDTO journalDTO = JournalDTOFactory.createJournalDTO(journal.getId(), journal.getName(),
                        journal.getDescription(), journal.getCreationDate());
                journalDTOS.add(journalDTO);
            }
            tx1.commit();
            session.close();
        }
        catch (HibernateException e){
            throw new GetSortedByCriteriaJournalException(DAOErrorConstants.GET_SORTED_BY_CRITERIA_JOURNAL_EXCEPTION_MESSAGE + e.getMessage());
        }
        return journalDTOS;
    }

    @Override
    public List<JournalDTO> getFilteredByPattern(int userId, String column, String pattern, String criteria) throws GetFilteredByPatternJournalException {
        List<JournalDTO> journalDTOS = new ArrayList<>();
        try {
            Session session = HibernateSessionFactoryUtil.getInstance().getSessionFactory().openSession();
            String hql = "FROM Journal WHERE (user_id = :user_id OR is_private = :value) AND (%s LIKE :pattern) " +
                    "ORDER BY %s %s";
            hql = String.format(hql, column, column, criteria);
            Transaction tx1 = session.beginTransaction();
            Query query = session.createQuery(hql);
            List<Journal> journals = new ArrayList<>();
            query.setParameter(HibernateDAOConstants.USER_ID, userId);
            query.setParameter(HibernateDAOConstants.VALUE, false);
            query.setParameter(HibernateDAOConstants.PATTERN, pattern);
            for (Object o : query.list()) {
                journals.add((Journal)o);
            }
            for (Journal journal : journals) {
                JournalDTO journalDTO = JournalDTOFactory.createJournalDTO(journal.getId(), journal.getName(),
                        journal.getDescription(), journal.getCreationDate());
                journalDTOS.add(journalDTO);
            }
            tx1.commit();
            session.close();
        }
        catch (HibernateException e){
            throw new GetFilteredByPatternJournalException(DAOErrorConstants.GET_FILTERED_BY_PATTERN_JOURNAL_EXCEPTION_MESSAGE + e.getMessage());
        }
        return journalDTOS;
    }

    @Override
    public List<JournalDTO> getFilteredByEquals(int userId, String column, String equal, String criteria) throws GetFilteredByEqualsJournalException {
        List<JournalDTO> journalDTOS = new ArrayList<>();
        try {
            Session session = HibernateSessionFactoryUtil.getInstance().getSessionFactory().openSession();
            String hql = "FROM Journal WHERE (user_id = :user_id " +
                    "OR is_private = :value) AND (%s = :equal) ORDER BY %s %s";
            hql = String.format(hql, column, column, criteria);
            Transaction tx1 = session.beginTransaction();
            Query query = session.createQuery(hql);
            List<Journal> journals = new ArrayList<>();
            query.setParameter(HibernateDAOConstants.USER_ID, userId);
            query.setParameter(HibernateDAOConstants.VALUE, false);
            query.setParameter(HibernateDAOConstants.EQUAL, equal);
            for (Object o : query.list()) {
                journals.add((Journal)o);
            }
            for (Journal journal : journals) {
                JournalDTO journalDTO = JournalDTOFactory.createJournalDTO(journal.getId(), journal.getName(),
                        journal.getDescription(), journal.getCreationDate());
                journalDTOS.add(journalDTO);
            }
            tx1.commit();
            session.close();
        }
        catch (HibernateException e){
            throw new GetFilteredByEqualsJournalException(DAOErrorConstants.GET_FILTERED_BY_EQUALS_JOURNAL_EXCEPTION_MESSAGE + e.getMessage());
        }
        return journalDTOS;
    }
}
