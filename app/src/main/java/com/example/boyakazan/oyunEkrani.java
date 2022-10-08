package com.example.boyakazan;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.boyakazan.databinding.ActivityOyunEkraniBinding;

import java.util.ArrayList;
import java.util.Arrays;

public class oyunEkrani extends AppCompatActivity {
    private ActivityOyunEkraniBinding binding;
    final int red = 0xFFFF0000;
    final int yellow = 0xFFFFFF00;
    final int green = 0xFF008000;
    final int lime = 0xFF00FF00;
    final int blue = 0xFF0000FF;
    final int orange = 0xFFFFA500;
    final int black = 0xFF000000;
    final int gray = 0xFF808080;
    final int brown = 0xFFA52A2A;
    final int transparent = 0;
    int satirSayisi = 5;
    int sutun1yeri = satirSayisi, sutun2yeri = satirSayisi, sutun3yeri = satirSayisi, sutun4yeri = satirSayisi, sutun5yeri = satirSayisi,
            sutun6yeri = satirSayisi, sutun7yeri = satirSayisi, sutun8yeri = satirSayisi, sutun9yeri = satirSayisi, sutun10yeri = satirSayisi;
    int renkDegis = 0;
    int oyuncu1Renk, oyuncu2Renk;
    private long geriTusuBasilisSuresi;
    Toast geriTusuToast;
    Button[][] koordinatlar;
    String kazananOyuncu;
    String oyuncu1Ad, oyuncu2Ad;
    ArrayList<ArrayList<Integer>> koordinatKaydediciArray;

    @Override
    public void onBackPressed() {
        if (geriTusuBasilisSuresi + 2000 > System.currentTimeMillis()) {
            geriTusuToast.cancel();
            openActivity(girisEkrani.class);
            finish();
            super.onBackPressed();
            return;
        } else {
            geriTusuToast = Toast.makeText(getApplicationContext(), "Giriş sayfasına gitmek için bir daha tıklayın.", Toast.LENGTH_SHORT);
            geriTusuToast.show();
        }
        geriTusuBasilisSuresi = System.currentTimeMillis();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOyunEkraniBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        baslangic();
        atamalar();
/*
        //sütunları kontrol eden butonlara değerler atanıyor. İlk atanan degerler en alt satirdan baslanacagi icin 5 ve butonun oldugu sutun degeri
        buton_tikla(binding.sutun1,sutun1yeri,0);
        buton_tikla(binding.sutun2,sutun2yeri,1);
        buton_tikla(binding.sutun3,sutun3yeri,2);
        buton_tikla(binding.sutun4,sutun4yeri,3);
        buton_tikla(binding.sutun5,sutun5yeri,4);
        buton_tikla(binding.sutun6,sutun6yeri,5);
        buton_tikla(binding.sutun7,sutun7yeri,6);
        buton_tikla(binding.sutun8,sutun8yeri,7);
        buton_tikla(binding.sutun9,sutun9yeri,8);
        buton_tikla(binding.sutun10,sutun10yeri,9);
 */
        butonClick();
    }

