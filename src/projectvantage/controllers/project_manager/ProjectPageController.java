/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.controllers.project_manager;

import projectvantage.utility.LogConfig;
import projectvantage.utility.SessionConfig;
import projectvantage.models.Project;
import projectvantage.utility.dbConnect;
import projectvantage.utility.ElementConfig;
import projectvantage.utility.AlertConfig;
import projectvantage.utility.PageConfig;
import projectvantage.utility.DatabaseConfig;
import projectvantage.models.User;
import projectvantage.controllers.admin.AdminPageController;

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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import projectvantage.controllers.team_manager.TeamManagerPageController;

/**
 * FXML Controller class
 *
 * @author Mark Work Account
 */
public class ProjectPageController implements Initializable {
    
    private static ProjectPageController instance;
    
    LogConfig logConf = new LogConfig();
    dbConnect db = new dbConnect();
    ElementConfig elementConf = new ElementConfig();
    AlertConfig alertConf = new AlertConfig();
    PageConfig pageConf = new PageConfig();
    DatabaseConfig databaseConf = new DatabaseConfig();
    
    ObservableList<Project> projectList = FXCollections.observableArrayList();
    
    private static final int ROWS_PER_PAGE = 9;
    private static final double ICON_HEIGHT = 26;
    private static final double ICON_WIDTH = 26;
    
//    private String username;
    private String role;
    private int userId;

