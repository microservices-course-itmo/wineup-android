package com.itmo.wineup.features.profile.presentation

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.gson.JsonNull
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.JsonPrimitive
import com.itmo.wineup.MainActivity
import com.itmo.wineup.R
import com.itmo.wineup.features.profile.model.Profile
import kotlinx.android.synthetic.main.profile_data_change_fragment.*

class ProfileDataEditFragment : Fragment() {

    lateinit var oldPhone: String
    lateinit var oldName: String
    var oldCityId: Int = 1

    private lateinit var viewModel: ProfileDataEditViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.profile_data_change_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ProfileDataEditViewModel::class.java)
        viewModel.userChangeSuccess.observe(viewLifecycleOwner, Observer(this::showSuccess))
        val profileData = arguments?.getSerializable("profile") as Profile
        city_input.adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.cities_list,
            R.layout.support_simple_spinner_dropdown_item
        )
        with(profileData) {
            oldPhone = phone
            oldName = name ?: ""
            oldCityId = cityId ?: 1
            userName.setText(name)
            userPhone.setText(phone)
            if (cityId != null) {
                city_input.setSelection(cityId - 1)
            }
        }
        profile_edit_button.setOnClickListener {
            showChangePhoneAlert()
        }
    }

    private fun showChangePhoneAlert() {
        val dialog = AlertDialog.Builder(requireContext())
            .setMessage(
                requireContext().getString(
                    if (oldPhone != userPhone.text.toString()) R.string.linking_phone_number else R.string.change_info_alert
                )
            )
            .setCancelable(false)
            .setPositiveButton(requireContext().getString(R.string.global_yes)) { dialogInterface: DialogInterface, i: Int ->
                dialogInterface.cancel()
                val newPhoneNumber = userPhone.text.toString()
                if (newPhoneNumber != oldPhone) {
                    (activity as MainActivity).openConfirmCodeFragment(
                        if (userName.text.toString() != oldName) userName.text.toString() else null,
                        userPhone.text.toString(),
                        if (oldCityId != city_input.selectedItemPosition + 1) city_input.selectedItemPosition + 1 else null
                    )
                } else {
                    saveChanges(userName.text.toString(), city_input.selectedItemPosition + 1)
                }

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

    private fun saveChanges(name: String, cityId: Int) {
        val stubUser = JsonObject().apply {
            add("birthday", JsonNull.INSTANCE)
            add("cityId", if (oldCityId == cityId) JsonNull.INSTANCE else JsonPrimitive(cityId))
            add("name", if (oldName == name) JsonNull.INSTANCE else JsonParser.parseString(name))
            add("phoneNumber", JsonNull.INSTANCE)
        }
        viewModel.patchUser(stubUser)
    }

    private fun showSuccess(message: String) {
        if (message == "success")
            Toast.makeText(context, "Изменения успешно сохранены!", Toast.LENGTH_SHORT).show()
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