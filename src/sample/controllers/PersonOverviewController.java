package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.Main;
import sample.models.Person;
import sample.utils.DateUtil;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class PersonOverviewController{
    @FXML
    private TableView<Person> personTableView;

    @FXML
    private TableColumn<Person, String> firstNameColumn;

    @FXML
    private TableColumn<Person, String> lastNameColumn;

    public void setPersonTableView(TableView<Person> personTableView) {
        this.personTableView = personTableView;
    }

    // Labels block

    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label streetLabel;
    @FXML
    private Label cityLabel;
    @FXML
    private Label postalCodeLabel;
    @FXML
    private Label birthdayLabel;

    // Buttons block

    @FXML
    private Button newButton;

    @FXML
    private Button editButton;

    @FXML
    private Button deleteButton;

    private Main mainApp;

    public PersonOverviewController(){  }

    @FXML
    public void initialize(){

        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().getFirstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().getLastNameProperty());
        showPersonDetails(null);
        personTableView.getSelectionModel().selectedItemProperty().addListener(
                ((observableValue, oldValue, newValue) -> showPersonDetails(newValue))
        );
    }

    @FXML
    private void handleDeletePerson() {
        int selectedIndex = personTableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            personTableView.getItems().remove(selectedIndex);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleNewPerson() {
        Person tempPerson = new Person();
        boolean isOkClicked = mainApp.showPersonEditDialog(tempPerson);
        if (isOkClicked) {
            mainApp.getPersonData().add(tempPerson);
        }
    }

    @FXML
    private void handleEditPerson() {
        Person selectedPerson = personTableView.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            boolean isOkClicked = mainApp.showPersonEditDialog(selectedPerson);
            if (isOkClicked) {
                showPersonDetails(selectedPerson);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table");
            alert.showAndWait();
        }
    }

    private void showPersonDetails(Person person) {
        if (person != null) {
            firstNameLabel.setText(person.getFirstName());
            lastNameLabel.setText(person.getLastName());
            cityLabel.setText(person.getCity());
            streetLabel.setText(person.getStreet());
            postalCodeLabel.setText(Integer.toString(person.getPostalCode()));
            birthdayLabel.setText(DateUtil.format(person.getBirthday()));
        } else {
            firstNameLabel.setText("");
            lastNameLabel.setText("");
            cityLabel.setText("");
            streetLabel.setText("");
            postalCodeLabel.setText("");
            birthdayLabel.setText("");
        }
    }

    public void newButton(ActionEvent actionEvent) {
        System.out.println("Создать новую запись");
    }

    public void editButton(ActionEvent actionEvent) {
        System.out.println("Редактировать!");
    }

    public void deleteButton(ActionEvent actionEvent) {
        System.out.println("Удалятор записей 2000!");
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        personTableView.setItems(mainApp.getPersonData());
    }
}
