package com.example.tictactoe_kotlin.Adapters

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.tictactoe_kotlin.Activities.GameScreen
import com.example.tictactoe_kotlin.Models.Model
import com.example.tictactoe_kotlin.R

class RecyclerAdapter(private val context: Activity, private var rvData: ArrayList<Model>, private val gameScreen: GameScreen):
    RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>() {

    private var totalMoves: Int = 0;
    private var isGameOver: Boolean = false
    private var currentPlayer: Boolean = false   // False -> circle | True -> cross

    private var circle = ArrayList<Int>()
    private var cross = ArrayList<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.board_rv_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.MyViewHolder, position: Int) {
        val item: Model = rvData[position]

        if (item.player == "null"){
            holder.playerIcon.setImageResource(R.drawable.transparent_image)
        }

        holder.itemView.setOnClickListener{
            if (!item.isClicked && !isGameOver) {
                totalMoves++

                if (!currentPlayer) {
                    // false -> circle
                    holder.playerIcon.setImageResource(R.drawable.circle)
                    item.player = "circle"
                    circle.add(holder.adapterPosition)
                } else {
                    // true -> cross
                    holder.playerIcon.setImageResource(R.drawable.cross)
                    item.player = "cross"
                    cross.add(holder.adapterPosition)
                }
                item.isClicked = true
                currentPlayer = !currentPlayer
            } else if (isGameOver) {
                Toast.makeText(context, "Please start a new game!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            checkWinner(item.player)
        }

    }

    override fun getItemCount(): Int {
        return rvData.size
    }

    private fun checkWinner(currentPlayer: String) {
        val currentPlayerMoves: List<Int> = if (currentPlayer == "circle") circle else cross

        // winning combs
        val winningCombinations = listOf<List<Int>>(
            mutableListOf(0, 1, 2),
            mutableListOf(3, 4, 5),
            mutableListOf(6, 7, 8),
            mutableListOf(0, 3, 6),
            mutableListOf(1, 4, 7),
            mutableListOf(2, 5, 8),
            mutableListOf(0, 4, 8),
            mutableListOf(2, 4, 6)
        )

        // Check if the current player has a winning combination
        for (combination in winningCombinations) {
            if (currentPlayerMoves.containsAll(combination)) {
                isGameOver = true
                gameScreen.animAndScore(currentPlayer)
                return
            }
        }

        // Check for a draw
        if (totalMoves == itemCount) {
            isGameOver = true
            gameScreen.animAndScore("draw")
            resetGame()
        }
    }

    fun resetGame() {
        for (item in rvData) {
            item.isClicked = false
            item.player = "null"
        }
        circle.clear()
        cross.clear()
        currentPlayer = false
        totalMoves = 0
        isGameOver = false
        notifyDataSetChanged()
    }

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val playerIcon: ImageView = itemView.findViewById(R.id.item_image)
    }

}