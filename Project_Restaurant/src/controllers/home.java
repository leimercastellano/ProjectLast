package controllers;

import com.mysql.cj.xdevapi.Table;
import connections.ConnectionJDBC;
import connections.ConnectionMongo;
import dao.GestorPersistencia;
import dao.GestorPersistenciaJDBC;
import dao.GestorPersistenciaMongo;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.Book;
import models.Restaurant;
import resources.ReadProperties;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class home implements Initializable {
    ReadProperties readP = new ReadProperties();
    public static int positionRestaurant;
    public static Restaurant restaurantSelected;
    static String selected = principal.seletedMethod;
    @FXML
    private Button btnAddBook;
    @FXML
    private Button btnAddRestaurant;
    @FXML
    private Button btnConsultBook;
    @FXML
    private Button btnEditRestaurant;
    @FXML
    private TableView<Restaurant> tblRestaurant;
    @FXML
    private TableColumn colName;
    @FXML
    private TableColumn colCity;
    @FXML
    private TableColumn colCapacity;
    @FXML
    private Button btnEditBook;

    private ObservableList<Restaurant> observableRestaurants;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        observableRestaurants = FXCollections.observableArrayList();
        this.colName.setCellValueFactory(new PropertyValueFactory("Name"));
        this.colCity.setCellValueFactory(new PropertyValueFactory("City"));
        this.colCapacity.setCellValueFactory(new PropertyValueFactory("Capacity"));


        if (selected.equals("JDBC")) {
            System.out.println("JDBC");
            jdbcSelection();
        }
        if (selected.equals("XML")) {
            System.out.println("XML");
            xmlSelection();
        }
        if (selected.equals("MONGODB")) {
            System.out.println("Mongo");
            mongoSelection();
        }
    }

    @FXML
    public void loadRestaurants(List<Restaurant> restaurantList) {
        for (int i = 0; i < restaurantList.size(); i++) {
            this.observableRestaurants.add(restaurantList.get(i));
        }
        this.tblRestaurant.setItems(observableRestaurants);
    }

    private void jdbcSelection() {
        GestorPersistencia gestorPersistenciaJdbc = new GestorPersistenciaJDBC();
        List<Restaurant> restaurantsList = gestorPersistenciaJdbc.getAll_Restaurants();
        loadRestaurants(restaurantsList);
    }

    private void mongoSelection() {
        GestorPersistenciaMongo gestorPersistenciaMongo = new GestorPersistenciaMongo();
        List<Restaurant> restaurantList = gestorPersistenciaMongo.getAll_Restaurants();
        loadRestaurants(restaurantList);
    }

    private void xmlSelection() {

    }

    public void clickAddRestaurant(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(readP.getrView()+"addRestaurant.fxml"));
            Stage stage = (Stage) btnAddRestaurant.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clickConsultBook(ActionEvent actionEvent) {
        selection();
        if (restaurantSelected != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(readP.getrView()+"consultBooks.fxml"));
                Stage stage = (Stage) btnConsultBook.getScene().getWindow();
                Scene scene = new Scene(loader.load());
                stage.setScene(scene);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Has de seleccionar un restaurant, per fer aquesta opcio");

            alert.showAndWait();
        }
    }

    public void clickEditRestaurant(ActionEvent actionEvent) {
        selection();
        if (restaurantSelected != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(readP.getrView()+"editRestaurant.fxml"));
                Stage stage = (Stage) btnEditRestaurant.getScene().getWindow();
                Scene scene = new Scene(loader.load());
                stage.setScene(scene);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Has de seleccionar un restaurant, per fer aquesta opcio");

            alert.showAndWait();
        }
    }

    public void selection() {
        List<Restaurant> restaurantList = new ArrayList<>(observableRestaurants);
        ObservableList selectedItem = tblRestaurant.getSelectionModel().getSelectedItems();
        Restaurant r = (Restaurant) selectedItem.get(selectedItem.size() - 1);
        restaurantSelected = r;
    }

    public void clickAddBook(ActionEvent actionEvent) {
        selection();
        if (restaurantSelected != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(readP.getrView()+"addBook.fxml"));
                Stage stage = (Stage) btnAddBook.getScene().getWindow();
                Scene scene = new Scene(loader.load());
                stage.setScene(scene);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Has de seleccionar un restaurant, per fer aquesta opcio");

            alert.showAndWait();
        }
    }
}
