package com.semina.semi_na.view.adapter;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.semina.semi_na.R;
import com.semina.semi_na.data.db.entity.Location;

import java.util.ArrayList;

public class CreateLocationDetailRVAdapter extends RecyclerView.Adapter<CreateLocationDetailRVAdapter.ViewHolder>{
    private ArrayList<String> locationDetailList;

    public interface OnItemClickListener {
        void onItemClick(View v, int position) ;
    }

    // 리스너 객체 참조를 저장하는 변수
    private CreateLocationDetailRVAdapter.OnItemClickListener mListener = null ;

    // OnItemClickListener 리스너 객체 참조를 어댑터에 전달하는 메서드
    public void setOnItemClickListener(CreateLocationDetailRVAdapter.OnItemClickListener listener) {
        this.mListener = listener ;
    }


    @NonNull
    @Override
    public CreateLocationDetailRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.create_location_detail_rv_item, parent, false);
        return new CreateLocationDetailRVAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CreateLocationDetailRVAdapter.ViewHolder holder, int position) {
        holder.onBind(locationDetailList.get(position));
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setLocationDetailList(ArrayList<String> list){
        this.locationDetailList = list;
        Log.d("CreateLDRV", String.valueOf(list.size()));
        notifyDataSetChanged(); // 어뎁터에 데이터가 바뀜을 알림
    }

    @Override
    public int getItemCount() {
        if (locationDetailList == null) {
            return 0; // 리스트가 null인 경우 항목 개수는 0
        } else {
            return locationDetailList.size();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.create_location_textView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition() ;
                    if (pos != RecyclerView.NO_POSITION) {
                        // 리스너 객체의 메서드 호출.
                        if (mListener != null) {
                            mListener.onItemClick(v, pos) ;
                        }
                    }
                }
            });

        }

        void onBind(String item){
            name.setText(item);
        }
    }
}
