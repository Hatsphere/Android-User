package com.example.yashladha.android_user.Login;


import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;

import com.example.yashladha.android_user.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Login extends Fragment {

    public static final String TITLE = "Login";

    private TextInputLayout UsernameLayout;
    private TextInputLayout PasswordLayout;
    private EditText LoginUsername;
    private EditText LoginPassword;

    public Login() {
        // Required empty public constructor
    }

    public static Login newInstance() {
        return new Login();
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

        LoginUsername.addTextChangedListener(new MyTextWatcher(LoginUsername));
        LoginPassword.addTextChangedListener(new MyTextWatcher(LoginPassword));

        return view;
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
