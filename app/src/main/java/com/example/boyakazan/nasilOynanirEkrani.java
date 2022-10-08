package com.example.boyakazan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import com.example.boyakazan.databinding.ActivityNasilOynanirEkraniBinding;

import java.util.ArrayList;

public class nasilOynanirEkrani extends AppCompatActivity {
    private ActivityNasilOynanirEkraniBinding binding;
    ArrayList<Integer> resimler;
    ArrayList<String> aciklamalar;
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNasilOynanirEkraniBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        baslangic();

        binding.ileriButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i == resimler.size() - 1) {
                    i = -1;
                    openActivity(girisEkrani.class);
                } else {
                    i++;
                    setGravity(i);
                    binding.resimImageView.setImageResource(resimler.get(i));
                    binding.aciklamaTextview.setText(aciklamalar.get(i));
                }

            }
        });
        binding.geriButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i == 0) {
                    i = resimler.size();
                }
                i--;
                setGravity(i);
                binding.resimImageView.setImageResource(resimler.get(i));
                binding.aciklamaTextview.setText(aciklamalar.get(i));
            }
        });
    }

    public void openActivity(Class<?> classAdi) {
        Intent intent = new Intent(this, classAdi);
        startActivity(intent);
    }

    public void baslangic() {
        i = 0;
        resimler = new ArrayList<>();
        resimler.add(R.drawable.telefonresim1);
        resimler.add(R.drawable.telefonresim2);
        resimler.add(R.drawable.telefonresim3);
        resimler.add(R.drawable.telefonresim4);
        resimler.add(R.drawable.telefonresim5);
        resimler.add(R.drawable.telefonresim6);
        resimler.add(R.drawable.telefonresim7);
        resimler.add(R.drawable.telefonresim8);
        resimler.add(R.drawable.telefonresim9);
        //0-255 arasi deger alir
        binding.resimImageView.setImageAlpha(150);
        aciklamalar = new ArrayList<>();
        aciklamalar.add("Oyunumuz ekranın üstünde bulunan 10 adet butonla oynanmaktadır.");
        aciklamalar.add("Ayarlar sayfasından oyuncu isimleri ve renklerini belirleyebilirsiniz.");
        aciklamalar.add("Her butona tıklamada sıra diğer oyuncuya geçer.");
        aciklamalar.add("Tıklanan butonun en altında bulunan ve boyanmamış en alt satırdan itibaren boyanmaya başlanır.");
        aciklamalar.add("Oyunu kazanmak için aynı renkten 4 kare DİKEY,");
        aciklamalar.add("Çapraz,");
        aciklamalar.add("Çapraz,");
        aciklamalar.add("Veya Yatay konumda olmalıdır.");
        aciklamalar.add("Bir oyuncu 4'lüsünü yapınca oyun biter ve kazananın ismi yazar.");

        binding.aciklamaTextview.setText(aciklamalar.get(0));
        binding.resimImageView.setImageResource(resimler.get(0));
    }

    public void setGravity(int i) {
        if (i == resimler.size() - 1) {
            binding.aciklamaTextview.setGravity(Gravity.NO_GRAVITY);
        } else {
            binding.aciklamaTextview.setGravity(Gravity.CENTER);
        }
    }

    @Override
    public void onBackPressed() {
        openActivity(girisEkrani.class);
        finish();
        super.onBackPressed();
    }

}