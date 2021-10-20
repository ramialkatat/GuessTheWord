package com.example.guesstheword.screens.player

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.guesstheword.R
import com.example.guesstheword.database.Player
import com.example.guesstheword.databinding.ListItemBinding


class RecyclerViewAdapter(private val clickListener: (Player) -> Unit) :
    RecyclerView.Adapter<MyViewHolder>() {
    private val playersList = ArrayList<Player>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ListItemBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.list_item, parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return playersList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(playersList[position], clickListener)
    }

    fun setList(players: Collection<Player>) {
        playersList.clear()
        playersList.addAll(players)

    }

}

class MyViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(player: Player, clickListener: (Player) -> Unit) {
        binding.nameTextView.text = "Player Name: " + player.P_Name
        binding.usernameTextView.text = "Username: " + player.P_Username
        binding.listItemLayout.setOnClickListener {
            clickListener(player)
        }
    }
}