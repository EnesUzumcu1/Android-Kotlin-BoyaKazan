package com.example.boyakazan

import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.boyakazan.databinding.FragmentGameBinding
import java.util.*

class GameFragment : Fragment() {
    private lateinit var bindingGameFragment: FragmentGameBinding
    private lateinit var navController: NavController
    private val red = -0x10000
    private val yellow = -0x100
    private val green = -0xff8000
    private val lime = -0xff0100
    private val blue = -0xffff01
    private val orange = -0x5b00
    private val black = -0x1000000
    private val gray = -0x7f7f80
    private val brown = -0x5ad5d6
    private val transparent = 0
    private var satirSayisi = 5
    private var sutun1yeri: Int = satirSayisi
    private var sutun2yeri: Int = satirSayisi
    private var sutun3yeri: Int = satirSayisi
    private var sutun4yeri: Int = satirSayisi
    private var sutun5yeri: Int = satirSayisi
    private var sutun6yeri: Int = satirSayisi
    private var sutun7yeri: Int = satirSayisi
    private var sutun8yeri: Int = satirSayisi
    private var sutun9yeri: Int = satirSayisi
    private var sutun10yeri: Int = satirSayisi
    //0 0 0 0 0 0 0 0 0 0
    //1 1 1 1 1 1 1 1 1 1       satirsayisi yuksekligi belirliyor
    //2 2 2 2 2 2 2 2 2 2       koordinatlar arrayi icindeki viewler bu numaralandirmaya gore cagiriliyor
    //3 3 3 3 3 3 3 3 3 3       en alttaki view en yuksek satirSayisina sahip
    //4 4 4 4 4 4 4 4 4 4       <-koordinatlar y ekseni sirasi
    //5 5 5 5 5 5 5 5 5 5

    //0 1 2 3 4 5 6 7 8 9       <-koordinatlar x ekseni sirasi
    private var renkDegis = 0
    private var oyuncu1Renk: Int = black
    private var oyuncu2Renk: Int = red

    //oyuncu1RengiStr ve oyuncu2RengiStr sayfa yeniden yüklenince verileri tekrardan almak için eklendi
    private lateinit var oyuncu1RengiStr: String
    private lateinit var oyuncu2RengiStr: String
    private var geriTusuBasilisSuresi: Long = 0
    private lateinit var koordinatlar: Array<Array<Button>>
    private var kazananOyuncu: String? = null
    private var oyuncu1Ad: String = "Oyuncu 1"
    private var oyuncu2Ad: String = "Oyuncu 2"
    private lateinit var koordinatKaydediciArray: ArrayList<ArrayList<Int>>
    private var isBuilderExisting: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindingGameFragment = FragmentGameBinding.inflate(inflater, container, false)
        return bindingGameFragment.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        baslangic()
        atamalar()
        butonClick()

