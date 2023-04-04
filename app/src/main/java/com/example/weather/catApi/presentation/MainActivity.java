package com.example.weather.catApi.presentation;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

import com.example.weather.R;
import com.example.weather.WeatherApplication;
import com.example.weather.catApi.data.dataSource.CatLocalDataSourcesImpl;
import com.example.weather.catApi.data.dataSource.CatRemoteDataSourceImpl;
import com.example.weather.catApi.domain.useCase.GetCachedImageUseCase;
import com.example.weather.catApi.domain.useCase.LoadNewCatUseCase;
import com.example.weather.core.files.FileUtils;
import com.example.weather.core.newtwork.callbacks.RequestFileListener;
import com.example.weather.databinding.ActivityMainBinding;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SplashScreen.installSplashScreen(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mainState(getCurrentImage());
        binding.buttonGetLink.setOnClickListener(v -> {
            loadingState();
            setNewImageCat();
        });
    }

    private void setNewImageCat() {
        LoadNewCatUseCase newCatUseCase = new LoadNewCatUseCase(
                new CatRemoteDataSourceImpl(WeatherApplication.get().mainIOPool)
        );
        newCatUseCase.invoke(WeatherApplication.get().mainThreadHandler, new RequestFileListener() {
            @Override
            public void onResponse(File file) {
                mainState(FileUtils.fileToBitMap(file));
            }

            @Override
            public void onError(Exception e) {
                mainState(getCurrentImage());
                showToast(getString(R.string.default_error_msg));
            }
        });
    }

    @Nullable
    private Bitmap getCurrentImage() {
        GetCachedImageUseCase cachedCatUC = new GetCachedImageUseCase(
                new CatLocalDataSourcesImpl()
        );
        File tmpFile = cachedCatUC.invoke();
        if (tmpFile != null) {
            return FileUtils.fileToBitMap(tmpFile);
        }
        return null;
    }

    private void loadingState() {
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.buttonGetLink.setVisibility(View.GONE);
        binding.catImage.setVisibility(View.GONE);
    }

    private void mainState(@Nullable Bitmap bmp) {
        if (bmp == null) {
            binding.catImage.setImageResource(R.drawable.ic_cat_placeholder);
            binding.buttonGetLink.setText(R.string.main_screen_get_image_cat);
        } else {
            binding.catImage.setImageBitmap(bmp);
            binding.buttonGetLink.setText(R.string.main_screen_refresh_image_cat);
        }
        binding.catImage.setVisibility(View.VISIBLE);
        binding.buttonGetLink.setVisibility(View.VISIBLE);
        binding.progressBar.setVisibility(View.GONE);
    }

    private void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}