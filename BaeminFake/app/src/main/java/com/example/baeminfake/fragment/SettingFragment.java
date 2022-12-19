package com.example.baeminfake.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.baeminfake.R;

import java.util.ArrayList;
import java.util.List;

public class SettingFragment extends Fragment {

    private TextView save_setting;
    private Spinner textsize, themes, language;
    private List<String> listsize = new ArrayList<>();
    private List<String> listthemes = new ArrayList<>();
    private List<String> listlanguage = new ArrayList<>();

    public SettingFragment() {
    }

    public static SettingFragment newInstance() {
        SettingFragment fragment = new SettingFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings, container, false);
        textsize = view.findViewById(R.id.text_size);
        themes = view.findViewById(R.id.theme);
        language = view.findViewById(R.id.language);
        save_setting = view.findViewById(R.id.save_settings);

        listsize.add("Nhỏ");
        listsize.add("Vừa");
        listsize.add("Lớn");
        listthemes.add("Dark Theme");
        listthemes.add("Light Theme");
        listthemes.add("Dracula Theme");
        listlanguage.add("Tiếng Việt");
        listlanguage.add("English");
        listlanguage.add("French");
        listlanguage.add("Japanese");
        textsize.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, listsize));
        themes.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, listthemes));
        language.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, listlanguage));

        this.save_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}