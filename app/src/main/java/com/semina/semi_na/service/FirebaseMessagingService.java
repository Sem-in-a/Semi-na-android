package com.semina.semi_na.service;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;
import com.semina.semi_na.R;

import java.io.IOException;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    private final String CHANNEL_ID = "semina_channel";
    private final String CHANNEL_NAME = "semina";
    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                // Get new FCM registration token
                String token = task.getResult();
                sendTokenToServer(token);
            }
        });
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());

        if (notificationManager.getNotificationChannel(CHANNEL_ID) == null) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder;

        String title = Objects.requireNonNull(remoteMessage.getNotification()).getTitle();
        String body = remoteMessage.getNotification().getBody();

        builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID);

        builder.setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.drawable.ic_launcher_background);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManager.notify(1, builder.build());
    }

    private void sendTokenToServer(String token) {
        // HTTP 요청을 사용하여 Cloud Function에 토큰 전송
        // 이 부분에서는 적절한 서버 엔드포인트를 사용하세요
        String serverUrl = "https://semina-bfeab.firebaseapp.com/saveToken";

        // 예시: OkHttpClient를 사용한 간단한 HTTP POST 요청
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\"token\":\"" + token + "\"}");
        Request request = new Request.Builder()
                .url(serverUrl)
                .post(body)
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                // 서버로의 토큰 전송이 성공한 경우의 처리
            } else {
                // 서버로의 토큰 전송이 실패한 경우의 처리
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
