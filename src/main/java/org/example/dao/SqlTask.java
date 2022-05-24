package org.example.dao;

import org.example.interfaces.InterfaceTask;
import org.example.models.Task;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

import static java.lang.System.out;

public class SqlTask implements InterfaceTask {

    private final Sql2o sql2o;

    public SqlTask (Sql2o sql2o){
        this.sql2o = sql2o;
    }

    //CREATE
    @Override
    public void add (Task task) {
        String sql = "INSERT INTO tasks (userid, name, description, createdon, completed) VALUES (:userId, :name, :description, now(), :completed)";
        try (Connection con = sql2o.open()){
            int id = (int) con.createQuery(sql, true)
                    .bind(task)
                    .executeUpdate()
                    .getKey();
            task.setId(id);
        } catch (Sql2oException ex) {
            out.println(ex);
        }
    }


    //READ
    @Override
    public Task findById(int id) {
        try(Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM tasks WHERE id = :id")
                    .addParameter("id", id)
                    .throwOnMappingFailure(false)
                    .executeAndFetchFirst(Task.class);
        }
    }

    @Override
    public List<Task> getAll(int userId) {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM tasks WHERE userid = :userId;")
                    .addParameter("userId", userId)
                    .executeAndFetch(Task.class);
        }
    }


    //UPDATE
    @Override
    public void update(int id, String name, String description) {
        String sql = "UPDATE tasks SET (name, description) = (:name, :description) WHERE id=:id";
        try(Connection con = sql2o.open()){
            con.createQuery(sql)
                    .addParameter("name", name)
                    .addParameter("description", description)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            out.println(ex);
        }
    }


    //DELETE
    @Override
    public void deleteById(int id) {
        try (Connection con = sql2o.open()) {
            con.createQuery("DELETE FROM tasks WHERE id=:id")
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex){
            out.println(ex);
        }
    }

    @Override
    public void clearAllTasks() {
        try (Connection con = sql2o.open()) {
            con.createQuery("DELETE FROM tasks").executeUpdate();
        } catch (Sql2oException ex) {
            out.println(ex);
        }
    }
}
