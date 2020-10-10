package com.example.aralarm.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Switch;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.aralarm.R;
import com.example.aralarm.data.Alarm;

import java.util.List;

public class AlarmListAdapter extends RecyclerView.Adapter<AlarmListAdapter.AlarmViewHolder> {

    private List<Alarm> mAlarms;
    private final LayoutInflater mInflater;
    public AlarmListAdapter(Context context) {mInflater = LayoutInflater.from(context);}

    private OnItemClickListener mListener = null;
    public interface OnItemClickListener{ void onItemClick(View v, int position); }
    public void setOnItemClickListener(OnItemClickListener listener){ mListener = listener; }

    private OnItemLongClickListener mLongListener = null;
    public interface OnItemLongClickListener{ void onItemLongClick(View v, int position); }
    public void setOnItemLongClickListener(OnItemLongClickListener listener){ mLongListener = listener; }

    private OnSwitchClickListener mSwitchListener = null;
    public interface OnSwitchClickListener{ void onSwitchClick(View v, int position); }
    public void setOnSwitchClickListener(OnSwitchClickListener listener){ mSwitchListener = listener;}

    public Alarm getAlarm(int pos){
        return  mAlarms.get(pos);
    }

    private boolean checkVis = false;

    public void showCheckBox(boolean checkVis){
        this.checkVis = checkVis;

        notifyDataSetChanged();
    }


    @Override
    public AlarmViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = mInflater.inflate(R.layout.item_alarm, parent, false);
        return new AlarmViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AlarmViewHolder holder, int position){
        holder.itemView.setOnClickListener(v -> holder.del.performClick());
        if(mAlarms != null){
            Alarm current = mAlarms.get(position);
            holder.dateView.setText(current.getYear() + "년 " + current.getMonth() + "월 " + current.getDay() + "일");
            if(Integer.parseInt(current.getHour()) < 13)
                holder.timeView.setText("오전 " + current.getHour() + "시 " + current.getMinute() + "분");
            else
                holder.timeView.setText("오후 " + (current.getIntHour() - 12) + "시 " + current.getMinute() + "분");
            holder.on.setChecked(current.isOn());

            if(checkVis == true){
                holder.del.setVisibility(View.VISIBLE);
                holder.on.setVisibility(View.GONE);
            }
            else{
                holder.del.setVisibility(View.GONE);
                holder.on.setVisibility(View.VISIBLE);
            }
        } else{
            holder.dateView.setText("No Date");
            holder.timeView.setText("No Time");
            holder.on.setChecked(false);
        }
    }

    public void setAlarms(List<Alarm> alarms){
        mAlarms = alarms;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount(){
        if(mAlarms != null)
            return mAlarms.size();
        else return 0;
    }

    class AlarmViewHolder extends RecyclerView.ViewHolder{
        private final TextView dateView;
        private final TextView timeView;
        private final Switch on;
        private final CheckBox del;

        private AlarmViewHolder(View itemView){
            super(itemView);
            dateView = itemView.findViewById(R.id.txt_alarm_date);
            timeView = itemView.findViewById(R.id.txt_alarm_time);
            on = itemView.findViewById(R.id.switch_alarm);
            del = itemView.findViewById(R.id.cb_delete);

            itemView.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if(pos != RecyclerView.NO_POSITION)
                    if(mListener != null)
                        mListener.onItemClick(v, pos);
            });

            itemView.setOnLongClickListener(v -> {
                int pos = getAdapterPosition();
                if(pos != RecyclerView.NO_POSITION)
                    if(mLongListener != null)
                        mLongListener.onItemLongClick(v, pos);
                return true;
            });

            on.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if(pos != RecyclerView.NO_POSITION)
                    if(mSwitchListener != null)
                        mSwitchListener.onSwitchClick(v, pos);
            });
        }
    }
}
