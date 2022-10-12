package com.example.boyakazan

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.boyakazan.databinding.FragmentHowtoplayBinding

class HowtoplayFragment : Fragment() {
    private lateinit var bindingFragmentHowtoplay: FragmentHowtoplayBinding
    private lateinit var navController: NavController
    private lateinit var resimler: ArrayList<Int>
    private lateinit var aciklamalar: ArrayList<String>
    private var i = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindingFragmentHowtoplay =
            FragmentHowtoplayBinding.inflate(layoutInflater, container, false)
        return bindingFragmentHowtoplay.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tanimlamalar()

        //sonraki resme gecmek icin onClick atandi
        bindingFragmentHowtoplay.ileriButon.setOnClickListener {
            //index listenin boyutunu ulasinca sifirlandi ve homeFragmente gidildi
            if (i == resimler.size - 1) {
                i = -1
                navController.navigate(R.id.action_howtoplayFragment_to_homeFragment)
            } else {
                i++
                setGravity(i)
                bindingFragmentHowtoplay.resimImageView.setImageResource(resimler[i])
                bindingFragmentHowtoplay.aciklamaTextview.text = aciklamalar[i]
            }
        }

        //önceki resme gecmek icin onClick atandi
        bindingFragmentHowtoplay.geriButon.setOnClickListener {
            //index sifira ulasinca listenin boyutuna ayarlandi
            if (i == 0) {
                i = resimler.size
            }
            i--
            setGravity(i)
            bindingFragmentHowtoplay.resimImageView.setImageResource(resimler[i])
            bindingFragmentHowtoplay.aciklamaTextview.text = aciklamalar[i]
        }
    }

    private fun tanimlamalar() {
        navController = findNavController()
        //önceden belirlenmis resim ve yazılar arraylere eklendi
        resimler = java.util.ArrayList()
        resimler.add(R.drawable.telefonresim1)
        resimler.add(R.drawable.telefonresim2)
        resimler.add(R.drawable.telefonresim3)
        resimler.add(R.drawable.telefonresim4)
        resimler.add(R.drawable.telefonresim5)
        resimler.add(R.drawable.telefonresim6)
        resimler.add(R.drawable.telefonresim7)
        resimler.add(R.drawable.telefonresim8)
        resimler.add(R.drawable.telefonresim9)
        //0-255 arasi deger alir
        bindingFragmentHowtoplay.resimImageView.imageAlpha = 150
        aciklamalar = java.util.ArrayList()
        aciklamalar.add(getString(R.string.aciklama1))
        aciklamalar.add(getString(R.string.aciklama2))
        aciklamalar.add(getString(R.string.aciklama3))
        aciklamalar.add(getString(R.string.aciklama4))
        aciklamalar.add(getString(R.string.aciklama5))
        aciklamalar.add(getString(R.string.aciklama6))
        aciklamalar.add(getString(R.string.aciklama7))
        aciklamalar.add(getString(R.string.aciklama8))
        aciklamalar.add(getString(R.string.aciklama9))

        //ilk olarak ilk aciklama ve resim cagirildi
        bindingFragmentHowtoplay.aciklamaTextview.text = aciklamalar[0]
        bindingFragmentHowtoplay.resimImageView.setImageResource(resimler[0])
    }

    //son resimde problem olusmamasi icin son yazinin gravitysi degistirildi
    private fun setGravity(i: Int) {
        if (i == resimler.size - 1) {
            bindingFragmentHowtoplay.aciklamaTextview.gravity = Gravity.NO_GRAVITY
        } else {
            bindingFragmentHowtoplay.aciklamaTextview.gravity = Gravity.CENTER
        }
    }
}