package com.semina.semi_na.view.search;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.semina.semi_na.data.db.entity.Semina;
import com.semina.semi_na.databinding.ActivitySearchResultsBinding;
import com.semina.semi_na.view.adapter.SearchResultsAdapter;
import com.semina.semi_na.view.detail.SeminaDetailActivity;

public class SearchResultsActivity extends AppCompatActivity {

  private SearchResultsAdapter searchResultsAdapter;
  private ActivitySearchResultsBinding binding;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    binding = ActivitySearchResultsBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());

    // 아이콘 클릭 시 홈으로 이동 로직 추가
    binding.homeGoBtn.setOnClickListener(view -> {
      finish();
    });

    // 검색 버튼 클릭 이벤트
    binding.searchGoBtn.setOnClickListener(view -> {
      String searchQuery = binding.searchEditText.getText().toString();
      Log.d("SearchAdapter", "검색 값: " + searchQuery);
      if (searchQuery != null && !searchQuery.isEmpty()) {
        searchResultsAdapter.search(searchQuery);
      }
    });

    // 어뎁터 초기화
    searchResultsAdapter = new SearchResultsAdapter();

    // 아이템 클릭 이벤트
    searchResultsAdapter.setOnItemClickListener(new SearchResultsAdapter.OnItemClickListener() {
      @Override
      public void onItemClick(Semina semina) {
        Intent intent = new Intent(SearchResultsActivity.this, SeminaDetailActivity.class);
        intent.putExtra("Semina", semina);
        startActivity(intent);
      }
    });

    binding.recyclerview.setLayoutManager(new LinearLayoutManager(this));
    binding.recyclerview.setAdapter(searchResultsAdapter);
  }
}



