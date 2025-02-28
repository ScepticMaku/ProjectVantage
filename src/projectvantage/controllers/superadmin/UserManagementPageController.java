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
import javafx.fxml.Initializable;
import javafx.scene.Node;
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
    private TableColumn<User, Void> actionColumn;
    @FXML
    private ImageView addButton;
    @FXML
    private ImageView editButton;
    @FXML
    private ImageView deleteButton;
    @FXML
    private ImageView searchButton;
    @FXML
    private TextField searchField;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        userId.setCellValueFactory(new PropertyValueFactory<>("id"));
        userLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        userRole.setCellValueFactory(new PropertyValueFactory<>("role"));
        userStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        
        loadTableData();
    }    
    
    private Node createPage(int pageIndex) {
        int fromIndex = pageIndex * ROWS_PER_PAGE;
        int toIndex = Math.min(fromIndex + ROWS_PER_PAGE, userList.size());
        userTable.setItems(FXCollections.observableArrayList(userList.subList(fromIndex, toIndex)));
        return userTable;
    }
    
    private void loadTableData() {
        dbConnect db = new dbConnect();
        String sql = "SELECT id, first_name, last_name, email, phone_number, username, password, role, status FROM user";
        try(ResultSet result = db.getData(sql)) {
            while(result.next()) {
                userList.add(new User(
                        result.getInt("id"),
                        result.getString("first_name"),
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
    private void addButtonMouseReleaseHandler(MouseEvent event) {
        config.releaseIcon(addButton);
        
    }

    @FXML
    private void addButtonMouseExitHandler(MouseEvent event) {
        unhoverIcon(addButton);
        changeIcon("/projectvantage/resources/icons/add-icon-unselected.png", addButton);
    }

    @FXML
    private void addButtonMouseEnterHandler(MouseEvent event) {
        hoverIcon(addButton);
        changeIcon("/projectvantage/resources/icons/add-icon-selected.png", addButton);
    }

    @FXML
    private void addButtonMouseClickHandler(MouseEvent event) {
    }

    @FXML
    private void addButtonMousePressHandler(MouseEvent event) {
        addButton.setScaleX(0.9);
        addButton.setScaleY(0.9);
    }

    @FXML
    private void editButtonMouseReleaseHandler(MouseEvent event) {
        config.releaseIcon(editButton);
    }

    @FXML
    private void editButtonMouseExitHandler(MouseEvent event) {
        unhoverIcon(editButton);
        changeIcon("/projectvantage/resources/icons/edit-icon-unselected.png", editButton);
    }

    @FXML
    private void editButtonMouseEnterHandler(MouseEvent event) {
        hoverIcon(editButton);
        changeIcon("/projectvantage/resources/icons/edit-icon-selected.png", editButton);
    }

    @FXML
    private void editButtonMouseClickHandler(MouseEvent event) {
    }

    @FXML
    private void editButtonMousePressHandler(MouseEvent event) {
        editButton.setScaleX(0.9);
        editButton.setScaleY(0.9);
    }

    @FXML
    private void userTableMouseClickHandler(MouseEvent event) {
    }

    @FXML
    private void deleteButtonMouseReleaseHandler(MouseEvent event) {
        config.releaseIcon(deleteButton);
    }

    @FXML
    private void deleteButtonMouseExitHandler(MouseEvent event) {
        unhoverIcon(deleteButton);
        changeIcon("/projectvantage/resources/icons/delete-icon-unselected.png", deleteButton);
    }

    @FXML
    private void deleteButtonMouseEnterHandler(MouseEvent event) {
        hoverIcon(deleteButton);
        changeIcon("/projectvantage/resources/icons/delete-icon-selected.png", deleteButton);
    }

    @FXML
    private void deleteButtonMouseclickHnadler(MouseEvent event) {
    }

    @FXML
    private void deleteButtonMousePressHandler(MouseEvent event) {
        deleteButton.setScaleX(0.9);
        deleteButton.setScaleY(0.9);
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
    
}
