package com.enesuzumcu.boyakazan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.enesuzumcu.boyakazan.databinding.FragmentSettingsBinding
import com.enesuzumcu.boyakazan.model.Player

class SettingsFragment : Fragment() {
    private lateinit var bindingFragmentSettings: FragmentSettingsBinding
    private lateinit var navController: NavController

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
        bindingFragmentSettings.btnSave.setOnClickListener {
            saveChanges()
        }
    }

    private fun tanimlamalar() {
        navController = findNavController()
        //varsayilan olarak oyuncu1 black, oyuncu2 red renkle basliyor
        updateBackgroundResource(Player.player1,bindingFragmentSettings.ivSecilenOyuncu1Rengi,true)
        updateBackgroundResource(Player.player2,bindingFragmentSettings.ivSecilenOyuncu2Rengi,false)
        //her butona ait renk tag olarak eklendi
        setTagColorButtons()
        //renk secmesi icin imageviewlere onClick atamasi yapiliyor
        setOnClickPlayer1Buttons(bindingFragmentSettings.btnOyuncu1Renk1)
        setOnClickPlayer1Buttons(bindingFragmentSettings.btnOyuncu1Renk2)
        setOnClickPlayer1Buttons(bindingFragmentSettings.btnOyuncu1Renk3)
        setOnClickPlayer1Buttons(bindingFragmentSettings.btnOyuncu1Renk4)
        setOnClickPlayer1Buttons(bindingFragmentSettings.btnOyuncu1Renk5)
        setOnClickPlayer1Buttons(bindingFragmentSettings.btnOyuncu1Renk6)
        setOnClickPlayer1Buttons(bindingFragmentSettings.btnOyuncu1Renk7)
        setOnClickPlayer1Buttons(bindingFragmentSettings.btnOyuncu1Renk8)
        setOnClickPlayer1Buttons(bindingFragmentSettings.btnOyuncu1Renk9)

        setOnClickPlayer2Buttons(bindingFragmentSettings.btnOyuncu2Renk1)
        setOnClickPlayer2Buttons(bindingFragmentSettings.btnOyuncu2Renk2)
        setOnClickPlayer2Buttons(bindingFragmentSettings.btnOyuncu2Renk3)
        setOnClickPlayer2Buttons(bindingFragmentSettings.btnOyuncu2Renk4)
        setOnClickPlayer2Buttons(bindingFragmentSettings.btnOyuncu2Renk5)
        setOnClickPlayer2Buttons(bindingFragmentSettings.btnOyuncu2Renk6)
        setOnClickPlayer2Buttons(bindingFragmentSettings.btnOyuncu2Renk7)
        setOnClickPlayer2Buttons(bindingFragmentSettings.btnOyuncu2Renk8)
        setOnClickPlayer2Buttons(bindingFragmentSettings.btnOyuncu2Renk9)
    }

    private fun setOnClickPlayer1Buttons(imageView: ImageView) {
        imageView.setOnClickListener {
            //secilen butonun backgroundu ivSecilenOyuncu1Rengi viewine set ediliyor
            bindingFragmentSettings.ivSecilenOyuncu1Rengi.background = imageView.background
            Player.player1?.color = (ContextCompat.getColor(
                requireContext(),
                imageView.tag.toString().toInt()
            ))
        }
    }

    private fun setOnClickPlayer2Buttons(imageView: ImageView) {
        imageView.setOnClickListener {
            //secilen butonun backgroundu ivSecilenOyuncu2Rengi viewine set ediliyor
            bindingFragmentSettings.ivSecilenOyuncu2Rengi.background = imageView.background
            Player.player2?.color = (ContextCompat.getColor(
                requireContext(),
                imageView.tag.toString().toInt()
            ))
        }
    }

    private fun setPlayer1Name() {
        val player1Name: String = bindingFragmentSettings.etOyuncu1adi.text.toString().trim()
        if (player1Name.isNotEmpty()) {
            Player.player1?.name  = (player1Name)
        } else {
            Player.player1?.name = getString(R.string.player1DefaultName)
            bindingFragmentSettings.oyuncu1adiEditText.error = null
        }
    }

    private fun setPlayer2Name() {
        val player2Name: String = bindingFragmentSettings.etOyuncu2adi.text.toString().trim()
        if (player2Name.isNotEmpty()) {
            Player.player2?.name = (player2Name)
        } else {
            Player.player2?.name = getString(R.string.player2DefaultName)
            bindingFragmentSettings.oyuncu2adiEditText.error = null
        }
    }

    //iki oyuncu adi ayni mi diye kontrol ediliyor
    private fun checkPlayersNames(): Boolean {
        return if (Player.player1?.name == Player.player2?.name) {
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
    private fun checkPlayersColors(): Boolean {
        return if (Player.player1?.color == Player.player2?.color){
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

    private fun setTagColorButtons(){
        bindingFragmentSettings.btnOyuncu1Renk1.tag = R.color.lime
        bindingFragmentSettings.btnOyuncu1Renk2.tag = R.color.gray
        bindingFragmentSettings.btnOyuncu1Renk3.tag = R.color.black
        bindingFragmentSettings.btnOyuncu1Renk4.tag = R.color.green
        bindingFragmentSettings.btnOyuncu1Renk5.tag = R.color.orange
        bindingFragmentSettings.btnOyuncu1Renk6.tag = R.color.red
        bindingFragmentSettings.btnOyuncu1Renk7.tag = R.color.blue
        bindingFragmentSettings.btnOyuncu1Renk8.tag = R.color.brown
        bindingFragmentSettings.btnOyuncu1Renk9.tag = R.color.yellow

        bindingFragmentSettings.btnOyuncu2Renk1.tag = R.color.lime
        bindingFragmentSettings.btnOyuncu2Renk2.tag = R.color.gray
        bindingFragmentSettings.btnOyuncu2Renk3.tag = R.color.black
        bindingFragmentSettings.btnOyuncu2Renk4.tag = R.color.green
        bindingFragmentSettings.btnOyuncu2Renk5.tag = R.color.orange
        bindingFragmentSettings.btnOyuncu2Renk6.tag = R.color.red
        bindingFragmentSettings.btnOyuncu2Renk7.tag = R.color.blue
        bindingFragmentSettings.btnOyuncu2Renk8.tag = R.color.brown
        bindingFragmentSettings.btnOyuncu2Renk9.tag = R.color.yellow
    }

    private fun saveChanges() {
        setPlayer1Name()
        setPlayer2Name()
        if (checkPlayersColors() && checkPlayersNames()) {
            navController.navigate(R.id.action_settingsFragment_to_gameFragment)
        }
    }

}