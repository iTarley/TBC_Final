package com.example.tbc_final.presentation.store

import android.text.TextUtils
import com.example.tbc_final.databinding.FragmentOtpBinding
import com.example.tbc_final.presentation.base.BaseFragment
import com.example.tbc_final.utils.extensions.toast
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit


class OtpFragment : BaseFragment<FragmentOtpBinding>(FragmentOtpBinding::inflate) {
    private var verificationId: String? = null
    private lateinit var mAuth: FirebaseAuth
    override fun onBind() {
        mAuth = FirebaseAuth.getInstance()
        setOnClickListener()
    }

    private fun setOnClickListener() {
        binding?.idBtnGetOtp?.setOnClickListener {
            if (TextUtils.isEmpty(binding?.idEdtPhoneNumber?.text.toString())) {
                toast("Please enter a valid phone number.")
            } else {
                var phone = "+995" + binding?.idEdtPhoneNumber?.text.toString()
                sendVerificationCode(phone)
            }
        }

        binding?.idBtnVerify?.setOnClickListener {
            if (TextUtils.isEmpty(binding?.idEdtOtp?.text.toString())) {
                toast("Please enter OTP")
            } else {
                verifyCode(binding?.idEdtOtp?.text.toString())
            }
        }
    }

    private fun signInWithCredential(credential: PhoneAuthCredential) {
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    toast("success")
                } else {
                    toast(task.exception!!.message)
                }
            }
    }

    private fun sendVerificationCode(number: String) {

        val options = PhoneAuthOptions.newBuilder(mAuth)
            .setPhoneNumber(number)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this.requireActivity())
            .setCallbacks(mCallBack)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private val mCallBack: PhoneAuthProvider.OnVerificationStateChangedCallbacks =
        object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onCodeSent(
                s: String,
                forceResendingToken: PhoneAuthProvider.ForceResendingToken
            ) {
                super.onCodeSent(s, forceResendingToken)
                verificationId = s
            }

            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                val code = phoneAuthCredential.smsCode
                if (code != null) {
                    binding?.idEdtOtp!!.setText(code)
                    verifyCode(code)
                }
            }

            override fun onVerificationFailed(e: FirebaseException) {
                toast("Error")
            }
        }

    private fun verifyCode(code: String) {
        val credential = PhoneAuthProvider.getCredential(verificationId!!, code)
        signInWithCredential(credential)
    }

}