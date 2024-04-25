package com.example.samsungschoolproject.view_adapter.calendar;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.samsungschoolproject.R;
import com.example.samsungschoolproject.utils.CalendarUtils;

import java.time.LocalDate;
import java.util.ArrayList;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder> {
    private final ArrayList<LocalDate> days;
    private final OnCalendarItemListener onCalendarItemListener;

    public CalendarAdapter(ArrayList<LocalDate> days, OnCalendarItemListener onCalendarItemListener){
        this.days = days;
        this.onCalendarItemListener = onCalendarItemListener;
    }
    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calendar_cell, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();

        // Задает размер для "месячного" режима (в нем количество дней > 15)
        if (days.size() > 15){
            layoutParams.height = (int) (parent.getHeight() * 0.166666666);
        }
        // Задает размер для "недельного" режима
        else{
            layoutParams.height = (parent.getHeight());
        }

        return new CalendarViewHolder(view, onCalendarItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {
        final LocalDate date = days.get(position);
        if (date == null){
            holder.day.setText("");
        }
        else{
            holder.day.setText(String.valueOf(date.getDayOfMonth()));
            if (date.equals(CalendarUtils.selectedDate)){ // Устанавливает серый цвет, если элемент был выбран
                holder.itemView.setBackgroundColor(Color.GRAY);
                CalendarUtils.selectedDatePosition = holder.getAdapterPosition();
                return;
            }
            holder.itemView.setBackgroundColor(Color.TRANSPARENT); // Очищает цвет, если элемент не должен быть подсвечен
        }
    }

    @Override
    public int getItemCount() {
        return days.size();
    }

    // Обновляет две ячейки календаря: новую выбранную и предыдущую
    public void resetBacklitItem(int new_position){
        notifyItemChanged(new_position);
        notifyItemChanged(CalendarUtils.selectedDatePosition);
    }

    public interface OnCalendarItemListener {
        void onItemClick(int position, String dayText);
    }

    public static class CalendarViewHolder extends RecyclerView.ViewHolder{
        private final View itemView;
        private final TextView day;
        private final OnCalendarItemListener onCalendarItemListener;

        public CalendarViewHolder(@NonNull View itemView, OnCalendarItemListener onCalendarItemListener) {
            super(itemView);
            this.itemView = itemView;
            this.onCalendarItemListener = onCalendarItemListener;
            day = itemView.findViewById(R.id.day);

            setOnClickListeners();
        }

        private void setOnClickListeners(){
            itemView.setOnClickListener(v -> {
                onCalendarItemListener.onItemClick(getAdapterPosition(), (String) day.getText());
            });
        }
    }
}
