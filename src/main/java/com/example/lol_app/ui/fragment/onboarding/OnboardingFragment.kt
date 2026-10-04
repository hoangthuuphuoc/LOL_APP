package com.example.lol_app.ui.fragment.onboarding

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.lol_app.R
import com.example.lol_app.ui.onboarding.OnboardingActivity

class OnboardingFragment : Fragment(R.layout.fragment_onboarding) {

    companion object {
        private const val KEY_PAGE = "KEY_PAGE"

        fun newInstance(page: Int): OnboardingFragment {
            return OnboardingFragment().apply {
                arguments = Bundle().apply {
                    putInt(KEY_PAGE, page)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupOnboarding(view)
    }

    private fun setupOnboarding(view: View) {
        val page = arguments?.getInt(KEY_PAGE) ?: 0

        val imgBg = view.findViewById<ImageView>(R.id.iv_img_bg)
        val tvTitle = view.findViewById<TextView>(R.id.tv_title)
        val tvDesc = view.findViewById<TextView>(R.id.tv_desc)
        val btnNext = view.findViewById<ImageView>(R.id.iv_next)
        val btnStart = view.findViewById<TextView>(R.id.btn_start)
        val image = view.findViewById<ImageView>(R.id.iv_game)
        val play = view.findViewById<ImageView>(R.id.iv_play)

        when (page) {
            0 -> {
                imgBg.setImageResource(R.drawable.img_bg_fragment1)

                tvTitle.text = "Escolha os melhores\ncampeões"

                tvDesc.text = "Os melhores campeões, builds,\nmatch-ups e tudo o que você precisa\npara melhorar no jogo."

                btnNext.visibility = View.VISIBLE
                btnStart.visibility = View.GONE
                image.visibility = View.VISIBLE
                play.visibility = View.VISIBLE
            }

            1 -> {
                imgBg.setImageResource(R.drawable.img_bg_fragment2)

                tvTitle.text = "Suba de elo\nrápido"

                tvDesc.text = "Aqui você não vai aprender apenas sobre\no jogo, mas também irá aprender\ntudo sobre os campeões."

                btnNext.visibility = View.VISIBLE
                btnStart.visibility = View.GONE
                image.visibility = View.GONE
                play.visibility = View.GONE
            }

            2 -> {
                imgBg.setImageResource(R.drawable.img_bg_fragment3)

                tvTitle.text = "Divirta-se"

                tvDesc.text = "Seja bem-vindo ao app que vai\nte levar para o próximo nível\nno League."

                btnNext.visibility = View.GONE
                btnStart.visibility = View.VISIBLE
                image.visibility = View.GONE
                play.visibility = View.GONE
            }
        }
        animateText(tvTitle,tvDesc)

        btnNext.setOnClickListener {
            (requireActivity() as OnboardingActivity).nextPage()
        }

        btnStart.setOnClickListener {
            (requireActivity() as OnboardingActivity).nextPage()
        }
    }
    private fun animateText(tvTitle: TextView, tvDesc: TextView) {
        tvTitle.alpha = 0f
        tvTitle.translationY = 30f

        tvDesc.alpha = 0f
        tvDesc.translationY = 30f

        tvTitle.animate()
            .alpha(1f)
            .translationY(0f)
            .setDuration(500)
            .start()

        tvDesc.animate()
            .alpha(1f)
            .translationY(0f)
            .setStartDelay(150)
            .setDuration(500)
            .start()
    }

}