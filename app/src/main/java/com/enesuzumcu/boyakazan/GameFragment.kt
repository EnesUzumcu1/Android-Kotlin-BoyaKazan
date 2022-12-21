package com.enesuzumcu.boyakazan

import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.enesuzumcu.boyakazan.databinding.FragmentGameBinding
import com.enesuzumcu.boyakazan.model.Player

class GameFragment : Fragment() {
    private lateinit var bindingGameFragment: FragmentGameBinding
    private lateinit var navController: NavController
    private var maxValueYAxis = 5
    private var yAxis1: Int = 0
    private var yAxis2: Int = 0
    private var yAxis3: Int = 0
    private var yAxis4: Int = 0
    private var yAxis5: Int = 0
    private var yAxis6: Int = 0
    private var yAxis7: Int = 0
    private var yAxis8: Int = 0
    private var yAxis9: Int = 0
    private var yAxis10: Int = 0
    //5
    //4                         maxValueYAxis max yuksekligi belirliyor
    //3                         koordinatlar arrayi icindeki viewler bu numaralandirmaya gore cagiriliyor
    //2                         en ustteki view en yuksek maxValueYAxis sahip
    //1                         //<-koordinatlar y ekseni sirasi
    //0
    //0 1 2 3 4 5 6 7 8 9       <-koordinatlar x ekseni sirasi

    //true - Player1 / false - Player2
    private var currentPlayer = true

    private var backPressTime: Long = 0
    private lateinit var koordinatlar: MutableList<MutableList<ImageView>>
    private lateinit var moveRecorderArray: ArrayList<ArrayList<Int>>
    private var isBuilderExisting: Boolean = false

    //max tiklanma sayisi ulasmasina ragmen oyun bitmediyse beraberlik vermek icin sayici olusturuldu
    private var clickCount: Int = 0
    private var maxClickCount: Int = 60

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
        setBtnClick()

