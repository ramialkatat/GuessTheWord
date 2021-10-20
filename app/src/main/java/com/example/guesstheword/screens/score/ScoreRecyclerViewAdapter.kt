package com.example.guesstheword.screens.score

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.guesstheword.R
import com.example.guesstheword.database.Score
import com.example.guesstheword.databinding.ScoresListBinding


class ScoreRecyclerViewAdapter(private val clickListener: (Score) -> Unit) :
    RecyclerView.Adapter<MyViewHolder>() {
    private val scoresList = ArrayList<Score>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ScoresListBinding = DataBindingUtil.inflate(layoutInflater, R.layout.scores_list, parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return scoresList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(scoresList[position], clickListener)
    }

    fun setList(scores: Collection<Score>) {
        scoresList.clear()
        scoresList.addAll(scores)
    }
}

class MyViewHolder(val binding: ScoresListBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(score: Score, clickListener: (Score) -> Unit) {
        binding.scoreTextView.text = "Player score: " + score.score
        binding.listItem.setOnClickListener {
            clickListener(score)
        }
    }
}