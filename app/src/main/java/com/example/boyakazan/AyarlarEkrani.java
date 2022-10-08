package com.example.boyakazan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.boyakazan.databinding.ActivityAyarlarEkraniBinding;


public class AyarlarEkrani extends AppCompatActivity {
    private ActivityAyarlarEkraniBinding binding;
    public static Drawable oyuncu1Drawable, oyuncu2Drawable;
    public static Object tag1, tag2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAyarlarEkraniBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        atamalar();
        binding.kaydetButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kaydet();
            }
        });
    }

    public void atamalar() {
        binding.oyuncu1RengiButon.setBackgroundResource(R.drawable.custom_button_black);
        binding.oyuncu2RengiButon.setBackgroundResource(R.drawable.custom_button_red);
        solButonOnClickAtama(binding.solRenk1Button);
        solButonOnClickAtama(binding.solRenk2Button);
        solButonOnClickAtama(binding.solRenk3Button);
        solButonOnClickAtama(binding.solRenk4Button);
        solButonOnClickAtama(binding.solRenk5Button);
        solButonOnClickAtama(binding.solRenk6Button);
        solButonOnClickAtama(binding.solRenk7Button);
        solButonOnClickAtama(binding.solRenk8Button);
        solButonOnClickAtama(binding.solRenk9Button);
        sagButonOnClickAtama(binding.sagRenk1Button);
        sagButonOnClickAtama(binding.sagRenk2Button);
        sagButonOnClickAtama(binding.sagRenk3Button);
        sagButonOnClickAtama(binding.sagRenk4Button);
        sagButonOnClickAtama(binding.sagRenk5Button);
        sagButonOnClickAtama(binding.sagRenk6Button);
        sagButonOnClickAtama(binding.sagRenk7Button);
        sagButonOnClickAtama(binding.sagRenk8Button);
        sagButonOnClickAtama(binding.sagRenk9Button);
    }

    public void solButonOnClickAtama(final Button buton) {
        buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable getdrawable = buton.getBackground();
                binding.oyuncu1RengiButon.setBackground(getdrawable);
                binding.oyuncu1RengiButon.setTag(buton.getTag());
            }
        });
    }

    public void sagButonOnClickAtama(final Button buton) {
        buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable getdrawable = buton.getBackground();
                binding.oyuncu2RengiButon.setBackground(getdrawable);
                binding.oyuncu2RengiButon.setTag(buton.getTag());
            }
        });
    }

    public boolean oyuncu1AdiDogrula() {
        String oyuncu1adi = binding.oyuncu1adiEditText.getEditText().getText().toString().trim();
        if (oyuncu1adi.isEmpty()) {
            binding.oyuncu1adiEditText.getEditText().setText("Oyuncu 1");
            return false;
        } else {
            binding.oyuncu1adiEditText.setError(null);
            return true;
        }
    }

    public boolean oyuncu2AdiDogrula() {
        String oyuncu2adi = binding.oyuncu2adiEditText.getEditText().getText().toString().trim();
        if (oyuncu2adi.isEmpty()) {
            binding.oyuncu2adiEditText.getEditText().setText("Oyuncu 2");
            return false;
        } else {
            binding.oyuncu2adiEditText.setError(null);
            return true;
        }
    }

    public boolean oyuncuAdiBenzerlikKontrol() {
        String oyuncu1adi = binding.oyuncu1adiEditText.getEditText().getText().toString().trim();
        String oyuncu2adi = binding.oyuncu2adiEditText.getEditText().getText().toString().trim();
        if (oyuncu1adi.equals(oyuncu2adi)) {
            binding.oyuncu1adiEditText.setError("Oyuncu adları aynı olamaz!");
            binding.oyuncu2adiEditText.setError("Oyuncu adları aynı olamaz!");
            return false;
        } else {
            binding.oyuncu1adiEditText.setError(null);
            binding.oyuncu2adiEditText.setError(null);
            return true;
        }
    }

    public boolean oyuncuRenkleriKontrol() {
        Drawable.ConstantState oyuncu1Drawable = binding.oyuncu1RengiButon.getBackground().getConstantState();
        Drawable.ConstantState oyuncu2Drawable = binding.oyuncu2RengiButon.getBackground().getConstantState();
        String x1 = String.valueOf(binding.oyuncu1RengiButon.getTag());
        String x2 = String.valueOf(binding.oyuncu2RengiButon.getTag());
        if (oyuncu1Drawable.equals(oyuncu2Drawable) || x1.equals(x2)) {
            Toast.makeText(getApplicationContext(), "İki renk aynı olamaz.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    public void kaydet() {
        //Önceden çağrılmazsa 2.kere cağrılması gerekiyor. Sonuçlar önceden belirlenmesi için önceden çağırıldı.
        oyuncu1AdiDogrula();
        oyuncu2AdiDogrula();
        if (oyuncu1AdiDogrula() && oyuncu2AdiDogrula() && oyuncuRenkleriKontrol() && oyuncuAdiBenzerlikKontrol()) {
            Toast.makeText(getApplicationContext(), "Kaydedildi.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, oyunEkrani.class);
            intent.putExtra("oyuncu1Ad", binding.oyuncu1adiEditText.getEditText().getText().toString().trim());
            intent.putExtra("oyuncu2Ad", binding.oyuncu2adiEditText.getEditText().getText().toString().trim());
            oyuncu1Drawable = binding.oyuncu1RengiButon.getBackground();
            oyuncu2Drawable = binding.oyuncu2RengiButon.getBackground();
            tag1 = binding.oyuncu1RengiButon.getTag();
            tag2 = binding.oyuncu2RengiButon.getTag();
            startActivity(intent);
        }
    }

    public void openActivity(Class<?> classAdi) {
        Intent intent = new Intent(this, classAdi);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        openActivity(girisEkrani.class);
        finish();
        super.onBackPressed();
    }

}
