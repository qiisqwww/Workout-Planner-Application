package com.example.samsungschoolproject.database.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.samsungschoolproject.database.entity.PlannedWorkoutExercise;

import java.util.List;

@Dao
public interface PlannedWorkoutExerciseDAO {
    @Insert
    void addPlannedWorkoutExercise(PlannedWorkoutExercise plannedWorkoutExercise);

    @Query("SELECT * FROM planned_workout_exercises WHERE planned_workout_id==:planned_workoutId")
    List<PlannedWorkoutExercise> getPlannedWorkoutExercisesByWorkoutId(int planned_workoutId);

    @Query("DELETE FROM planned_workout_exercises WHERE planned_workout_id==:plannedWorkoutId")
    void deletePlannedWorkoutExerciseByWorkoutId(int plannedWorkoutId);

    @Query("SELECT exercise_id FROM planned_workout_exercises " +
            "GROUP BY exercise_id " +
            "HAVING COUNT(exercise_id) = " +
                "(SELECT COUNT(exercise_id) as exercise_count FROM planned_workout_exercises " +
                "GROUP BY exercise_id " +
                "ORDER BY exercise_count " +
                "LIMIT 1)")
    int getTheMostPreferredExercise();
}
