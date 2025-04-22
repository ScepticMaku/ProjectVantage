/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.controllers.project_manager;

import projectvantage.models.User;
import projectvantage.utility.ElementConfig;
import projectvantage.utility.AlertConfig;
import projectvantage.utility.dbConnect;
import projectvantage.utility.DatabaseConfig;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.time.LocalDate;
import javafx.application.Platform;
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

/**
 * FXML Controller class
 *
 * @author Mark Work Account
 */
public class AddProjectPageController implements Initializable {
    
    ElementConfig elementConf = new ElementConfig();
    AlertConfig alertConf = new AlertConfig();
    dbConnect db = new dbConnect();
    DatabaseConfig dbConf = new DatabaseConfig();

    @FXML
    private TextField projectNameField;
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
    @FXML
    private AnchorPane rootPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO        
        
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
        ProjectPageController projectController = ProjectPageController.getInstance();
        
        String name = projectNameField.getText();
        String description = descriptionTextArea.getText();
        String creationDate = getCurrentDate();
        String dueDate = datePicker.getEditor().getText();
        String username = projectController.getUsername();
        
        User user = dbConf.getUserByUsername(username);
        Date formattedDueDate = formatDate(dueDate);
        LocalDate selectedDate = datePicker.getValue();
        LocalDate today = LocalDate.now();
        
        if(name.isEmpty()) {
            alertConf.showAddProjectErrorAlert(currentStage, "You must enter a project name.");
            return;
        }
        
        if(description.isEmpty()) {
            alertConf.showAddProjectErrorAlert(currentStage, "You must add a description.");
            return;
        }
        
        if(dueDate.isEmpty()) {
            alertConf.showAddProjectErrorAlert(currentStage, "You must set a due date.");
            return;
        }
        
        if(selectedDate.isBefore(today)) {
            alertConf.showAddProjectErrorAlert(currentStage, "Due Date must not be before the current date.");
            return;
        }
        
        String sql = "INSERT INTO project (name, description, creation_date,  due_date, user_id) VALUES (?, ?, ?, ?, ?)";
        
        if(db.executeQuery(sql, name, description, creationDate, formattedDueDate, user.getId())) {
            System.out.println("Project added to database!");
            alertConf.showAlert(Alert.AlertType.INFORMATION, "Project successfully Added!", "Add Successful", currentStage);
            projectController.refreshTable();
            currentStage.close();
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
