package com.ldt.quanlyca;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {
    TextView tvLogin;
    Button btnSignup;
    EditText edtFirstName, edtLastName, edtUserName, edtPassword, edtConfirmPassword, edtEmail;
    User user;
    private FirebaseAuth mAuth;
    private DatabaseReference mData = FirebaseDatabase.getInstance().getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        anhXa();
        mAuth = FirebaseAuth.getInstance();

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
            }
        });

    }

    private void signUp() {
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString();
        String confirmPassword = edtConfirmPassword.getText().toString();
        String firstName = edtFirstName.getText().toString().trim();
        String lastName = edtLastName.getText().toString().trim();
        String userName = edtUserName.getText().toString().trim();
        String fullName =firstName + " " + lastName;
        if(firstName.isEmpty()) {
            edtFirstName.requestFocus();
            return;
        }
        if(lastName.isEmpty()) {
            edtLastName.requestFocus();
            return;
        }
        if(email.isEmpty()) {
            edtEmail.requestFocus();
            return;
        }
        if(userName.isEmpty()) {
            edtUserName.requestFocus();
            return;
        }
        if(password.isEmpty()) {
            edtPassword.requestFocus();
            return;
        }
        if(confirmPassword.isEmpty()) {
            edtConfirmPassword.requestFocus();
            return;
        }
        if(!confirmPassword.equals(password)) {
            Toast.makeText(this, "Confirm password không chính xác!", Toast.LENGTH_SHORT).show();
            edtConfirmPassword.requestFocus();
            return;
        }

        User u = new User(userName, password,  fullName, email);
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        FirebaseUser user = mAuth.getCurrentUser();
                        mData.child("Users").child(user.getUid()).setValue(u);
                        Toast.makeText(SignupActivity.this, "Đăng ký thành công.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                        finish();
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(SignupActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(SignupActivity.this, "Đăng ký thất bại.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
//            reload();
        }
    }

    private void anhXa() {
        tvLogin = findViewById(R.id.tvLogin);
        btnSignup = findViewById(R.id.btnSignup);
        edtFirstName = findViewById(R.id.edtFirstName);
        edtLastName = findViewById(R.id.edtLastName);
        edtUserName = findViewById(R.id.edtUsernameSignup);
        edtPassword = findViewById(R.id.edtPasswordSignup);
        edtConfirmPassword = findViewById(R.id.edtConfirmPasswordSignup);
        edtEmail = findViewById(R.id.edtEmailSignup);
    }
}