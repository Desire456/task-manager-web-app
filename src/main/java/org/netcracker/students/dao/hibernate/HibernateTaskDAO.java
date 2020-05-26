package org.netcracker.students.dao.hibernate;

import org.hibernate.*;
import org.netcracker.students.dao.exceptions.taskDAO.*;
import org.netcracker.students.dao.interfaces.TasksDAO;
import org.netcracker.students.dao.postrgresql.DAOErrorConstants;
import org.netcracker.students.factories.TaskDTOFactory;
import org.netcracker.students.factories.TaskFactory;
import org.netcracker.students.model.Task;
import org.netcracker.students.model.dto.TaskDTO;

import javax.persistence.TemporalType;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class HibernateTaskDAO implements TasksDAO {
    private final SessionFactory sessionFactory;

    public HibernateTaskDAO(SessionFactory sessionFactory){ this.sessionFactory = sessionFactory; }

    public Task getByName(String name, int journal_id) {
        Session session = sessionFactory.openSession();
        String hql = "From Task where name = :name AND journal_id = :journal_id";
        Task task = null;
        Transaction tx1 = session.beginTransaction();
        List<Task> tasks = new ArrayList<>();
        Query query = session.createQuery(hql);
        query.setParameter(HibernateDAOConstants.NAME, name);
        query.setParameter(HibernateDAOConstants.JOURNAL_ID, journal_id);
        for (Object o : query.list()) {
            tasks.add((Task)o);
        }
        task = tasks.size() == 0 ? null : tasks.get(0);
        tx1.commit();
        session.close();
        return task;
    }

    @Override
    public Task create(String name, String status, String description, Timestamp plannedDate, Timestamp dateOfDone, Integer journalId) throws CreateTaskException {
        Task task;
        Session session = sessionFactory.openSession();
        try {
            task = TaskFactory.createTask(name, description, plannedDate.toLocalDateTime(), status, journalId);
            Transaction tx1 = session.beginTransaction();
            session.save(task);
            tx1.commit();
            session.close();
        }
        catch (HibernateException e){
            throw new CreateTaskException(DAOErrorConstants.CREATE_TASK_EXCEPTION_MESSAGE + e.getMessage());
        }
        return task;
    }

    @Override
    public Task create(int id, String name, String status, String description, Timestamp plannedDate, Timestamp dateOfDone, Integer journalId) throws CreateTaskException{
        Session session = sessionFactory.openSession();
        try {
            Transaction tx1 = session.beginTransaction();
            Query query = session.createNativeQuery("INSERT INTO tasks VALUES (?, ?, ?, ?, ?, ?, ?)");
            query.setParameter(1, id);
            query.setParameter(2, journalId);
            query.setParameter(3, name);
            query.setParameter(4, description);
            query.setParameter(5, status);
            query.setParameter(6, plannedDate);
            query.setParameter(7, dateOfDone, TemporalType.TIMESTAMP);
            query.executeUpdate();
            tx1.commit();
            session.close();
        }
        catch (HibernateException e){
            throw new CreateTaskException(DAOErrorConstants.CREATE_TASK_EXCEPTION_MESSAGE + e.getMessage());
        }
        return TaskFactory.createTask(id, journalId, name, description, plannedDate.toLocalDateTime(),
                dateOfDone == null ? null : dateOfDone.toLocalDateTime(), status);
    }

    @Override
    public Task read(int id) throws ReadTaskException {
        Task task;
        try {
            task = sessionFactory.openSession().
                    get(Task.class, id);
        } catch (HibernateException e) {
            throw new ReadTaskException(DAOErrorConstants.READ_TASK_EXCEPTION_MESSAGE + e.getMessage());
        }
        return task;
    }

    @Override
    public void update(Task task) throws UpdateTaskException {
        try {
            Session session = sessionFactory.openSession();
            Transaction tx1 = session.beginTransaction();
            session.update(task);
            tx1.commit();
            session.close();
        }
        catch (HibernateException e){
            throw new UpdateTaskException(DAOErrorConstants.UPDATE_TASK_EXCEPTION_MESSAGE + e.getMessage());
        }
    }

    @Override
    public void deleteByTaskId(int id) throws DeleteTaskException {
        try {
            Session session = sessionFactory.openSession();
            String hql = "Delete From Task where task_id = :task_id";
            Transaction tx1 = session.beginTransaction();
            Query query = session.createQuery(hql);
            query.setParameter(HibernateDAOConstants.TASK_ID, id);
            query.executeUpdate();
            tx1.commit();
            session.close();
        }
        catch (HibernateException e){
            throw new DeleteTaskException(DAOErrorConstants.DELETE_TASK_EXCEPTION_MESSAGE + e.getMessage());
        }
    }

    @Override
    public List<Task> getAllByJournalId(int journalId) throws GetAllTaskException {
        List<Task> tasks = new ArrayList<>();
        try {
            Session session = sessionFactory.openSession();
            String hql = "From Task where journal_id = :journal_id";
            Transaction tx1 = session.beginTransaction();
            Query query = session.createQuery(hql);
            query.setParameter(HibernateDAOConstants.JOURNAL_ID, journalId);
            for (Object o : query.list()) {
                tasks.add((Task)o);
            }
            tx1.commit();
            session.close();
        }
        catch (HibernateException e){
            throw new GetAllTaskException(DAOErrorConstants.GET_ALL_TASK_EXCEPTION_MESSAGE + e.getMessage());
        }
        return tasks;
    }

    @Override
    public List<TaskDTO> getAll() throws GetAllTaskException {
        List<TaskDTO> taskDTOS = new ArrayList<>();
        try {
            Session session = sessionFactory.openSession();
            String hql = "From Task";
            Transaction tx1 = session.beginTransaction();
            List<Task> tasks = new ArrayList<>();
            Query query = session.createQuery(hql);
            for (Object o : query.list()) {
                tasks.add((Task)o);
            }
            for (Task task : tasks) {
                TaskDTO taskDTO = TaskDTOFactory.createTaskDTO(task.getId(), task.getName(), task.getDescription(),
                        task.getStatus(), task.getPlannedDate(), task.getDateOfDone());
                taskDTOS.add(taskDTO);
            }
            tx1.commit();
            session.close();
        }
        catch (HibernateException e){
            throw new GetAllTaskException(DAOErrorConstants.GET_ALL_TASK_EXCEPTION_MESSAGE + e.getMessage());
        }
        return taskDTOS;
    }

    @Override
    public List<TaskDTO> getAll(int journalId) throws GetAllTaskException {
        List<TaskDTO> taskDTOS = new ArrayList<>();
        try {
            Session session = sessionFactory.openSession();
            String hql = "From Task where journal_id = :journal_id";
            Transaction tx1 = session.beginTransaction();
            List<Task> tasks = new ArrayList<>();
            Query query = session.createQuery(hql);
            query.setParameter(HibernateDAOConstants.JOURNAL_ID, journalId);
            for (Object o : query.list()) {
                tasks.add((Task)o);
            }
            for (Task task : tasks) {
                TaskDTO taskDTO = TaskDTOFactory.createTaskDTO(task.getId(), task.getName(), task.getDescription(),
                        task.getStatus(), task.getPlannedDate(), task.getDateOfDone());
                taskDTOS.add(taskDTO);
            }
            tx1.commit();
            session.close();
        }
        catch (HibernateException e){
            throw new GetAllTaskException(DAOErrorConstants.GET_ALL_TASK_EXCEPTION_MESSAGE + e.getMessage());
        }
        return taskDTOS;
    }

    @Override
    public List<TaskDTO> getSortedByCriteria(int journalId, String column, String criteria) throws GetSortedByCriteriaTaskException {
        List<TaskDTO> taskDTOS = new ArrayList<>();
        try {
            Session session = sessionFactory.openSession();
            String hql = "From Task where journal_id = :journal_id " +
                    "ORDER BY %s %s";
            hql = String.format(hql, column, criteria);
            Transaction tx1 = session.beginTransaction();
            List<Task> tasks = new ArrayList<>();
            Query query = session.createQuery(hql);
            query.setParameter(HibernateDAOConstants.JOURNAL_ID, journalId);
            for (Object o : query.list()) {
                tasks.add((Task)o);
            }
            for (Task task : tasks) {
                TaskDTO taskDTO = TaskDTOFactory.createTaskDTO(task.getId(), task.getName(), task.getDescription(),
                        task.getStatus(), task.getPlannedDate(), task.getDateOfDone());
                taskDTOS.add(taskDTO);
            }
            tx1.commit();
            session.close();
        }
        catch (HibernateException e){
            throw new GetSortedByCriteriaTaskException(DAOErrorConstants.GET_SORTED_BY_CRITERIA_TASK_EXCEPTION_MESSAGE
                    + e.getMessage());
        }
        return taskDTOS;
    }

    @Override
    public List<TaskDTO> getFilteredByPattern(int journalId, String column, String pattern, String criteria) throws GetFilteredByPatternTaskException {
        List<TaskDTO> taskDTOS = new ArrayList<>();
        try {
            Session session = sessionFactory.openSession();
            String hql = "From Task where journal_id = :journal_id and (%s LIKE :pattern) " +
                    "ORDER BY %s %s";
            hql = String.format(hql, column, column, criteria);
            Transaction tx1 = session.beginTransaction();
            List<Task> tasks = new ArrayList<>();
            Query query = session.createQuery(hql);
            query.setParameter(HibernateDAOConstants.JOURNAL_ID, journalId);
            query.setParameter(HibernateDAOConstants.PATTERN, pattern);
            for (Object o : query.list()) {
                tasks.add((Task)o);
            }
            for (Task task : tasks) {
                TaskDTO taskDTO = TaskDTOFactory.createTaskDTO(task.getId(), task.getName(), task.getDescription(),
                        task.getStatus(), task.getPlannedDate(), task.getDateOfDone());
                taskDTOS.add(taskDTO);
            }
            tx1.commit();
            session.close();
        }
        catch (HibernateException e){
            throw new GetFilteredByPatternTaskException(DAOErrorConstants.GET_FILTERED_BY_PATTERN_TASK_EXCEPTION_MESSAGE
                    + e.getMessage());
        }
        return taskDTOS;
    }

    @Override
    public List<TaskDTO> getFilteredByEquals(int journalId, String column, String equal, String criteria) throws GetFilteredByEqualsTaskException {
        List<TaskDTO> taskDTOS = new ArrayList<>();
        try {
            Session session = sessionFactory.openSession();
            String hql = "From Task where journal_id = :journal_id and (%s = :equal) " +
                    "ORDER BY %s %s";
            hql = String.format(hql, column, column, criteria);
            Transaction tx1 = session.beginTransaction();
            List<Task> tasks = new ArrayList<>();
            Query query = session.createQuery(hql);
            query.setParameter(HibernateDAOConstants.JOURNAL_ID, journalId);
            query.setParameter(HibernateDAOConstants.EQUAL, equal);
            for (Object o : query.list()) {
                tasks.add((Task)o);
            }
            for (Task task : tasks) {
                TaskDTO taskDTO = TaskDTOFactory.createTaskDTO(task.getId(), task.getName(), task.getDescription(),
                        task.getStatus(), task.getPlannedDate(), task.getDateOfDone());
                taskDTOS.add(taskDTO);
            }
            tx1.commit();
            session.close();
        }
        catch (HibernateException e){
            throw new GetFilteredByEqualsTaskException(DAOErrorConstants.GET_FILTERED_BY_EQUALS_TASK_EXCEPTION_MESSAGE
                    + e.getMessage());
        }
        return taskDTOS;
    }
}
