package ru.mirea.tsitserid.firebaseauth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

import ru.mirea.tsitserid.firebaseauth.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private static final String TAG = MainActivity.class.getSimpleName();
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth = FirebaseAuth.getInstance();
        binding.VerifyEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmailVerification();
            }
        });
        binding.SignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });
        binding.SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn(binding.editTextTextEmailAddress.getText().toString(), binding.editTextTextPassword.getText().toString());
            }
        });
        binding.CreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount(binding.editTextTextEmailAddress.getText().toString(), binding.editTextTextPassword.getText().toString());
            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
// Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            binding.EmailUser.setText(getString(R.string.emailpassword_status_fmt,

                    user.getEmail(), user.isEmailVerified()));
            binding.FirebaseUID.setText(getString(R.string.firebase_status_fmt, user.getUid()));
            binding.EmailPasswordButtons.setVisibility(View.GONE);
            binding.EmailPasswordFields.setVisibility(View.GONE);
            binding.SignedInButtons.setVisibility(View.VISIBLE);
            binding.VerifyEmail.setEnabled(!user.isEmailVerified());
        } else {
            binding.EmailUser.setText(R.string.signed_out);
            binding.FirebaseUID.setText(null);
            binding.EmailPasswordButtons.setVisibility(View.VISIBLE);
            binding.EmailPasswordFields.setVisibility(View.VISIBLE);
            binding.SignedInButtons.setVisibility(View.GONE);
        }
    }

    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);
// [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
// Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
// If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
// [END create_user_with_email]
    }
    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
// [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
// Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
// If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
// [START_EXCLUDE]
                        if (!task.isSuccessful()) {
                            binding.EmailUser.setText(R.string.auth_failed);
                        }
// [END_EXCLUDE]
                    }
                });
// [END sign_in_with_email]
    }
    private void signOut() {
        mAuth.signOut();
        updateUI(null);
    }
    private void sendEmailVerification() {
// Disable button
        binding.VerifyEmail.setEnabled(false);
// Send verification email
// [START send_email_verification]
        final FirebaseUser user = mAuth.getCurrentUser();
        Objects.requireNonNull(user).sendEmailVerification().addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
// [START_EXCLUDE]
// Re-enable button
                        binding.VerifyEmail.setEnabled(true);
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Verification email sent to " + user.getEmail(), Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e(TAG, "sendEmailVerification", task.getException());
                            Toast.makeText(MainActivity.this, "Failed to send verification email.", Toast.LENGTH_SHORT).show();
                        }
// [END_EXCLUDE]
                    }
                });
// [END send_email_verification]
    }
}