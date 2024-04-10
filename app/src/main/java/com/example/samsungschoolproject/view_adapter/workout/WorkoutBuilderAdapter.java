package com.example.samsungschoolproject.view_adapter.workout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.samsungschoolproject.R;
import com.example.samsungschoolproject.database.model.Exercise;

import java.util.List;

public class WorkoutBuilderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private int length = 4;
    private final List<Exercise> exercises;

    public WorkoutBuilderAdapter (List<Exercise> exercises){
        this.exercises = exercises;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        switch(viewType){
            case 0:
                view = LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.field_input_name,
                        parent,
                        false
                );
                return new InputNameViewHolder(view);
            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.field_choose_exercise,
                        parent,
                        false
                );
                return new ChooseExerciseViewHolder(view, this, exercises);
            case 2:
                view = LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.field_add_exercise,
                        parent,
                        false
                );
                return new AddExerciseViewHolder(view, this);
            case 3:
                view = LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.field_save_workout,
                        parent,
                        false
                );
                return new SaveWorkoutButtonViewHolder(view);
        }

        return null;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0){
            return 0; //  inputName Field
        }
        if (position == getItemCount()-2){
            return 2; //  addExercise Button
        }
        if (position == getItemCount()-1){
            return 3; //  saveWorkout Button
        }

        return 1; //  fillExercise Field
    }

    public void addExercise(){
        length++;
        notifyItemChanged(length-1);
        notifyItemChanged(length-2);
        notifyItemChanged(length-3);
    }

    public void deleteExercise(int position){
        length--;
        notifyItemRemoved(position);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
    }


    @Override
    public int getItemCount() {
        return length;
    }

    public static class InputNameViewHolder extends RecyclerView.ViewHolder{

        public InputNameViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public static class ChooseExerciseViewHolder extends RecyclerView.ViewHolder{
        private final Button deleteExerciseButton;
        private final Spinner exerciseListSpinner, approachesListSpinner, repeatsListSpinner;
        private final TextView showExercise, showApproaches, showRepeats;
        private final int[] approaches = {1, 2, 3, 4, 5};
        private final int[] repeats = {5, 8, 10, 12, 15, 20};
        private final List<Exercise> exercises;
        private final WorkoutBuilderAdapter workoutBuilderAdapter;

        public ChooseExerciseViewHolder(@NonNull View itemView, WorkoutBuilderAdapter workoutBuilderAdapter, List<Exercise> exercises) {
            super(itemView);

            deleteExerciseButton = itemView.findViewById(R.id.deleteExercise);
            exerciseListSpinner = itemView.findViewById(R.id.exerciseList);
            approachesListSpinner = itemView.findViewById(R.id.approachesList);
            repeatsListSpinner = itemView.findViewById(R.id.repeatsList);
            showExercise = itemView.findViewById(R.id.showExercise);
            showApproaches = itemView.findViewById(R.id.showApproaches);
            showRepeats = itemView.findViewById(R.id.showRepeats);
            this.exercises = exercises;

            this.workoutBuilderAdapter = workoutBuilderAdapter;
            initButtonListeners(getAdapterPosition());
        }

        private void initButtonListeners(int position){
            deleteExerciseButton.setOnClickListener(v -> {
                workoutBuilderAdapter.deleteExercise(position);
            });
        }
    }

    public static class AddExerciseViewHolder extends RecyclerView.ViewHolder{
        private final Button addExerciseButton;
        private final WorkoutBuilderAdapter workoutBuilderAdapter;

        public AddExerciseViewHolder(@NonNull View itemView, WorkoutBuilderAdapter workoutBuilderAdapter) {
            super(itemView);
            addExerciseButton = itemView.findViewById(R.id.addExercise);
            this.workoutBuilderAdapter = workoutBuilderAdapter;
            initButtonListeners();
        }

        private void initButtonListeners(){
            addExerciseButton.setOnClickListener(v -> {
                workoutBuilderAdapter.addExercise();
            });
        }
    }

    public static class SaveWorkoutButtonViewHolder extends RecyclerView.ViewHolder{

        public SaveWorkoutButtonViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}