package com.example.samsungschoolproject.fragment.workout.lists;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ViewSwitcher;

import com.example.samsungschoolproject.DTO.ExerciseInfo;
import com.example.samsungschoolproject.DTO.TemplateInfo;
import com.example.samsungschoolproject.R;
import com.example.samsungschoolproject.database.WorkoutHelperDatabase;
import com.example.samsungschoolproject.database.model.Exercise;
import com.example.samsungschoolproject.database.model.WorkoutTemplate;
import com.example.samsungschoolproject.database.model.WorkoutTemplateExercise;
import com.example.samsungschoolproject.enums.TemplatesListStates;
import com.example.samsungschoolproject.fragment.workout.builder.TemplatesBuilderFragment;
import com.example.samsungschoolproject.fragment.workout.info.TemplateInfoFragment;
import com.example.samsungschoolproject.view_adapter.workout.list.WorkoutTemplateListAdapter;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class TemplatesListFragment extends Fragment implements WorkoutTemplateListAdapter.OnWorkoutItemListener, WorkoutTemplateListAdapter.DeleteWorkoutTemplateListener {
    private Button createNewTemplateButton;
    private WorkoutHelperDatabase database;
    private List<WorkoutTemplate> workoutTemplates;
    private RecyclerView workoutTemplatesRecycler;
    private WorkoutTemplateListAdapter workoutTemplateListAdapter;
    private ViewSwitcher templatesListSwitcher;
    private TemplatesListStates templatesListStates = TemplatesListStates.LIST_EMPTY_SHOWN;

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

        database = WorkoutHelperDatabase.getInstance(requireContext().getApplicationContext());

        initWidgets(view);
        initButtonListeners();
        loadTemplatesList();
    }

    private void initWidgets(View view){
        createNewTemplateButton = view.findViewById(R.id.addNewWorkoutTemplate);
        workoutTemplatesRecycler = view.findViewById(R.id.workoutTemplatesRecycler);
        templatesListSwitcher = view.findViewById(R.id.templatesListSwitcher);
    }

    private void initButtonListeners(){
        createNewTemplateButton.setOnClickListener(v -> {
            TemplatesBuilderFragment templatesBuilderFragment = new TemplatesBuilderFragment();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.workoutTemplatesContainer, templatesBuilderFragment)
                    .addToBackStack("TemplatesListFragment")
                    .commit();
        });
    }

    // Загружает список из WorkoutTemplate's из БД
    private void loadTemplatesList() {
        CompletableFuture<List<WorkoutTemplate>> future = CompletableFuture.supplyAsync(() -> database.getWorkoutTemplateDAO().getAllWorkoutTemplates());
        try {
            workoutTemplates = future.get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (workoutTemplates.isEmpty()) {
            if (templatesListStates.equals(TemplatesListStates.LIST_NOT_EMPTY_SHOWN))
                templatesListStates = TemplatesListStates.LIST_EMPTY_SHOWN;
            return;
        }

        if (templatesListStates.equals(TemplatesListStates.LIST_EMPTY_SHOWN)){
            templatesListSwitcher.showNext();
            templatesListStates = TemplatesListStates.LIST_NOT_EMPTY_SHOWN;
        }
        setWorkoutTemplatesRecycler();
    }

    private void setWorkoutTemplatesRecycler(){
        workoutTemplateListAdapter = new WorkoutTemplateListAdapter(workoutTemplates, this, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        workoutTemplatesRecycler.setLayoutManager(layoutManager);
        workoutTemplatesRecycler.setAdapter(workoutTemplateListAdapter);
    }

    @Override
    public void onWorkoutItemClick(int position) {
        CompletableFuture<TemplateInfo> future = CompletableFuture.supplyAsync(() -> {
            WorkoutTemplate workoutTemplate = workoutTemplateListAdapter.getItemByPosition(position);
            List<Exercise> exercises = database.getExerciseDAO().getAllExercises();
            List<WorkoutTemplateExercise> workoutTemplateExercises = database.getWorkoutTemplateExerciseDAO().getWorkoutTemplateExercisesByWorkoutTemplateId(workoutTemplate.id);

            return new TemplateInfo(
                    workoutTemplate,
                    ExerciseInfo.toExerciseInfoListForTemplate(
                            exercises,
                            workoutTemplateExercises)
            );
        });

        TemplateInfo templateInfo;
        try {
            templateInfo = future.get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        TemplateInfoFragment templateInfoFragment = new TemplateInfoFragment(templateInfo);
        TemplateInfoFragment.TAG = "New Instance"; // idk if this name is important

        templateInfoFragment.show(getActivity().getSupportFragmentManager(), TemplateInfoFragment.TAG);
    }

    @Override
    public void onDeleteButtonClick(int position) {
        // TODO
    }
}