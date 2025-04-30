/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.controllers.team_member;

import projectvantage.models.TeamMember;
import projectvantage.utility.dbConnect;
import projectvantage.utility.ElementConfig;
import projectvantage.utility.DatabaseConfig;
import projectvantage.utility.PageConfig;
import projectvantage.utility.AlertConfig;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Markj
 */
public class TeamMemberPageController implements Initializable {

    private static TeamMemberPageController instance;
    
    dbConnect db = new dbConnect();
    ElementConfig elementConf = new ElementConfig();
    DatabaseConfig databaseConf = new DatabaseConfig();
    AlertConfig alertConf = new AlertConfig();
    PageConfig pageConf = new PageConfig();
    
    ObservableList<TeamMember> teamMemberList = FXCollections.observableArrayList();
    
    private static final int ROWS_PER_PAGE = 9;
    private static final double ICON_HEIGHT = 26;
    private static final double ICON_WIDTH = 26;
    
    @FXML
    private AnchorPane rootPane;
    @FXML
    private Pagination pagination;
    @FXML
    private TableView<TeamMember> teamMemberTable;
    @FXML
    private TableColumn<TeamMember, Integer> idColumn;
    @FXML
    private TableColumn<TeamMember, String> lastNameColumn;
    @FXML
    private TableColumn<TeamMember, String> usernameColumn;
    @FXML
    private TableColumn<TeamMember, String> statusColumn;
    @FXML
    private TableColumn<TeamMember, String> actionColumn;
    @FXML
    private Button viewTeamMemberButton;
    @FXML
    private TableColumn<TeamMember, Integer> teamIdColumn;
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
        teamIdColumn.setSortable(false);
        usernameColumn.setSortable(false);
        lastNameColumn.setSortable(false);
        statusColumn.setSortable(false);
        
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        teamIdColumn.setCellValueFactory(new PropertyValueFactory<>("teamId"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        
        Callback<TableColumn<TeamMember, String>, TableCell<TeamMember, String>> cellFactory = (TableColumn<TeamMember, String> param) -> {
            final TableCell<TeamMember, String> cell = new TableCell<TeamMember, String>() {
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
                        int id = teamMemberTable.getSelectionModel().getSelectedItem().getId();
                        
                        String sql = "DELETE FROM team_member WHERE id = ?";
                        
                        alertConf.showDeleteConfirmationAlert(currentStage, sql, id);
                        refreshTable();
                    });
                    
                    HBox actionButtons = new HBox(deleteButton);
                    HBox.setMargin(deleteButton, new Insets(2, 2, 0, 3));
                    setGraphic(actionButtons);
                    setText(null);
                }
            };
            return cell;
        };
        actionColumn.setCellFactory(cellFactory);
        
        Platform.runLater(() -> {
            loadTableData();
        });
    }    
    
    public TeamMemberPageController getInstance() {
        return instance;
    }
    
    public void refreshTable() {
        teamMemberList.clear();
        loadTableData();
    }
    
    public void loadTableData() {
        String sql = "SELECT team_member.id AS id, team_id, user.last_name AS last_name, user.username AS username, team_member_role.name AS role, team_member_status.name AS status "
                + "FROM team_member INNER JOIN user ON user_id = user.id INNER JOIN team_member_role ON team_member.role_id = team_member_role.id "
                + "INNER JOIN team_member_status ON team_member.status_id = team_member_status.id";
        
        try(ResultSet result = db.getData(sql)) {
            while(result.next()) {
                teamMemberList.add(new TeamMember(
                        result.getInt("id"),
                        result.getInt("team_id"),
                        result.getString("last_name"),
                        result.getString("username"),
                        result.getString("role"),
                        result.getString("status")
                ));
            }
            
            teamMemberTable.setItems(teamMemberList);
        } catch (Exception e) {
            System.out.println("Database Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void userTableMouseClickHandler(MouseEvent event) {
    }

    @FXML
    private void viewTeamMemberButtonMouseClickHandler(MouseEvent event) throws Exception {
        String viewTeamMemberFXML = "/projectvantage/fxml/team_member/ViewTeamMemberPage.fxml";
        
        int teamMemberId = teamMemberTable.getSelectionModel().getSelectedItem().getId();
        int userId = databaseConf.getUserIdById(teamMemberId);
        
        pageConf.loadWindow(viewTeamMemberFXML, "Team Member", rootPane);
        ViewTeamMemberPageController.getInstance().loadContent(userId);
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
