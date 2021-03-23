package com.itmo.wineup.features.profile.presentation

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.itmo.wineup.MainActivity
import com.itmo.wineup.R
import com.itmo.wineup.features.profile.model.Profile
import kotlinx.android.synthetic.main.profile_data_change_fragment.*

class ProfileDataEditFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.profile_data_change_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val profileData = arguments?.getSerializable("profile") as Profile
        city_input.adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.cities_list,
            R.layout.support_simple_spinner_dropdown_item
        )
        with(profileData) {
            userName.setText(name)
            userPhone.setText(phone)
            city_input.setSelection(cityId - 1)
        }
        profile_edit_button.setOnClickListener {
            showChangePhoneAlert()
        }
    }

    private fun showChangePhoneAlert() {
        val dialog = AlertDialog.Builder(requireContext())
            .setMessage(requireContext().getString(R.string.linking_phone_number))
            .setCancelable(false)
            .setPositiveButton(requireContext().getString(R.string.global_yes)) { dialogInterface: DialogInterface, i: Int ->
                dialogInterface.cancel()
                (activity as MainActivity).openConfirmCodeFragment(userPhone.text.toString())
            }
            .setNegativeButton(requireContext().getString(R.string.global_no)) { dialogInterface: DialogInterface, i: Int ->
                dialogInterface.cancel()
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