package com.example.boyakazan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.boyakazan.databinding.ActivityGirisEkraniBinding;

public class girisEkrani extends AppCompatActivity {
    private ActivityGirisEkraniBinding binding;
    private long geriTusuBasilisSuresi;
    Toast geriTusuToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tanimlamalar();
        binding.oynaButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(oyunEkrani.class);
                finish();
            }
        });
        binding.nasilOynanirButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(nasilOynanirEkrani.class);
                finish();
            }
        });
        binding.ayarlarButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(AyarlarEkrani.class);
                finish();
            }
        });
    }

    public void tanimlamalar() {
        binding = ActivityGirisEkraniBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Drawable arkaplan = binding.enGenisLayout.getBackground();
        arkaplan.setAlpha(50);
    }

    public void openActivity(Class<?> classAdi) {
        Intent intent = new Intent(this, classAdi);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {

        if (geriTusuBasilisSuresi + 2000 > System.currentTimeMillis()) {
            geriTusuToast.cancel();
            finish();
            super.onBackPressed();
            return;
        } else {
            geriTusuToast = Toast.makeText(getApplicationContext(), "Uygulamadan çıkmak için bir daha tıklayın.", Toast.LENGTH_SHORT);
            geriTusuToast.show();
        }
        geriTusuBasilisSuresi = System.currentTimeMillis();
    }
}