    @SuppressLint("SetTextI18n")
    public void baslangic() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            oyuncu1Ad = intent.getStringExtra("oyuncu1Ad");
            oyuncu2Ad = intent.getStringExtra("oyuncu2Ad");
            binding.oyuncu1AdiTextview.setText(oyuncu1Ad);
            binding.oyuncu2AdiTextview.setText(oyuncu2Ad);
            binding.oyuncu1RengiButonu.setBackground(AyarlarEkrani.oyuncu1Drawable);
            binding.oyuncu2RengiButonu.setBackground(AyarlarEkrani.oyuncu2Drawable);
        } else {
            binding.oyuncu1AdiTextview.setText("Oyuncu 1");
            binding.oyuncu2AdiTextview.setText("Oyuncu 2");
            binding.oyuncu1RengiButonu.setBackgroundResource(R.drawable.custom_button_black);
            binding.oyuncu2RengiButonu.setBackgroundResource(R.drawable.custom_button_red);
        }
        binding.oyuncu2SiraImageView.setVisibility(View.INVISIBLE);
        binding.oyuncu1RelativeLayout.setBackgroundColor(Color.argb(40, 0, 255, 255));
        binding.oyuncu2RelativeLayout.setBackgroundColor(Color.TRANSPARENT);
        //butonlara tag ekleniyor. Eger yontem 1 ile renkler belirlenemezse bu yontemle belirlenecek
        binding.oyuncu1RengiButonu.setTag(AyarlarEkrani.tag1);
        binding.oyuncu2RengiButonu.setTag(AyarlarEkrani.tag2);
        oyuncu1Renk = renkleribelirle(binding.oyuncu1RengiButonu, oyuncu1Renk);
        oyuncu2Renk = renkleribelirle(binding.oyuncu2RengiButonu, oyuncu2Renk);
        //api 21'den düşüklerde gettag sayfa oluşmadan dolmadığı için null oluyor. Önlemek için kontrol ve varsayılan değer atandı.
        if (binding.oyuncu1RengiButonu.getTag() != null && binding.oyuncu2RengiButonu.getTag() != null) {
            if (oyuncu1Renk == 0 || oyuncu2Renk == 0) {
                oyuncu1Renk = renkleribelirleYontem2(binding.oyuncu1RengiButonu, oyuncu1Renk);
                oyuncu2Renk = renkleribelirleYontem2(binding.oyuncu2RengiButonu, oyuncu2Renk);
            }
        } else {
            oyuncu1Renk = black;
            oyuncu2Renk = red;
        }

    }

    public void atamalar() {
        koordinatlar = new Button[][]{
                {binding.button10, binding.button11, binding.button12, binding.button13, binding.button14, binding.button15, binding.button16, binding.button17, binding.button18, binding.button19},
                {binding.button20, binding.button21, binding.button22, binding.button23, binding.button24, binding.button25, binding.button26, binding.button27, binding.button28, binding.button29},
                {binding.button30, binding.button31, binding.button32, binding.button33, binding.button34, binding.button35, binding.button36, binding.button37, binding.button38, binding.button39},
                {binding.button40, binding.button41, binding.button42, binding.button43, binding.button44, binding.button45, binding.button46, binding.button47, binding.button48, binding.button49},
                {binding.button50, binding.button51, binding.button52, binding.button53, binding.button54, binding.button55, binding.button56, binding.button57, binding.button58, binding.button59},
                {binding.button60, binding.button61, binding.button62, binding.button63, binding.button64, binding.button65, binding.button66, binding.button67, binding.button68, binding.button69}
        };
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 10; j++) {
                koordinatlar[i][j].setBackgroundResource(R.drawable.buton_border_custom);
            }
        }
        koordinatKaydediciArray = new ArrayList<>();
    }

    /*
    public void buton_tikla(Button sutun, int satirdakiYeri, final int kacinciSutun){
        int[] x = {satirdakiYeri};
        sutun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(x[0] >-1) {
                    koordinatKaydediciArray.add(new ArrayList<>(Arrays.asList(x[0], kacinciSutun)));
                    if (renkDegis % 2 == 0) {
                        koordinatlar[x[0]][kacinciSutun].setBackgroundColor(oyuncu1Renk);
                        kazananOyuncu = binding.oyuncu1AdiTextview.getText().toString();
                    } else {
                        koordinatlar[x[0]][kacinciSutun].setBackgroundColor(oyuncu2Renk);
                        kazananOyuncu = binding.oyuncu2AdiTextview.getText().toString();
                    }
                    siraGoster(renkDegis);
                    tum_kontroller(x[0],kacinciSutun);
                    renkDegis++;
                    x[0]--;
                }
            }
        });
    }
     */
    public void butonClick() {
        binding.buttonGeriAl.setOnClickListener(v -> {
            if (koordinatKaydediciArray.size() > 0) {
                siraGoster(renkDegis);
                renkDegis++;
                koordinatlar[koordinatKaydediciArray.get(koordinatKaydediciArray.size() - 1).get(0)][koordinatKaydediciArray.get(koordinatKaydediciArray.size() - 1).get(1)].setBackgroundResource(R.drawable.buton_border_custom);
                switch (koordinatKaydediciArray.get(koordinatKaydediciArray.size() - 1).get(1)) {
                    case 0:
                        sutun1yeri++;
                        break;
                    case 1:
                        sutun2yeri++;
                        break;
                    case 2:
                        sutun3yeri++;
                        break;
                    case 3:
                        sutun4yeri++;
                        break;
                    case 4:
                        sutun5yeri++;
                        break;
                    case 5:
                        sutun6yeri++;
                        break;
                    case 6:
                        sutun7yeri++;
                        break;
                    case 7:
                        sutun8yeri++;
                        break;
                    case 8:
                        sutun9yeri++;
                        break;
                    case 9:
                        sutun10yeri++;
                        break;
                }
                koordinatKaydediciArray.remove(koordinatKaydediciArray.size() - 1);
            }
        });
        binding.sutun1.setOnClickListener(v -> {
            if (sutun1yeri > -1) {
                koordinatKaydediciArray.add(new ArrayList<>(Arrays.asList(sutun1yeri, 0)));
                if (renkDegis % 2 == 0) {
                    koordinatlar[sutun1yeri][0].setBackgroundColor(oyuncu1Renk);
                    kazananOyuncu = binding.oyuncu1AdiTextview.getText().toString();
                } else {
                    koordinatlar[sutun1yeri][0].setBackgroundColor(oyuncu2Renk);
                    kazananOyuncu = binding.oyuncu2AdiTextview.getText().toString();
                }
                siraGoster(renkDegis);
                tum_kontroller(sutun1yeri, 0);
                renkDegis++;
                sutun1yeri--;
            }
        });
        binding.sutun2.setOnClickListener(v -> {
            if (sutun2yeri > -1) {
                koordinatKaydediciArray.add(new ArrayList<>(Arrays.asList(sutun2yeri, 1)));
                if (renkDegis % 2 == 0) {
                    koordinatlar[sutun2yeri][1].setBackgroundColor(oyuncu1Renk);
                    kazananOyuncu = binding.oyuncu1AdiTextview.getText().toString();
                } else {
                    koordinatlar[sutun2yeri][1].setBackgroundColor(oyuncu2Renk);
                    kazananOyuncu = binding.oyuncu2AdiTextview.getText().toString();
                }
                siraGoster(renkDegis);
                tum_kontroller(sutun2yeri, 1);
                renkDegis++;
                sutun2yeri--;
            }
        });
        binding.sutun3.setOnClickListener(v -> {
            if (sutun3yeri > -1) {
                koordinatKaydediciArray.add(new ArrayList<>(Arrays.asList(sutun3yeri, 2)));
                if (renkDegis % 2 == 0) {
                    koordinatlar[sutun3yeri][2].setBackgroundColor(oyuncu1Renk);
                    kazananOyuncu = binding.oyuncu1AdiTextview.getText().toString();
                } else {
                    koordinatlar[sutun3yeri][2].setBackgroundColor(oyuncu2Renk);
                    kazananOyuncu = binding.oyuncu2AdiTextview.getText().toString();
                }
                siraGoster(renkDegis);
                tum_kontroller(sutun3yeri, 2);
                renkDegis++;
                sutun3yeri--;
            }
        });
        binding.sutun4.setOnClickListener(v -> {
            if (sutun4yeri > -1) {
                koordinatKaydediciArray.add(new ArrayList<>(Arrays.asList(sutun4yeri, 3)));
                if (renkDegis % 2 == 0) {
                    koordinatlar[sutun4yeri][3].setBackgroundColor(oyuncu1Renk);
                    kazananOyuncu = binding.oyuncu1AdiTextview.getText().toString();
                } else {
                    koordinatlar[sutun4yeri][3].setBackgroundColor(oyuncu2Renk);
                    kazananOyuncu = binding.oyuncu2AdiTextview.getText().toString();
                }
                siraGoster(renkDegis);
                tum_kontroller(sutun4yeri, 3);
                renkDegis++;
                sutun4yeri--;
            }
        });
        binding.sutun5.setOnClickListener(v -> {
            if (sutun5yeri > -1) {
                koordinatKaydediciArray.add(new ArrayList<>(Arrays.asList(sutun5yeri, 4)));
                if (renkDegis % 2 == 0) {
                    koordinatlar[sutun5yeri][4].setBackgroundColor(oyuncu1Renk);
                    kazananOyuncu = binding.oyuncu1AdiTextview.getText().toString();
                } else {
                    koordinatlar[sutun5yeri][4].setBackgroundColor(oyuncu2Renk);
                    kazananOyuncu = binding.oyuncu2AdiTextview.getText().toString();
                }
                siraGoster(renkDegis);
                tum_kontroller(sutun5yeri, 4);
                renkDegis++;
                sutun5yeri--;
            }
        });
        binding.sutun6.setOnClickListener(v -> {
            if (sutun6yeri > -1) {
                koordinatKaydediciArray.add(new ArrayList<>(Arrays.asList(sutun6yeri, 5)));
                if (renkDegis % 2 == 0) {
                    koordinatlar[sutun6yeri][5].setBackgroundColor(oyuncu1Renk);
                    kazananOyuncu = binding.oyuncu1AdiTextview.getText().toString();
                } else {
                    koordinatlar[sutun6yeri][5].setBackgroundColor(oyuncu2Renk);
                    kazananOyuncu = binding.oyuncu2AdiTextview.getText().toString();
                }
                siraGoster(renkDegis);
                tum_kontroller(sutun6yeri, 5);
                renkDegis++;
                sutun6yeri--;
            }
        });
        binding.sutun7.setOnClickListener(v -> {
            if (sutun7yeri > -1) {
                koordinatKaydediciArray.add(new ArrayList<>(Arrays.asList(sutun7yeri, 6)));
                if (renkDegis % 2 == 0) {
                    koordinatlar[sutun7yeri][6].setBackgroundColor(oyuncu1Renk);
                    kazananOyuncu = binding.oyuncu1AdiTextview.getText().toString();
                } else {
                    koordinatlar[sutun7yeri][6].setBackgroundColor(oyuncu2Renk);
                    kazananOyuncu = binding.oyuncu2AdiTextview.getText().toString();
                }
                siraGoster(renkDegis);
                tum_kontroller(sutun7yeri, 6);
                renkDegis++;
                sutun7yeri--;
            }
        });
        binding.sutun8.setOnClickListener(v -> {
            if (sutun8yeri > -1) {
                koordinatKaydediciArray.add(new ArrayList<>(Arrays.asList(sutun8yeri, 7)));
                if (renkDegis % 2 == 0) {
                    koordinatlar[sutun8yeri][7].setBackgroundColor(oyuncu1Renk);
                    kazananOyuncu = binding.oyuncu1AdiTextview.getText().toString();
                } else {
                    koordinatlar[sutun8yeri][7].setBackgroundColor(oyuncu2Renk);
                    kazananOyuncu = binding.oyuncu2AdiTextview.getText().toString();
                }
                siraGoster(renkDegis);
                tum_kontroller(sutun8yeri, 7);
                renkDegis++;
                sutun8yeri--;
            }
        });
        binding.sutun9.setOnClickListener(v -> {
            if (sutun9yeri > -1) {
                koordinatKaydediciArray.add(new ArrayList<>(Arrays.asList(sutun9yeri, 8)));
                if (renkDegis % 2 == 0) {
                    koordinatlar[sutun9yeri][8].setBackgroundColor(oyuncu1Renk);
                    kazananOyuncu = binding.oyuncu1AdiTextview.getText().toString();
                } else {
                    koordinatlar[sutun9yeri][8].setBackgroundColor(oyuncu2Renk);
                    kazananOyuncu = binding.oyuncu2AdiTextview.getText().toString();
                }
                siraGoster(renkDegis);
                tum_kontroller(sutun9yeri, 8);
                renkDegis++;
                sutun9yeri--;
            }
        });
        binding.sutun10.setOnClickListener(v -> {
            if (sutun10yeri > -1) {
                koordinatKaydediciArray.add(new ArrayList<>(Arrays.asList(sutun10yeri, 9)));
                if (renkDegis % 2 == 0) {
                    koordinatlar[sutun10yeri][9].setBackgroundColor(oyuncu1Renk);
                    kazananOyuncu = binding.oyuncu1AdiTextview.getText().toString();
                } else {
                    koordinatlar[sutun10yeri][9].setBackgroundColor(oyuncu2Renk);
                    kazananOyuncu = binding.oyuncu2AdiTextview.getText().toString();
                }
                siraGoster(renkDegis);
                tum_kontroller(sutun10yeri, 9);
                renkDegis++;
                sutun10yeri--;
            }
        });
    }

    public void siraGoster(int sayi) {
        if (sayi % 2 == 0) {
            //ilk tıklamada burası calisiyor
            binding.oyuncu1SiraImageView.setVisibility(View.INVISIBLE);
            binding.oyuncu2SiraImageView.setVisibility(View.VISIBLE);
            binding.oyuncu2RelativeLayout.setBackgroundColor(Color.argb(40, 0, 255, 255));
            binding.oyuncu1RelativeLayout.setBackgroundColor(Color.TRANSPARENT);
        } else {
            binding.oyuncu1SiraImageView.setVisibility(View.VISIBLE);
            binding.oyuncu2SiraImageView.setVisibility(View.INVISIBLE);
            binding.oyuncu1RelativeLayout.setBackgroundColor(Color.argb(40, 0, 255, 255));
            binding.oyuncu2RelativeLayout.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public int renkleribelirle(Button button, int oyuncu) {
        Drawable.ConstantState background = button.getBackground().getConstantState();
        if (getResources().getDrawable(R.drawable.custom_button_black).getConstantState().equals(background)) {
            oyuncu = black;
        } else if (getResources().getDrawable(R.drawable.custom_button_lime).getConstantState().equals(background)) {
            oyuncu = lime;
        } else if (getResources().getDrawable(R.drawable.custom_button_gray).getConstantState().equals(background)) {
            oyuncu = gray;
        } else if (getResources().getDrawable(R.drawable.custom_button_blue).getConstantState().equals(background)) {
            oyuncu = blue;
        } else if (getResources().getDrawable(R.drawable.custom_button_brown).getConstantState().equals(background)) {
            oyuncu = brown;
        } else if (getResources().getDrawable(R.drawable.custom_button_green).getConstantState().equals(background)) {
            oyuncu = green;
        } else if (getResources().getDrawable(R.drawable.custom_button_orange).getConstantState().equals(background)) {
            oyuncu = orange;
        } else if (getResources().getDrawable(R.drawable.custom_button_red).getConstantState().equals(background)) {
            oyuncu = red;
        } else if (getResources().getDrawable(R.drawable.custom_button_yellow).getConstantState().equals(background)) {
            oyuncu = yellow;
        }
        return oyuncu;
    }

    public int renkleribelirleYontem2(Button button, int oyuncu) {
        //ilk yontem ise yaramazsa bu yontemle renkler belirleniyor. Apı 21 altinda ilk yontem calismiyor
        try {
            if (button.getTag().equals("R.drawable.custom_button_black")) {
                oyuncu = black;
            } else if (button.getTag().equals("R.drawable.custom_button_lime")) {
                oyuncu = lime;
            } else if (button.getTag().equals("R.drawable.custom_button_gray")) {
                oyuncu = gray;
            } else if (button.getTag().equals("R.drawable.custom_button_blue")) {
                oyuncu = blue;
            } else if (button.getTag().equals("R.drawable.custom_button_brown")) {
                oyuncu = brown;
            } else if (button.getTag().equals("R.drawable.custom_button_green")) {
                oyuncu = green;
            } else if (button.getTag().equals("R.drawable.custom_button_orange")) {
                oyuncu = orange;
            } else if (button.getTag().equals("R.drawable.custom_button_red")) {
                oyuncu = red;
            } else if (button.getTag().equals("R.drawable.custom_button_yellow")) {
                oyuncu = yellow;
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), button.getTag().toString(), Toast.LENGTH_SHORT).show();
        }

        return oyuncu;
    }

    public void yukaridan_asagi_kontrol(int satirDegeri, int sutun) {
        try {
            //x
            //o
            //o
            //o
            ColorDrawable a = (ColorDrawable) koordinatlar[satirDegeri][sutun].getBackground();
            ColorDrawable b = (ColorDrawable) koordinatlar[satirDegeri + 1][sutun].getBackground();
            ColorDrawable c = (ColorDrawable) koordinatlar[satirDegeri + 2][sutun].getBackground();
            ColorDrawable d = (ColorDrawable) koordinatlar[satirDegeri + 3][sutun].getBackground();
            if (a.getColor() == b.getColor() && a.getColor() == c.getColor() && a.getColor() == d.getColor()) {
                int[][] dogruKoordinatlar = new int[][]{{satirDegeri, sutun}, {satirDegeri + 1, sutun}, {satirDegeri + 2, sutun}, {satirDegeri + 3, sutun}};
                sonuc_ekrani(dogruKoordinatlar);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sol_ust_sag_alt_kontrol1(int satirDegeri, int sutun) {
        try {
            //x---
            //-o--
            //--o-
            //---o
            ColorDrawable a = (ColorDrawable) koordinatlar[satirDegeri][sutun].getBackground();
            ColorDrawable b = (ColorDrawable) koordinatlar[satirDegeri + 1][sutun + 1].getBackground();
            ColorDrawable c = (ColorDrawable) koordinatlar[satirDegeri + 2][sutun + 2].getBackground();
            ColorDrawable d = (ColorDrawable) koordinatlar[satirDegeri + 3][sutun + 3].getBackground();
            if (a.getColor() == b.getColor() && a.getColor() == c.getColor() && a.getColor() == d.getColor()) {
                int[][] dogruKoordinatlar = new int[][]{{satirDegeri, sutun}, {satirDegeri + 1, sutun + 1}, {satirDegeri + 2, sutun + 2}, {satirDegeri + 3, sutun + 3}};
                sonuc_ekrani(dogruKoordinatlar);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sol_ust_sag_alt_kontrol2(int satirDegeri, int sutun) {
        try {
            //o---
            //-x--
            //--o-
            //---o
            ColorDrawable a = (ColorDrawable) koordinatlar[satirDegeri][sutun].getBackground();
            ColorDrawable b = (ColorDrawable) koordinatlar[satirDegeri - 1][sutun - 1].getBackground();
            ColorDrawable c = (ColorDrawable) koordinatlar[satirDegeri + 1][sutun + 1].getBackground();
            ColorDrawable d = (ColorDrawable) koordinatlar[satirDegeri + 2][sutun + 2].getBackground();
            if (a.getColor() == b.getColor() && a.getColor() == c.getColor() && a.getColor() == d.getColor()) {
                int[][] dogruKoordinatlar = new int[][]{{satirDegeri, sutun}, {satirDegeri - 1, sutun - 1}, {satirDegeri + 1, sutun + 1}, {satirDegeri + 2, sutun + 2}};
                sonuc_ekrani(dogruKoordinatlar);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sol_ust_sag_alt_kontrol3(int satirDegeri, int sutun) {
        try {
            //o---
            //-o--
            //--x-
            //---o
            ColorDrawable a = (ColorDrawable) koordinatlar[satirDegeri][sutun].getBackground();
            ColorDrawable b = (ColorDrawable) koordinatlar[satirDegeri - 1][sutun - 1].getBackground();
            ColorDrawable c = (ColorDrawable) koordinatlar[satirDegeri - 2][sutun - 2].getBackground();
            ColorDrawable d = (ColorDrawable) koordinatlar[satirDegeri + 1][sutun + 1].getBackground();
            if (a.getColor() == b.getColor() && a.getColor() == c.getColor() && a.getColor() == d.getColor()) {
                int[][] dogruKoordinatlar = new int[][]{{satirDegeri, sutun}, {satirDegeri - 1, sutun - 1}, {satirDegeri - 2, sutun - 2}, {satirDegeri + 1, sutun + 1}};
                sonuc_ekrani(dogruKoordinatlar);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sol_ust_sag_alt_kontrol4(int satirDegeri, int sutun) {
        try {
            //o---
            //-o--
            //--o-
            //---x
            ColorDrawable a = (ColorDrawable) koordinatlar[satirDegeri][sutun].getBackground();
            ColorDrawable b = (ColorDrawable) koordinatlar[satirDegeri - 1][sutun - 1].getBackground();
            ColorDrawable c = (ColorDrawable) koordinatlar[satirDegeri - 2][sutun - 2].getBackground();
            ColorDrawable d = (ColorDrawable) koordinatlar[satirDegeri - 3][sutun - 3].getBackground();
            if (a.getColor() == b.getColor() && a.getColor() == c.getColor() && a.getColor() == d.getColor()) {
                int[][] dogruKoordinatlar = new int[][]{{satirDegeri, sutun}, {satirDegeri - 1, sutun - 1}, {satirDegeri - 2, sutun - 2}, {satirDegeri - 3, sutun - 3}};
                sonuc_ekrani(dogruKoordinatlar);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sag_ust_sol_alt_kontrol1(int satirDegeri, int sutun) {
        try {
            //---x
            //--o-
            //-o--
            //o---
            ColorDrawable a = (ColorDrawable) koordinatlar[satirDegeri][sutun].getBackground();
            ColorDrawable b = (ColorDrawable) koordinatlar[satirDegeri + 1][sutun - 1].getBackground();
            ColorDrawable c = (ColorDrawable) koordinatlar[satirDegeri + 2][sutun - 2].getBackground();
            ColorDrawable d = (ColorDrawable) koordinatlar[satirDegeri + 3][sutun - 3].getBackground();
            if (a.getColor() == b.getColor() && a.getColor() == c.getColor() && a.getColor() == d.getColor()) {
                int[][] dogruKoordinatlar = new int[][]{{satirDegeri, sutun}, {satirDegeri + 1, sutun - 1}, {satirDegeri + 2, sutun - 2}, {satirDegeri + 3, sutun - 3}};
                sonuc_ekrani(dogruKoordinatlar);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sag_ust_sol_alt_kontrol2(int satirDegeri, int sutun) {
        try {
            //---o
            //--x-
            //-o--
            //o---
            ColorDrawable a = (ColorDrawable) koordinatlar[satirDegeri][sutun].getBackground();
            ColorDrawable b = (ColorDrawable) koordinatlar[satirDegeri - 1][sutun + 1].getBackground();
            ColorDrawable c = (ColorDrawable) koordinatlar[satirDegeri + 1][sutun - 1].getBackground();
            ColorDrawable d = (ColorDrawable) koordinatlar[satirDegeri + 2][sutun - 2].getBackground();
            if (a.getColor() == b.getColor() && a.getColor() == c.getColor() && a.getColor() == d.getColor()) {
                int[][] dogruKoordinatlar = new int[][]{{satirDegeri, sutun}, {satirDegeri - 1, sutun + 1}, {satirDegeri + 1, sutun - 1}, {satirDegeri + 2, sutun - 2}};
                sonuc_ekrani(dogruKoordinatlar);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sag_ust_sol_alt_kontrol3(int satirDegeri, int sutun) {
        try {
            //---o
            //--o-
            //-x--
            //o---
            ColorDrawable a = (ColorDrawable) koordinatlar[satirDegeri][sutun].getBackground();
            ColorDrawable b = (ColorDrawable) koordinatlar[satirDegeri - 1][sutun + 1].getBackground();
            ColorDrawable c = (ColorDrawable) koordinatlar[satirDegeri - 2][sutun + 2].getBackground();
            ColorDrawable d = (ColorDrawable) koordinatlar[satirDegeri + 1][sutun - 1].getBackground();
            if (a.getColor() == b.getColor() && a.getColor() == c.getColor() && a.getColor() == d.getColor()) {
                int[][] dogruKoordinatlar = new int[][]{{satirDegeri, sutun}, {satirDegeri - 1, sutun + 1}, {satirDegeri - 2, sutun + 2}, {satirDegeri + 1, sutun - 1}};
                sonuc_ekrani(dogruKoordinatlar);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sag_ust_sol_alt_kontrol4(int satirDegeri, int sutun) {
        try {
            //---o
            //--o-
            //-o--
            //x---
            ColorDrawable a = (ColorDrawable) koordinatlar[satirDegeri][sutun].getBackground();
            ColorDrawable b = (ColorDrawable) koordinatlar[satirDegeri - 1][sutun + 1].getBackground();
            ColorDrawable c = (ColorDrawable) koordinatlar[satirDegeri - 2][sutun + 2].getBackground();
            ColorDrawable d = (ColorDrawable) koordinatlar[satirDegeri - 3][sutun + 3].getBackground();
            if (a.getColor() == b.getColor() && a.getColor() == c.getColor() && a.getColor() == d.getColor()) {
                int[][] dogruKoordinatlar = new int[][]{{satirDegeri, sutun}, {satirDegeri - 1, sutun + 1}, {satirDegeri - 2, sutun + 2}, {satirDegeri - 3, sutun + 3}};
                sonuc_ekrani(dogruKoordinatlar);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void yatay_kontrol1(int satirDegeri, int sutun) {
        try {
            //xooo
            ColorDrawable a = (ColorDrawable) koordinatlar[satirDegeri][sutun].getBackground();
            ColorDrawable b = (ColorDrawable) koordinatlar[satirDegeri][sutun + 1].getBackground();
            ColorDrawable c = (ColorDrawable) koordinatlar[satirDegeri][sutun + 2].getBackground();
            ColorDrawable d = (ColorDrawable) koordinatlar[satirDegeri][sutun + 3].getBackground();
            if (a.getColor() == b.getColor() && a.getColor() == c.getColor() && a.getColor() == d.getColor()) {
                int[][] dogruKoordinatlar = new int[][]{{satirDegeri, sutun}, {satirDegeri, sutun + 1}, {satirDegeri, sutun + 2}, {satirDegeri, sutun + 3}};
                sonuc_ekrani(dogruKoordinatlar);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void yatay_kontrol2(int satirDegeri, int sutun) {
        try {
            //oxoo
            ColorDrawable a = (ColorDrawable) koordinatlar[satirDegeri][sutun].getBackground();
            ColorDrawable b = (ColorDrawable) koordinatlar[satirDegeri][sutun - 1].getBackground();
            ColorDrawable c = (ColorDrawable) koordinatlar[satirDegeri][sutun + 1].getBackground();
            ColorDrawable d = (ColorDrawable) koordinatlar[satirDegeri][sutun + 2].getBackground();
            if (a.getColor() == b.getColor() && a.getColor() == c.getColor() && a.getColor() == d.getColor()) {
                int[][] dogruKoordinatlar = new int[][]{{satirDegeri, sutun}, {satirDegeri, sutun - 1}, {satirDegeri, sutun + 1}, {satirDegeri, sutun + 2}};
                sonuc_ekrani(dogruKoordinatlar);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void yatay_kontrol3(int satirDegeri, int sutun) {
        try {
            //ooxo
            ColorDrawable a = (ColorDrawable) koordinatlar[satirDegeri][sutun].getBackground();
            ColorDrawable b = (ColorDrawable) koordinatlar[satirDegeri][sutun - 1].getBackground();
            ColorDrawable c = (ColorDrawable) koordinatlar[satirDegeri][sutun - 2].getBackground();
            ColorDrawable d = (ColorDrawable) koordinatlar[satirDegeri][sutun + 1].getBackground();
            if (a.getColor() == b.getColor() && a.getColor() == c.getColor() && a.getColor() == d.getColor()) {
                int[][] dogruKoordinatlar = new int[][]{{satirDegeri, sutun}, {satirDegeri, sutun - 1}, {satirDegeri, sutun - 2}, {satirDegeri, sutun + 1}};
                sonuc_ekrani(dogruKoordinatlar);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void yatay_kontrol4(int satirDegeri, int sutun) {
        try {
            //xooo
            ColorDrawable a = (ColorDrawable) koordinatlar[satirDegeri][sutun].getBackground();
            ColorDrawable b = (ColorDrawable) koordinatlar[satirDegeri][sutun - 1].getBackground();
            ColorDrawable c = (ColorDrawable) koordinatlar[satirDegeri][sutun - 2].getBackground();
            ColorDrawable d = (ColorDrawable) koordinatlar[satirDegeri][sutun - 3].getBackground();
            if (a.getColor() == b.getColor() && a.getColor() == c.getColor() && a.getColor() == d.getColor()) {
                int[][] dogruKoordinatlar = new int[][]{{satirDegeri, sutun}, {satirDegeri, sutun - 1}, {satirDegeri, sutun - 2}, {satirDegeri, sutun - 3}};
                sonuc_ekrani(dogruKoordinatlar);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void tum_kontroller(int satirDegeri, int sutun) {
        yukaridan_asagi_kontrol(satirDegeri, sutun);
        sol_ust_sag_alt_kontrol1(satirDegeri, sutun);
        sol_ust_sag_alt_kontrol2(satirDegeri, sutun);
        sol_ust_sag_alt_kontrol3(satirDegeri, sutun);
        sol_ust_sag_alt_kontrol4(satirDegeri, sutun);
        sag_ust_sol_alt_kontrol1(satirDegeri, sutun);
        sag_ust_sol_alt_kontrol2(satirDegeri, sutun);
        sag_ust_sol_alt_kontrol3(satirDegeri, sutun);
        sag_ust_sol_alt_kontrol4(satirDegeri, sutun);
        yatay_kontrol1(satirDegeri, sutun);
        yatay_kontrol2(satirDegeri, sutun);
        yatay_kontrol3(satirDegeri, sutun);
        yatay_kontrol4(satirDegeri, sutun);
    }

    @SuppressLint("SetTextI18n")
    public void sonuc_ekrani(int[][] koordinatlar) {
        binding.butonlarSatiri.setVisibility(View.INVISIBLE);
        binding.buttonGeriAl.setVisibility(View.INVISIBLE);
        siraGoster(renkDegis + 1);
        kazanma_efekti(koordinatlar);
    }

    public void kazanma_efekti(final int[][] koordinat) {
        long toplamsure = 3000;
        long saniye = 500;
        ColorDrawable buton = (ColorDrawable) koordinatlar[koordinat[0][0]][koordinat[0][1]].getBackground();
        final int suankirenkleri = buton.getColor();
        final int[] kontrolSayisi = {0};
        final ArrayList<Integer> renkdize = new ArrayList<>();
        //bu renkler sırayla yanıp sönerek kazananın kazandigi koordinatlari belli ediyor
        renkdize.add(suankirenkleri);
        renkdize.add(transparent);
        new CountDownTimer(toplamsure, saniye) {
            @Override
            public void onTick(long millisUntilFinished) {
                koordinatlar[koordinat[0][0]][koordinat[0][1]].setBackgroundColor(renkdize.get(kontrolSayisi[0] % 2));
                koordinatlar[koordinat[1][0]][koordinat[1][1]].setBackgroundColor(renkdize.get(kontrolSayisi[0] % 2));
                koordinatlar[koordinat[2][0]][koordinat[2][1]].setBackgroundColor(renkdize.get(kontrolSayisi[0] % 2));
                koordinatlar[koordinat[3][0]][koordinat[3][1]].setBackgroundColor(renkdize.get(kontrolSayisi[0] % 2));
                kontrolSayisi[0]++;
            }

            @Override
            public void onFinish() {
                koordinatlar[koordinat[0][0]][koordinat[0][1]].setBackgroundColor(suankirenkleri);
                koordinatlar[koordinat[1][0]][koordinat[1][1]].setBackgroundColor(suankirenkleri);
                koordinatlar[koordinat[2][0]][koordinat[2][1]].setBackgroundColor(suankirenkleri);
                koordinatlar[koordinat[3][0]][koordinat[3][1]].setBackgroundColor(suankirenkleri);
                uyari_ekrani();
            }
        }.start();
    }

    public void openActivity(Class<?> classAdi) {
        Intent intent = new Intent(this, classAdi);
        startActivity(intent);
    }

    public void uyari_ekrani() {
        AlertDialog.Builder builder = new AlertDialog.Builder(oyunEkrani.this);
        builder.setTitle("Tebrikler!");
        builder.setMessage(kazananOyuncu + " kazandı.");
        builder.setCancelable(false);
        builder.setNeutralButton("Girişe Git", (dialog, which) -> {
            openActivity(girisEkrani.class);
            finish();
        });
        builder.setNegativeButton("Tekrar Oyna", (dialog, which) -> {
            startActivity(getIntent());
            finish();
        });
        builder.show();
    }
}