/*package com.semina.semi_na.viewModel;

import androidx.recyclerview.widget.RecyclerView;

//주최한 카드뷰를 위한 어뎁터
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.google.firebase.firestore.DocumentSnapshot;
import com.semina.semi_na.R;
import java.util.List;

public class HostedSeminarAdapter extends RecyclerView.Adapter<HostedSeminarAdapter.HostedSeminarViewHolder> {

  private List<DocumentSnapshot> seminarDocuments;

  public HostedSeminarAdapter(List<DocumentSnapshot> seminarDocuments) {
    this.seminarDocuments = seminarDocuments;
  }

  @NonNull
  @Override
  public HostedSeminarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_hosted_proceeding, parent, false);
    return new HostedSeminarViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull HostedSeminarViewHolder holder, int position) {
    DocumentSnapshot document = seminarDocuments.get(position);
    holder.bind(document);
  }

  @Override
  public int getItemCount() {
    return seminarDocuments != null ? seminarDocuments.size() : 0;
  }

  static class HostedSeminarViewHolder extends RecyclerView.ViewHolder {
    private ImageView imageView;
    private TextView titleTextView;
    private TextView descriptionTextView;
    private TextView underDepartmentTextView;
    private TextView locationDetailTextView;
    private TextView majorTextView;
    private TextView gradeTextView;
    private TextView nameTextView;
    private TextView capacityTextView;

    HostedSeminarViewHolder(@NonNull View itemView) {
      super(itemView);
      imageView = itemView.findViewById(R.id.hosted_img);
      titleTextView = itemView.findViewById(R.id.hosted_title);
      descriptionTextView = itemView.findViewById(R.id.hosted_description);
      underDepartmentTextView = itemView.findViewById(R.id.hosted_under_department);
      locationDetailTextView = itemView.findViewById(R.id.hosted_location_detail);
      gradeTextView = itemView.findViewById(R.id.hosted_grade);
      capacityTextView = itemView.findViewById(R.id.hosted_capacity);
    }

    void bind(DocumentSnapshot document) {
      // 데이터 추출
      String imgUrl = document.getString("imgUrl");
      String title = document.getString("title");
      String description = document.getString("description");
      String underDepartment = document.getString("underDepartment");
      String locationDetail = document.getString("locationDetail");
      String major = document.getString("major");
      String grade = document.getString("grade");
      String name = document.getString("name");
      Long capacity = document.getLong("capacity");

      // 데이터 바인딩
      Glide.with(itemView.getContext()).load(imgUrl).into(imageView);
      titleTextView.setText(title);
      descriptionTextView.setText(description);
      underDepartmentTextView.setText(underDepartment);
      locationDetailTextView.setText(locationDetail);
      majorTextView.setText(major);
      gradeTextView.setText(grade);
      nameTextView.setText(name);
      capacityTextView.setText(String.format("%d명", capacity));
    }
  }
}
*/
