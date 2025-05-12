/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.controllers.task_manager;

import projectvantage.models.Project;
import projectvantage.utility.SessionConfig;
import projectvantage.utility.LogConfig;
import projectvantage.utility.dbConnect;
import projectvantage.utility.AlertConfig;
import projectvantage.utility.DatabaseConfig;
import projectvantage.utility.ElementConfig;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import projectvantage.controllers.project_manager.ProjectPageController;
import projectvantage.controllers.project_manager.ViewProjectPageController;
import projectvantage.controllers.team_manager.TeamPageController;

/**
 * FXML Controller class
 *
 * @author Markj
 */
public class AddTaskPageController implements Initializable {
    
    private static AddTaskPageController instance;
    
    DatabaseConfig databaseConf = new DatabaseConfig();
    AlertConfig alertConf = new AlertConfig();
    ElementConfig elementConf = new ElementConfig();
    dbConnect db = new dbConnect();
    LogConfig logConf = new LogConfig();
    
    private int projectId;
    private String name;
    private String description;
    private String creationDate;
    private String dueDate;
    private String projectName;

    @FXML
    private AnchorPane rootPane;
    @FXML
    private TextField taskNameField;
    @FXML
    private TextArea descriptionTextArea;
    @FXML
    private Button addButton;
    @FXML
    private DatePicker datePicker;
    @FXML
    private ImageView datePickerButton;
    @FXML
    private TextField dueDateField;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        instance = this;
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        
        StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate date) {
                if(date != null) {
                        return formatter.format(date);
                } else {
                    return "";
                }
            }
            
            @Override
            public LocalDate fromString(String string) {
                if(string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, formatter);
                } else {
                    return null;
                }
            }
        };
        
        datePicker.setConverter(converter);
        
        datePicker.getEditor().textProperty().bindBidirectional(dueDateField.textProperty());
    }    
    
    public static AddTaskPageController getInstance() {
        return instance;
    }
    
    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }
    
    private String getCurrentDate() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        return currentDate.format(formatter);
    }
    
    private Date formatDate(String date) {
        LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        return Date.valueOf(localDate);
    }

    @FXML
    private void addButtonMouseClickHandler(MouseEvent event) {
        Stage currentStage = (Stage)rootPane.getScene().getWindow();
        SessionConfig sessionConf = SessionConfig.getInstance();
        Project project = databaseConf.getProjectById(projectId);
        
        projectName = project.getName();
        name = taskNameField.getText();
        description = descriptionTextArea.getText();
        creationDate = getCurrentDate();
        dueDate = datePicker.getEditor().getText();
        
        
        Date formattedDueDate = formatDate(dueDate);
        LocalDate selectedDate = datePicker.getValue();
        LocalDate today = LocalDate.now();
        
        if(name.isEmpty()) {
            alertConf.showAddTaskErrorAlert(currentStage, "You must enter a task name.");
            return;
        }
        
        if(description.isEmpty()) {
            alertConf.showAddTaskErrorAlert(currentStage, "You add a description.");
            return;
        }
        
        if(dueDate.isEmpty()) {
            alertConf.showAddTaskErrorAlert(currentStage, "You must set a due date.");
            return;
        }
        
        if(selectedDate.isBefore(today)) {
            alertConf.showAddTaskErrorAlert(currentStage, "Due date must not be before the current date.");
            return;
        }
        
        String sql = "INSERT INTO task (name, description, date_created, due_date, user_id, project_id) VALUES (?, ?, ?, ?, ?, ?)";
        
        if(db.executeQuery(sql, name, description, creationDate, formattedDueDate, sessionConf.getId(), projectId)) {
            System.out.println("Task added to database!");
            logConf.logAddTask(sessionConf.getId(), projectId, name, projectName);
            alertConf.showAlert(Alert.AlertType.INFORMATION, "Task successfully added!", "Add successful", currentStage);
            currentStage.close();
            ViewProjectPageController.getInstance().load();
        }
    }

    @FXML
    private void datePickerButtonMouseExitHandler(MouseEvent event) {
        elementConf.unhoverIcon(datePickerButton);
        
    }

    @FXML
    private void datePickerButtonMouseEnterHandler(MouseEvent event) {
        elementConf.hoverIcon(datePickerButton);
    }

    @FXML
    private void datePickerButtonMouseClickHandler(MouseEvent event) {
        datePicker.show();
    }
    
}
