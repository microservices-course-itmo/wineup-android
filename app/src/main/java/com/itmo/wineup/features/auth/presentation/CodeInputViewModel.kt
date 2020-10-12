package com.itmo.wineup.features.auth.presentation

import androidx.lifecycle.ViewModel
import com.itmo.wineup.features.auth.domain.GetNewCodeUseCase
import com.itmo.wineup.features.auth.domain.ValidateCodeUseCase

class CodeInputViewModel : ViewModel() {

    private val validateCodeUseCase = ValidateCodeUseCase()
    private val getNewCodeUseCase = GetNewCodeUseCase()

    fun getNewCode() = getNewCodeUseCase.invoke()

    fun validateCode(code: String) = validateCodeUseCase.invoke(code)

}