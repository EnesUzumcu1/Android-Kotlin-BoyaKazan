package com.example.boyakazan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.boyakazan.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {
    private lateinit var bindingFragmentSettings: FragmentSettingsBinding
    private lateinit var navController: NavController

    //oyuncularin sececegi renkler, varsayilan degerleriyle atandi
    private var oyuncu1Rengi: String = "black"
    private var oyuncu2Rengi: String = "red"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindingFragmentSettings = FragmentSettingsBinding.inflate(inflater, container, false)
        return bindingFragmentSettings.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tanimlamalar()
        bindingFragmentSettings.kaydetButon.setOnClickListener {
            kaydet()
        }
    }

    private fun tanimlamalar() {
        navController = findNavController()
        //varsayilan olarak oyuncu1 black, oyuncu2 red renkle basliyor
        bindingFragmentSettings.ivSecilenOyuncu1Rengi.setBackgroundResource(R.drawable.custom_button_black)
        bindingFragmentSettings.ivSecilenOyuncu2Rengi.setBackgroundResource(R.drawable.custom_button_red)

        //renk secmesi icin imageviewlere onClick atamasi yapiliyor
        oyuncu1ImageViewOnClickAtama(bindingFragmentSettings.btnOyuncu1Renk1)
        oyuncu1ImageViewOnClickAtama(bindingFragmentSettings.btnOyuncu1Renk2)
        oyuncu1ImageViewOnClickAtama(bindingFragmentSettings.btnOyuncu1Renk3)
        oyuncu1ImageViewOnClickAtama(bindingFragmentSettings.btnOyuncu1Renk4)
        oyuncu1ImageViewOnClickAtama(bindingFragmentSettings.btnOyuncu1Renk5)
        oyuncu1ImageViewOnClickAtama(bindingFragmentSettings.btnOyuncu1Renk6)
        oyuncu1ImageViewOnClickAtama(bindingFragmentSettings.btnOyuncu1Renk7)
        oyuncu1ImageViewOnClickAtama(bindingFragmentSettings.btnOyuncu1Renk8)
        oyuncu1ImageViewOnClickAtama(bindingFragmentSettings.btnOyuncu1Renk9)

        oyuncu2ImageViewOnClickAtama(bindingFragmentSettings.btnOyuncu2Renk1)
        oyuncu2ImageViewOnClickAtama(bindingFragmentSettings.btnOyuncu2Renk2)
        oyuncu2ImageViewOnClickAtama(bindingFragmentSettings.btnOyuncu2Renk3)
        oyuncu2ImageViewOnClickAtama(bindingFragmentSettings.btnOyuncu2Renk4)
        oyuncu2ImageViewOnClickAtama(bindingFragmentSettings.btnOyuncu2Renk5)
        oyuncu2ImageViewOnClickAtama(bindingFragmentSettings.btnOyuncu2Renk6)
        oyuncu2ImageViewOnClickAtama(bindingFragmentSettings.btnOyuncu2Renk7)
        oyuncu2ImageViewOnClickAtama(bindingFragmentSettings.btnOyuncu2Renk8)
        oyuncu2ImageViewOnClickAtama(bindingFragmentSettings.btnOyuncu2Renk9)
    }

    private fun oyuncu1ImageViewOnClickAtama(imageView: ImageView) {
        imageView.setOnClickListener {
            //secilen butonun backgroundu ivSecilenOyuncu1Rengi viewine set ediliyor
            val getdrawable = imageView.background
            bindingFragmentSettings.ivSecilenOyuncu1Rengi.background = getdrawable
            oyuncuRenkleriniAyarla(imageView)
        }
    }

    private fun oyuncu2ImageViewOnClickAtama(imageView: ImageView) {
        imageView.setOnClickListener {
            //secilen butonun backgroundu ivSecilenOyuncu2Rengi viewine set ediliyor
            val getdrawable = imageView.background
            bindingFragmentSettings.ivSecilenOyuncu2Rengi.background = getdrawable
            oyuncuRenkleriniAyarla(imageView)
        }
    }

    //oyuncu adi belirlemesi kontrolu yapiliyor, bos ise varsayilan deger ataniyor
    private fun oyuncu1AdiDogrula(): Boolean {
        val oyuncu1adi: String =
            bindingFragmentSettings.etOyuncu1adi.text.toString().trim()
        return if (oyuncu1adi.isEmpty()) {
            bindingFragmentSettings.etOyuncu1adi.setText("Oyuncu 1")
            false
        } else {
            bindingFragmentSettings.oyuncu1adiEditText.error = null
            true
        }
    }

    private fun oyuncu2AdiDogrula(): Boolean {
        val oyuncu2adi: String =
            bindingFragmentSettings.etOyuncu2adi.text.toString().trim()
        return if (oyuncu2adi.isEmpty()) {
            bindingFragmentSettings.etOyuncu2adi.setText("Oyuncu 2")
            false
        } else {
            bindingFragmentSettings.oyuncu2adiEditText.error = null
            true
        }
    }

    //iki oyuncu adi ayni mi diye kontrol ediliyor
    private fun oyuncuAdiBenzerlikKontrol(): Boolean {
        val oyuncu1adi: String =
            bindingFragmentSettings.etOyuncu1adi.text.toString().trim()
        val oyuncu2adi: String =
            bindingFragmentSettings.etOyuncu2adi.text.toString().trim()
        return if (oyuncu1adi == oyuncu2adi) {
            bindingFragmentSettings.oyuncu1adiEditText.error = "Oyuncu adları aynı olamaz!"
            bindingFragmentSettings.oyuncu2adiEditText.error = "Oyuncu adları aynı olamaz!"
            false
        } else {
            bindingFragmentSettings.oyuncu1adiEditText.error = null
            bindingFragmentSettings.oyuncu2adiEditText.error = null
            true
        }
    }

    //oyuncu secilen renkler kontrol ediliyor
    private fun oyuncuRenkleriKontrol(): Boolean {
        return if (oyuncu1Rengi == oyuncu2Rengi) {
            Toast.makeText(
                this@SettingsFragment.requireActivity(),
                "İki renk aynı olamaz.",
                Toast.LENGTH_SHORT
            ).show()
            false
        } else {
            true
        }
    }

    //secilen imageviewdeki renk degiskene atamasi string yapiliyor
    private fun oyuncuRenkleriniAyarla(imageView: ImageView) {
        when (imageView.id) {
            bindingFragmentSettings.btnOyuncu1Renk1.id -> oyuncu1Rengi = getString(R.string.lime)
            bindingFragmentSettings.btnOyuncu1Renk2.id -> oyuncu1Rengi = getString(R.string.gray)
            bindingFragmentSettings.btnOyuncu1Renk3.id -> oyuncu1Rengi = getString(R.string.black)
            bindingFragmentSettings.btnOyuncu1Renk4.id -> oyuncu1Rengi = getString(R.string.green)
            bindingFragmentSettings.btnOyuncu1Renk5.id -> oyuncu1Rengi = getString(R.string.orange)
            bindingFragmentSettings.btnOyuncu1Renk6.id -> oyuncu1Rengi = getString(R.string.red)
            bindingFragmentSettings.btnOyuncu1Renk7.id -> oyuncu1Rengi = getString(R.string.blue)
            bindingFragmentSettings.btnOyuncu1Renk8.id -> oyuncu1Rengi = getString(R.string.brown)
            bindingFragmentSettings.btnOyuncu1Renk9.id -> oyuncu1Rengi = getString(R.string.yellow)

            bindingFragmentSettings.btnOyuncu2Renk1.id -> oyuncu2Rengi = getString(R.string.lime)
            bindingFragmentSettings.btnOyuncu2Renk2.id -> oyuncu2Rengi = getString(R.string.gray)
            bindingFragmentSettings.btnOyuncu2Renk3.id -> oyuncu2Rengi = getString(R.string.black)
            bindingFragmentSettings.btnOyuncu2Renk4.id -> oyuncu2Rengi = getString(R.string.green)
            bindingFragmentSettings.btnOyuncu2Renk5.id -> oyuncu2Rengi = getString(R.string.orange)
            bindingFragmentSettings.btnOyuncu2Renk6.id -> oyuncu2Rengi = getString(R.string.red)
            bindingFragmentSettings.btnOyuncu2Renk7.id -> oyuncu2Rengi = getString(R.string.blue)
            bindingFragmentSettings.btnOyuncu2Renk8.id -> oyuncu2Rengi = getString(R.string.brown)
            bindingFragmentSettings.btnOyuncu2Renk9.id -> oyuncu2Rengi = getString(R.string.yellow)
        }
    }

    private fun kaydet() {
        //Önceden çağrılmazsa 2.kere cağrılması gerekiyor. Sonuçlar önceden belirlenmesi için önceden çağırıldı.
        oyuncu1AdiDogrula()
        oyuncu2AdiDogrula()
        if (oyuncu1AdiDogrula() && oyuncu2AdiDogrula() && oyuncuRenkleriKontrol() && oyuncuAdiBenzerlikKontrol()) {
            Toast.makeText(
                this@SettingsFragment.requireActivity(),
                "Kaydedildi.",
                Toast.LENGTH_SHORT
            ).show()

            //oyuncu adlari ve renkleri gonderildi
            navController.navigate(R.id.action_settingsFragment_to_gameFragment, Bundle().apply {
                putString("oyuncu1Ad", bindingFragmentSettings.etOyuncu1adi.text.toString().trim())
                putString("oyuncu2Ad", bindingFragmentSettings.etOyuncu2adi.text.toString().trim())
                putString("oyuncu1Rengi", oyuncu1Rengi)
                putString("oyuncu2Rengi", oyuncu2Rengi)
            })
        }
    }

}