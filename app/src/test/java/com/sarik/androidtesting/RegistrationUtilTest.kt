package com.sarik.androidtesting

import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test


/**
 * Created by Mehedi Hasan on 11/19/2020.
 */
class RegistrationUtilTest{


    /*
    * TDD 1
    * */
    @Test
    fun `pass Empty UserName and valid password`(){ // should return false
        val result = RegistrationUtil.validateUserInfo(
            "",
            "3456",
            "3456"
        )
        /**
         * IF @result is true than test case will be failed because it's expected true
         * and test case will be passed id result is false
         */
        assertThat(result).isFalse()

    }

    /*
   * TDD 2
   * */
    @Test
    fun `pass valid user name and same password`(){ // should return true
        val result = RegistrationUtil.validateUserInfo(
            "hasbi",
            "3456",
            "3456"
        )
        assertThat(result).isTrue()
    }

    /*
  * TDD 3
  * */
    @Test
    fun `pass exist user name and same password`(){ // should return false
        val result = RegistrationUtil.validateUserInfo(
            "sarik",
            "3456",
            "3456"
        )
        assertThat(result).isFalse()
    }

    /*
  * TDD 4
  * */
    @Test
    fun `pass valid user name and empty password`(){ // should return false
        val result = RegistrationUtil.validateUserInfo(
            "hasan",
            "",
            ""
        )
        assertThat(result).isFalse()
    }

    /*
  * TDD 5
  * */
    @Test
    fun `pass valid user name and different password`(){ // should return false
        val result = RegistrationUtil.validateUserInfo(
            "hasan",
            "3456",
            "5678"
        )
        assertThat(result).isFalse()
    }

    /*
  * TDD 6
  * */
    @Test
    fun `pass valid user name and password less than 4`(){ // should return false
        val result = RegistrationUtil.validateUserInfo(
            "hasan",
            "345",
            "345"
        )
        assertThat(result).isFalse()
    }
}