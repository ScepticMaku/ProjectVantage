/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.controllers.team_manager;

import projectvantage.models.Team;
import projectvantage.models.User;
import projectvantage.utility.dbConnect;
import projectvantage.utility.ElementConfig;
import projectvantage.utility.AlertConfig;
import projectvantage.utility.PageConfig;
import projectvantage.utility.DatabaseConfig;

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
import projectvantage.controllers.admin.AdminPageController;

/**
 * FXML Controller class
 *
 * @author Markj
 */
public class TeamPageController implements Initializable {
    
    private static TeamPageController instance;
    
    dbConnect db = new dbConnect();
    ElementConfig elementConf = new ElementConfig();
    AlertConfig alertConf = new AlertConfig();
    PageConfig pageConf = new PageConfig();
    DatabaseConfig databaseConf = new DatabaseConfig();
    
    ObservableList<Team> teamList = FXCollections.observableArrayList();
    
    private static final int ROWS_PER_PAGE = 9;
    private static final double ICON_HEIGHT = 26;
    private static final double ICON_WIDTH = 26;

    private String username;
    private String role;
    
    @FXML
    private Pagination pagination;
    @FXML
    private TableColumn<Team, Integer> idColumn;
    @FXML
    private TableColumn<Team, String> nameColumn;
    @FXML
    private TableColumn<Team, String> actionColumn;
    @FXML
    private TableView<Team> teamTable;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private Button addTeamButton;
    @FXML
    private Button viewTeamButton;
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
        actionColumn.setSortable(false);
        
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        
        Callback<TableColumn<Team, String>, TableCell<Team, String>> cellFactory = (TableColumn<Team, String> param) -> {
            final TableCell<Team, String> cell = new TableCell<Team, String>() {
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
                        Team selectedRow = teamTable.getSelectionModel().getSelectedItem();
                        int id = selectedRow.getId();
                        
                        String sql = "DELETE FROM team WHERE id = ?";
                        
                        alertConf.showDeleteConfirmationAlert(currentStage, sql, id);
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
                        String editTeamFXML = "/projectvantage/fxml/team_manager/EditTeamPage.fxml";
                        
                        
                        Team selectedRow = teamTable.getSelectionModel().getSelectedItem();
                        int id = selectedRow.getId();
                        try {
                            pageConf.loadWindow(editTeamFXML, "Edit Team", rootPane);
                            EditTeamPageController.getInstance().loadContent(id);
                        } catch (Exception e) {
                            e.printStackTrace();
                            alertConf.showWindowLoadErrorAlert(currentStage, "Failed to load edit project window.");
                        }
                    });
                    
                    HBox actionButtons = new HBox(editButton, deleteButton);
                    HBox.setMargin(deleteButton, new Insets(2, 2, 0 ,3));
                    HBox.setMargin(editButton, new Insets(2, 3, 0, 2));
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
    
    public static TeamPageController getInstance() {
        return instance;
    }
    
    public void setUsername(String username) {
        this.username = username;
        
        User user = databaseConf.getUserByUsername(username);
        
        this.role = user.getRole();
    }
    
    public void refreshTable() {
        teamList.clear();
        loadTableData();
    }
    
    private Node createPage(int pageIndex) {
        int fromIndex = pageIndex * ROWS_PER_PAGE;
        int toIndex = Math.min(fromIndex + ROWS_PER_PAGE, teamList.size());
        teamTable.setItems(FXCollections.observableArrayList(teamList.subList(fromIndex, toIndex)));
        return teamTable;
    }
    
    private void loadTableData() {
        String sql = "SELECT id, name, project_id FROM team ORDER BY id DESC";
        
        try(ResultSet result = db.getData(sql)) {
            while(result.next()) {
                teamList.add(new Team(
                        result.getInt("id"),
                        result.getString("name"),
                        result.getInt("project_id")
                ));
            }
            
            int pageCount = (int) Math.ceil((double) teamList.size() / ROWS_PER_PAGE);
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
    private void addTeamButtonMouseClickHandler(MouseEvent event) throws Exception {
        pageConf.loadWindow("/projectvantage/fxml/team_manager/AddTeamPage.fxml", "Add Team", rootPane);
    }

    @FXML
    private void viewTeamButtonMouseClickHandler(MouseEvent event) {
        Stage currentStage = (Stage)rootPane.getScene().getWindow();
        
        try {
            AdminPageController adminController = AdminPageController.getInstance();
            TeamManagerPageController teamManagerController = TeamManagerPageController.getInstance();

            Team selectedTeam = teamTable.getSelectionModel().getSelectedItem();
            int teamId = selectedTeam.getId();
            String viewTeamFXML = "/projectvantage/fxml/team_manager/ViewTeamPage.fxml";
            
            if(role.equals("admin")) {
                adminController.loadPage(viewTeamFXML, "Team");
                ViewTeamPageController viewTeamController = ViewTeamPageController.getInstance();
                viewTeamController.loadContent(teamId, username);
                return;
            }

            if(role.equals("team manager")) {
                teamManagerController.loadPage(viewTeamFXML, "Team");
                ViewTeamPageController viewTeamController = ViewTeamPageController.getInstance();
                viewTeamController.loadContent(selectedTeam.getId(), username);
            }
            
        } catch(Exception e) {
            e.printStackTrace();
            alertConf.showAlert(Alert.AlertType.ERROR, "Error Opening a Team", "You must select a team", currentStage);
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
