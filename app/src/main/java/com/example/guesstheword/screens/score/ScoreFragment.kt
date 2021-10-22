package com.example.guesstheword.screens.score

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.guesstheword.R
import com.example.guesstheword.database.PlayerDB
import com.example.guesstheword.database.Score
import com.example.guesstheword.databinding.ScoreFragmentBinding


class ScoreFragment : Fragment() {
    lateinit var resultImage: ImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate view and obtain an instance of the binding class.
        val binding: ScoreFragmentBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.score_fragment,
            container,
            false
        )
        val application=requireNotNull(this.activity).application

        val dao = PlayerDB.getInstance(application).playerDBDao
        val repository = ScoreRepository(dao)
        val scoreFragmentArgs by navArgs<ScoreFragmentArgs>()
        val viewModelFactory = ScoreViewModelFactory(scoreFragmentArgs.score, repository,application)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(ScoreViewModel::class.java)
        val adapter= ScoreRecyclerViewAdapter { selectedItem: Score ->
            listItemClicked(selectedItem)
        }

        // Add observer for score
//        viewModel.score.observe(viewLifecycleOwner, Observer { newScore ->
//            binding.scoreText.text = newScore.toString()
//        }) //not needed anymore

        binding.scoreViewModel = viewModel
        binding.lifecycleOwner = this


        binding.scoreRecyclerView.layoutManager = LinearLayoutManager(activity)
        binding.scoreRecyclerView.adapter = adapter

        viewModel._savedScore.observe(viewLifecycleOwner, Observer {
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        })


        // Navigates back to title when button is pressed
        viewModel.eventPlayAgain.observe(viewLifecycleOwner, Observer { playAgain ->
            if (playAgain) {
                findNavController().navigate(ScoreFragmentDirections.actionRestart())
                viewModel.onPlayAgainComplete()
            }
        })
        resultImage=binding.backgroundImage
        viewModel.won.observe(viewLifecycleOwner, Observer { won->
            setImage(won)
        })
        return binding.root
    }
    private fun listItemClicked(score: Score) {
        Toast.makeText(activity, "", Toast.LENGTH_LONG).show()
    }
//    private fun initRecyclerView(application:Application,viewModel: ScoreViewModel) {
//        binding.scoreRecyclerView.layoutManager = LinearLayoutManager(activity)
//        binding.scoreRecyclerView.adapter = adapter
//
//        displayScoresList(viewModel)
//    }

//    private fun displayScoresList(viewModel:ScoreViewModel) {
//        viewModel._savedScore.observe(viewLifecycleOwner, Observer {
//            adapter.setList(it)
//            adapter.notifyDataSetChanged()
//        })
//    }
private fun setImage(b:Boolean) {
    val drawableResource = when (b) {
        true -> R.drawable.you_win
        false -> R.drawable.try_again
    }
    resultImage.setImageResource(drawableResource)
}
}