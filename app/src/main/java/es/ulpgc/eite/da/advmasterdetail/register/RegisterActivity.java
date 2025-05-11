package es.ulpgc.eite.da.advmasterdetail.register;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import es.ulpgc.eite.da.advmasterdetail.R;

public class RegisterActivity extends AppCompatActivity implements RegisterContract.View {

    public static final String TAG = "RegisterActivity";

    private RegisterContract.Presenter presenter;

    private EditText usernameField;
    private EditText emailField;
    private EditText passwordField;
    private EditText repeatPasswordField;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register); // Asegúrate de que este layout exista
        setTitle(R.string.app_name);

        RegisterScreen.configure(this); // MVP setup

        initViews();

        registerButton.setOnClickListener(view -> {
            String username = usernameField.getText().toString().trim();
            String email = emailField.getText().toString().trim();
            String password = passwordField.getText().toString().trim();
            String repeatPassword = repeatPasswordField.getText().toString().trim();

            presenter.onRegisterButtonClicked(username, email, password, repeatPassword);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResumeCalled();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.onPauseCalled();
    }

    private void initViews() {
        usernameField = findViewById(R.id.edit_register_username);
        emailField = findViewById(R.id.edit_register_email);
        passwordField = findViewById(R.id.edit_register_password);
        repeatPasswordField = findViewById(R.id.edit_register_repeat_password);
        registerButton = findViewById(R.id.btn_register);
    }

    @Override
    public void injectPresenter(RegisterContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showValidationError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void finishRegistration() {
        Toast.makeText(this, "Cuenta creada correctamente", Toast.LENGTH_SHORT).show();
        finish(); // Puedes redirigir a login o main si prefieres
    }

    @Override
    public void navigateToPreviousScreen() {

    }
}
