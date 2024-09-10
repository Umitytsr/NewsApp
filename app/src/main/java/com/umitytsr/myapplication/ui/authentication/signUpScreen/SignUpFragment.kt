package com.umitytsr.myapplication.ui.authentication.signUpScreen

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.umitytsr.myapplication.databinding.FragmentSignUpBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignUpBinding
    private val viewModel: SignUpViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val spaceRemoverTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }

            override fun afterTextChanged(s: Editable?) {
                s?.let {
                    val result = it.toString().replace(" ", "")
                    if (result != it.toString()) {
                        (it as Editable).replace(0, it.length, result)
                    }
                }
            }
        }

        with(binding){
            eMailTextField.editText?.addTextChangedListener(spaceRemoverTextWatcher)
            passwordTextField.editText?.addTextChangedListener(spaceRemoverTextWatcher)
            confirmPasswordTextField.editText?.addTextChangedListener(spaceRemoverTextWatcher)

            binding.signInTextButton.setOnClickListener {
                findNavController().navigate(
                    SignUpFragmentDirections.actionSignUpFragmentToSignInFragment()
                )
            }
            continueButton.setOnClickListener {
                val email = eMailTextField.editText?.text.toString()
                val password = passwordTextField.editText?.text.toString()
                val confirmPassword = confirmPasswordTextField.editText?.text.toString()
                viewModel.signUp(email, password, confirmPassword)
            }
        }

    }
}