package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.User;
<<<<<<< HEAD
import service.UserService;
=======
import service.ReclamationService;
import service.UserService;
import utils.MyDatabase;
>>>>>>> gestionnes
import utils.Session;

import java.io.IOException;

public class LoginController {

    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;

    private final UserService userService = new UserService();

    @FXML
    private void handleLogin() {
        String email = emailField.getText();
        String password = passwordField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Veuillez remplir tous les champs.");
            return;
        }

        User user = userService.getByEmail(email);
<<<<<<< HEAD
        if (user != null && userService.login(email, password)) {
            System.out.println("Login successful for user ID: " + user.getId()); // Debug log
            Session.setCurrentUser(user);
            System.out.println("Session user ID after set: " + Session.getCurrentUser().getId()); // Debug log
=======
        if (user == null) {
            showAlert(Alert.AlertType.ERROR, "Aucun utilisateur trouvé avec cet email.");
            return;
        }

        // Vérifie si l'utilisateur a atteint le seuil de réclamations
        ReclamationService reclamationService = new ReclamationService(MyDatabase.getInstance().getCon());
        int reclamationCount = reclamationService.countReclamationsRecues(user.getId());
        if (reclamationCount >= 5) {
            showAlert(Alert.AlertType.ERROR, "Connexion refusée : votre compte a reçu 5 réclamations ou plus.");
            return;
        }

        // Authentification
        boolean isLoginSuccessful = userService.login(email, password);
        if (isLoginSuccessful) {
            Session.setCurrentUser(user);
>>>>>>> gestionnes
            showAlert(Alert.AlertType.INFORMATION, "Connexion réussie !");
            loadRoleUI(user.getRole());
        } else {
            showAlert(Alert.AlertType.ERROR, "Email ou mot de passe incorrect.");
        }
    }

    private void loadRoleUI(String role) {
        String fxml = role.equalsIgnoreCase("Admin") ? "/Admin/AdminDashboard.fxml" : "/User/UserInterface.fxml";

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) emailField.getScene().getWindow();
            stage.setScene(scene);
            stage.sizeToScene();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur lors du chargement de l'interface.");
        }
    }

    @FXML
    private void goToSignup() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Signup.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) emailField.getScene().getWindow();
            stage.setScene(scene);
            stage.sizeToScene();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur de chargement de la page d'inscription.");
        }
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
