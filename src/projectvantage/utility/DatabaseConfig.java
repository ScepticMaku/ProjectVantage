/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.utility;

import projectvantage.models.Project;
import projectvantage.models.User;
import projectvantage.models.Team;
import projectvantage.models.TeamMember;
import projectvantage.models.Task;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Mark Work Account
 */
public class DatabaseConfig {
    dbConnect db = new dbConnect();
    
    public User getUserByUsername(String username) {
        String query = "SELECT user.id, first_name, middle_name, last_name, email, phone_number, username, salt, password, secret_key, user_role.name AS role, user_status.name AS status "
                + "FROM user INNER JOIN user_role ON user.role_id = user_role.id INNER JOIN user_status ON user.status_id = user_status.id WHERE username = '" + username + "'";
        
        try(ResultSet result = db.getData(query)) {
            if(result.next()) {
                return new User(
                        result.getInt("user.id"),
                        result.getString("first_name"),
                        result.getString("middle_name"),
                        result.getString("last_name"),
                        result.getString("email"),
                        result.getString("phone_number"),
                        result.getString("username"),
                        result.getString("salt"),
                        result.getString("password"),
                        result.getString("secret_key"),
                        result.getString("role"),
                        result.getString("status")
                );
            }
        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    
    public User getUserByEmail(String email) {
        String query = "SELECT user.id, first_name, middle_name, last_name, email, phone_number, username, salt, password, secret_key, user_role.name AS role, user_status.name AS status "
                + "FROM user INNER JOIN user_role ON user.role_id = user_role.id INNER JOIN user_status ON user.status_id = user_status.id WHERE email = '" + email + "'";
        
        try(ResultSet result = db.getData(query)) {
            if(result.next()) {
                return new User(
                        result.getInt("user.id"),
                        result.getString("first_name"),
                        result.getString("middle_name"),
                        result.getString("last_name"),
                        result.getString("email"),
                        result.getString("phone_number"),
                        result.getString("username"),
                        result.getString("salt"),
                        result.getString("password"),
                        result.getString("secret_key"),
                        result.getString("role"),
                        result.getString("status")
                );
            }
        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    
    public User getUserById(int id) {
        String query = "SELECT user.id, first_name, middle_name, last_name, email, phone_number, username, salt, password, secret_key, user_role.name AS role, user_status.name AS status "
                + "FROM user INNER JOIN user_role ON user.role_id = user_role.id INNER JOIN user_status ON user.status_id = user_status.id WHERE user.id = " + id;
        
        try(ResultSet result = db.getData(query)) {
            if(result.next()) {
                return new User(
                        result.getInt("user.id"),
                        result.getString("first_name"),
                        result.getString("middle_name"),
                        result.getString("last_name"),
                        result.getString("email"),
                        result.getString("phone_number"),
                        result.getString("username"),
                        result.getString("salt"),
                        result.getString("password"),
                        result.getString("secret_key"),
                        result.getString("role"),
                        result.getString("status")
                );
            }
        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    
    public int getUserRoleIdByUsername(String username) {
        String query = "SELECT role_id FROM user WHERE username = '" + username + "'";
        
        try(ResultSet result = db.getData(query)) {
            if(result.next()) {
                return result.getInt("role_id");
            }
        } catch (Exception e) {
            System.out.println("Database Error: " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }
    
    public String getImagePath(String username) {
        String query = "SELECT user.username AS username, image_path FROM user_image INNER JOIN user ON user_image.user_id = user.id WHERE username = '" + username + "'";
        
        try(ResultSet result = db.getData(query)) {
            if(result.next()) {
                return result.getString("image_path");
            }
        } catch (Exception e) {
            System.out.println("Database Error: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    
    private String formatDate(Date date) {
        return date.toLocalDate().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
    }
    
    public Project getProjectById(int id) {
        String sql = "SELECT project.id, project.name, description, creation_date, due_date, user.last_name AS creator_name, project_status.name AS status "
                + "FROM project INNER JOIN project_status ON project.status_id = project_status.id INNER JOIN user ON project.user_id = user.id WHERE project.id = " + id;
        
        try(ResultSet result = db.getData(sql)) {
            if(result.next()) {
                return new Project(
                        result.getInt("id"),
                        result.getString("name"),
                        result.getString("description"),
                        formatDate(result.getDate("creation_date")),
                        formatDate(result.getDate("due_date")),
                        result.getString("creator_name"),
                        result.getString("status")
               ); 
            } 
        } catch (Exception e) {
            System.out.println("Database Error: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    
    public int getStatusIdById(int id) {
        String sql = "SELECT status_id FROM project WHERE id = " + id;
        
        try(ResultSet result = db.getData(sql)) {
            if(result.next()) {
                return result.getInt("status_id");
            }
        } catch (Exception e) {
            System.out.println("Database Error: " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }
    
    public Team getTeamById(int id) {
        
        String sql = "SELECT id, name, project_id FROM team WHERE id = " + id;
        
        try(ResultSet result = db.getData(sql)) {
            if(result.next()) {
                return new Team(
                        result.getInt("id"),
                        result.getString("name"),
                        result.getInt("project_id")
                );
            }
        } catch (Exception e) {
            System.out.println("Database Error: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
    
    public TeamMember getTeamMemberByUserId(int id) {
        String sql = "SELECT team_member.id AS id, team_id, user.last_name AS last_name, user.username AS username, team_member_role.name AS role, team_member_status.name AS status "
                + "FROM team_member INNER JOIN user ON user_id = user.id INNER JOIN team_member_role ON team_member.role_id = team_member_role.id "
                + "INNER JOIN team_member_status ON team_member.status_id = team_member_status.id WHERE team_member.user_id = " + id;
        
        try (ResultSet result = db.getData(sql)){
            if(result.next()) {
                return new TeamMember(
                        result.getInt("id"),
                        result.getInt("team_id"),
                        result.getString("last_name"),
                        result.getString("username"),
                        result.getString("role"),
                        result.getString("status")
                );
            }
        } catch(Exception e) {
            System.out.println("Database Error: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    
    public TeamMember getTeamMemberById(int id) {
        String sql = "SELECT team_member.id AS id, team_id, user.last_name AS last_name, user.username AS username, team_member_role.name AS role, team_member_status.name AS status "
                + "FROM team_member INNER JOIN user ON user_id = user.id INNER JOIN team_member_role ON team_member.role_id = team_member_role.id "
                + "INNER JOIN team_member_status ON team_member.status_id = team_member_status.id WHERE team_member.id = " + id;
        
        try (ResultSet result = db.getData(sql)){
            if(result.next()) {
                return new TeamMember(
                        result.getInt("id"),
                        result.getInt("team_id"),
                        result.getString("last_name"),
                        result.getString("username"),
                        result.getString("role"),
                        result.getString("status")
                );
            }
        } catch(Exception e) {
            System.out.println("Database Error: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    
    public TeamMember getTeamMembersByTeamId(int id) {
        String sql = "SELECT team_member.id AS id, team_id, user.last_name AS last_name, user.username AS username, team_member_role.name AS role, team_member_status.name AS status "
                + "FROM team_member INNER JOIN user ON user_id = user.id INNER JOIN team_member_role ON team_member.role_id = team_member_role.id "
                + "INNER JOIN team_member_status ON team_member.status_id = team_member_status.id WHERE team_id = " + id;
        
        try (ResultSet result = db.getData(sql)){
            return new TeamMember(
                    result.getInt("id"),
                    result.getInt("team_id"),
                    result.getString("last_name"),
                    result.getString("username"),
                    result.getString("role"),
                    result.getString("status")
            );
        } catch(Exception e) {
            System.out.println("Database Error: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    
    public int getUserIdById(int id) {
        String sql = "SELECT user_id FROM team_member WHERE id = " + id;
        
        try(ResultSet result = db.getData(sql)) {
            if(result.next()) {
                return result.getInt("user_id");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Database Error: " + e.getMessage());
        }
        
        return -1;
    }
    
    public String getTeamLeaderByTeamId(int id) {
        String sql = "SELECT team_member.id, team_id, user.username AS username FROM team_member INNER JOIN user ON user_id = user.id WHERE team_id = " + id + " AND team_member.role_id = 2";
        
        try (ResultSet result = db.getData(sql)){
            if(result.next()) {
                return result.getString("username");
            }
        } catch (Exception e) {
            System.out.println("Database Error: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    
    public Task getTaskById(int id) {
        String sql = "SELECT task.id, task.name, task.description, date_created, due_date, user.last_name, team_member_id, project_id, task_status.name AS status "
                + "FROM task INNER JOIN user ON user_id = user.id INNER JOIN task_status ON task.status_id = task_status.id WHERE task.id = " + id;
        
        try(ResultSet result = db.getData(sql)) {
            if(result.next()) {
                return new Task(
                    result.getInt("id"),
                    result.getString("name"),
                    result.getString("description"),
                    result.getString("date_created"),
                    result.getString("due_date"),
                    result.getString("last_name"),
                    result.getInt("team_member_id"),
                    result.getInt("project_id"),
                    result.getString("status")
                );
            }
        } catch (Exception e) {
            System.out.println("Database Error: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
    
    public Task getTaskByTeamMemberId(int teamMemberId) {
        String sql = "SELECT task.id, task.name, task.description, date_created, due_date, user.last_name, team_member_id, project_id, task_status.name AS status "
                + "FROM task INNER JOIN user ON user_id = user.id INNER JOIN task_status ON task.status_id = task_status.id WHERE team_member_id = " + teamMemberId;
        
        try(ResultSet result = db.getData(sql)) {
            if(result.next()) {
                return new Task(
                    result.getInt("id"),
                    result.getString("name"),
                    result.getString("description"),
                    result.getString("date_created"),
                    result.getString("due_date"),
                    result.getString("last_name"),
                    result.getInt("team_member_id"),
                    result.getInt("project_id"),
                    result.getString("status")
                );
            }
        } catch (Exception e) {
            System.out.println("Database Error: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
    
    public int getCompletedTasks(int projectId) {
        String sql = "SELECT COUNT(*) AS total FROM task WHERE status_id = 2 AND project_id = " + projectId;
        
        try (ResultSet result = db.getData(sql)) {
            if(result.next()) {
                return result.getInt("total");
            }
        } catch (Exception e) {
            System.out.println("Database Error: " + e.getMessage());
            e.printStackTrace();
        }
        return -1;
    }
    
    public int getTotalTasks(int projectId) {
        String sql = "SELECT COUNT(*) AS total FROM task WHERE project_id = " + projectId;
        
        try(ResultSet result = db.getData(sql)) {
            if(result.next()) {
                return result.getInt("total");
            }
        } catch (Exception e) {
            System.out.println("Database Error: " + e.getMessage());
            e.printStackTrace();
        }
        return -1;
    }
}

