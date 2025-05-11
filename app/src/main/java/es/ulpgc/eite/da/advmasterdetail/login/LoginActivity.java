package es.ulpgc.eite.da.advmasterdetail.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import es.ulpgc.eite.da.advmasterdetail.R;
import es.ulpgc.eite.da.advmasterdetail.movies.MoviesListActivity;
import es.ulpgc.eite.da.advmasterdetail.register.RegisterActivity;

public class LoginActivity extends AppCompatActivity implements LoginContract.View {

    public static final String TAG = "LoginActivity";

    private LoginContract.Presenter presenter;

    private EditText usernameField;
    private EditText passwordField;
    private Button loginButton;
    private Button guestButton;
    private TextView signUpText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login); // usa el layout correcto
        setTitle(R.string.app_name);

        LoginScreen.configure(this); // MVP setup

        initViews();

        if (savedInstanceState == null) {
            presenter.onCreateCalled();
        } else {
            presenter.onRecreateCalled();
        }

        loginButton.setOnClickListener(view -> {
            String username = usernameField.getText().toString().trim();
            String password = passwordField.getText().toString().trim();
            presenter.onLoginButtonClicked(username, password);
        });

        guestButton.setOnClickListener(view -> presenter.onGuestButtonClicked());

        signUpText.setOnClickListener(view -> presenter.onSignUpClicked());
    }

    private void initViews() {
        usernameField = findViewById(R.id.edit_username);
        passwordField = findViewById(R.id.edit_password);
        loginButton = findViewById(R.id.btn_login);
        guestButton = findViewById(R.id.btn_guest);
        signUpText = findViewById(R.id.signup);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroyCalled();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        presenter.onBackButtonPressed();
    }

    @Override
    public void injectPresenter(LoginContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showLoginError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void navigateToMovieList() {
        Intent intent = new Intent(this, MoviesListActivity.class);
        startActivity(intent);
    }

    @Override
    public void navigateToRegisterScreen() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    public void navigateAsGuest() {
        // Mismo destino que un usuario normal (por ahora)
        navigateToMovieList();
    }
}
