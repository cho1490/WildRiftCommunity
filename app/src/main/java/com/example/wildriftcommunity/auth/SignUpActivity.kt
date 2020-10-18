package com.example.wildriftcommunity.auth

import android.app.Application
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.wildriftcommunity.ProgressListener
import com.example.wildriftcommunity.R
import com.example.wildriftcommunity.databinding.ActivityLoginBinding
import com.example.wildriftcommunity.databinding.ActivitySignUpBinding
import com.example.wildriftcommunity.main.MainActivity
import kotlinx.android.synthetic.main.activity_login.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class SignUpActivity : AppCompatActivity(), ProgressListener, KodeinAware {

    override val kodein by kodein()
    private val factory: AuthViewModelFactory by instance()
    private lateinit var viewModel : AuthViewModel
    private lateinit var binding : ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this, factory).get(AuthViewModel::class.java)
        viewModel.progressListener = this

        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
        binding.authViewModel = viewModel
        binding.lifecycleOwner = this

        binding.passwordCheck.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(binding.password.text.toString() == binding.passwordCheck.text.toString()){
                    binding.passwordCheckText.text = "비밀번호 일치!"
                    binding.passwordCheckText.setTextColor(Color.parseColor("#AAAAAA"))
                }else{
                    binding.passwordCheckText.text = "비밀번호 불일치!"
                    binding.passwordCheckText.setTextColor(Color.parseColor("#DC3B3B"))
                }
            }
        })

        viewModel.startSignUp.observe(this, Observer {
            if (it == true) {
                viewModel.setRegisterValue(
                    binding.email.text.toString(),
                    binding.password.text.toString()

                )
            }
        })

        viewModel.startMain.observe(this, Observer {
            if (it == true) {
                startActivity(Intent(this, MainActivity::class.java))
            }
        })

    }

    override fun onStarted() {
        progressbar.visibility = View.VISIBLE
    }

    override fun onSuccess(message: String) {
        progressbar.visibility = View.GONE
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onFailure(message: String) {
        progressbar.visibility = View.GONE
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}