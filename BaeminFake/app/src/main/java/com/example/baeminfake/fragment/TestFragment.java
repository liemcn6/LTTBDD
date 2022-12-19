package com.example.baeminfake.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.baeminfake.R;
import com.example.baeminfake.activity.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class TestFragment extends Fragment {

    private TextView logout, name;
    private FirebaseAuth firebaseAuth;

    public TestFragment() {
    }

    public static TestFragment newInstance() {
        TestFragment fragment = new TestFragment();
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
        View view = inflater.inflate(R.layout.test_fragment, container, false);
        name = view.findViewById(R.id.hello);
        logout = view.findViewById(R.id.logout);
        firebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser user = firebaseAuth.getCurrentUser();
        name.setText("Xin chao: " + user.getDisplayName());

        this.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
