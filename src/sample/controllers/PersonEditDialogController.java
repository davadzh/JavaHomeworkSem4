package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.models.Person;
import sample.utils.DateUtil;

public class PersonEditDialogController {
    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField cityField;

    @FXML
    private TextField streetField;

    @FXML
    private TextField postalCodeField;

    @FXML
    private TextField birthdayField;

    private Stage dialogStage;
    private Person person;
    boolean okClicked = false;

    @FXML
    private void initialize() {}

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setPerson(Person person) {
        this.person = person;
        firstNameField.setText(person.getFirstName());
        lastNameField.setText(person.getLastName());
        cityField.setText(person.getCity());
        streetField.setText(person.getStreet());
        postalCodeField.setText(Integer.toString(person.getPostalCode()));
        birthdayField.setText(DateUtil.format(person.getBirthday()));
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    @FXML
    private void handleOK() {
        if (isInputValid()) {
            person.setFirstName(firstNameField.getText());
            person.setLastName(lastNameField.getText());
            person.setCity(cityField.getText());
            person.setStreet(streetField.getText());
            person.setPostalCode(Integer.parseInt(postalCodeField.getText()));
            person.setBirthday(DateUtil.parse(birthdayField.getText()));

            okClicked = true;
            dialogStage.close();
        }
    }

    private boolean isInputValid() {
        String errorMessage = "";
        if (firstNameField.getText() == null || firstNameField.getText().length() == 0) {
            errorMessage += "Не введено имя\n";
        }
        if (lastNameField.getText() == null || firstNameField.getText().length() == 0) {
            errorMessage += "Не введена фамилия\n";
        }
        if (cityField.getText() == null || firstNameField.getText().length() == 0) {
            errorMessage += "Не введен город\n";
        }
        if (streetField.getText() == null || firstNameField.getText().length() == 0) {
            errorMessage += "Не введена улица\n";
        }
        if (postalCodeField.getText() == null || firstNameField.getText().length() == 0) {
            errorMessage += "Не введен почтовый индекс\n";
        } else {
            try {
                Integer.parseInt(postalCodeField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Почтовый индекс должен быть цифрами\n";
            }
        }
        if (birthdayField.getText() == null || firstNameField.getText().length() == 0) {
            errorMessage += "Не введен день рождения\n";
        } else {
            if (!DateUtil.isValidDate(birthdayField.getText())) {
                errorMessage += "Формат даты должен быть dd.mm.yyyy\n";
            }
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Error");
            alert.setHeaderText("Поля введены некорректно");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
    }

}