        //backpress basildiginda gerekli durum icin callback cagirildi
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            // Handle the back button event
            onBackPressed()
        }
    }

    private fun baslangic() {
        navController = findNavController()
        //gonderilen verilerin kontrolu yapiliyor
        arguments?.let {
            val oyuncu1adi = it.getString("oyuncu1Ad")
            val oyuncu2adi = it.getString("oyuncu2Ad")
            val oyuncu1drawable = it.getString("oyuncu1Rengi")
            val oyuncu2drawable = it.getString("oyuncu2Rengi")
            oyuncu1adi?.let { safeValue ->
                oyuncu1Ad = safeValue
                bindingGameFragment.oyuncu1AdiTextview.text = oyuncu1Ad
            }
            oyuncu2adi?.let { safeValue ->
                oyuncu2Ad = safeValue
                bindingGameFragment.oyuncu2AdiTextview.text = oyuncu2Ad
            }
            oyuncu1drawable?.let { safeData ->
                oyuncu1RengiStr = safeData
                //gelen string degere gore background degistiriliyor ve oyuncu rengi belirleniyor
                when (safeData) {
                    getString(R.string.lime) -> {
                        bindingGameFragment.ivOyuncu1Rengi.setBackgroundResource(R.drawable.custom_button_lime)
                        oyuncu1Renk = lime
                    }
                    getString(R.string.gray) -> {
                        bindingGameFragment.ivOyuncu1Rengi.setBackgroundResource(R.drawable.custom_button_gray)
                        oyuncu1Renk = gray
                    }
                    getString(R.string.black) -> {
                        bindingGameFragment.ivOyuncu1Rengi.setBackgroundResource(R.drawable.custom_button_black)
                        oyuncu1Renk = black
                    }
                    getString(R.string.green) -> {
                        bindingGameFragment.ivOyuncu1Rengi.setBackgroundResource(R.drawable.custom_button_green)
                        oyuncu1Renk = green
                    }
                    getString(R.string.orange) -> {
                        bindingGameFragment.ivOyuncu1Rengi.setBackgroundResource(R.drawable.custom_button_orange)
                        oyuncu1Renk = orange
                    }
                    getString(R.string.red) -> {
                        bindingGameFragment.ivOyuncu1Rengi.setBackgroundResource(R.drawable.custom_button_red)
                        oyuncu1Renk = red
                    }
                    getString(R.string.blue) -> {
                        bindingGameFragment.ivOyuncu1Rengi.setBackgroundResource(R.drawable.custom_button_blue)
                        oyuncu1Renk = blue
                    }
                    getString(R.string.brown) -> {
                        bindingGameFragment.ivOyuncu1Rengi.setBackgroundResource(R.drawable.custom_button_brown)
                        oyuncu1Renk = brown
                    }
                    getString(R.string.yellow) -> {
                        bindingGameFragment.ivOyuncu1Rengi.setBackgroundResource(R.drawable.custom_button_yellow)
                        oyuncu1Renk = yellow
                    }
                }
            }
            oyuncu2drawable?.let { safeData ->
                oyuncu2RengiStr = safeData
                //gelen string degere gore background degistiriliyor ve oyuncu rengi belirleniyor
                when (safeData) {
                    getString(R.string.lime) -> {
                        bindingGameFragment.ivOyuncu2Rengi.setBackgroundResource(R.drawable.custom_button_lime)
                        oyuncu2Renk = lime
                    }
                    getString(R.string.gray) -> {
                        bindingGameFragment.ivOyuncu2Rengi.setBackgroundResource(R.drawable.custom_button_gray)
                        oyuncu2Renk = gray
                    }
                    getString(R.string.black) -> {
                        bindingGameFragment.ivOyuncu2Rengi.setBackgroundResource(R.drawable.custom_button_black)
                        oyuncu2Renk = black
                    }
                    getString(R.string.green) -> {
                        bindingGameFragment.ivOyuncu2Rengi.setBackgroundResource(R.drawable.custom_button_green)
                        oyuncu2Renk = green
                    }
                    getString(R.string.orange) -> {
                        bindingGameFragment.ivOyuncu2Rengi.setBackgroundResource(R.drawable.custom_button_orange)
                        oyuncu2Renk = orange
                    }
                    getString(R.string.red) -> {
                        bindingGameFragment.ivOyuncu2Rengi.setBackgroundResource(R.drawable.custom_button_red)
                        oyuncu2Renk = red
                    }
                    getString(R.string.blue) -> {
                        bindingGameFragment.ivOyuncu2Rengi.setBackgroundResource(R.drawable.custom_button_blue)
                        oyuncu2Renk = blue
                    }
                    getString(R.string.brown) -> {
                        bindingGameFragment.ivOyuncu2Rengi.setBackgroundResource(R.drawable.custom_button_brown)
                        oyuncu2Renk = brown
                    }
                    getString(R.string.yellow) -> {
                        bindingGameFragment.ivOyuncu2Rengi.setBackgroundResource(R.drawable.custom_button_yellow)
                        oyuncu2Renk = yellow
                    }
                }
            }
        } ?: run {
            //eger baska br sayfadan veri yollanilmadan bu fragmente gelinirse default degerler ayarlaniyor
            bindingGameFragment.oyuncu1AdiTextview.text = oyuncu1Ad
            bindingGameFragment.oyuncu2AdiTextview.text = oyuncu2Ad
            bindingGameFragment.ivOyuncu1Rengi.setBackgroundResource(R.drawable.custom_button_black)
            oyuncu1Renk = black
            bindingGameFragment.ivOyuncu2Rengi.setBackgroundResource(R.drawable.custom_button_red)
            oyuncu2Renk = red

            oyuncu1RengiStr = getString(R.string.black)
            oyuncu2RengiStr = getString(R.string.red)
        }
        //oyun oyuncu1'in baslamasi icin oyuncu2'nin viewi invisible yapiliyor
        bindingGameFragment.oyuncu2SiraImageView.visibility = View.INVISIBLE
        bindingGameFragment.clOyuncu1.setBackgroundColor(Color.argb(40, 0, 255, 255))
        bindingGameFragment.clOyuncu2.setBackgroundColor(Color.TRANSPARENT)
    }

    private fun atamalar() {
        //2 boyutlu bir array olusturuluyor ve viewler sirasina gore ataniyor (10 genislik 6 yukseklik)
        koordinatlar = arrayOf(
            arrayOf(
                bindingGameFragment.button10,
                bindingGameFragment.button11,
                bindingGameFragment.button12,
                bindingGameFragment.button13,
                bindingGameFragment.button14,
                bindingGameFragment.button15,
                bindingGameFragment.button16,
                bindingGameFragment.button17,
                bindingGameFragment.button18,
                bindingGameFragment.button19
            ),
            arrayOf(
                bindingGameFragment.button20,
                bindingGameFragment.button21,
                bindingGameFragment.button22,
                bindingGameFragment.button23,
                bindingGameFragment.button24,
                bindingGameFragment.button25,
                bindingGameFragment.button26,
                bindingGameFragment.button27,
                bindingGameFragment.button28,
                bindingGameFragment.button29
            ),
            arrayOf(
                bindingGameFragment.button30,
                bindingGameFragment.button31,
                bindingGameFragment.button32,
                bindingGameFragment.button33,
                bindingGameFragment.button34,
                bindingGameFragment.button35,
                bindingGameFragment.button36,
                bindingGameFragment.button37,
                bindingGameFragment.button38,
                bindingGameFragment.button39
            ),
            arrayOf(
                bindingGameFragment.button40,
                bindingGameFragment.button41,
                bindingGameFragment.button42,
                bindingGameFragment.button43,
                bindingGameFragment.button44,
                bindingGameFragment.button45,
                bindingGameFragment.button46,
                bindingGameFragment.button47,
                bindingGameFragment.button48,
                bindingGameFragment.button49
            ),
            arrayOf(
                bindingGameFragment.button50,
                bindingGameFragment.button51,
                bindingGameFragment.button52,
                bindingGameFragment.button53,
                bindingGameFragment.button54,
                bindingGameFragment.button55,
                bindingGameFragment.button56,
                bindingGameFragment.button57,
                bindingGameFragment.button58,
                bindingGameFragment.button59
            ),
            arrayOf(
                bindingGameFragment.button60,
                bindingGameFragment.button61,
                bindingGameFragment.button62,
                bindingGameFragment.button63,
                bindingGameFragment.button64,
                bindingGameFragment.button65,
                bindingGameFragment.button66,
                bindingGameFragment.button67,
                bindingGameFragment.button68,
                bindingGameFragment.button69
            )
        )
        //butun viewlerin background degistiliyor
        for (i in 0..5) {
            for (j in 0..9) {
                koordinatlar[i][j].setBackgroundResource(R.drawable.buton_border_custom)
            }
        }
        //yapilan hamlelerin geri alinmasi icin her hamle kaydediliyor
        koordinatKaydediciArray = ArrayList()
    }

    private fun butonClick() {
        bindingGameFragment.buttonGeriAl.setOnClickListener {
            //geri alma islemi icin sirayi eski oyuncuya ceviriyor, eski koordinattaki view eski background olarak setleniyor
            if (koordinatKaydediciArray.size > 0) {
                siraGoster(renkDegis)
                renkDegis++
                koordinatlar[koordinatKaydediciArray[koordinatKaydediciArray.size - 1][0]][koordinatKaydediciArray[koordinatKaydediciArray.size - 1][1]]
                    .setBackgroundResource(R.drawable.buton_border_custom)
                //geri alindigi zaman sutun sayisi arttiriliyor. sutun sayisi en alttan satirdan baslayip 5 ile baslar
                //oyuncu bir hamle yaptigi zaman sutun sayisi azalir, o yuzden burada arttiliyor.
                when (koordinatKaydediciArray[koordinatKaydediciArray.size - 1][1]) {
                    0 -> sutun1yeri++
                    1 -> sutun2yeri++
                    2 -> sutun3yeri++
                    3 -> sutun4yeri++
                    4 -> sutun5yeri++
                    5 -> sutun6yeri++
                    6 -> sutun7yeri++
                    7 -> sutun8yeri++
                    8 -> sutun9yeri++
                    9 -> sutun10yeri++
                }
                //hamle geri alindiktan sonra arrayden silindi
                koordinatKaydediciArray.removeAt(koordinatKaydediciArray.size - 1)
            }
        }
        //sutundaki yukseklik -1 den buyuksek mevcut sutun yerindeki viewin backgroundunu degistirir.
        bindingGameFragment.sutun1.setOnClickListener {
            if (sutun1yeri > -1) {
                //hamle kaydedildi.
                koordinatKaydediciArray.add(
                    ArrayList(
                        listOf(
                            sutun1yeri,
                            0
                        )
                    )
                )
                //renkDegisdeki degere gore oyuncu1 veya oyuncu2 nin rengi backgrounda set edilir ve kazananOyuncu'ya oyuncu adi atanir.
                //en son kazananOyuncu kimde kaldiysa o kazanmis olur
                kazananOyuncu = if (renkDegis % 2 == 0) {
                    //oyuncu1
                    koordinatlar[sutun1yeri][0].setBackgroundColor(oyuncu1Renk)
                    bindingGameFragment.oyuncu1AdiTextview.text.toString()
                } else {
                    //oyuncu2
                    koordinatlar[sutun1yeri][0].setBackgroundColor(oyuncu2Renk)
                    bindingGameFragment.oyuncu2AdiTextview.text.toString()
                }
                siraGoster(renkDegis)
                tumKontroller(sutun1yeri, 0)
                //renkDegis arttilrildi bu sayede diger oyuncuya sira gecmis oldu
                renkDegis++
                //bu sutundaki deger azaltildi bu sayede bir ust view'a gecildi
                sutun1yeri--
            }
        }
        bindingGameFragment.sutun2.setOnClickListener {
            if (sutun2yeri > -1) {
                koordinatKaydediciArray.add(
                    ArrayList(
                        listOf(
                            sutun2yeri,
                            1
                        )
                    )
                )
                kazananOyuncu = if (renkDegis % 2 == 0) {
                    koordinatlar[sutun2yeri][1].setBackgroundColor(oyuncu1Renk)
                    bindingGameFragment.oyuncu1AdiTextview.text.toString()
                } else {
                    koordinatlar[sutun2yeri][1].setBackgroundColor(oyuncu2Renk)
                    bindingGameFragment.oyuncu2AdiTextview.text.toString()
                }
                siraGoster(renkDegis)
                tumKontroller(sutun2yeri, 1)
                renkDegis++
                sutun2yeri--
            }
        }
        bindingGameFragment.sutun3.setOnClickListener {
            if (sutun3yeri > -1) {
                koordinatKaydediciArray.add(
                    ArrayList(
                        listOf(
                            sutun3yeri,
                            2
                        )
                    )
                )
                kazananOyuncu = if (renkDegis % 2 == 0) {
                    koordinatlar[sutun3yeri][2].setBackgroundColor(oyuncu1Renk)
                    bindingGameFragment.oyuncu1AdiTextview.text.toString()
                } else {
                    koordinatlar[sutun3yeri][2].setBackgroundColor(oyuncu2Renk)
                    bindingGameFragment.oyuncu2AdiTextview.text.toString()
                }
                siraGoster(renkDegis)
                tumKontroller(sutun3yeri, 2)
                renkDegis++
                sutun3yeri--
            }
        }
        bindingGameFragment.sutun4.setOnClickListener {
            if (sutun4yeri > -1) {
                koordinatKaydediciArray.add(
                    ArrayList(
                        listOf(
                            sutun4yeri,
                            3
                        )
                    )
                )
                kazananOyuncu = if (renkDegis % 2 == 0) {
                    koordinatlar[sutun4yeri][3].setBackgroundColor(oyuncu1Renk)
                    bindingGameFragment.oyuncu1AdiTextview.text.toString()
                } else {
                    koordinatlar[sutun4yeri][3].setBackgroundColor(oyuncu2Renk)
                    bindingGameFragment.oyuncu2AdiTextview.text.toString()
                }
                siraGoster(renkDegis)
                tumKontroller(sutun4yeri, 3)
                renkDegis++
                sutun4yeri--
            }
        }
        bindingGameFragment.sutun5.setOnClickListener {
            if (sutun5yeri > -1) {
                koordinatKaydediciArray.add(
                    ArrayList(
                        listOf(
                            sutun5yeri,
                            4
                        )
                    )
                )
                kazananOyuncu = if (renkDegis % 2 == 0) {
                    koordinatlar[sutun5yeri][4].setBackgroundColor(oyuncu1Renk)
                    bindingGameFragment.oyuncu1AdiTextview.text.toString()
                } else {
                    koordinatlar[sutun5yeri][4].setBackgroundColor(oyuncu2Renk)
                    bindingGameFragment.oyuncu2AdiTextview.text.toString()
                }
                siraGoster(renkDegis)
                tumKontroller(sutun5yeri, 4)
                renkDegis++
                sutun5yeri--
            }
        }
        bindingGameFragment.sutun6.setOnClickListener {
            if (sutun6yeri > -1) {
                koordinatKaydediciArray.add(
                    ArrayList(
                        listOf(
                            sutun6yeri,
                            5
                        )
                    )
                )
                kazananOyuncu = if (renkDegis % 2 == 0) {
                    koordinatlar[sutun6yeri][5].setBackgroundColor(oyuncu1Renk)
                    bindingGameFragment.oyuncu1AdiTextview.text.toString()
                } else {
                    koordinatlar[sutun6yeri][5].setBackgroundColor(oyuncu2Renk)
                    bindingGameFragment.oyuncu2AdiTextview.text.toString()
                }
                siraGoster(renkDegis)
                tumKontroller(sutun6yeri, 5)
                renkDegis++
                sutun6yeri--
            }
        }
        bindingGameFragment.sutun7.setOnClickListener {
            if (sutun7yeri > -1) {
                koordinatKaydediciArray.add(
                    ArrayList(
                        listOf(
                            sutun7yeri,
                            6
                        )
                    )
                )
                kazananOyuncu = if (renkDegis % 2 == 0) {
                    koordinatlar[sutun7yeri][6].setBackgroundColor(oyuncu1Renk)
                    bindingGameFragment.oyuncu1AdiTextview.text.toString()
                } else {
                    koordinatlar[sutun7yeri][6].setBackgroundColor(oyuncu2Renk)
                    bindingGameFragment.oyuncu2AdiTextview.text.toString()
                }
                siraGoster(renkDegis)
                tumKontroller(sutun7yeri, 6)
                renkDegis++
                sutun7yeri--
            }
        }
        bindingGameFragment.sutun8.setOnClickListener {
            if (sutun8yeri > -1) {
                koordinatKaydediciArray.add(
                    ArrayList(
                        listOf(
                            sutun8yeri,
                            7
                        )
                    )
                )
                kazananOyuncu = if (renkDegis % 2 == 0) {
                    koordinatlar[sutun8yeri][7].setBackgroundColor(oyuncu1Renk)
                    bindingGameFragment.oyuncu1AdiTextview.text.toString()
                } else {
                    koordinatlar[sutun8yeri][7].setBackgroundColor(oyuncu2Renk)
                    bindingGameFragment.oyuncu2AdiTextview.text.toString()
                }
                siraGoster(renkDegis)
                tumKontroller(sutun8yeri, 7)
                renkDegis++
                sutun8yeri--
            }
        }
        bindingGameFragment.sutun9.setOnClickListener {
            if (sutun9yeri > -1) {
                koordinatKaydediciArray.add(
                    ArrayList(
                        listOf(
                            sutun9yeri,
                            8
                        )
                    )
                )
                kazananOyuncu = if (renkDegis % 2 == 0) {
                    koordinatlar[sutun9yeri][8].setBackgroundColor(oyuncu1Renk)
                    bindingGameFragment.oyuncu1AdiTextview.text.toString()
                } else {
                    koordinatlar[sutun9yeri][8].setBackgroundColor(oyuncu2Renk)
                    bindingGameFragment.oyuncu2AdiTextview.text.toString()
                }
                siraGoster(renkDegis)
                tumKontroller(sutun9yeri, 8)
                renkDegis++
                sutun9yeri--
            }
        }
        bindingGameFragment.sutun10.setOnClickListener {
            if (sutun10yeri > -1) {
                koordinatKaydediciArray.add(
                    ArrayList(
                        listOf(
                            sutun10yeri,
                            9
                        )
                    )
                )
                kazananOyuncu = if (renkDegis % 2 == 0) {
                    koordinatlar[sutun10yeri][9].setBackgroundColor(oyuncu1Renk)
                    bindingGameFragment.oyuncu1AdiTextview.text.toString()
                } else {
                    koordinatlar[sutun10yeri][9].setBackgroundColor(oyuncu2Renk)
                    bindingGameFragment.oyuncu2AdiTextview.text.toString()
                }
                siraGoster(renkDegis)
                tumKontroller(sutun10yeri, 9)
                renkDegis++
                sutun10yeri--
            }
        }

    }

    private fun siraGoster(sayi: Int) {
        //sira oyuncu1deyken oyuncu2, oyuncu2deyken oyuncu1'in alanlari gizlendi ve background renkleri degistirildi
        if (sayi % 2 == 0) {
            //ilk tıklamada burası calisiyor
            bindingGameFragment.oyuncu1SiraImageView.visibility = View.INVISIBLE
            bindingGameFragment.oyuncu2SiraImageView.visibility = View.VISIBLE
            bindingGameFragment.clOyuncu2.setBackgroundColor(
                Color.argb(
                    40,
                    0,
                    255,
                    255
                )
            )
            bindingGameFragment.clOyuncu1.setBackgroundColor(Color.TRANSPARENT)
        } else {
            bindingGameFragment.oyuncu1SiraImageView.visibility = View.VISIBLE
            bindingGameFragment.oyuncu2SiraImageView.visibility = View.INVISIBLE
            bindingGameFragment.clOyuncu1.setBackgroundColor(
                Color.argb(
                    40,
                    0,
                    255,
                    255
                )
            )
            bindingGameFragment.clOyuncu2.setBackgroundColor(Color.TRANSPARENT)
        }
    }

    private fun yukaridanAsagiKontrol(satirDegeri: Int, sutun: Int) {
        //en son tiklanilan buton (x olarak gosterilen)baz alinarak arraydeki belirtilen sekilde kontrol ediliyor
        //toplamda 4 view backgroundlari ayni mi diye kontrol ediliyor
        //eger hepsi ayni ise oyun sonlandirma asamasi basliyor
        //kontroller array disina gidebilecegi icin try-catch icinde yapiliyor
        try {
            //x
            //o
            //o
            //o
            val a = koordinatlar[satirDegeri][sutun].background as ColorDrawable
            val b = koordinatlar[satirDegeri + 1][sutun].background as ColorDrawable
            val c = koordinatlar[satirDegeri + 2][sutun].background as ColorDrawable
            val d = koordinatlar[satirDegeri + 3][sutun].background as ColorDrawable
            if (a.color == b.color && a.color == c.color && a.color == d.color) {
                val dogruKoordinatlar = arrayOf(
                    intArrayOf(satirDegeri, sutun),
                    intArrayOf(satirDegeri + 1, sutun),
                    intArrayOf(satirDegeri + 2, sutun),
                    intArrayOf(satirDegeri + 3, sutun)
                )
                sonucEkrani(dogruKoordinatlar)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun solUstSagAltKontrol1(satirDegeri: Int, sutun: Int) {
        try {
            //x---
            //-o--
            //--o-
            //---o
            val a = koordinatlar[satirDegeri][sutun].background as ColorDrawable
            val b = koordinatlar[satirDegeri + 1][sutun + 1].background as ColorDrawable
            val c = koordinatlar[satirDegeri + 2][sutun + 2].background as ColorDrawable
            val d = koordinatlar[satirDegeri + 3][sutun + 3].background as ColorDrawable
            if (a.color == b.color && a.color == c.color && a.color == d.color) {
                val dogruKoordinatlar = arrayOf(
                    intArrayOf(satirDegeri, sutun),
                    intArrayOf(satirDegeri + 1, sutun + 1),
                    intArrayOf(satirDegeri + 2, sutun + 2),
                    intArrayOf(satirDegeri + 3, sutun + 3)
                )
                sonucEkrani(dogruKoordinatlar)
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    private fun solUstSagAltKontrol2(satirDegeri: Int, sutun: Int) {
        try {
            //o---
            //-x--
            //--o-
            //---o
            val a = koordinatlar[satirDegeri][sutun].background as ColorDrawable
            val b = koordinatlar[satirDegeri - 1][sutun - 1].background as ColorDrawable
            val c = koordinatlar[satirDegeri + 1][sutun + 1].background as ColorDrawable
            val d = koordinatlar[satirDegeri + 2][sutun + 2].background as ColorDrawable
            if (a.color == b.color && a.color == c.color && a.color == d.color) {
                val dogruKoordinatlar = arrayOf(
                    intArrayOf(satirDegeri, sutun),
                    intArrayOf(satirDegeri - 1, sutun - 1),
                    intArrayOf(satirDegeri + 1, sutun + 1),
                    intArrayOf(satirDegeri + 2, sutun + 2)
                )
                sonucEkrani(dogruKoordinatlar)
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    private fun solUstSagAltKontrol3(satirDegeri: Int, sutun: Int) {
        try {
            //o---
            //-o--
            //--x-
            //---o
            val a = koordinatlar[satirDegeri][sutun].background as ColorDrawable
            val b = koordinatlar[satirDegeri - 1][sutun - 1].background as ColorDrawable
            val c = koordinatlar[satirDegeri - 2][sutun - 2].background as ColorDrawable
            val d = koordinatlar[satirDegeri + 1][sutun + 1].background as ColorDrawable
            if (a.color == b.color && a.color == c.color && a.color == d.color) {
                val dogruKoordinatlar = arrayOf(
                    intArrayOf(satirDegeri, sutun),
                    intArrayOf(satirDegeri - 1, sutun - 1),
                    intArrayOf(satirDegeri - 2, sutun - 2),
                    intArrayOf(satirDegeri + 1, sutun + 1)
                )
                sonucEkrani(dogruKoordinatlar)
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    private fun solUstSagAltKontrol4(satirDegeri: Int, sutun: Int) {
        try {
            //o---
            //-o--
            //--o-
            //---x
            val a = koordinatlar[satirDegeri][sutun].background as ColorDrawable
            val b = koordinatlar[satirDegeri - 1][sutun - 1].background as ColorDrawable
            val c = koordinatlar[satirDegeri - 2][sutun - 2].background as ColorDrawable
            val d = koordinatlar[satirDegeri - 3][sutun - 3].background as ColorDrawable
            if (a.color == b.color && a.color == c.color && a.color == d.color) {
                val dogruKoordinatlar = arrayOf(
                    intArrayOf(satirDegeri, sutun),
                    intArrayOf(satirDegeri - 1, sutun - 1),
                    intArrayOf(satirDegeri - 2, sutun - 2),
                    intArrayOf(satirDegeri - 3, sutun - 3)
                )
                sonucEkrani(dogruKoordinatlar)
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    private fun sagUstSolAltKontrol1(satirDegeri: Int, sutun: Int) {
        try {
            //---x
            //--o-
            //-o--
            //o---
            val a = koordinatlar[satirDegeri][sutun].background as ColorDrawable
            val b = koordinatlar[satirDegeri + 1][sutun - 1].background as ColorDrawable
            val c = koordinatlar[satirDegeri + 2][sutun - 2].background as ColorDrawable
            val d = koordinatlar[satirDegeri + 3][sutun - 3].background as ColorDrawable
            if (a.color == b.color && a.color == c.color && a.color == d.color) {
                val dogruKoordinatlar = arrayOf(
                    intArrayOf(satirDegeri, sutun),
                    intArrayOf(satirDegeri + 1, sutun - 1),
                    intArrayOf(satirDegeri + 2, sutun - 2),
                    intArrayOf(satirDegeri + 3, sutun - 3)
                )
                sonucEkrani(dogruKoordinatlar)
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    private fun sagUstSolAltKontrol2(satirDegeri: Int, sutun: Int) {
        try {
            //---o
            //--x-
            //-o--
            //o---
            val a = koordinatlar[satirDegeri][sutun].background as ColorDrawable
            val b = koordinatlar[satirDegeri - 1][sutun + 1].background as ColorDrawable
            val c = koordinatlar[satirDegeri + 1][sutun - 1].background as ColorDrawable
            val d = koordinatlar[satirDegeri + 2][sutun - 2].background as ColorDrawable
            if (a.color == b.color && a.color == c.color && a.color == d.color) {
                val dogruKoordinatlar = arrayOf(
                    intArrayOf(satirDegeri, sutun),
                    intArrayOf(satirDegeri - 1, sutun + 1),
                    intArrayOf(satirDegeri + 1, sutun - 1),
                    intArrayOf(satirDegeri + 2, sutun - 2)
                )
                sonucEkrani(dogruKoordinatlar)
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    private fun sagUstSolAltKontrol3(satirDegeri: Int, sutun: Int) {
        try {
            //---o
            //--o-
            //-x--
            //o---
            val a = koordinatlar[satirDegeri][sutun].background as ColorDrawable
            val b = koordinatlar[satirDegeri - 1][sutun + 1].background as ColorDrawable
            val c = koordinatlar[satirDegeri - 2][sutun + 2].background as ColorDrawable
            val d = koordinatlar[satirDegeri + 1][sutun - 1].background as ColorDrawable
            if (a.color == b.color && a.color == c.color && a.color == d.color) {
                val dogruKoordinatlar = arrayOf(
                    intArrayOf(satirDegeri, sutun),
                    intArrayOf(satirDegeri - 1, sutun + 1),
                    intArrayOf(satirDegeri - 2, sutun + 2),
                    intArrayOf(satirDegeri + 1, sutun - 1)
                )
                sonucEkrani(dogruKoordinatlar)
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    private fun sagUstSolAltKontrol4(satirDegeri: Int, sutun: Int) {
        try {
            //---o
            //--o-
            //-o--
            //x---
            val a = koordinatlar[satirDegeri][sutun].background as ColorDrawable
            val b = koordinatlar[satirDegeri - 1][sutun + 1].background as ColorDrawable
            val c = koordinatlar[satirDegeri - 2][sutun + 2].background as ColorDrawable
            val d = koordinatlar[satirDegeri - 3][sutun + 3].background as ColorDrawable
            if (a.color == b.color && a.color == c.color && a.color == d.color) {
                val dogruKoordinatlar = arrayOf(
                    intArrayOf(satirDegeri, sutun),
                    intArrayOf(satirDegeri - 1, sutun + 1),
                    intArrayOf(satirDegeri - 2, sutun + 2),
                    intArrayOf(satirDegeri - 3, sutun + 3)
                )
                sonucEkrani(dogruKoordinatlar)
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    private fun yatayKontrol1(satirDegeri: Int, sutun: Int) {
        try {
            //xooo
            val a = koordinatlar[satirDegeri][sutun].background as ColorDrawable
            val b = koordinatlar[satirDegeri][sutun + 1].background as ColorDrawable
            val c = koordinatlar[satirDegeri][sutun + 2].background as ColorDrawable
            val d = koordinatlar[satirDegeri][sutun + 3].background as ColorDrawable
            if (a.color == b.color && a.color == c.color && a.color == d.color) {
                val dogruKoordinatlar = arrayOf(
                    intArrayOf(satirDegeri, sutun),
                    intArrayOf(satirDegeri, sutun + 1),
                    intArrayOf(satirDegeri, sutun + 2),
                    intArrayOf(satirDegeri, sutun + 3)
                )
                sonucEkrani(dogruKoordinatlar)
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    private fun yatayKontrol2(satirDegeri: Int, sutun: Int) {
        try {
            //oxoo
            val a = koordinatlar[satirDegeri][sutun].background as ColorDrawable
            val b = koordinatlar[satirDegeri][sutun - 1].background as ColorDrawable
            val c = koordinatlar[satirDegeri][sutun + 1].background as ColorDrawable
            val d = koordinatlar[satirDegeri][sutun + 2].background as ColorDrawable
            if (a.color == b.color && a.color == c.color && a.color == d.color) {
                val dogruKoordinatlar = arrayOf(
                    intArrayOf(satirDegeri, sutun),
                    intArrayOf(satirDegeri, sutun - 1),
                    intArrayOf(satirDegeri, sutun + 1),
                    intArrayOf(satirDegeri, sutun + 2)
                )
                sonucEkrani(dogruKoordinatlar)
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    private fun yatayKontrol3(satirDegeri: Int, sutun: Int) {
        try {
            //ooxo
            val a = koordinatlar[satirDegeri][sutun].background as ColorDrawable
            val b = koordinatlar[satirDegeri][sutun - 1].background as ColorDrawable
            val c = koordinatlar[satirDegeri][sutun - 2].background as ColorDrawable
            val d = koordinatlar[satirDegeri][sutun + 1].background as ColorDrawable
            if (a.color == b.color && a.color == c.color && a.color == d.color) {
                val dogruKoordinatlar = arrayOf(
                    intArrayOf(satirDegeri, sutun),
                    intArrayOf(satirDegeri, sutun - 1),
                    intArrayOf(satirDegeri, sutun - 2),
                    intArrayOf(satirDegeri, sutun + 1)
                )
                sonucEkrani(dogruKoordinatlar)
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    private fun yatayKontrol4(satirDegeri: Int, sutun: Int) {
        try {
            //xooo
            val a = koordinatlar[satirDegeri][sutun].background as ColorDrawable
            val b = koordinatlar[satirDegeri][sutun - 1].background as ColorDrawable
            val c = koordinatlar[satirDegeri][sutun - 2].background as ColorDrawable
            val d = koordinatlar[satirDegeri][sutun - 3].background as ColorDrawable
            if (a.color == b.color && a.color == c.color && a.color == d.color) {
                val dogruKoordinatlar = arrayOf(
                    intArrayOf(satirDegeri, sutun),
                    intArrayOf(satirDegeri, sutun - 1),
                    intArrayOf(satirDegeri, sutun - 2),
                    intArrayOf(satirDegeri, sutun - 3)
                )
                sonucEkrani(dogruKoordinatlar)
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    private fun tumKontroller(satirDegeri: Int, sutun: Int) {
        //tum kontroller her hamleden sonra buradan cagiriliyor
        yukaridanAsagiKontrol(satirDegeri, sutun)
        solUstSagAltKontrol1(satirDegeri, sutun)
        solUstSagAltKontrol2(satirDegeri, sutun)
        solUstSagAltKontrol3(satirDegeri, sutun)
        solUstSagAltKontrol4(satirDegeri, sutun)
        sagUstSolAltKontrol1(satirDegeri, sutun)
        sagUstSolAltKontrol2(satirDegeri, sutun)
        sagUstSolAltKontrol3(satirDegeri, sutun)
        sagUstSolAltKontrol4(satirDegeri, sutun)
        yatayKontrol1(satirDegeri, sutun)
        yatayKontrol2(satirDegeri, sutun)
        yatayKontrol3(satirDegeri, sutun)
        yatayKontrol4(satirDegeri, sutun)
    }

    private fun sonucEkrani(koordinatlar: Array<IntArray>) {
        //bir oyuncu kazandigi zaman 10 adet viewin bulundugu viewler gizleniyor
        bindingGameFragment.butonlarSatiri.visibility = View.INVISIBLE
        bindingGameFragment.buttonGeriAl.visibility = View.INVISIBLE
        siraGoster(renkDegis + 1)
        kazanmaEfekti(koordinatlar)
    }

    private fun kazanmaEfekti(koordinat: Array<IntArray>) {
        //kazanan oyuncunun nasil kazandigini gostermek icin kazandigi kosullardaki viewler yanip sonme efekti ile gosteriliyor
        val toplamsure: Long = 3000
        val saniye: Long = 500
        val buton = koordinatlar[koordinat[0][0]][koordinat[0][1]].background as ColorDrawable
        val suankirenkleri = buton.color
        val kontrolSayisi = intArrayOf(0)
        val renkdize = ArrayList<Int>()
        //bu renkler sırayla yanıp sönerek kazananın kazandigi koordinatlari belli ediyor
        renkdize.add(suankirenkleri)
        renkdize.add(transparent)
        object : CountDownTimer(toplamsure, saniye) {
            override fun onTick(millisUntilFinished: Long) {
                koordinatlar[koordinat[0][0]][koordinat[0][1]].setBackgroundColor(renkdize[kontrolSayisi[0] % 2])
                koordinatlar[koordinat[1][0]][koordinat[1][1]].setBackgroundColor(renkdize[kontrolSayisi[0] % 2])
                koordinatlar[koordinat[2][0]][koordinat[2][1]].setBackgroundColor(renkdize[kontrolSayisi[0] % 2])
                koordinatlar[koordinat[3][0]][koordinat[3][1]].setBackgroundColor(renkdize[kontrolSayisi[0] % 2])
                kontrolSayisi[0]++
            }

            override fun onFinish() {
                koordinatlar[koordinat[0][0]][koordinat[0][1]].setBackgroundColor(suankirenkleri)
                koordinatlar[koordinat[1][0]][koordinat[1][1]].setBackgroundColor(suankirenkleri)
                koordinatlar[koordinat[2][0]][koordinat[2][1]].setBackgroundColor(suankirenkleri)
                koordinatlar[koordinat[3][0]][koordinat[3][1]].setBackgroundColor(suankirenkleri)
                //eğer aynı anda birden fazla şekilde kazanma durumu varsa birden fazla builder oluşturmamak için kontrol
                if (!isBuilderExisting) uyariEkrani()
            }
        }.start()
    }

    private fun uyariEkrani() {
        //oyunun bittigini gostermek icin alertdialog ile kullanici bilgilendiriliyor
        //ayni anda birden fazla kontrol dogru cikmasi ihtimaline karsi isBuilderExisting ile yalnizca 1 kez olusturulmasi saglaniyor
        isBuilderExisting = true
        val builder = AlertDialog.Builder(this@GameFragment.requireActivity())
        builder.setTitle("Tebrikler!")
        builder.setMessage("$kazananOyuncu kazandı.")
        builder.setCancelable(false)
        builder.setNeutralButton("Girişe Git") { _: DialogInterface?, _: Int ->
            navController.navigate(R.id.action_gameFragment_to_homeFragment)
        }
        builder.setNegativeButton("Tekrar Oyna") { _: DialogInterface?, _: Int ->
            //tekrar oynanmasi icin oyuncu adlari ve renkleri tekrar gonderiliyor
            navController.navigate(R.id.action_gameFragment_self, Bundle().apply {
                putString("oyuncu1Ad", oyuncu1Ad)
                putString("oyuncu2Ad", oyuncu2Ad)
                putString("oyuncu1Rengi", oyuncu1RengiStr)
                putString("oyuncu2Rengi", oyuncu2RengiStr)
            })
        }
        builder.show()
    }

    private fun onBackPressed() {
        //kullanici oyun ekranindayken backpress durumunda toplamda 2 saniyeden fazla basarsa geri yonlerdirme yapiliyor
        val geriTusuToast: Toast = Toast.makeText(
            this@GameFragment.requireActivity(),
            "Giriş sayfasına gitmek için bir daha tıklayın.",
            Toast.LENGTH_SHORT
        )
        if (geriTusuBasilisSuresi + 2000 > System.currentTimeMillis()) {
            geriTusuToast.cancel()
            navController.navigate(R.id.action_gameFragment_to_homeFragment)
        } else {
            geriTusuToast.show()
        }
        geriTusuBasilisSuresi = System.currentTimeMillis()
    }
}