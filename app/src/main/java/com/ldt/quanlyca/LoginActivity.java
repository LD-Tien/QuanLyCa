package com.ldt.quanlyca;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private Button btnLogin;
    private TextView tvSignup;
    private EditText edtEmail, edtUserPassword;
    private CheckBox cbRememberPassword;
    private FirebaseAuth mAuth;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        anhXa();

        sharedPreferences = getSharedPreferences("dataLogin", MODE_PRIVATE);
        edtEmail.setText(sharedPreferences.getString("email",""));
        edtUserPassword.setText(sharedPreferences.getString("userPassword", ""));
        cbRememberPassword.setChecked(sharedPreferences.getBoolean("checked", false));

//        createDefaultUsers();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // kiểm tra nhập thông tin

                login();

            }
        });

        tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), SignupActivity.class);
                startActivity(intent);
            }
        });
    }

    private void anhXa() {
        btnLogin = findViewById(R.id.btnLogin);
        tvSignup = findViewById(R.id.tvSignup);
        edtEmail = findViewById(R.id.edtEmailLogin);
        edtUserPassword = findViewById(R.id.edtPasswordLogin);
        cbRememberPassword = findViewById(R.id.cbRememberPassword);
    }

    private void login() {
        String email = edtEmail.getText().toString();
        String password = edtUserPassword.getText().toString();
        if(email.isEmpty()) {
            edtEmail.requestFocus();
            return;
        }
        if(password.isEmpty()) {
            edtUserPassword.requestFocus();
            return;
        }
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(LoginActivity.this, ListLoaiCaActivity.class);
                            intent.putExtra("user", user);
                            if(cbRememberPassword.isChecked()) {
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("email", email);
                                editor.putString("userPassword",  password);
                                editor.putBoolean("checked", true);
                                editor.commit();
                            } else {
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("email", "");
                                editor.putString("userPassword",  "");
                                editor.putBoolean("checked", false);
                                editor.commit();
                            }
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("aaa", "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}