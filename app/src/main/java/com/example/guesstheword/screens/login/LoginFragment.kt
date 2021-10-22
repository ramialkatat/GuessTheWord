package com.example.guesstheword.screens.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.guesstheword.MainActivity
import com.example.guesstheword.R
import com.example.guesstheword.database.PlayerDB
import com.example.guesstheword.databinding.FragmentLoginBinding
import com.example.guesstheword.screens.player.PlayerActivity
import com.example.guesstheword.screens.player.PlayerRepository

class LoginFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate view and obtain an instance of the binding class.
        val binding: FragmentLoginBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_login,
            container,
            false
        )

        val application = requireNotNull(this.activity).application

        val dao = PlayerDB.getInstance(application).playerDBDao
        val repository = PlayerRepository(dao)
        val factory = LoginViewModelFactory(repository)
        val viewModel = ViewModelProvider(this, factory).get(LoginViewModel::class.java)
        binding.myViewModel = viewModel

        viewModel.message.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
            }
        })
        binding.registerNav.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        }
        viewModel.navigatetoGame.observe(viewLifecycleOwner, Observer { hasFinished->
            if (hasFinished == true){
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToMainActivity())
            }
        })
        return binding.root
    }
}