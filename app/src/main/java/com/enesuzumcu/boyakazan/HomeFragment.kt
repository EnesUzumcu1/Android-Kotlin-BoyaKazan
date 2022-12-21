package com.enesuzumcu.boyakazan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.enesuzumcu.boyakazan.databinding.FragmentHomeBinding
import com.enesuzumcu.boyakazan.model.Player

class HomeFragment : Fragment() {
    private lateinit var bindingFragmentHome: FragmentHomeBinding
    private lateinit var navController: NavController
    private var backPressTime: Long = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindingFragmentHome = FragmentHomeBinding.inflate(inflater, container, false)
        return bindingFragmentHome.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tanimlamalar()
        bindingFragmentHome.btnOyna.setOnClickListener {
            navController.navigate(R.id.action_homeFragment_to_gameFragment)
        }
        bindingFragmentHome.btnNasilOynanir.setOnClickListener {
            navController.navigate(R.id.action_homeFragment_to_howtoplayFragment)
        }
        bindingFragmentHome.btnAyarlar.setOnClickListener {
            navController.navigate(R.id.action_homeFragment_to_settingsFragment)
        }

        //BackPress basildigi zaman yapilacak islemler icin callback eklendi
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            // Handle the back button event
            onBackPressed()
        }
    }

    private fun tanimlamalar() {
        bindingFragmentHome.enGenisLayout.background.alpha = 50
        navController = findNavController()
        if (Player.player1 == null) {
            Player.setPlayer1(
                name = getString(R.string.player1DefaultName),
                color = ContextCompat.getColor(requireContext(), R.color.black)
            )
        }
        if (Player.player2 == null) {
            Player.setPlayer2(
                name = getString(R.string.player2DefaultName),
                color = ContextCompat.getColor(requireContext(), R.color.red)
            )
        }
    }

    private fun onBackPressed() {
        //backpress toplamda 2 saniyeden uzun sure basilirsa uygulama kapanmasi saglandi
        val toast: Toast = Toast.makeText(
            this@HomeFragment.requireActivity(),
            "Uygulamadan çıkmak için bir daha tıklayın.",
            Toast.LENGTH_SHORT
        )
        if (backPressTime + 2000 > System.currentTimeMillis()) {
            toast.cancel()
            requireActivity().finish()
        } else {
            toast.show()
        }
        backPressTime = System.currentTimeMillis()
    }

}