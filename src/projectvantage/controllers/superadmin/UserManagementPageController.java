/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.controllers.superadmin;

import projectvantage.models.User;
import projectvantage.utility.Config;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.animation.ScaleTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
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
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import projectvantage.utility.dbConnect;

/**
 * FXML Controller class
 *
 * @author Mark
 */
public class UserManagementPageController implements Initializable {
    
    private static final int ROWS_PER_PAGE = 10;
    
    ObservableList<User> userList = FXCollections.observableArrayList();
    Config config = new Config();

    @FXML
    private TableView<User> userTable;
    @FXML
    private TableColumn<User, Integer> userId;
    @FXML
    private TableColumn<User, String> userLastName;
    @FXML
    private TableColumn<User, String> userRole;
    @FXML
    private TableColumn<User, String> userStatus;
    @FXML
    private Pagination tablePage;
    @FXML
    private Button addButton;
    @FXML
    private ImageView searchButton;
    @FXML
    private TextField searchField;
    @FXML
    private TableColumn<User, String> userFirstName;
    @FXML
    private AnchorPane rootPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        userId.setCellValueFactory(new PropertyValueFactory<>("id"));
        userFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        userLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        userRole.setCellValueFactory(new PropertyValueFactory<>("role"));
        userStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        
        loadTableData();
    }    
    
    /*private void loadPage(String targetFXML) {
        Stage currentStage = (Stage) rootPane.getScene().getWindow();
        SuperAdminPageController admin = SuperAdminPageController.getInstance();
        
        try{
            Parent root = FXMLLoader.load(getClass().getResource(targetFXML));
            admin.getRootPane().setCenter(root);
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
            config.showErrorMessage("There was a problem with the database", "Database Error:", currentStage);
        }
        
    }*/
    
    private Node createPage(int pageIndex) {
        int fromIndex = pageIndex * ROWS_PER_PAGE;
        int toIndex = Math.min(fromIndex + ROWS_PER_PAGE, userList.size());
        userTable.setItems(FXCollections.observableArrayList(userList.subList(fromIndex, toIndex)));
        return userTable;
    }
    
    private void loadTableData() {
        dbConnect db = new dbConnect();
        String sql = "SELECT id, first_name, middle_name, last_name, email, phone_number, username, password, role, status FROM user";
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
                        result.getString("password"),
                        result.getString("role"),
                        result.getString("status")
                ));
            }
            
            int pageCount = (int) Math.ceil((double) userList.size() / ROWS_PER_PAGE);
            tablePage.setPageCount(pageCount);
            tablePage.setPageFactory(this::createPage);
            
        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
        }
    }
    
    private void changeIcon(String location, ImageView icon) {
        Image image = new Image(location);
        icon.setImage(image);
    }
    
    public void hoverIcon(ImageView image) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(100), image);
        scaleTransition.setFromX(1.0);
        scaleTransition.setFromY(1.0);
        scaleTransition.setToX(1.1);
        scaleTransition.setToY(1.1);
        scaleTransition.play();
    }
    
    public void unhoverIcon(ImageView image) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(100), image);
        scaleTransition.setFromX(1.1);
        scaleTransition.setFromY(1.1);
        scaleTransition.setToX(1.0);
        scaleTransition.setToY(1.0);
        scaleTransition.play();
    }

    @FXML
    private void tablePageOnMouseClickHandler(MouseEvent event) {
    }

    @FXML
    private void userTableMouseClickHandler(MouseEvent event) {
    }

    @FXML
    private void searchButtonMouseReleaseHandler(MouseEvent event) {
        config.releaseIcon(searchButton);
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
        String FXML = "/projectvantage/fxml/superadmin/AddUserPage.fxml";
        SuperAdminPageController admin = SuperAdminPageController.getInstance();
        admin.loadPage(FXML);
    }
}
