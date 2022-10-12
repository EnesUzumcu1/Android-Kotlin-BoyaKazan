package com.example.boyakazan

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.boyakazan.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var bindingFragmentHome: FragmentHomeBinding
    private lateinit var navController: NavController
    private var geriTusuBasilisSuresi: Long = 0

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
        val arkaplan: Drawable = bindingFragmentHome.enGenisLayout.background
        arkaplan.alpha = 50
        navController = findNavController()
    }

    private fun onBackPressed() {
        //backpress toplamda 2 saniyeden uzun sure basilirsa uygulama kapanmasi saglandi
        val geriTusuToast: Toast = Toast.makeText(
            this@HomeFragment.requireActivity(),
            "Uygulamadan çıkmak için bir daha tıklayın.",
            Toast.LENGTH_SHORT
        )
        if (geriTusuBasilisSuresi + 2000 > System.currentTimeMillis()) {
            geriTusuToast.cancel()
            requireActivity().finish()
        } else {
            geriTusuToast.show()
        }
        geriTusuBasilisSuresi = System.currentTimeMillis()
    }

}