    @FXML
    private Pagination pagination;
    @FXML
    private TableView<Project> projectTable;
    @FXML
    private TableColumn<Project, Integer> idColumn;
    @FXML
    private TableColumn<Project, String> nameColumn;
    @FXML
    private TableColumn<Project, String> duedateColumn;
    @FXML
    private TableColumn<Project, String> statusColumn;
    @FXML
    private TableColumn<Project, String> actionColumn;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private Button addProjectButton;
    @FXML
    private Button viewProjectButton;
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
        duedateColumn.setSortable(false);
        statusColumn.setSortable(false);
        actionColumn.setSortable(false);
        
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        duedateColumn.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        
        Callback<TableColumn<Project, String>, TableCell<Project, String>> cellFactory = (TableColumn<Project, String> param) -> {
            final TableCell<Project, String> cell = new TableCell<Project, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    
                    if(empty) {
                        setGraphic(null);
                        setText(null);
                        return;
                    }
                    
                    Stage currentStage = (Stage)rootPane.getScene().getWindow();
                    
                    ImageView deleteButton = new ImageView("/projectvantage/resources/icons/delete-icon.png");
                    
                    deleteButton.setFitHeight(ICON_HEIGHT);
                    deleteButton.setFitWidth(ICON_WIDTH);
                    deleteButton.setCursor(Cursor.HAND);
                    
                    deleteButton.setOnMouseEntered(event -> {
                        elementConf.hoverIcon(deleteButton);
                    });
                    
                    deleteButton.setOnMouseExited(event -> {
                        elementConf.unhoverIcon(deleteButton);
                    });
                    
                    
                    deleteButton.setOnMouseClicked(event -> {
                        Project selectedRow = projectTable.getSelectionModel().getSelectedItem();
                        
                        int id = selectedRow.getId();
                        String name = selectedRow.getName();
                        
                        String sql = "DELETE FROM project WHERE id = ?";
                        
                        alertConf.showDeleteConfirmationAlert(currentStage, sql, id);
                        logConf.logDeleteProject(userId, name);
                        refreshTable();
                    });
                    
                    ImageView editButton = new ImageView("/projectvantage/resources/icons/edit-icon.png");
                    
                    editButton.setFitHeight(ICON_HEIGHT);
                    editButton.setFitWidth(ICON_WIDTH);
                    editButton.setCursor(Cursor.HAND);
                    
                    editButton.setOnMouseEntered(event -> {
                        elementConf.hoverIcon(editButton);
                    });
                    
                    editButton.setOnMouseExited(event -> {
                        elementConf.unhoverIcon(editButton);
                    });
                    
                    editButton.setOnMouseClicked(event -> {
                        String editProjectFXML = "/projectvantage/fxml/project_manager/EditProjectPage.fxml";
                        
                        Project selectedRow = projectTable.getSelectionModel().getSelectedItem();
                        int id = selectedRow.getId();
                        
                        try {
                            pageConf.loadWindow(editProjectFXML, "Edit Project", rootPane);
                            EditProjectPageController.getInstance().loadProjectContent(id);
                        } catch (Exception e) {
                            e.printStackTrace();
                            alertConf.showWindowLoadErrorAlert(currentStage, "Failed to load edit project window.");
                        }
                    });
                    
                    HBox actionButtons = new HBox(editButton, deleteButton);
                    HBox.setMargin(deleteButton, new Insets(2, 2, 0, 3));
                    HBox.setMargin(editButton, new Insets(2, 3, 0, 2));
                    setGraphic(actionButtons);
                    setText(null);
                }
            };
            return cell;
        };
        actionColumn.setCellFactory(cellFactory);
        
        Platform.runLater(() -> {
        
            SessionConfig sessionConf = SessionConfig.getInstance();

            role = sessionConf.getRole();
            userId = sessionConf.getId();
            
            loadTableData();
        });
    }
    
    public static ProjectPageController getInstance() {
        return instance;
    }
    
    public void refreshTable() {
        projectList.clear();
        loadTableData();
    }
    
    private Node createPage(int pageIndex) {
        int fromIndex = pageIndex * ROWS_PER_PAGE;
        int toIndex = Math.min(fromIndex + ROWS_PER_PAGE, projectList.size());
        projectTable.setItems(FXCollections.observableArrayList(projectList.subList(fromIndex, toIndex)));
        return projectTable;
    }
    
    private String formatDate(Date date) {
        return date.toLocalDate().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
    }
    
    private void loadTableData() {
        
        String sql = "SELECT project.id, project.name, description, creation_date, due_date, user.last_name AS creator_name, project_status.name AS status "
                + "FROM project INNER JOIN project_status ON project.status_id = project_status.id INNER JOIN user ON project.user_id = user.id WHERE user_id = " + userId + " ORDER BY project.id DESC";
        
        if(role.equals("admin")) {
            sql = "SELECT project.id, project.name, description, creation_date, due_date, user.last_name AS creator_name, project_status.name AS status "
                + "FROM project INNER JOIN project_status ON project.status_id = project_status.id INNER JOIN user ON project.user_id = user.id ORDER BY project.id DESC";
        }
        
        try(ResultSet result = db.getData(sql)) {
            while(result.next()) {
                projectList.add(new Project(
                        result.getInt("id"),
                        result.getString("name"),
                        result.getString("description"),
                        formatDate(result.getDate("creation_date")),
                        formatDate(result.getDate("due_date")),
                        result.getString("creator_name"),
                        result.getString("status")
                ));
            }   
            
            int pageCount = (int) Math.ceil((double) projectList.size() / ROWS_PER_PAGE);
            pagination.setPageCount(pageCount);
            pagination.setPageFactory(this::createPage);
            
        } catch (Exception e) {
            System.out.println("Database Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @FXML
    private void userTableMouseClickHandler(MouseEvent event) {
    }

    @FXML
    private void addProjectButtonMouseClickHandler(MouseEvent event) throws Exception {
        String AddProjectFXML = "/projectvantage/fxml/project_manager/AddProjectPage.fxml";
        pageConf.loadWindow(AddProjectFXML, "Add Project", rootPane);
        
    }

    @FXML
    private void viewProjectButtonMouseClickHandler(MouseEvent event) {
        Stage currentStage = (Stage)rootPane.getScene().getWindow();
        AdminPageController adminController = AdminPageController.getInstance();
        ProjectManagerPageController projectManagerController = ProjectManagerPageController.getInstance();
        
        Project selectedProject = projectTable.getSelectionModel().getSelectedItem();
        String viewProjectFXML = "/projectvantage/fxml/project_manager/ViewProjectPage.fxml";
        
        if(selectedProject == null) {
            alertConf.showAlert(Alert.AlertType.ERROR, "Error Opening a Project", "You must select a project", currentStage);
            return;
        }
        
        try {
            if(role.equals("admin")) {
                adminController.loadPage(viewProjectFXML, selectedProject.getName());
                ViewProjectPageController.getInstance().loadContent(selectedProject.getId());
                return;
            }
            
            projectManagerController.loadPage(viewProjectFXML, selectedProject.getName());
            ViewProjectPageController.getInstance().loadContent(selectedProject.getId());
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
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