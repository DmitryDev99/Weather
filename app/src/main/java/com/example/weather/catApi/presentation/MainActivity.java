package com.example.weather.catApi.presentation;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

import com.example.weather.R;
import com.example.weather.WeatherApplication;
import com.example.weather.catApi.data.dataSource.CatImageRemoteDataSourceImpl;
import com.example.weather.catApi.data.dataSource.CatRemoteDataSourceImpl;
import com.example.weather.catApi.data.entity.CatResponse;
import com.example.weather.catApi.domain.CatImageRemoteDataSource;
import com.example.weather.catApi.domain.CatRemoteDataSource;
import com.example.weather.core.newtwork.callbacks.RequestFileListener;
import com.example.weather.core.newtwork.callbacks.RequestListener;
import com.example.weather.databinding.ActivityMainBinding;

import org.jetbrains.annotations.NotNull;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private static final String CAT_FILE_NAME = "AnyCatImage";
    private static final String SP_NAME_PREF = "CatImagePref";
    private static final String SP_NAME_KEY = "CatImageKey";
    private WeatherApplication app;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SplashScreen.installSplashScreen(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        app = (WeatherApplication) this.getApplication();
        mainState(getCurrentImage());
        binding.buttonGetLink.setOnClickListener(v -> {
            loadingState();
            setNewImageCat();
        });
    }

    private void setNewImageCat() {
        CatRemoteDataSource catDS = new CatRemoteDataSourceImpl(app.mainIOPool);
        catDS.getCatLink(
                app.mainThreadHandler, new RequestListener<CatResponse[]>() {
                    @Override
                    public void onResponse(CatResponse[] result) {
                        String urlNewImage = result[0].getUrl();
                        loadCatImage(urlNewImage);
                    }

                    @Override
                    public void onError(Exception e) {
                        mainState(getCurrentImage());
                        showToast(getString(R.string.default_error_msg));
                    }
                }
        );
    }

    @Nullable
    private Bitmap getCurrentImage() {
        String fileName = getSharedPreferences(SP_NAME_PREF, MODE_PRIVATE).getString(SP_NAME_KEY, "");
        File tmpFile = new File(this.getFilesDir(), fileName);
        if (tmpFile.exists()) {
            return BitmapFactory.decodeFile(tmpFile.getAbsolutePath());
        }
        return null;
    }

    private void loadCatImage(@NotNull String url) {
        CatImageRemoteDataSource catDS = new CatImageRemoteDataSourceImpl(url, app.mainIOPool);
        catDS.getImageCat(this.getFilesDir(), CAT_FILE_NAME, app.mainThreadHandler, new RequestFileListener() {
            @Override
            public void onResponse(File file) {
                Bitmap bmp = BitmapFactory.decodeFile(file.getAbsolutePath());
                mainState(bmp);
                getSharedPreferences(SP_NAME_PREF, MODE_PRIVATE).edit().putString(SP_NAME_KEY, file.getName()).apply();
            }
            @Override
            public void onError(Exception e) {
                mainState(getCurrentImage());
                showToast(getString(R.string.default_error_msg));
            }
        });
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