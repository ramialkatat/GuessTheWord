package com.example.guesstheword.screens.player

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.guesstheword.MainActivity
import com.example.guesstheword.R
import com.example.guesstheword.database.PlayerDB
import com.example.guesstheword.databinding.PlayerActivityBinding


class PlayerActivity : AppCompatActivity() {
    private lateinit var binding: PlayerActivityBinding
    private lateinit var playerViewModel: PlayerViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.player_activity)
        val dao = PlayerDB.getInstance(application).playerDBDao
        val repository = PlayerRepository(dao)
        val factory = PlayerViewModelFactory(repository)
        playerViewModel = ViewModelProvider(this, factory).get(PlayerViewModel::class.java)
        binding.myViewModel = playerViewModel
        binding.lifecycleOwner = this
        binding.playButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java).apply {
            }
            startActivity(intent)
        }
        playerViewModel.message.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        })
    }
}
