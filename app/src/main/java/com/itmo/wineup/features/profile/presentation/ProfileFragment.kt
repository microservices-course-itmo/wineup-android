package com.itmo.wineup.features.profile.presentation

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.itmo.wineup.MainActivity
import com.itmo.wineup.R
import com.itmo.wineup.features.auth.USER_ACCESS_INFO
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
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_profile, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ProfileViewModel::class.java)
        viewModel.phoneLiveData.observe(viewLifecycleOwner, Observer(this::setPhone))
        viewModel.nameLiveData.observe(viewLifecycleOwner, Observer(this::setName))
        viewModel.cityLiveData.observe(viewLifecycleOwner, Observer(this::setCity))
        viewModel.nonAuthUser.observe(viewLifecycleOwner, Observer(this::showNonAuthScreen))
        viewModel.currentUser()
        profile_exit_button.setOnClickListener {
            showLogOutAlert()
        }
        profile_edit_button.setOnClickListener {
            //todo
        }

        enter_button.setOnClickListener {
            val preferences =
                requireActivity().getSharedPreferences(USER_ACCESS_INFO, Context.MODE_PRIVATE)
            preferences.edit().clear().apply()
            val exitIntent =
                Intent(requireContext().applicationContext, AgeAccessActivity::class.java)
            exitIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(exitIntent)
        }

    }

    private fun showNonAuthScreen(show: Boolean) {
        if (show) {
            content_group.visibility = View.INVISIBLE
            non_auth_alert.visibility = View.VISIBLE
        }
    }

    private fun setPhone(phone: String) {
        content_group.visibility = View.VISIBLE
        profile_phone.text = phone
        non_auth_alert.visibility = View.INVISIBLE
    }

    private fun setName(name: String) {
        content_group.visibility = View.VISIBLE
        profile_name.text = name
        non_auth_alert.visibility = View.INVISIBLE
    }

    private fun setCity(cityId: Int) {
        content_group.visibility = View.VISIBLE
        non_auth_alert.visibility = View.INVISIBLE
        profile_geo.text = requireContext().getString(
            when (cityId) {
                1 -> R.string.Moscow
                else -> R.string.Spb
            }
        )
    }

    private fun logOut() {
        val preferences =
            requireActivity().getSharedPreferences(USER_ACCESS_INFO, Context.MODE_PRIVATE)
        preferences.edit().clear().apply()
        val exitIntent = Intent(requireContext().applicationContext, AgeAccessActivity::class.java)
        exitIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(exitIntent)
    }

    private fun showLogOutAlert() {
        val dialog = AlertDialog.Builder(requireContext())
            .setMessage(requireContext().getString(R.string.log_out_alert))
            .setCancelable(false)
            .setPositiveButton(requireContext().getString(R.string.global_yes)) { dialogInterface: DialogInterface, i: Int -> dialogInterface.cancel()
                    logOut()
            }
            .setNegativeButton(requireContext().getString(R.string.global_no)) { dialogInterface: DialogInterface, i: Int -> dialogInterface.cancel()
            }
            .show()
        with(dialog.getButton(AlertDialog.BUTTON_POSITIVE)) {
            setTextColor(context.getColor(R.color.red))
            setTypeface(android.graphics.Typeface.DEFAULT, android.graphics.Typeface.BOLD)
        }
        with(dialog.getButton(AlertDialog.BUTTON_NEGATIVE)) {
            setTextColor(context.getColor(R.color.red))
            setTypeface(android.graphics.Typeface.DEFAULT, android.graphics.Typeface.BOLD)
        }
    }

    /*
    //todo: to edit info screen
    private fun showChangePhoneAlert() {
        val dialog = AlertDialog.Builder(requireContext())
            .setMessage(requireContext().getString(R.string.linking_phone_number))
            .setCancelable(false)
            .setPositiveButton(requireContext().getString(R.string.global_yes)) { dialogInterface: DialogInterface, i: Int -> dialogInterface.cancel()
                (activity as MainActivity).openConfirmCodefragment(profile_phone.text.toString())
                phoneChangeAvailability()
            }
            .setNegativeButton(requireContext().getString(R.string.global_no)) { dialogInterface: DialogInterface, i: Int -> dialogInterface.cancel()
            }
            .show()
        with(dialog.getButton(AlertDialog.BUTTON_POSITIVE)) {
            setTextColor(context.getColor(R.color.red))
            setTypeface(android.graphics.Typeface.DEFAULT, android.graphics.Typeface.BOLD)
        }
        with(dialog.getButton(AlertDialog.BUTTON_NEGATIVE)) {
            setTextColor(context.getColor(R.color.red))
            setTypeface(android.graphics.Typeface.DEFAULT, android.graphics.Typeface.BOLD)
        }
    }*/
}

/* код кастомных алертов Славы, пока пусть закомментирован пусть будет
private fun showChangePhoneAlert() {
       val mDialogView = LayoutInflater.from(this).inflate((R.layout.linking_phone_number, null));

       val mBuilder = AlertDialog.Builder(this)
           .setView(mDialogView)

       val mAlertDialog =  mBuilder.show()

       mAlertDialog.buttonYes.setOnClickListener {
           showAccessChangePhoneAlert()
           mAlertDialog.dismiss()
       }

       mDialogView.buttonNo.setOnClickListener {
           mAlertDialog.dismiss()
       }

   }

   private fun showAccessChangePhoneAlert() {
       val mDialogView = LayoutInflater.from(this).inflate((R.layout.linking_phone_success, null));

       val mBuilder = AlertDialog.Builder(this)
           .setView(mDialogView)

       val mAlertDialog = mBuilder.show()
       mAlertDialog.dismiss()
   }*/