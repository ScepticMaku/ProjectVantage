/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.utility;

import projectvantage.models.TeamMember;

/**
 *
 * @author SCC8
 */
public class LogConfig {
    dbConnect db = new dbConnect();
    DatabaseConfig databaseConf = new DatabaseConfig();
    
    private String action;
    private String description;
    
    private void insertLog(int userId, String act, String desc) {
        String sql = "INSERT INTO system_log (user_id, action, description) VALUES (?, ?, ?)";
        
        db.executeQuery(sql, userId, act, desc);
    }
    
    private void insertProjectLog(int userId, int projectId, String action, String description) {
        String sql = "INSERT INTO project_log (user_id, project_id, action, description) VALUES (?, ?, ?, ?)";
        db.executeQuery(sql, userId, projectId, action, description);
    } 
    
    private void insertTeamMemberLog(int userId, int teamMemberId, String action, String description) {
        String sql = "INSERT INTO team_member_log (user_id, team_member_id, action, description) VALUES (?, ?, ? , ?)";
        db.executeQuery(sql, userId, teamMemberId, action, description);
    }
    
    private void insertTeamLog(int userId, int teamId, String action, String description) {
        String sql = "INSERT INTO team_log (user_id, team_id, action, description) VALUES (?, ?, ?, ?)";
        db.executeQuery(sql, userId, teamId, action, description);
    }
    
    public void loginLog(boolean isUserLoggedIn, int userId, String description) {
        action = "Login Success";
        
        if(!isUserLoggedIn) {
            action = "Login Failed";
        }
        
        insertLog(userId, action, description);
    }
    
    public void registerLog(int userId) {
        action = "Register";
        description = "User successully registered.";
        
        insertLog(userId, action, description);
    }
    
    public void logLogout(int userId) {
        action = "Logout";
        description = "User logged out";
        
        insertLog(userId, action, description);
    }
    
    public void logResetPassword(int userId) {
        action = "Password Changed/Reset";
        description = "User changed password";
        
        insertLog(userId, action, description);
    }
    
    public void logEditProfile(int userId, String description) {
        action = "Profile Edit";
        
        insertLog(userId, action, description);
    }
    
    public void logEditUserProfilePicture(int userId, String username) {
        action = "Edit User Profile Picture";
        description = "User: " + username + " changed profile picture";
        insertLog(userId, action, description);
    }
    
    public void logAssignMember(int userId, int projectId, int teamId, String teamMemberName, String taskName, String projectName) {
        action = "Assign Task";
        description = "Team member: " + teamMemberName + " assigned to task: " + taskName + ", from project: " + projectName;
        insertLog(userId, action, description);
        insertProjectLog(userId, projectId, action, description);
        insertTeamLog(userId, teamId, action, description);
    }
    
    public void logRemoveMember(int userId, int projectId, int teamId, String teamMemberName, String taskName, String projectName) {
        action = "Remove Task";
        description = "Team member: " + teamMemberName + " removed from task: " + taskName + ", from project: " + projectName;
        insertLog(userId, action, description);
        insertProjectLog(userId, projectId, action, description);
        insertTeamLog(userId, teamId, action, description);
    }
    
    public void logAddUser(int userId, String addedUserUsername) {
        action = "Add User";
        description = "User added: " + addedUserUsername;
        
        insertLog(userId, action, description);
    }
    
    public void logAddProject(int userId, int projectId, String projectName) {
        action = "Add Project";
        description = "Project added: " + projectName;
        insertLog(userId, action, description);
    }
    
    public void logAddTeam(int userId, int teamId, String teamName) {
        action = "Add Team";
        description = "Team added: " + teamName;
        insertLog(userId, action, description);
    }
    
    public void logAddTask(int userId, int projectId, String taskName, String projectName) {
        action = "Add Task";
        description = "Task added: " + taskName + ", to project: " + projectName;
        insertLog(userId, action, description);
        insertProjectLog(userId, projectId, action, description);
    }
    
    public void logAssignTeam(int userId, int projectId, String teamName, String projectName) {
        action = "Assign Team";
        description = "Team assigned: " + teamName + ", to project: " + projectName;
        insertLog(userId, action, description);
        insertProjectLog(userId, projectId, action, description);
    }
    
    public void logEditProject(int userId, int projectId, String description) {
        action = "Edit Project";
        insertLog(userId, action, description);
        insertProjectLog(userId, projectId, action, description);
    }
    
    public void logEditTask(int userId, int projectId, String description) {
        action = "Edit Task";
        insertLog(userId, action, description);
        insertProjectLog(userId, projectId, action, description);
    }
    
    public void logEditTeam(int userId, int teamId, String teamName) {
        action = "Edit Team";
        description = "User updated team information, changed: " + teamName;
        insertLog(userId, action, description);
        insertTeamLog(userId, teamId, action, description);
    }
    
