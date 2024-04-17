package com.example.samsungschoolproject.view_adapter.workout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.samsungschoolproject.R;
import com.example.samsungschoolproject.database.model.WorkoutTemplate;
import com.example.samsungschoolproject.databinding.TemplateItemBinding;
import com.example.samsungschoolproject.utils.WorkoutListUtils;

import java.util.List;

public class WorkoutTemplateListAdapter extends RecyclerView.Adapter<WorkoutTemplateListAdapter.WorkoutTemplateViewHolder> {

    private final List<WorkoutTemplate> workoutTemplates;
    private final OnWorkoutItemListener onWorkoutItemListener;

    public WorkoutTemplateListAdapter(List<WorkoutTemplate> items, OnWorkoutItemListener onWorkoutItemListener) {
        workoutTemplates = items;
        this.onWorkoutItemListener = onWorkoutItemListener;
    }

    @NonNull
    @Override
    public WorkoutTemplateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.template_item,
                parent,
                false
        );
        return new WorkoutTemplateViewHolder(view, onWorkoutItemListener);
    }

    @Override
    public void onBindViewHolder(final WorkoutTemplateViewHolder holder, int position) {
        holder.bind(workoutTemplates.get(position));
    }

    @Override
    public int getItemCount() {
        return workoutTemplates.size();
    }

    public interface OnWorkoutItemListener {
        void onWorkoutItemClick(int position);
    }

    public static class WorkoutTemplateViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TemplateItemBinding templateItemBinding;
        private final OnWorkoutItemListener onWorkoutItemListener;

        public WorkoutTemplateViewHolder(@NonNull View itemView, OnWorkoutItemListener onWorkoutItemListener) {
            super(itemView);
            templateItemBinding = TemplateItemBinding.bind(itemView);
            this.onWorkoutItemListener = onWorkoutItemListener;
            itemView.setOnClickListener(this);
        }

        public void bind(WorkoutTemplate workoutTemplate){
            String fieldText = "Название: " + workoutTemplate.name;
            templateItemBinding.name.setText(fieldText);
            templateItemBinding.approximateLength.setText(WorkoutListUtils.configureWorkoutLengthInfo(workoutTemplate.approximate_length));
        }

        @Override
        public void onClick(View v) {
            onWorkoutItemListener.onWorkoutItemClick(getAdapterPosition());
        }
    }
}