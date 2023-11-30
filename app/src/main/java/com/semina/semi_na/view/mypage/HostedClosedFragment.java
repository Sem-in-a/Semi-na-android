package com.semina.semi_na.view.mypage;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.paging.PagingConfig;
import androidx.recyclerview.widget.GridLayoutManager;
import com.firebase.ui.firestore.paging.FirestorePagingAdapter;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.semina.semi_na.R;
import com.semina.semi_na.data.db.entity.Semina;
import com.semina.semi_na.databinding.FragmentHostedClosedBinding;
import com.semina.semi_na.databinding.SeminarCardViewItemBinding;
import com.semina.semi_na.view.viewHolder.SeminarCardViewHolder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

// 내가 주최한 세미나 - 마감 된 : 프론트 단에서 현재 날짜 비교
public class HostedClosedFragment extends Fragment {

  private FragmentHostedClosedBinding binding;
  private FirestorePagingAdapter<Semina, SeminarCardViewHolder> adapter;

  private String getCurrentUserId() {
    SharedPreferences preferences = getActivity().getSharedPreferences("UserInfo", MODE_PRIVATE);
    return preferences.getString("studentNum", "");
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    binding = FragmentHostedClosedBinding.inflate(inflater, container, false);
    View view = binding.getRoot();

    String currentUserId = getCurrentUserId();
    Date currentDate = new Date();

    Query baseQuery = FirebaseFirestore.getInstance()
        .collection("Semina")
        .whereEqualTo("host", currentUserId)
        .orderBy("date", Query.Direction.DESCENDING);

    // 페이징 구성
    PagingConfig config = new PagingConfig(10, 5, false);

    // 어댑터 옵션 설정
    FirestorePagingOptions<Semina> options = new FirestorePagingOptions.Builder<Semina>()
        .setLifecycleOwner(this)
        .setQuery(baseQuery, config, Semina.class)
        .build();

    // 어댑터 설정
    adapter = new FirestorePagingAdapter<Semina, SeminarCardViewHolder>(options) {
      @Override
      protected void onBindViewHolder(@NonNull SeminarCardViewHolder holder, int position, @NonNull Semina model) { // 뷰 홀더 이름 변경
        // 날짜 문자열을 Date 객체로 파싱
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년 MM월 dd일", Locale.KOREA);
        try {
          Date seminarDate = dateFormat.parse(model.getDate()); // 날짜 문자열 파싱
          Date currentDate = new Date(); // 현재 날짜

          // 세미나 날짜가 현재 날짜 이전인지 확인
          if (seminarDate != null && seminarDate.before(currentDate)) {
            // 과거 날짜인 경우에만 뷰 홀더에 데이터를 바인딩
            holder.bind(model);
            holder.itemView.setVisibility(View.VISIBLE); // 뷰 홀더를 표시
          } else {
            // 아닌 경우에는 카드뷰를 숨김
            holder.itemView.setVisibility(View.GONE);
          }
        } catch (ParseException e) {
          Log.e("HostedClosedFragment", "날짜 파싱 에러", e);
        }
      }

      @NonNull
      @Override
      public SeminarCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("HostedClosedFragment", "onCreateViewHolder is called");
        View itemView = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.seminar_card_view_item, parent, false);
        SeminarCardViewItemBinding binding = SeminarCardViewItemBinding.bind(itemView);
        return new SeminarCardViewHolder(binding);
      }
    };

    // 리사이클러뷰 설정
    binding.hostedRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
    binding.hostedRecyclerView.setAdapter(adapter);

    return view;
  }

  @Override
  public void onStart() {
    super.onStart();
    if (adapter != null) {
      adapter.startListening();
    }
  }

  @Override
  public void onStop() {
    super.onStop();
    if (adapter != null) {
      adapter.stopListening();
    }
  }
}


