package com.example.samsungschoolproject.fragment.templates;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.samsungschoolproject.R;

public class TemplatesListFragment extends Fragment {
    private Button createNewTemplateButton;
    private RecyclerView workoutTemplatesRecycler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_templates_list, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initWidgets(view);
        initButtonListeners();
        loadTemplatesList();
    }

    private void initWidgets(View view){
        createNewTemplateButton = view.findViewById(R.id.addNewWorkoutTemplate);
        workoutTemplatesRecycler = view.findViewById(R.id.workoutTemplatesRecycler);
    }

    private void initButtonListeners(){
        createNewTemplateButton.setOnClickListener(v -> {
            TemplatesBuilderFragment templatesListFragment = new TemplatesBuilderFragment();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.workoutTemplatesContainer, templatesListFragment)
                    .commit();
        });
    }

    private void loadTemplatesList(){

    }
}