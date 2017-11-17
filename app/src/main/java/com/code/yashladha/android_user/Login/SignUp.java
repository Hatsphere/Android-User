package com.code.yashladha.android_user.Login;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.code.yashladha.android_user.Helper;
import com.code.yashladha.android_user.Models.User;
import com.code.yashladha.android_user.Portal.Index;
import com.code.yashladha.android_user.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUp extends Fragment {

    public static final String TITLE = "Sign Up";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private TextInputLayout UsernameLayout;
    private TextInputLayout PasswordLayout;
    private TextInputLayout ConfirmPasswordLayout;
    private EditText SignUpUsername;
    private EditText SignUpPassword;
    private EditText SignUpConfirmPassword;
    private ProgressBar progressBar;
    private LinearLayout mainLayout;
    private Button signUp;

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currUser = mAuth.getCurrentUser();
        mAuth.addAuthStateListener(mAuthListener);
        if (currUser != null) {
            Log.i(TITLE, "User logged in already: " + currUser.getUid());
        }
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    public SignUp() {
        // Required empty public constructor
    }

    public static SignUp newInstance() {
        return new SignUp();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        UsernameLayout = view.findViewById(R.id.sign_up_layout_username);
        PasswordLayout = view.findViewById(R.id.sign_up_layout_password);
        ConfirmPasswordLayout = view.findViewById(R.id.signup_layout_confirm_password);
        SignUpUsername = view.findViewById(R.id.sign_up_username);
        SignUpPassword = view.findViewById(R.id.sign_up_password);
        SignUpConfirmPassword = view.findViewById(R.id.sign_up_confirm_password);
        signUp = view.findViewById(R.id.btn_signup);
        progressBar = view.findViewById(R.id.sign_up_progressBar);
        mainLayout = view.findViewById(R.id.sign_up_main);

        SignUpUsername.addTextChangedListener(new MyTextWatcher(SignUpUsername));
        SignUpPassword.addTextChangedListener(new MyTextWatcher(SignUpPassword));
        SignUpConfirmPassword.addTextChangedListener(new MyTextWatcher(SignUpConfirmPassword));
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d("User", "User Exists");
                    if (!user.isEmailVerified()) {
                        sendVerificationEmail();
                    }
                } else {
                    Log.d("User", "User doesnot exists");
                }
            }
        };

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                unhideProgress();

                if (!validateUserName()) {
                    hideProgress();
                    return;
                }

                if (!validatePassword()) {
                    hideProgress();
                    return;
                }

                if (!validateConfirmPassword()) {
                    hideProgress();
                    return;
                }

                final String email = SignUpUsername.getText().toString();
                String password = SignUpPassword.getText().toString();

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    final String uid = task.getResult().getUser().getUid();
                                    Log.d(TITLE, "createUserWithEmailAndPassword:success " + uid);
                                    User user = new User(0, email, "", "");
                                    Helper.Companion.getROOTREF().collection("users")
                                            .document(uid)
                                            .set(user)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    hideProgress();
                                                }
                                            })
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Log.d(TITLE, "User pushed");
                                                    Toast.makeText(view.getContext(), "Please verify Email", Toast.LENGTH_SHORT).show();
                                                    clearFields();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.w(TITLE, "Error while pushing user", e);
                                                }
                                            });
                                } else {
                                    Log.w(TITLE, "createUserWithEmailAndPassword:failure ", task.getException());
                                    Toast.makeText(getActivity(), "SignUp Failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
        return view;
    }

    private void Cleanup() {
        SignUpUsername.setText("");
        SignUpPassword.setText("");
        SignUpConfirmPassword.setText("");
    }

    private void sendVerificationEmail() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            user.sendEmailVerification()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d(getClass().getSimpleName(), "Email sent");
                            } else {
                                Log.e(getClass().getSimpleName(), "Email not sent");
                            }
                        }
                    });
        }
    }

    private void callIntent(String uid) {
        Intent intent = new Intent(getContext(), Index.class);
        intent.putExtra("UID", uid);
        startActivity(intent);
    }

    private void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
        mainLayout.setAlpha(1.0F);
    }

    private void unhideProgress() {
        progressBar.setVisibility(View.VISIBLE);
        mainLayout.setAlpha(0.5F);
    }

    private void clearFields() {
        SignUpUsername.setText("");
        SignUpPassword.setText("");
        SignUpConfirmPassword.setText("");
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        public MyTextWatcher(View view) {
            this.view = view;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.sign_up_username:
                    validateUserName();
                    break;
                case R.id.sign_up_password:
                    validatePassword();
                    break;
                case R.id.sign_up_confirm_password:
                    validateConfirmPassword();
                    break;
            }
        }
    }

    private boolean validateConfirmPassword() {
        String password = SignUpPassword.getText().toString();
        String confirmPassword = SignUpConfirmPassword.getText().toString();
        if (password.trim().isEmpty() || confirmPassword.trim().isEmpty() || !Objects.equals(password, confirmPassword)) {
            ConfirmPasswordLayout.setError("Confirm Password is not valid");
            requestFocus(SignUpConfirmPassword);
            return false;
        } else {
            ConfirmPasswordLayout.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validatePassword() {
        if (SignUpPassword.getText().toString().trim().isEmpty()) {
            PasswordLayout.setError("Password is not valid");
            requestFocus(SignUpPassword);
            return false;
        } else {
            PasswordLayout.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateUserName() {
        String email = SignUpUsername.getText().toString().trim();
        if (email.isEmpty() || !isValidEmail(email)) {
            UsernameLayout.setError("Username is not valid");
            requestFocus(SignUpUsername);
            return false;
        } else {
            UsernameLayout.setErrorEnabled(false);
        }
        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

}
