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

import com.example.baeminfake.R;
import com.example.baeminfake.activity.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import static com.example.baeminfake.activity.SplashActivity.login_code;

public class MailRegisterFragment extends Fragment {

    private EditText regisName, regisMail, regisPass, regisRePass;
    private TextView register;
    private FirebaseAuth firebaseAuth;

    public MailRegisterFragment() {

    }

    public static MailRegisterFragment newInstance() {
        MailRegisterFragment fragment = new MailRegisterFragment();
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
        View view = inflater.inflate(R.layout.mail_register_fragment, container, false);
        regisName = view.findViewById(R.id.regis_name);
        regisMail = view.findViewById(R.id.regis_mail);
        regisPass = view.findViewById(R.id.regis_password);
        regisRePass = view.findViewById(R.id.regis_repassword);
        register = view.findViewById(R.id.register);
        firebaseAuth = FirebaseAuth.getInstance();

        this.register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strml = regisMail.getText().toString().trim();
                String strpw = regisPass.getText().toString().trim();
                String strrp = regisRePass.getText().toString().trim();
                String strnm = regisName.getText().toString().trim();

                if (TextUtils.isEmpty(strnm)) {
                    regisName.setError("Không được để trống tên người dùng");
                    return;
                }

                if (TextUtils.isEmpty(strml)) {
                    regisMail.setError("Không được để trống email đăng nhập");
                    return;
                }

                if (strpw.length() < 6) {
                    regisPass.setError("Độ dài mật khẩu phải lớn hơn 6 kí tự");
                    return;
                }

                if (strpw.equals(strrp) != true) {
                    regisRePass.setError("Mật khẩu bạn vừa nhập không trùng khớp");
                    return;
                }

                firebaseAuth.createUserWithEmailAndPassword(strml, strpw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(strnm)
                                    .build();
                            user.updateProfile(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(getActivity(), "Đăng kí tà khoản thành công!", Toast.LENGTH_SHORT).show();
                                    login_code = 0;
                                    startActivity(new Intent(getActivity(), MainActivity.class));
                                    getActivity().finish();
                                }
                            });
                        } else {
                            Toast.makeText(getActivity(), "Email đã tồn tại!", Toast.LENGTH_SHORT).show();
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
}