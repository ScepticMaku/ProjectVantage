/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.controllers.admin;

import projectvantage.models.User;
import projectvantage.utility.DatabaseConfig;
import projectvantage.utility.AlertConfig;
import projectvantage.utility.Config;
import projectvantage.utility.PageConfig;
import projectvantage.utility.ElementConfig;
import projectvantage.utility.dbConnect;
import projectvantage.utility.SessionConfig;
import projectvantage.utility.LogConfig;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author PC15
 */
public class UserManagementPageController implements Initializable {
    
    private static UserManagementPageController instance;
    
    private static final int ROWS_PER_PAGE = 9;
    private static final double DELETE_IMAGE_HEIGHT = 26;
    private static final double DELETE_IMAGE_WIDTH = 26;
    
    ObservableList<User> userList = FXCollections.observableArrayList();
    AlertConfig alertConf = new AlertConfig();
    Config config = new Config();
    LogConfig logConf = new LogConfig();
    PageConfig pageConf = new PageConfig();
    ElementConfig elementConf = new ElementConfig();
    DatabaseConfig dbConf = new DatabaseConfig();
    dbConnect db = new dbConnect();
    
    private int sessionUserId;

    @FXML
    private AnchorPane rootPane;
    @FXML
    private Pagination tablePage;
    @FXML
    private TableView<User> userTable;
    @FXML
    private TableColumn<User, Integer> userId;
    @FXML
    private TableColumn<User, String> userFirstName;
    @FXML
    private TableColumn<User, String> userLastName;
    @FXML
    private TableColumn<User, String> userRole;
    @FXML
    private TableColumn<User, String> userStatus;
    @FXML
    private TextField searchField;
    @FXML
    private ImageView searchButton;
    @FXML
    private Button addButton;
    @FXML
    private Button viewButton;
    @FXML
    private TableColumn<User, String> userAction;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        instance = this;
        
        userId.setSortable(false);
        userFirstName.setSortable(false);
        userLastName.setSortable(false);
        userRole.setSortable(false);
        userStatus.setSortable(false);
        userAction.setSortable(false);
        
        userId.setCellValueFactory(new PropertyValueFactory<>("id"));
        userFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        userLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        userRole.setCellValueFactory(new PropertyValueFactory<>("role"));
        userStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        
        Callback<TableColumn<User, String>, TableCell<User, String>> cellFactory = (TableColumn<User, String> param) -> {
            final TableCell<User, String> cell = new TableCell<User, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    
                    if(empty) {
                        setGraphic(null);
                        setText(null);
                        return;
                    }
                    
                    ImageView deleteButton = new ImageView("/projectvantage/resources/icons/delete-icon.png");
                    
                    deleteButton.setFitHeight(DELETE_IMAGE_HEIGHT);
                    deleteButton.setFitWidth(DELETE_IMAGE_WIDTH);
                    deleteButton.setCursor(Cursor.HAND);
                    
                    deleteButton.setOnMouseEntered(event -> {
                        elementConf.hoverIcon(deleteButton);
                    });
                    
                    deleteButton.setOnMouseExited(event -> {
                        elementConf.unhoverIcon(deleteButton);
                    });
                    
                    deleteButton.setOnMouseClicked(event -> {
                        Stage currentStage = (Stage)rootPane.getScene().getWindow();
                        User selectedRow = userTable.getSelectionModel().getSelectedItem();
                        int id = selectedRow.getId();
                        String role = selectedRow.getRole();
                        String username = selectedRow.getUsername();
                        
                        String sql = "DELETE FROM user WHERE id = ?";
                        
                        switch(role) {
                            case "admin":
                                alertConf.showAlert(Alert.AlertType.INFORMATION, "User deletion alert", "You can't delete an admin.", currentStage);
                                break;
                            default:
                                alertConf.showDeleteConfirmationAlert(currentStage, sql, id);
                                logConf.logDeleteUser(sessionUserId, username);
                                refreshTable();
                        }
                    });
                    
                    HBox actionButtons = new HBox(deleteButton);
                    setGraphic(actionButtons);
                    setText(null);
                }
            };
            return cell;
        };
        userAction.setCellFactory(cellFactory);
        
        loadTableData();
        
        Platform.runLater(() -> {
            SessionConfig sessionConf = SessionConfig.getInstance();
            
            sessionUserId = sessionConf.getId();
        });
    }
    
    public static UserManagementPageController getInstance() {
        return instance;
    }
    
    public void refreshTable() {
        userList.clear();
        loadTableData();
    }

    private Node createPage(int pageIndex) {
        int fromIndex = pageIndex * ROWS_PER_PAGE;
        int toIndex = Math.min(fromIndex + ROWS_PER_PAGE, userList.size());
        userTable.setItems(FXCollections.observableArrayList(userList.subList(fromIndex, toIndex)));
        return userTable;
    }
    
    private void loadTableData() {
        String sql = "SELECT user.id, first_name, middle_name, last_name, email, phone_number, username, salt, password, secret_key, user_role.name AS role, user_status.name AS status "
                + "FROM user INNER JOIN user_role ON user.role_id = user_role.id INNER JOIN user_status ON user.status_id = user_status.id ORDER BY user.id ASC";
        
        try(ResultSet result = db.getData(sql)) {
            while(result.next()) {
                userList.add(new User(
                        result.getInt("id"),
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
                ));
            }
            
            int pageCount = (int) Math.ceil((double) userList.size() / ROWS_PER_PAGE);
            tablePage.setPageCount(pageCount);
            tablePage.setPageFactory(this::createPage);
            
        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
//    private void changeIcon(String location, ImageView icon) {
//        Image image = new Image(location);
//        icon.setImage(image);
//    }

    @FXML
    private void tablePageOnMouseClickHandler(MouseEvent event) {
    }

    @FXML
    private void userTableMouseClickHandler(MouseEvent event) {
    }

    @FXML
    private void searchButtonMouseReleaseHandler(MouseEvent event) {
        elementConf.releaseIcon(searchButton);
    }

    @FXML
    private void searchButtonMouseClickHandler(MouseEvent event) {
        
    }

    @FXML
    private void searchButtonMousePressHandler(MouseEvent event) {
        searchButton.setScaleX(0.9);
        searchButton.setScaleY(0.9);
    }

    @FXML
    private void addButtonMouseClickHandler(MouseEvent event) throws Exception {
        String FXML = "/projectvantage/fxml/admin/AddUserPage.fxml";
        pageConf.loadWindow(FXML, "Add User", rootPane);
    }

    @FXML
    private void viewButtonMouseClickHandler(MouseEvent event) throws Exception {
        AdminPageController adminController = AdminPageController.getInstance();
        
        Stage currentStage = (Stage) rootPane.getScene().getWindow();
        User selectedRow = userTable.getSelectionModel().getSelectedItem();
        
        if(selectedRow == null) {
            alertConf.showAlert(Alert.AlertType.ERROR, "User View Failed", "You must select a user.", currentStage);
            return;
        }
        
        String fxmlLocation = "/projectvantage/fxml/admin/AdminUserPage.fxml";
        String user = selectedRow.getUsername();
        pageConf.loadUserPage(fxmlLocation, user , adminController.getBackgroundPane(), adminController.getRootPane());
    }
}
