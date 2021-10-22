package com.example.guesstheword.screens.player


import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.guesstheword.R
import com.example.guesstheword.database.Player
import com.example.guesstheword.database.PlayerDB
import com.example.guesstheword.databinding.PlayerActivityBinding

class PlayerActivity : AppCompatActivity() {
    private lateinit var binding: PlayerActivityBinding
    private lateinit var playerViewModel: PlayerViewModel
    private lateinit var adapter: RecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            setContentView(R.layout.player_activity)
    }

//    private fun listItemClicked(player: Player){
//        playerViewModel.initUpdateAndDelete(player)
//    }
//
//    private fun initRecyclerView(){
//        adapter = RecyclerViewAdapter({selectedItem: Player ->listItemClicked(selectedItem)})
//        binding.playerRecyclerView.layoutManager = LinearLayoutManager(this)
//        binding.playerRecyclerView.adapter = adapter
//        displayPlayersList()
//    }
}
