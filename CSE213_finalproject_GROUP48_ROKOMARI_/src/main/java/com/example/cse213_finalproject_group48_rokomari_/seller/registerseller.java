package com.example.cse213_finalproject_group48_rokomari_.seller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.beans.value.ChangeListener;

import java.util.regex.Pattern;

public class registerseller {

    @FXML private TextField nameField;
    @FXML private TextField emailField;
    @FXML private TextField phoneField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private Button registerButton;
    @FXML private Label messageLabel;

    private boolean verified = false; // set true after successful verification

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}$", Pattern.CASE_INSENSITIVE
    );

    // Example: Bangladeshi phone pattern simplified (+880 or 01...), adjust as needed
    private static final Pattern PHONE_PATTERN = Pattern.compile("^(\\+?88)?0?1[0-9]{9}$");

    @FXML
    public void initialize() {
        // Live basic validation: clear messages when user types
        ChangeListener<String> clearMsg = (obs, oldVal, newVal) -> messageLabel.setText("");
        nameField.textProperty().addListener(clearMsg);
        emailField.textProperty().addListener(clearMsg);
        phoneField.textProperty().addListener(clearMsg);
        passwordField.textProperty().addListener(clearMsg);
        confirmPasswordField.textProperty().addListener(clearMsg);

        // Disable register until verification completes
        updateRegisterButtonState();

        // Optional: restrict phone input to digits and plus sign
        phoneField.textProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal.matches("[+0-9]*")) {
                phoneField.setText(oldVal);
            }
        });
    }

    private void updateRegisterButtonState() {
        // register enabled only if verified and terms accepted and basic fields non-empty
        boolean basicFilled = !nameField.getText().trim().isEmpty()
                && !emailField.getText().trim().isEmpty()
                && !phoneField.getText().trim().isEmpty()
                && !passwordField.getText().isEmpty()
                && passwordField.getText().equals(confirmPasswordField.getText());
        registerButton.setDisable(!(verified && basicFilled && termsCheck.isSelected()));
    }

    @FXML
    private void handleVerify(ActionEvent event) {
        // 1) client-side quick validation
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();

        if (!EMAIL_PATTERN.matcher(email).matches()) {
            messageLabel.setText("Enter a valid email.");
            return;
        }
        if (!PHONE_PATTERN.matcher(phone).matches()) {
            messageLabel.setText("Enter a valid phone number.");
            return;
        }

        // 2) Trigger verification process.
        // Replace the following block with your verification implementation — e.g., send OTP via backend service.
        boolean mockSent = mockSendVerification(email, phone);
        if (!mockSent) {
            messageLabel.setText("Failed to send verification. Try again.");
            verified = false;
            updateRegisterButtonState();
            return;
        }

        // 3) Simulate asking for OTP (you can open a dialog to input OTP)
        TextInputDialog otpDialog = new TextInputDialog();
        otpDialog.setTitle("Enter OTP");
        otpDialog.setHeaderText("A verification code was sent to your email/phone.");
        otpDialog.setContentText("Enter code:");
        otpDialog.showAndWait().ifPresent(code -> {
            boolean ok = mockVerifyCode(code);
            if (ok) {
                messageLabel.setText("Verification successful.");
                verified = true;
            } else {
                messageLabel.setText("Invalid verification code.");
                verified = false;
            }
            updateRegisterButtonState();
        });
    }

    @FXML
    private void handleRegister(ActionEvent event) {
        // Final validation
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();
        String password = passwordField.getText();
        String confirm = confirmPasswordField.getText();

        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty()) {
            messageLabel.setText("Please fill all required fields.");
            return;
        }
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            messageLabel.setText("Invalid email.");
            return;
        }
        if (!PHONE_PATTERN.matcher(phone).matches()) {
            messageLabel.setText("Invalid phone.");
            return;
        }
        if (!password.equals(confirm)) {
            messageLabel.setText("Passwords do not match.");
            return;
        }

        }
        if (!verified) {
            messageLabel.setText("Please verify email/phone first.");
            return;
        }

        // Build seller DTO / entity
        SellerDto seller = new SellerDto();
        seller.setName(name);
        seller.setEmail(email);
        seller.setPhone(phone);
        seller.setPassword(password); // hash on server-side!


        // TODO: call your backend / service to save the seller. Example:
        boolean saved = mockSaveSeller(seller);

        if (saved) {
            // show confirmation: you can use Alert or messageLabel
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("Registration Complete");
            a.setHeaderText(null);
            a.setContentText("Account registered and pending verification/approval.");
            a.showAndWait();
            clearForm();
        } else {
            messageLabel.setText("Failed to register. Try again.");
        }
    }

    @FXML
    private void handleCancel(ActionEvent event) {
        clearForm();
        messageLabel.setText("Registration cancelled.");
    }

    private void clearForm() {
        nameField.clear();
        emailField.clear();
        phoneField.clear();
        passwordField.clear();
        confirmPasswordField.clear();
        verified = false;
        updateRegisterButtonState();
    }

    // ---- Mock / placeholders: replace with real implementation ----
    private boolean mockSendVerification(String email, String phone) {
        // TODO: call backend to send OTP via email/SMS
        System.out.println("Sending mock OTP to " + email + " / " + phone);
        return true;
    }
    private boolean mockVerifyCode(String code) {
        // In real world, verify via backend. Here accept "123456" as example.
        return "123456".equals(code);
    }
    private boolean mockSaveSeller(SellerDto seller) {
        // TODO: call backend to persist seller (hash password, store, set status PENDING_APPROVAL)
        System.out.println("Mock saving seller: " + seller.getEmail());
        return true;
    }
    // --------------------------------------------------------------

    // A simple DTO inner class for example (replace with real model)
    public static class SellerDto {
        private String name, email, phone, password, businessName;
        public void setName(String s){name=s;} public void setEmail(String s){email=s;}
        public void setPhone(String s){phone=s;} public void setPassword(String s){password=s;}
        public void setBusinessName(String s){businessName=s;}
        public String getEmail(){return email;}
    }
}

