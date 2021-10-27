package com.example.guesstheword.screens.registration

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
import com.example.guesstheword.R
import com.example.guesstheword.database.GameDB
import com.example.guesstheword.databinding.FragmentRegistrationBinding
import com.example.guesstheword.screens.player.PlayerRepository


class RegisterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate view and obtain an instance of the binding class.
        val binding: FragmentRegistrationBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_registration,
            container,
            false
        )
        val application = requireNotNull(this.activity).application

        val dao = GameDB.getInstance(application).gameDBDao
        val repository = PlayerRepository(dao)
        val factory = RegisterViewModelFactory(repository)
        val viewModel = ViewModelProvider(this, factory).get(RegisterViewModel::class.java)
        binding.myViewModel = viewModel

        viewModel.message.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
            }
        })
        binding.loginNav.setOnClickListener {
            findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
        }
        viewModel.navigatetoLogin.observe(viewLifecycleOwner, Observer { hasFinished ->
            if (hasFinished == true) {
                findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
            }
        })

        return binding.root
    }
}