    public void logEditUser(int userId, String description) {
        action = "Edit User";
        insertLog(userId, action, description);
    }
    
    public void logDeleteProject(int userId, String projectName) {
        action = "Delete Project";
        description = "Project deleted: " + projectName;
        insertLog(userId, action, description);
    }
    
    public void logDeleteTask(int userId, int projectId, String taskName) {
        action = "Delete Task";
        description = "Task deleted: " + taskName;
        insertLog(userId, action, description);
        insertProjectLog(userId, projectId, action, description);
    }
    
    public void logDeleteTeam(int userId, String teamName) {
        action = "Delete Team";
        description = "Team deleted: " + teamName;
        insertLog(userId, action, description);
    }
    
    public void logDeleteUser(int userId, String username) {
        action = "Delete Username";
        description = "User deleted: " + username;
        insertLog(userId, action, description);
    }
    
    public void logRemoveTeam(int userId, int projectId, String teamName, String projectName) {
        action = "Removed Team";
        description = "Team removed: " + teamName + ", from project: " + projectName;
        insertLog(userId, action, description);
        insertProjectLog(userId, projectId, action, description);
    }
    
    public void logGeneratePDF(int userId, int projectId, String projectName) {
        action = "Generate PDF";
        description = "Project generated pdf: " + projectName;
        insertLog(userId, action, description);
        insertProjectLog(userId, projectId, action, description);
    }
    
    public void logPrintReport(int userId, int projectId, String projectName) {
        action = "Print Report";
        description = "Project report printed: " + projectName;
        insertLog(userId, action, description);
        insertProjectLog(userId, projectId, action, description);
    }
    
    public void logCompleteTask(int userId, int projectId, String taskName, String projectName) {
        TeamMember member = databaseConf.getTeamMemberByUserId(userId);
        
        action = "Complete Task";
        description = "Task: " + taskName + " completed, from project: " + projectName;
        insertLog(userId, action, description);
        insertProjectLog(userId, projectId, action, description);
        
        if(member != null) {
            int teamMemberId = member.getId();
            int teamId = member.getTeamId();
            
            insertTeamMemberLog(userId, teamMemberId, action, description);
            insertTeamLog(userId, teamId, action, description);
        }
    }
    
    public void logUncompleteTask(int userId, int projectId, String taskName, String projectName) {
        TeamMember member = databaseConf.getTeamMemberByUserId(userId);
        
        action = "Uncomplete Task";
        description = "Task: " + taskName + " uncompleted, from project: " + projectName;
        insertLog(userId, action, description);
        insertProjectLog(userId, projectId, action, description);
        
        
        if(member != null) {
            int teamMemberId = member.getId();
            int teamId = member.getTeamId();
            
            insertTeamMemberLog(userId, teamMemberId, action, description);
            insertTeamLog(userId, teamId, action, description);
        }
    }
    
    public void logMakeLeader(int userId, int projectId, int teamMemberId, int teamId, String teamMemberName, String teamName, String projectName) {
        action = "Assign Leadership";
        description = "Member: " + teamMemberName + " assigned leader in team: " + teamName;
        
        if(projectId != -1) {
            description = "Member: " + teamMemberName + " assigned leader in team: " + teamName + ", in project: " + projectName;
            insertProjectLog(userId, projectId, action, description);
        }
        
        insertLog(userId, action, description);
        insertTeamMemberLog(userId, teamMemberId, action, description);
        insertTeamLog(userId, teamId, action, description);
    }
    
    public void logRemoveLeader(int userId, int projectId, int teamMemberId, int teamId, String teamMemberName, String teamName, String projectName) {
        action = "Revoke Leadership";
        description = "Member: " + teamMemberName + " removed leader in team: " + teamName + ", in project: " + projectName;
        insertLog(userId, action, description);
        insertProjectLog(userId, projectId, action, description);
        insertTeamMemberLog(userId, teamMemberId, action, description);
        insertTeamLog(userId, teamId, action, description);
    }
    
    public void logAddTeamMember(int userId, int teamId, int teamMemberId, String userLastName, String teamName) {
        action = "Add Team Member";
        description = "User: " + userLastName + " added to team: " + teamName;
        insertLog(userId, action, description);
        insertTeamLog(userId, teamId, action, description);
        insertTeamMemberLog(userId, teamMemberId, action, description);
    }
    
    public void logRemoveTeamMember(int userId, int teamId, String teamMemberName, String teamName) {
        action = "Remove Team Member";
        description = "Member: " + teamMemberName + " removed from team: " + teamName;
        insertLog(userId, action, description);
        insertTeamLog(userId, teamId, action, description);
    }
    
    public void logActivateUser(int userId, String username) {
        action = "Activate User";
        description = "User: " + username + " activated";
        insertLog(userId, action, description);
    }
    
    public void logDeactivateUser(int userId, String username) {
        action = "Activate User";
        description = "User: " + username + " deactivated";
        insertLog(userId, action, description);
    }
}
