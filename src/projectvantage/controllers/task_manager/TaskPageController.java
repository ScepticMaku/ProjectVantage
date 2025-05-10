/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.controllers.task_manager;

import projectvantage.models.Task;
import projectvantage.utility.PageConfig;
import projectvantage.utility.AlertConfig;
import projectvantage.utility.dbConnect;
import projectvantage.utility.DatabaseConfig;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Markj
 */
public class TaskPageController implements Initializable {

    private static TaskPageController instance;
    
    dbConnect db = new dbConnect();
    PageConfig pageConf = new PageConfig();
    AlertConfig alertConf = new AlertConfig();
    DatabaseConfig databaseConf = new DatabaseConfig();
    
    ObservableList<Task> taskList = FXCollections.observableArrayList();
    
    private static final int ROWS_PER_PAGE = 9;
    
    @FXML
    private Pagination pagination;
    @FXML
    private TableColumn<Task, Integer> idColumn;
    @FXML
    private TableColumn<Task, String> nameColumn;
    @FXML
    private TableColumn<Task, String> statusColumn;
    @FXML
    private Button viewTaskButton;
    @FXML
    private TableView<Task> taskTable;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private ImageView searchButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        instance = this;
        
        idColumn.setSortable(false);
        nameColumn.setSortable(false);
        statusColumn.setSortable(false);
        
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        
        Platform.runLater(() -> {
            loadTaskTable();
        });
        
    }    
    
    public static TaskPageController getInstance() {
        return instance;
    }
    
    public void refreshTaskTable() {
        taskList.clear();
        loadTaskTable();
    }
    
    private Node createPage(int pageIndex) {
        int fromIndex = pageIndex * ROWS_PER_PAGE;
        int toIndex = Math.min(fromIndex + ROWS_PER_PAGE, taskList.size());
        taskTable.setItems(FXCollections.observableArrayList(taskList.subList(fromIndex, toIndex)));
        return taskTable;
    }
    
    public void loadTaskTable() {
        
        String sql = "SELECT task.id, task.name, task.description, date_created, due_date, user.last_name, team_member_id, project_id, task_status.name AS status "
                + "FROM task INNER JOIN user ON user_id = user.id INNER JOIN task_status ON task.status_id = task_status.id ORDER BY task.id DESC";
        
        try(ResultSet result = db.getData(sql)) {
            while(result.next()) {
                taskList.add(new Task(
                        result.getInt("id"),
                        result.getString("name"),
                        result.getString("description"),
                        result.getString("date_created"),
                        result.getString("due_date"),
                        result.getString("last_name"),
                        result.getInt("team_member_id"),
                        result.getInt("project_id"),
                        result.getString("status")
                ));
            }
            
            int pageCount = (int) Math.ceil((double) taskList.size() / ROWS_PER_PAGE);
            pagination.setPageCount(pageCount);
            pagination.setPageFactory(this::createPage);
            
        } catch (Exception e) {
            System.out.println("Database Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void viewTaskButtonMouseClickHandler(MouseEvent event) throws Exception {
        Stage currentStage = (Stage)rootPane.getScene().getWindow();
        Task task = taskTable.getSelectionModel().getSelectedItem();
        
        if(task == null) {
            alertConf.showAlert(Alert.AlertType.ERROR, "Error opening a Task", "You must select a task", currentStage);
            return;
        }
        
        int taskId = task.getId();
        
        pageConf.loadWindow("/projectvantage/fxml/task_manager/ViewTaskPage.fxml", "View Task", rootPane);
        ViewTaskPageController.getInstance().loadContent(taskId);
    }

    private void addTaskButtonMouseClickHandler(MouseEvent event) throws Exception {
        pageConf.loadWindow("/projectvantage/fxml/task_manager/AddTaskPage.fxml", "Add Task", rootPane);
        
    }

    @FXML
    private void taskTableMouseClickHandler(MouseEvent event) {
    }

    @FXML
    private void searchButtonMouseReleaseHandler(MouseEvent event) {
    }

    @FXML
    private void searchButtonMouseClickHandler(MouseEvent event) {
    }

    @FXML
    private void searchButtonMousePressHandler(MouseEvent event) {
    }
    
}
