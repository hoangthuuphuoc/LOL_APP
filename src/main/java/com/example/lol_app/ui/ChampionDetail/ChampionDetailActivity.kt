package com.example.lol_app.ui.detail

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.lol_app.R
import com.example.lol_app.data.adapter.SkillAdapter
import com.example.lol_app.data.model.ChampionDetailItem
import com.example.lol_app.data.model.SkillItem
import com.example.lol_app.databinding.ActivityDetailBinding
import kotlinx.coroutines.launch

class ChampionDetailActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }

    private val viewModel: ChampionDetailViewModel by viewModels()

    private var hasAnimated = false

    private val skillAdapter = SkillAdapter { skill ->
        viewModel.onEvent(
            ChampionDetailUiEvent.ClickSkill(skill)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        prepareAnimation()
        initRecyclerView()
        initListener()
        observeData()
        getChampionDetail()
    }

    private fun getChampionDetail() {
        val championId = intent.getStringExtra(
            KEY_CHAMPION_ID
        )

        if (championId.isNullOrEmpty()) {
            finish()
            return
        }

        viewModel.onEvent(
            ChampionDetailUiEvent.GetChampionDetail(
                championId
            )
        )
    }

    private fun initRecyclerView() {
        binding.rcvSkills.apply {
            adapter = skillAdapter

            layoutManager = LinearLayoutManager(
                this@ChampionDetailActivity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        }
    }

    private fun initListener() {
        binding.ivBack.setOnClickListener {
            viewModel.onEvent(
                ChampionDetailUiEvent.ClickBack
            )
        }
    }

    private fun observeData() {
        lifecycleScope.launch {
            repeatOnLifecycle(
                Lifecycle.State.STARTED
            ) {
                launch {
                    viewModel.uiState.collect { state ->
                        handleUiState(state)
                    }
                }

                launch {
                    viewModel.uiEffect.collect { effect ->
                        handleUiEffect(effect)
                    }
                }
            }
        }
    }

    private fun handleUiState(
        state: ChampionDetailUiState
    ) {
        state.championDetail?.let { champion ->
            showChampionDetail(champion)
        }

        state.selectedSkill?.let { skill ->
            showSkill(skill)
        }
    }

    private fun handleUiEffect(
        effect: ChampionDetailUiEffect
    ) {
        when (effect) {
            ChampionDetailUiEffect.BackScreen -> {
                finish()
            }

            is ChampionDetailUiEffect.ShowToast -> {
                Toast.makeText(
                    this,
                    effect.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun showChampionDetail(
        champion: ChampionDetailItem
    ) {
        binding.tvChampionName.text = champion.name
        binding.tvChampionTitle.text = champion.title
        binding.tvBlurb.text = champion.blurb
        binding.tvRole.text = champion.role
        binding.tvDifficulty.text = champion.difficulty

        binding.ivRole.setImageResource(
            getRoleIcon(champion.role)
        )

        Glide.with(this)
            .load(champion.splashUrl)
            .into(binding.ivSplash)

        skillAdapter.updateData(champion.skills)

        animateDetailScreen()
    }

    private fun showSkill(skill: SkillItem) {
        binding.tvSkillLabel.text = skill.key
        binding.tvSkillName.text = skill.name.uppercase()
        binding.tvSkillDesc.text = skill.description

        binding.tvSkillName.alpha = 0f
        binding.tvSkillDesc.alpha = 0f

        binding.tvSkillName.animate()
            .alpha(1f)
            .setDuration(250)
            .start()

        binding.tvSkillDesc.animate()
            .alpha(1f)
            .setDuration(250)
            .start()
    }

    private fun getRoleIcon(role: String): Int {
        return when (role) {
            "Atirador" -> R.drawable.ic_bot
            "Lutador" -> R.drawable.ic_top
            "Tanque" -> R.drawable.ic_support
            "Mago" -> R.drawable.ic_mid
            "Assassino" -> R.drawable.ic_jung
            "Suporte" -> R.drawable.ic_support
            else -> R.drawable.img_role
        }
    }

    companion object {
        const val KEY_CHAMPION_ID = "CHAMPION_ID"
    }

    private fun prepareAnimation() {
        binding.ivSplash.alpha = 0f
        binding.tvChampionName.alpha = 0f
        binding.tvChampionTitle.alpha = 0f
        binding.tvBlurb.alpha = 0f
        binding.rcvSkills.alpha = 0f
        binding.tvSkillLabel.alpha = 0f
        binding.tvSkillName.alpha = 0f
        binding.tvSkillDesc.alpha = 0f
        binding.layoutInfo.alpha = 0f
        binding.ivFavorite.alpha=0f
        binding.viewLine.alpha=0f
        binding.tvSkillsTitle.alpha=0f
        binding.btnStart.alpha=0f
    }

    private fun animateDetailScreen() {
        if (hasAnimated) {
            return
        }

        hasAnimated = true

        binding.ivSplash.scaleX = 1.08f
        binding.ivSplash.scaleY = 1.08f

        binding.tvChampionName.translationY = 30f
        binding.tvChampionTitle.translationY = 30f
        binding.tvBlurb.translationY = 30f
        binding.tvSkillsTitle.translationY = 30f
        binding.rcvSkills.translationY = 30f
        binding.tvSkillLabel.translationY = 20f
        binding.tvSkillName.translationY = 20f
        binding.tvSkillDesc.translationY = 20f
        binding.layoutInfo.translationY = 30f
        binding.btnStart.translationY = 30f

        binding.ivFavorite.scaleX = 0.7f
        binding.ivFavorite.scaleY = 0.7f

        binding.viewLine.scaleX = 0f
        binding.viewLine.pivotX = 0f

        binding.ivSplash.animate()
            .alpha(1f)
            .scaleX(1f)
            .scaleY(1f)
            .setDuration(700)
            .start()

        binding.ivFavorite.animate()
            .alpha(1f)
            .scaleX(1f)
            .scaleY(1f)
            .setStartDelay(180)
            .setDuration(350)
            .start()

        binding.tvChampionName.animate()
            .alpha(1f)
            .translationY(0f)
            .setStartDelay(150)
            .setDuration(450)
            .start()

        binding.tvChampionTitle.animate()
            .alpha(1f)
            .translationY(0f)
            .setStartDelay(220)
            .setDuration(450)
            .start()

        binding.tvBlurb.animate()
            .alpha(1f)
            .translationY(0f)
            .setStartDelay(300)
            .setDuration(450)
            .start()

        binding.tvSkillsTitle.animate()
            .alpha(1f)
            .translationY(0f)
            .setStartDelay(360)
            .setDuration(400)
            .start()

        binding.rcvSkills.animate()
            .alpha(1f)
            .translationY(0f)
            .setStartDelay(420)
            .setDuration(450)
            .start()

        binding.viewLine.animate()
            .alpha(1f)
            .scaleX(1f)
            .setStartDelay(500)
            .setDuration(400)
            .start()

        binding.tvSkillLabel.animate()
            .alpha(1f)
            .translationY(0f)
            .setStartDelay(560)
            .setDuration(350)
            .start()

        binding.tvSkillName.animate()
            .alpha(1f)
            .translationY(0f)
            .setStartDelay(610)
            .setDuration(350)
            .start()

        binding.tvSkillDesc.animate()
            .alpha(1f)
            .translationY(0f)
            .setStartDelay(660)
            .setDuration(350)
            .start()

        binding.layoutInfo.animate()
            .alpha(1f)
            .translationY(0f)
            .setStartDelay(730)
            .setDuration(450)
            .start()

        binding.btnStart.animate()
            .alpha(1f)
            .translationY(0f)
            .setStartDelay(800)
            .setDuration(450)
            .start()
    }
}