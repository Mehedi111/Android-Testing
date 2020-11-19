package com.sarik.androidtesting

/**
 * Created by Mehedi Hasan on 11/19/2020.
 */
object RegistrationUtil {

    private val existingUsers = listOf("mehedi hasan", "sarik", "Arif")
    /**
     * the user will be invalid if
     * the username and password is empty
     * or existing user
     * and the password is not equal with the confirm password
     * and password is less than 4 digit
     */

    fun validateUserInfo(
        username: String,
        password: String,
        confirmedPassword: String
    ): Boolean {
        if(username.isEmpty() || password.isEmpty()) {
            return false
        }
        if(username in existingUsers) {
            return false
        }
        if(password != confirmedPassword) {
            return false
        }
        if(password.count { it.isDigit() } < 4) {
            return false
        }
        return true
    }
}