package com.example.samsungschoolproject.fragment.workout;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.samsungschoolproject.R;
import com.example.samsungschoolproject.database.WorkoutHelperDatabase;
import com.example.samsungschoolproject.database.model.Exercise;
import com.example.samsungschoolproject.utils.ExerciseListUtils;
import com.example.samsungschoolproject.view_adapter.workout.WorkoutBuilderAdapter;

import java.util.ArrayList;
import java.util.List;


public class TemplatesBuilderFragment extends Fragment implements WorkoutBuilderAdapter.StartPreviousFragment {
    private Button goBackButton;
    private WorkoutHelperDatabase database;
    private RecyclerView templateBuilderRecycler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_templates_builder, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        database = WorkoutHelperDatabase.getInstance(requireContext().getApplicationContext()); //  Получить объект БД

        initWidgets(view);
        initButtonListeners();

        setWorkoutBuilderRecycler();
    }

    private void initWidgets(View view){
        goBackButton = view.findViewById(R.id.goBack);

        templateBuilderRecycler = view.findViewById(R.id.templateBuilderRecycler);
    }

    private void initButtonListeners(){
        goBackButton.setOnClickListener(v -> {
            TemplatesListFragment templatesListFragment = new TemplatesListFragment();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.popBackStack();
            fragmentManager.beginTransaction()
                    .replace(R.id.workoutTemplatesContainer, templatesListFragment)
                    .commit();
        });
    }

    private List<Exercise> getAllExercises(){
        List<Exercise> exercises = database.getExerciseDAO().getAllExercises();
        return exercises;
    }

    private void setWorkoutBuilderRecycler(){
        List<String> stringExercises = ExerciseListUtils.parseExerciseToStrings(getAllExercises());

        WorkoutBuilderAdapter workoutBuilderAdapter = new WorkoutBuilderAdapter(stringExercises, templateBuilderRecycler, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        templateBuilderRecycler.setLayoutManager(layoutManager);
        templateBuilderRecycler.setAdapter(workoutBuilderAdapter);
    }

    @Override
    public void startPreviousFragment() {
        TemplatesListFragment templatesListFragment = new TemplatesListFragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.remove(new TemplatesListFragment());
        fragmentTransaction.replace(R.id.workoutTemplatesContainer, templatesListFragment).commit();
    }
}