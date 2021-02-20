package com.itmo.wineup.features.profile.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.itmo.wineup.R
import com.itmo.wineup.features.auth.USER_ACCESS_INFO
import com.itmo.wineup.features.auth.USER_CURRENT_ID
import com.itmo.wineup.features.auth.presentation.AgeAccessActivity
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment() {

    companion object {
        fun newInstance() =
            ProfileFragment()
    }

    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View = inflater.inflate(R.layout.fragment_profile, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ProfileViewModel::class.java)
        viewModel.phoneLiveData.observe(viewLifecycleOwner, Observer(profile_phone::setText))
        viewModel.nameLiveData.observe(viewLifecycleOwner, Observer(profile_name::setText))
        viewModel.currentUser()
        profile_exit_button.setOnClickListener {
            val preferences = requireActivity().getSharedPreferences(USER_ACCESS_INFO, Context.MODE_PRIVATE)
            preferences.edit().clear().apply()
            val exitIntent = Intent(requireContext().applicationContext, AgeAccessActivity::class.java)
            exitIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(exitIntent)
        }
    }

}