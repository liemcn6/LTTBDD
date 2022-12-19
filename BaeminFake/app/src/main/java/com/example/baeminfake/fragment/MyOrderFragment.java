package com.example.baeminfake.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baeminfake.R;
import com.example.baeminfake.controller.CartRecyclerViewAdapter;
import com.example.baeminfake.controller.SQLiteCartHelper;
import com.example.baeminfake.model.Cart;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MyOrderFragment extends Fragment {

    private TextView turnback;
    private RecyclerView recyclerView;
    private CartRecyclerViewAdapter adapter;
    private SQLiteCartHelper sqLite;
    private FirebaseUser firebaseUser;

    public MyOrderFragment() {
    }

    public static MyOrderFragment newInstance() {
        MyOrderFragment fragment = new MyOrderFragment();
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
        View view = inflater.inflate(R.layout.myorder_fragment, container, false);
        init(view);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        adapter = new CartRecyclerViewAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        sqLite = new SQLiteCartHelper(getActivity());
        List<Cart> carts = sqLite.getCartUserPay(firebaseUser.getUid(), 1);
        adapter.setCarts(carts);
        recyclerView.setAdapter(adapter);

        this.turnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
                sqLite.close();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void init(View view) {
        turnback = view.findViewById(R.id.back_to_profile2);
        recyclerView = view.findViewById(R.id.list_history);
    }

    public void getFragment(Fragment current) {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.profile_holder, current)
                .addToBackStack(null)
                .commit();
    }
}
