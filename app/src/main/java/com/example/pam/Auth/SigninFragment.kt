package com.example.pam.Auth

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.pam.R
import kotlinx.android.synthetic.main.signin_fragment.view.*

class SigninFragment : Fragment() {

    private lateinit var sendButton: Button
    private lateinit var infoText: TextView
    private lateinit var name: EditText
    private lateinit var password: EditText
    private lateinit var viewModel: SigninViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.signin_fragment, container, false)
        Log.i("viewCreate", "aaaaa")
        infoText = view.error_message
        name = view.login
        password = view.password
        sendButton = view.signin
        sendButton.setOnClickListener { signin() }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.i("activityCreate", "aaaaa")

        viewModel = ViewModelProvider(this).get(SigninViewModel::class.java)
        viewModel.init(AuthService())
        initView(false)
    }

    private fun initView(wasTried: Boolean){
        if (viewModel.isLoggedIn()) {
            name.visibility = View.INVISIBLE
            password.visibility = View.INVISIBLE
            infoText.setText(R.string.signin_success)
            infoText.setTextColor(resources.getColor(R.color.green))
            sendButton.setText(R.string.logout_button_text)
            sendButton.setOnClickListener { logout() }
        } else if (wasTried){
            infoText.setText(R.string.signin_failure)
            infoText.setTextColor(resources.getColor(R.color.red))
        }
    }

    private fun logout(){
        viewModel.logout()
        name.visibility = View.VISIBLE
        password.visibility = View.VISIBLE
        infoText.setText("")
        infoText.setTextColor(resources.getColor(R.color.green))
        sendButton.setText(R.string.sign_in_button)
        sendButton.setOnClickListener { signin() }
    }

    fun signin(){
        val callback = {
            requireActivity().runOnUiThread {
                initView(true)
            }
        }
        viewModel.signin(name.text.toString(), password.text.toString(), callback)
    }

}