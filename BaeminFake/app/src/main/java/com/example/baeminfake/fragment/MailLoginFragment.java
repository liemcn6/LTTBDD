package com.example.baeminfake.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.baeminfake.R;
import com.example.baeminfake.activity.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static com.example.baeminfake.activity.SplashActivity.login_code;

public class MailLoginFragment extends Fragment {

    private EditText username, password;
    private TextView login, createAccount;
    private FirebaseAuth firebaseAuth;

    public MailLoginFragment() {
    }

    public static MailLoginFragment newInstance() {
        MailLoginFragment fragment = new MailLoginFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mail_login_fragment, container, false);
        username = view.findViewById(R.id.auth_mail);
        password = view.findViewById(R.id.auth_mail_pass);
        login = view.findViewById(R.id.login);
        createAccount = view.findViewById(R.id.create_account);
        firebaseAuth = FirebaseAuth.getInstance();

        this.createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment current = new MailRegisterFragment();
                getFragment(current);
            }
        });

        this.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usname = username.getText().toString().trim();
                String passw = password.getText().toString().trim();

                if (TextUtils.isEmpty(usname)) {
                    username.setError("Email không được để trống");
                    return;
                }

                if (TextUtils.isEmpty(passw)) {
                    password.setError("Mật khẩu không được để trống");
                    return;
                }

                firebaseAuth.signInWithEmailAndPassword(usname, passw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            login_code = 0;
                            startActivity(new Intent(getActivity(), MainActivity.class));
                            getActivity().finish();
                        } else {
                            Toast.makeText(getActivity(), "Tên đăng nhập hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void getFragment(Fragment current) {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.author_holder, current)
                .addToBackStack(null)
                .commit();
    }
}