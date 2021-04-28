package br.com.etec.ddm.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.etec.ddm.R;
import br.com.etec.ddm.model.ScheduleModel;

public class ScheduleDetailAdapter extends RecyclerView.Adapter<ScheduleDetailAdapter.ScheduleDetailViewHolder> {

    List<ScheduleModel> scheduleList;

    public ScheduleDetailAdapter(List<ScheduleModel> scheduleList){
        this.scheduleList = scheduleList;
    }

    @NonNull
    @Override
    public ScheduleDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_schedule_detail, parent, false);
        return new ScheduleDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleDetailViewHolder holder, int position) {
        holder.bind(scheduleList.get(position));
    }

    @Override
    public int getItemCount() {
        return scheduleList.size();
    }

    class ScheduleDetailViewHolder extends RecyclerView.ViewHolder{

        public ScheduleDetailViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void bind(ScheduleModel model){
            TextView detail = itemView.findViewById(R.id.isd_detail);
            detail.setText(model.getDetail());
        }
    }

}
