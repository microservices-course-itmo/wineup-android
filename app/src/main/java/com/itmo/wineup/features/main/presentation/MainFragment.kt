package com.itmo.wineup.features.main.presentation

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.itmo.wineup.R
import com.itmo.wineup.features.errors.Error404Activity
import com.itmo.wineup.features.errors.Error503Activity
import com.itmo.wineup.features.errors.ErrorPageNotFoundActivity
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() =
            MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    private lateinit var viewModel: MainViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        button1.setOnClickListener {
            startActivity(Intent(context, Error404Activity::class.java))
        }
        button2.setOnClickListener {
            startActivity(Intent(context, Error503Activity::class.java))
        }
        button3.setOnClickListener {
            startActivity(Intent(context, ErrorPageNotFoundActivity::class.java))
        }
    }

}