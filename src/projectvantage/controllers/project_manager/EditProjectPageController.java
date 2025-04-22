/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.controllers.project_manager;

import projectvantage.models.ProjectStatus;
import projectvantage.utility.AlertConfig;
import projectvantage.models.Project;
import projectvantage.utility.DatabaseConfig;
import projectvantage.utility.ElementConfig;
import projectvantage.utility.dbConnect;

import java.time.LocalDate;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
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
public class EditProjectPageController implements Initializable {
    
    private static EditProjectPageController instance;
    
    DatabaseConfig dbConf = new DatabaseConfig();
    ElementConfig elementConf = new ElementConfig();
    dbConnect db = new dbConnect();
    AlertConfig alertConf = new AlertConfig();
    
    ObservableList<ProjectStatus> statusList = FXCollections.observableArrayList();
    
    private int id;
    private String name;
    private String description;
    private String creationDate;
    private String dueDate;
    private String status;
    
    @FXML
    private AnchorPane rootPane;
    @FXML
    private TextField projectNameField;
    @FXML
    private TextArea descriptionTextArea;
    @FXML
    private ComboBox<ProjectStatus> statusComboBox;
    @FXML
    private Button submitButton;
    @FXML
    private ImageView datePickerButton;
    @FXML
    private TextField dateCreatedField;
    @FXML
    private TextField dueDateField;
    @FXML
    private DatePicker datePicker;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        instance = this;
        
        Platform.runLater(() -> {
            projectNameField.setText(name);
            descriptionTextArea.setText(description);
            dateCreatedField.setText(creationDate);
            dueDateField.setText(dueDate);
            statusComboBox.setPromptText(status);
            
            loadStatusData();
        });
        
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
    
    public static EditProjectPageController getInstance() {
        return instance;
    }
    
    private void displayStatus(ComboBox<ProjectStatus> comboBox, ObservableList<ProjectStatus> list) {
        comboBox.setItems(list);

        comboBox.setCellFactory(lv -> new ListCell<ProjectStatus>(){
                @Override
                protected void updateItem(ProjectStatus item, boolean empty) {
                    super.updateItem(item, empty);
                    if(empty || item == null) {
                        setText(null);
                    } else {
                        setText(item.getName());
                    }
                }
            });

        comboBox.setButtonCell(new ListCell<ProjectStatus>() {
            @Override
            protected void updateItem(ProjectStatus item, boolean empty) {
                super.updateItem(item, empty);
                if(empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getName());
                }
            }
        });
    }
    
    private void loadStatusData() {
        String sql = "SELECT id, name FROM project_status";
        
        try(ResultSet result = db.getData(sql)) {
            while(result.next()) {
                statusList.add(new ProjectStatus(
                        result.getInt("id"),
                        result.getString("name")
                ));
            }
            displayStatus(statusComboBox, statusList);
        } catch (Exception e) {
            System.out.println("Database Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void loadProjectContent(int projectId) {
        Project project = dbConf.getProjectById(projectId);
        
        id = project.getId();
        name = project.getName();
        description = project.getDescription();
        creationDate = project.getCreationDate();
        dueDate = project.getDueDate();
        status = project.getStatus();
    }
    
    private Date formatDate(String date) {
        LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        return Date.valueOf(localDate);
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

    @FXML
    private void submitButtonMouseClickHandler(MouseEvent event) {
        Stage currentStage = (Stage)rootPane.getScene().getWindow();
        
        String nameF = projectNameField.getText();
        String descF = descriptionTextArea.getText();
        String dueDateF = dueDateField.getText();
        Date formattedDueDateF = formatDate(dueDateF);
        int pStatus = dbConf.getStatusIdById(id);
        
        if(nameF.isEmpty()) {
            alertConf.showEditProjectErrorAlert(currentStage, "You must add a project name.");
            return;
        }
        
        if(descF.isEmpty()) {
            alertConf.showEditProjectErrorAlert(currentStage, "You must add a description.");
            return;
        }
        
        ProjectStatus projectStatus = statusComboBox.getSelectionModel().getSelectedItem();
        
       if(projectStatus != null) {
           pStatus = projectStatus.getId();
       }
       
       String sql = "UPDATE project SET name = ?, description = ?, due_date = ?, status_id = ? WHERE id = ?";
       
       if(db.executeQuery(sql, nameF, descF, formattedDueDateF, pStatus, id)) {
            System.out.println("Project updated successfully!");
            alertConf.showAlert(Alert.AlertType.INFORMATION, "Project Update Successful", "Project Updated Succesfully!", currentStage);
            ProjectPageController.getInstance().refreshTable();
            currentStage.close();
       }
       
    }
    
}
