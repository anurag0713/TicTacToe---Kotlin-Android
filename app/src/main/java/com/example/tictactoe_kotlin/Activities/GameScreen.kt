package com.example.tictactoe_kotlin.Activities

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import com.example.tictactoe_kotlin.Adapters.RecyclerAdapter
import com.example.tictactoe_kotlin.Models.Model
import com.example.tictactoe_kotlin.R
import com.example.tictactoe_kotlin.databinding.ActivityGameScreenBinding

class GameScreen : AppCompatActivity() {

    private lateinit var binding: ActivityGameScreenBinding
    private lateinit var boardAdapter: RecyclerAdapter
    private val boardData: ArrayList<Model> = ArrayList()
    private var circleScore: Int = 0
    private var crossScore: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.boardRv.layoutManager = GridLayoutManager(this@GameScreen, 3)
        boardAdapter = RecyclerAdapter(this@GameScreen, emptyBoardData(), this)
        binding.boardRv.adapter = boardAdapter
        binding.boardRv.hasFixedSize()

        binding.newGameBtn.setOnClickListener {

            // new game
            if (binding.winningCard.isVisible) {
                val slideDown = AnimationUtils.loadAnimation(this@GameScreen, R.anim.slide_down)
                binding.winningCard.animation = slideDown
                binding.winningCard.visibility = View.INVISIBLE
            }

            val slideFromLeft = AnimationUtils.loadAnimation(this@GameScreen, R.anim.slide_to_left)
            val slideToRight = AnimationUtils.loadAnimation(this@GameScreen, R.anim.slide_right)

            binding.boardRv.animation = slideToRight

            slideToRight.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {}
                override fun onAnimationEnd(animation: Animation) {
                    binding.boardRv.startAnimation(slideFromLeft)
                }

                override fun onAnimationRepeat(animation: Animation) {}
            })
            boardAdapter.resetGame()
        }

        binding.resetScoreBtn.setOnClickListener{
            circleScore = 0
            crossScore = 0
            binding.circleScoreText.text = ": 0"
            binding.crossScoreText.text = ": 0"
        }
    }

    private fun emptyBoardData(): ArrayList<Model>{
        for (i in 0 until 9){
            boardData.add(Model())
        }

        return boardData
    }

    fun animAndScore(winner: String) {

        val slideUp = AnimationUtils.loadAnimation(this@GameScreen, R.anim.slide_up)
        binding.scoreLl.animation = slideUp
        binding.scoreLl.visibility = View.VISIBLE

        when (winner){
            "circle" -> {
                binding.winningImage.setImageResource(R.drawable.circle)
                binding.winningImage.visibility = View.VISIBLE
                binding.winningText.text = "Wins!!!"
                circleScore++
                val winScore = ": $circleScore"
                binding.circleScoreText.text = winScore
            }

            "cross" -> {
                binding.winningImage.setImageResource(R.drawable.cross)
                binding.winningImage.visibility = View.VISIBLE
                binding.winningText.text = "Wins!!!"
                crossScore++
                val winScore = ": $crossScore"
                binding.crossScoreText.text = winScore
            }

            "draw" -> {
                binding.winningImage.visibility = View.GONE
                binding.winningText.text = "It's a Draw!!!"
            }
        }

        val slide = AnimationUtils.loadAnimation(this@GameScreen, R.anim.slide_up)
        binding.winningCard.animation = slide
        binding.winningCard.visibility = View.VISIBLE
    }
}