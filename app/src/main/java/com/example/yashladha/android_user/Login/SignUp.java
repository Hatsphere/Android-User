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
public class SignUp extends Fragment {

    public static final String TITLE = "Sign Up";

    private TextInputLayout UsernameLayout;
    private TextInputLayout PasswordLayout;
    private TextInputLayout ConfirmPasswordLayout;
    private EditText SignUpUsername;
    private EditText SignUpPassword;
    private EditText SignUpConfirmPassword;

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
        if (password.trim().isEmpty() || confirmPassword.trim().isEmpty() || password != confirmPassword) {
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
