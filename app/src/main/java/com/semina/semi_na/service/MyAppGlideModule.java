package com.semina.semi_na.service;

import android.content.Context;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.module.AppGlideModule;
import com.google.firebase.storage.StorageReference;

import java.io.InputStream;

public class MyAppGlideModule extends AppGlideModule {
//    @Override
//    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
//        registry.append(StorageReference.class, InputStream.class,new FirebaseImageLoader.Factory())
//    }
}
