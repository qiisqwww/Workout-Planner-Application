package com.example.samsungschoolproject.database.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.samsungschoolproject.database.model.WorkoutExercise;

@Dao
public interface WorkoutExerciseDAO {
    @Insert
    public void addWorkoutExercise(WorkoutExercise workoutExercise);

    @Update
    public void updateWorkoutExercise(WorkoutExercise workoutExercise);

    @Delete
    public void deleteWorkoutExercise(WorkoutExercise workoutExercise);


    @Query("SELECT * FROM workout_exercises WHERE workout_id==:workoutId")
    public WorkoutExercise getWorkoutExercisesByWorkoutId(int workoutId);
}