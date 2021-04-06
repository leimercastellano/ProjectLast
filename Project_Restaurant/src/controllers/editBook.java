package controllers;

import Utils.Comprobaciones;
import dao.GestorPersistencia;
import dao.GestorPersistenciaJDBC;
import dao.GestorPersistenciaMongo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import models.Book;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class editBook implements Initializable {

    @FXML
    private Button btnGoBack;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtDate;
    @FXML
    private TextField txtDinners;
    @FXML
    private TextField txtTable;
    @FXML
    private TextField txtVgMenu;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnImg();
        //prueba();
    }

    public void ClickGoBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("\\..\\views\\consultBooks.fxml"));
            Stage stage = (Stage) btnGoBack.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void btnImg() {
        Image imageDecline = new Image(getClass().getResourceAsStream("\\..\\images\\gobackCircle.png"));

        ImageView imageView1 = new ImageView(imageDecline);
        imageView1.setFitHeight(30);
        imageView1.setFitWidth(30);
        btnGoBack.setGraphic(imageView1);
    }

    public void prueba(){
        txtDate.setPromptText("Entry in format dd/mm/YYYY"); //to set the hint text
        txtDate.getParent().requestFocus();
    }

    public void ClickEditR(ActionEvent actionEvent) {
        GestorPersistencia gestorPersistenciaJdbc = new GestorPersistenciaJDBC();
        GestorPersistencia gestorPersistenciaMongo = new GestorPersistenciaMongo();

        int idSelectedRestaurant = home.restaurantSelected.getIdRestaurant();
        int idbooksele = consultBook.idBookSelectioned;
        String name = this.txtName.getText();
        String date = this.txtDate.getText();
        int dinnersBook = Integer.parseInt(this.txtDinners.getText());
        int tableNro = Integer.parseInt(this.txtTable.getText());
        String vgMenu = this.txtVgMenu.getText();


        boolean vgMyMenu = Comprobaciones.comprobacionVgMenu(vgMenu);
        LocalDate localDate = Comprobaciones.comprobacionFecha(date);
        Book book = new Book(idbooksele, idSelectedRestaurant, name, localDate, dinnersBook, tableNro, vgMyMenu);


        if (home.selected.equals("JDBC")) {
            gestorPersistenciaJdbc.update_Book(book);

        } else if (home.selected.equals("MONGODB")) {
            gestorPersistenciaMongo.update_Book(book);
        }
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText("S'ha actualitzat el book");
        alert.showAndWait();

        ClickGoBack();
    }

    public void ClickDeleteBook(ActionEvent actionEvent) {
        GestorPersistencia gestorPersistenciaJdbc = new GestorPersistenciaJDBC();
        GestorPersistencia gestorPersistenciaMongo = new GestorPersistenciaMongo();

        if (home.selected.equals("JDBC")) {
            gestorPersistenciaJdbc.deleteOne_Book(consultBook.idBookSelectioned);

        } else if (home.selected.equals("MONGODB")) {
            gestorPersistenciaMongo.deleteOne_Book(consultBook.idBookSelectioned);
        }

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("S'ha eliminat el book");
        alert.showAndWait();
        ClickGoBack();
    }
}
    
