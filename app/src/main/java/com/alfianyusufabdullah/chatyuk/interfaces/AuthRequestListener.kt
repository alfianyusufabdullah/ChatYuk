package com.alfianyusufabdullah.chatyuk.interfaces

/**
 * Created by JonesRandom on 9/15/17.
 *
 * @site www.androidexample.web.id
 * @github @alfianyusufabdullah
 */

interface AuthRequestListener {

    fun onAuthFailed(error: String)

    fun onAuthSuccess()

}