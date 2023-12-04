package com.semina.semi_na.view.mypage;

import static android.content.Context.MODE_PRIVATE;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.DialogFragment;
import com.semina.semi_na.R;
import com.semina.semi_na.view.login.LoginActivity;

public class LogoutModalFragment extends DialogFragment {

  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    LayoutInflater inflater = getActivity().getLayoutInflater();
    View customView = inflater.inflate(R.layout.fragment_logout_modal, null);

    //'취소'버튼
    AppCompatButton noButton = customView.findViewById(R.id.logout_no_btn);
    noButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        LogoutModalFragment.this.getDialog().dismiss();
      }
    });

    //'확인'버튼
    AppCompatButton yesButton = customView.findViewById(R.id.logout_yes_btn);
    yesButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        // 로그아웃 로직 처리
        SharedPreferences preferences = getActivity().getSharedPreferences("UserInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();

        // 앱 종료 또는 로그인 화면으로 이동
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        getActivity().finish();
      }
    });

    Dialog dialog = new Dialog(getActivity());
    dialog.setContentView(customView);
    dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    return dialog;
  }
}