        //backpress basildiginda gerekli durum icin callback cagirildi
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            // Handle the back button event
            onBackPressed()
        }
    }

    private fun baslangic() {
        navController = findNavController()

        bindingGameFragment.tvPlayer1Name.text = Player.player1?.name
        bindingGameFragment.tvPlayer2Name.text = Player.player2?.name

        updateBackgroundResource(Player.player1, bindingGameFragment.ivOyuncu1Rengi, true)
        updateBackgroundResource(Player.player2, bindingGameFragment.ivOyuncu2Rengi, false)

        //oyun oyuncu1'in baslamasi icin oyuncu2'nin viewi invisible yapiliyor
        bindingGameFragment.ivPlayer2Turn.visibility = View.INVISIBLE
        bindingGameFragment.clOyuncu1.setBackgroundColor(Color.argb(40, 0, 255, 255))
        bindingGameFragment.clOyuncu2.setBackgroundColor(Color.TRANSPARENT)
    }

    private fun atamalar() {
        //2 boyutlu bir array olusturuluyor ve viewler sirasina gore ataniyor (10 genislik 6 yukseklik)
        koordinatlar = mutableListOf(
            mutableListOf(
                bindingGameFragment.iv60,
                bindingGameFragment.iv61,
                bindingGameFragment.iv62,
                bindingGameFragment.iv63,
                bindingGameFragment.iv64,
                bindingGameFragment.iv65,
                bindingGameFragment.iv66,
                bindingGameFragment.iv67,
                bindingGameFragment.iv68,
                bindingGameFragment.iv69
            ),
            mutableListOf(
                bindingGameFragment.iv50,
                bindingGameFragment.iv51,
                bindingGameFragment.iv52,
                bindingGameFragment.iv53,
                bindingGameFragment.iv54,
                bindingGameFragment.iv55,
                bindingGameFragment.iv56,
                bindingGameFragment.iv57,
                bindingGameFragment.iv58,
                bindingGameFragment.iv59
            ),mutableListOf(
                bindingGameFragment.iv40,
                bindingGameFragment.iv41,
                bindingGameFragment.iv42,
                bindingGameFragment.iv43,
                bindingGameFragment.iv44,
                bindingGameFragment.iv45,
                bindingGameFragment.iv46,
                bindingGameFragment.iv47,
                bindingGameFragment.iv48,
                bindingGameFragment.iv49
            ),mutableListOf(
                bindingGameFragment.iv30,
                bindingGameFragment.iv31,
                bindingGameFragment.iv32,
                bindingGameFragment.iv33,
                bindingGameFragment.iv34,
                bindingGameFragment.iv35,
                bindingGameFragment.iv36,
                bindingGameFragment.iv37,
                bindingGameFragment.iv38,
                bindingGameFragment.iv39
            ),mutableListOf(
                bindingGameFragment.iv20,
                bindingGameFragment.iv21,
                bindingGameFragment.iv22,
                bindingGameFragment.iv23,
                bindingGameFragment.iv24,
                bindingGameFragment.iv25,
                bindingGameFragment.iv26,
                bindingGameFragment.iv27,
                bindingGameFragment.iv28,
                bindingGameFragment.iv29
            ),mutableListOf(
                bindingGameFragment.iv10,
                bindingGameFragment.iv11,
                bindingGameFragment.iv12,
                bindingGameFragment.iv13,
                bindingGameFragment.iv14,
                bindingGameFragment.iv15,
                bindingGameFragment.iv16,
                bindingGameFragment.iv17,
                bindingGameFragment.iv18,
                bindingGameFragment.iv19
            )
        )
        //butun viewlerin background degistiliyor
        for (i in 0..5) {
            for (j in 0..9) {
                koordinatlar[i][j].setBackgroundResource(R.drawable.buton_border_custom)
            }
        }
        //yapilan hamlelerin geri alinmasi icin her hamle kaydediliyor
        moveRecorderArray = ArrayList()
    }

    private fun checkMoves(yAxis: Int, xAxis: Int): Boolean {
        //yAxis yuksekligi maxValueYAxis kucukse mevcut sutun yerindeki viewin backgroundunu degistirir.
        if (yAxis <= maxValueYAxis) {
            //hamle kaydedildi.
            moveRecorderArray.add(
                ArrayList(
                    listOf(
                        yAxis,
                        xAxis
                    )
                )
            )
            //renkDegisdeki degere gore oyuncu1 veya oyuncu2 nin rengi backgrounda set edilir.
            if (currentPlayer) {
                //player1
                koordinatlar[yAxis][xAxis].setBackgroundColor(Player.player1?.color!!)
            } else {
                //player2
                koordinatlar[yAxis][xAxis].setBackgroundColor(Player.player2?.color!!)
            }
            //currentPlayer degistirildi bu sayede diger oyuncuya sira gecmis oldu
            currentPlayer = currentPlayer.not()
            allControls(yAxis, xAxis)
            showNextPlayer(currentPlayer)

            clickCount++
            if (clickCount == maxClickCount) showAlertDialog(getString(R.string.beraberlik))
            return true
        } else return false
    }

    private fun setBtnClick() {
        bindingGameFragment.buttonGeriAl.setOnClickListener {
            //geri alma islemi icin sirayi eski oyuncuya ceviriyor, eski koordinattaki view eski background olarak setleniyor
            if (moveRecorderArray.size > 0) {
                showNextPlayer(currentPlayer)
                currentPlayer = currentPlayer.not()
                koordinatlar[moveRecorderArray[moveRecorderArray.size - 1][0]][moveRecorderArray[moveRecorderArray.size - 1][1]]
                    .setBackgroundResource(R.drawable.buton_border_custom)
                //geri alindigi zaman sutun sayisi arttiriliyor. sutun sayisi en alttan satirdan baslayip 5 ile baslar
                //oyuncu bir hamle yaptigi zaman sutun sayisi azalir, o yuzden burada arttiliyor.
                when (moveRecorderArray[moveRecorderArray.size - 1][1]) {
                    0 -> yAxis1--
                    1 -> yAxis2--
                    2 -> yAxis3--
                    3 -> yAxis4--
                    4 -> yAxis5--
                    5 -> yAxis6--
                    6 -> yAxis7--
                    7 -> yAxis8--
                    8 -> yAxis9--
                    9 -> yAxis10--
                }
                //hamle geri alindiktan sonra arrayden silindi
                moveRecorderArray.removeAt(moveRecorderArray.size - 1)
                //hamel geri alininca tiklama sayisida azaltiliyor
                clickCount--
            }
        }
        bindingGameFragment.xAxis1.setOnClickListener {
            if (checkMoves(yAxis1, 0)) {
                //bu sutundaki deger azaltildi bu sayede bir ust view'a gecildi
                yAxis1++
            }
        }
        bindingGameFragment.xAxis2.setOnClickListener {
            if (checkMoves(yAxis2, 1)) {
                yAxis2++
            }
        }
        bindingGameFragment.xAxis3.setOnClickListener {
            if (checkMoves(yAxis3, 2)) {
                yAxis3++
            }
        }
        bindingGameFragment.xAxis4.setOnClickListener {
            if (checkMoves(yAxis4, 3)) {
                yAxis4++
            }
        }
        bindingGameFragment.xAxis5.setOnClickListener {
            if (checkMoves(yAxis5, 4)) {
                yAxis5++
            }
        }
        bindingGameFragment.xAxis6.setOnClickListener {
            if (checkMoves(yAxis6, 5)) {
                yAxis6++
            }
        }
        bindingGameFragment.xAxis7.setOnClickListener {
            if (checkMoves(yAxis7, 6)) {
                yAxis7++
            }
        }
        bindingGameFragment.xAxis8.setOnClickListener {
            if (checkMoves(yAxis8, 7)) {
                yAxis8++
            }
        }
        bindingGameFragment.xAxis9.setOnClickListener {
            if (checkMoves(yAxis9, 8)) {
                yAxis9++
            }
        }
        bindingGameFragment.xAxis10.setOnClickListener {
            if (checkMoves(yAxis10, 9)) {
                yAxis10++
            }
        }

    }

    private fun showNextPlayer(currentPlayer: Boolean) {
        //sira oyuncu1deyken oyuncu2, oyuncu2deyken oyuncu1'in alanlari gizlendi ve background renkleri degistirildi
        //true - Player1 / false - Player2
        if (currentPlayer) {
            //ilk tıklamada burası calisiyor
            bindingGameFragment.ivPlayer1Turn.visibility = View.INVISIBLE
            bindingGameFragment.ivPlayer2Turn.visibility = View.VISIBLE
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
            bindingGameFragment.ivPlayer1Turn.visibility = View.VISIBLE
            bindingGameFragment.ivPlayer2Turn.visibility = View.INVISIBLE
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

    private fun checkVertical(yAxis: Int, xAxis: Int) {
        //en son tiklanilan buton (x olarak gosterilen)baz alinarak arraydeki belirtilen sekilde kontrol ediliyor
        //x
        //o
        //o
        //o
        baseCheck(
            yAxis,
            xAxis,
            distanceFromYAxis1 = -1,
            distanceFromYAxis2 = -2,
            distanceFromYAxis3 = -3
        )
    }

    private fun checkLeftTopRightBottom1(yAxis: Int, xAxis: Int) {
        //x---
        //-o--
        //--o-
        //---o
        baseCheck(
            yAxis,
            xAxis,
            distanceFromYAxis1 = -1,
            distanceFromYAxis2 = -2,
            distanceFromYAxis3 = -3,
            distanceFromXAxis1 = +1,
            distanceFromXAxis2 = +2,
            distanceFromXAxis3 = +3
        )
    }

    private fun checkLeftTopRightBottom2(yAxis: Int, xAxis: Int) {
        //o---
        //-x--
        //--o-
        //---o
        baseCheck(
            yAxis,
            xAxis,
            distanceFromYAxis1 = +1,
            distanceFromYAxis2 = -1,
            distanceFromYAxis3 = -2,
            distanceFromXAxis1 = -1,
            distanceFromXAxis2 = +1,
            distanceFromXAxis3 = +2
        )
    }

    private fun checkLeftTopRightBottom3(yAxis: Int, xAxis: Int) {
        //o---
        //-o--
        //--x-
        //---o
        baseCheck(
            yAxis,
            xAxis,
            distanceFromYAxis1 = +1,
            distanceFromYAxis2 = +2,
            distanceFromYAxis3 = -1,
            distanceFromXAxis1 = -1,
            distanceFromXAxis2 = -2,
            distanceFromXAxis3 = +1
        )
    }

    private fun checkLeftTopRightBottom4(yAxis: Int, xAxis: Int) {
        //o---
        //-o--
        //--o-
        //---x
        baseCheck(
            yAxis,
            xAxis,
            distanceFromYAxis1 = +1,
            distanceFromYAxis2 = +2,
            distanceFromYAxis3 = +3,
            distanceFromXAxis1 = -1,
            distanceFromXAxis2 = -2,
            distanceFromXAxis3 = -3
        )
    }

    private fun checkRightTopLeftBottom1(yAxis: Int, xAxis: Int) {
        //---x
        //--o-
        //-o--
        //o---
        baseCheck(
            yAxis,
            xAxis,
            distanceFromYAxis1 = -1,
            distanceFromYAxis2 = -2,
            distanceFromYAxis3 = -3,
            distanceFromXAxis1 = -1,
            distanceFromXAxis2 = -2,
            distanceFromXAxis3 = -3
        )
    }

    private fun checkRightTopLeftBottom2(yAxis: Int, xAxis: Int) {
        //---o
        //--x-
        //-o--
        //o---
        baseCheck(
            yAxis,
            xAxis,
            distanceFromYAxis1 = +1,
            distanceFromYAxis2 = -1,
            distanceFromYAxis3 = -2,
            distanceFromXAxis1 = +1,
            distanceFromXAxis2 = -1,
            distanceFromXAxis3 = -2
        )
    }

    private fun checkRightTopLeftBottom3(yAxis: Int, xAxis: Int) {
        //---o
        //--o-
        //-x--
        //o---
        baseCheck(
            yAxis,
            xAxis,
            distanceFromYAxis1 = +1,
            distanceFromYAxis2 = +2,
            distanceFromYAxis3 = -1,
            distanceFromXAxis1 = +1,
            distanceFromXAxis2 = +2,
            distanceFromXAxis3 = -1
        )
    }

    private fun checkRightTopLeftBottom4(yAxis: Int, xAxis: Int) {
        //---o
        //--o-
        //-o--
        //x---
        baseCheck(
            yAxis,
            xAxis,
            distanceFromYAxis1 = +1,
            distanceFromYAxis2 = +2,
            distanceFromYAxis3 = +3,
            distanceFromXAxis1 = +1,
            distanceFromXAxis2 = +2,
            distanceFromXAxis3 = +3
        )
    }

    private fun checkHorizontal1(yAxis: Int, xAxis: Int) {
        //xooo
        baseCheck(
            yAxis,
            xAxis,
            distanceFromXAxis1 = +1,
            distanceFromXAxis2 = +2,
            distanceFromXAxis3 = +3
        )
    }

    private fun checkHorizontal2(yAxis: Int, xAxis: Int) {
        //oxoo
        baseCheck(
            yAxis,
            xAxis,
            distanceFromXAxis1 = -1,
            distanceFromXAxis2 = +1,
            distanceFromXAxis3 = +2
        )
    }

    private fun checkHorizontal3(yAxis: Int, xAxis: Int) {
        //ooxo
        baseCheck(
            yAxis,
            xAxis,
            distanceFromXAxis1 = -1,
            distanceFromXAxis2 = -2,
            distanceFromXAxis3 = +1
        )
    }

    private fun checkHorizontal4(yAxis: Int, xAxis: Int) {
        //ooox
        baseCheck(
            yAxis,
            xAxis,
            distanceFromXAxis1 = -1,
            distanceFromXAxis2 = -2,
            distanceFromXAxis3 = -3
        )
    }

    private fun baseCheck(
        yAxis: Int,
        xAxis: Int,
        distanceFromYAxis1: Int = 0,
        distanceFromYAxis2: Int = 0,
        distanceFromYAxis3: Int = 0,
        distanceFromXAxis1: Int = 0,
        distanceFromXAxis2: Int = 0,
        distanceFromXAxis3: Int = 0
    ) {
        //en son tiklanilan buton baz alinarak arraydeki belirtilen sekilde kontrol ediliyor
        //toplamda 4 view backgroundlari ayni mi diye kontrol ediliyor
        //eger hepsi ayni ise oyun sonlandirma asamasi basliyor
        //kontroller array disina gidebilecegi icin try-catch icinde yapiliyor

        //----
        //----              saga dogru gitmek icin deger arttirmak gerekiyor, sola dogru azalmak gerekiyor
        //----              yukari cikmak icin deger azalmak gerekiyor, asagı dogru arttırmak gerekiyor
        //----
        //satirDegeri yukardan asagi dogru hangi satirda oldugunun degeri
        //sutun sagdan sola hangi sutun oldugunun degeri
        try {
            val a = koordinatlar[yAxis][xAxis].background as ColorDrawable
            val b = koordinatlar[yAxis + (distanceFromYAxis1)][xAxis + (distanceFromXAxis1)].background as ColorDrawable
            val c = koordinatlar[yAxis + (distanceFromYAxis2)][xAxis + (distanceFromXAxis2)].background as ColorDrawable
            val d = koordinatlar[yAxis + (distanceFromYAxis3)][xAxis + (distanceFromXAxis3)].background as ColorDrawable
            if (a.color == b.color && a.color == c.color && a.color == d.color) {
                val dogruKoordinatlar = arrayOf(
                    intArrayOf(yAxis, xAxis),
                    intArrayOf(yAxis + (distanceFromYAxis1), xAxis + (distanceFromXAxis1)),
                    intArrayOf(yAxis + (distanceFromYAxis2), xAxis + (distanceFromXAxis2)),
                    intArrayOf(yAxis + (distanceFromYAxis3), xAxis + (distanceFromXAxis3))
                )
                result(dogruKoordinatlar)
            }
        } catch (_: java.lang.Exception) {
        }
    }

    private fun allControls(yAxis: Int, xAxis: Int) {
        //tum kontroller her hamleden sonra buradan cagiriliyor
        checkVertical(yAxis, xAxis)
        checkLeftTopRightBottom1(yAxis, xAxis)
        checkLeftTopRightBottom2(yAxis, xAxis)
        checkLeftTopRightBottom3(yAxis, xAxis)
        checkLeftTopRightBottom4(yAxis, xAxis)
        checkRightTopLeftBottom1(yAxis, xAxis)
        checkRightTopLeftBottom2(yAxis, xAxis)
        checkRightTopLeftBottom3(yAxis, xAxis)
        checkRightTopLeftBottom4(yAxis, xAxis)
        checkHorizontal1(yAxis, xAxis)
        checkHorizontal2(yAxis, xAxis)
        checkHorizontal3(yAxis, xAxis)
        checkHorizontal4(yAxis, xAxis)
    }

    private fun result(coordinates: Array<IntArray>) {
        //bir oyuncu kazandigi zaman 10 adet viewin bulundugu viewler gizleniyor
        bindingGameFragment.butonlarSatiri.visibility = View.INVISIBLE
        bindingGameFragment.buttonGeriAl.visibility = View.INVISIBLE
        showNextPlayer(currentPlayer)
        winEffect(coordinates)
    }

    private fun winEffect(coordinate: Array<IntArray>) {
        //kazanan oyuncunun nasil kazandigini gostermek icin kazandigi kosullardaki viewler yanip sonme efekti ile gosteriliyor
        val winnerColor = if (currentPlayer.not()) {
            Player.player1?.color!!
        } else {
            Player.player2?.color!!
        }
        var controlCount = 0
        val colors = ArrayList<Int>()
        //bu renkler sırayla yanıp sönerek kazananın kazandigi koordinatlari belli ediyor
        colors.add(winnerColor)
        colors.add(Color.TRANSPARENT)
        object : CountDownTimer(3000, 500) {
            override fun onTick(millisUntilFinished: Long) {
                koordinatlar[coordinate[0][0]][coordinate[0][1]].setBackgroundColor(colors[controlCount % 2])
                koordinatlar[coordinate[1][0]][coordinate[1][1]].setBackgroundColor(colors[controlCount % 2])
                koordinatlar[coordinate[2][0]][coordinate[2][1]].setBackgroundColor(colors[controlCount % 2])
                koordinatlar[coordinate[3][0]][coordinate[3][1]].setBackgroundColor(colors[controlCount % 2])
                controlCount++
            }

            override fun onFinish() {
                koordinatlar[coordinate[0][0]][coordinate[0][1]].setBackgroundColor(winnerColor)
                koordinatlar[coordinate[1][0]][coordinate[1][1]].setBackgroundColor(winnerColor)
                koordinatlar[coordinate[2][0]][coordinate[2][1]].setBackgroundColor(winnerColor)
                koordinatlar[coordinate[3][0]][coordinate[3][1]].setBackgroundColor(winnerColor)
                //eğer aynı anda birden fazla şekilde kazanma durumu varsa birden fazla builder oluşturmamak için kontrol
                if (!isBuilderExisting) showAlertDialog()
            }
        }.start()
    }

    private fun showAlertDialog(durum: String = getString(R.string.galibiyet)) {
        //oyunun bittigini gostermek icin alertdialog ile kullanici bilgilendiriliyor
        //ayni anda birden fazla kontrol dogru cikmasi ihtimaline karsi isBuilderExisting ile yalnizca 1 kez olusturulmasi saglaniyor
        isBuilderExisting = true
        val builder = AlertDialog.Builder(this@GameFragment.requireActivity())
        builder.setTitle("Tebrikler!")
        val winner = if (currentPlayer.not()) {
            Player.player1?.name
        } else {
            Player.player2?.name
        }
        builder.setMessage("$winner kazandı.")
        if (durum == getString(R.string.beraberlik)) {
            builder.setTitle("Beraberlik!")
            builder.setMessage("Kazanan çıkmadı.")
        }
        builder.setCancelable(false)
        builder.setNeutralButton("Girişe Git") { _: DialogInterface?, _: Int ->
            navController.navigate(R.id.action_gameFragment_to_homeFragment)
        }
        builder.setNegativeButton("Tekrar Oyna") { _: DialogInterface?, _: Int ->
            //tekrar oynanmasi icin oyuncu adlari ve renkleri tekrar gonderiliyor
            navController.navigate(R.id.action_gameFragment_self)
        }
        builder.show()
    }

    private fun onBackPressed() {
        //kullanici oyun ekranindayken backpress durumunda toplamda 2 saniyeden fazla basarsa geri yonlerdirme yapiliyor
        val toast: Toast = Toast.makeText(
            this@GameFragment.requireActivity(),
            "Giriş sayfasına gitmek için bir daha tıklayın.",
            Toast.LENGTH_SHORT
        )
        if (backPressTime + 2000 > System.currentTimeMillis()) {
            toast.cancel()
            navController.navigate(R.id.action_gameFragment_to_homeFragment)
        } else {
            toast.show()
        }
        backPressTime = System.currentTimeMillis()
    }
}