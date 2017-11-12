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

import com.code.yashladha.android_user.Portal.Index;
import com.code.yashladha.android_user.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * A simple {@link Fragment} subclass.
 */
public class Login extends Fragment {

    public static final String TITLE = "Login";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private TextInputLayout UsernameLayout;
    private TextInputLayout PasswordLayout;
    private EditText LoginUsername;
    private EditText LoginPassword;
    private Button login;
    private ProgressBar progressBar;
    private LinearLayout mainLayout;

    public Login() {
        // Required empty public constructor
    }

    public static Login newInstance() {
        return new Login();
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            Log.i(TITLE, "User already present");
        }
    }

    public String getUserId()
    {
        FirebaseUser user = mAuth.getCurrentUser();
        String userId = user.getUid();
        return userId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        UsernameLayout = view.findViewById(R.id.login_layout_username);
        PasswordLayout = view.findViewById(R.id.login_layout_password);
        LoginUsername = view.findViewById(R.id.login_username);
        LoginPassword = view.findViewById(R.id.login_password);
        login = view.findViewById(R.id.btn_login);
        progressBar = view.findViewById(R.id.login_progress_bar);
        mainLayout = view.findViewById(R.id.login_main);

        mAuth = FirebaseAuth.getInstance();

        LoginUsername.addTextChangedListener(new MyTextWatcher(LoginUsername));
        LoginPassword.addTextChangedListener(new MyTextWatcher(LoginPassword));

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unhideProgress();

                if (!validateUserName()) {
                    hideProgress();
                    return;
                }

                if (!validatePassword()) {
                    hideProgress();
                    return;
                }

                String email = LoginUsername.getText().toString();
                String password = LoginPassword.getText().toString();

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                hideProgress();
                                if (task.isSuccessful()) {
                                    String uid = task.getResult().getUser().getUid();
                                    Log.d(TITLE, "signInWithEmailAndPassword:success " + uid);
                                    callIntent(uid);
                                } else {
                                    Log.w(TITLE, "signInWithEmailAndPassword:failure " + task.getException());
                                    Toast.makeText(getActivity(), "Login Failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });

        return view;
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
                case R.id.login_username:
                    validateUserName();
                    break;
                case R.id.login_password:
                    validatePassword();
                    break;
            }
        }
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private boolean validatePassword() {
        if (LoginPassword.getText().toString().trim().isEmpty()) {
            PasswordLayout.setError("Password is not valid");
            requestFocus(LoginPassword);
            return false;
        } else {
            PasswordLayout.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateUserName() {
        String email = LoginUsername.getText().toString().trim();
        if (email.isEmpty() || !isValidEmail(email)) {
            UsernameLayout.setError("Username is not valid");
            requestFocus(LoginUsername);
            return false;
        } else {
            UsernameLayout.setErrorEnabled(false);
        }
        return true;
    }

    private boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

}
