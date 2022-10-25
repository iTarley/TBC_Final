package com.example.tbc_final.ui.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.tbc_final.databinding.FragmentStartBinding



class StartFragment : BaseFragment<FragmentStartBinding>(FragmentStartBinding::inflate) {


    override fun start() {
        checkPermission()
        binding.button.setOnClickListener {
//            findNavController().navigate(StartFragmentDirections.actionStartFragmentToHomeFragment())
        }
    }






    private fun checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q &&
            requireActivity().packageManager.hasSystemFeature(PackageManager.FEATURE_SENSOR_STEP_COUNTER) &&
            ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACTIVITY_RECOGNITION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACTIVITY_RECOGNITION), 0)
        }
    }



}

