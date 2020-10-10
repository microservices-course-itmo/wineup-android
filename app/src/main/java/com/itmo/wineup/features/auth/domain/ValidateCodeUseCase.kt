package com.itmo.wineup.features.auth.domain

class ValidateCodeUseCase {

    operator fun invoke(code: String): Boolean {
        //todo: validate code
        return code == "000000"
    }
}