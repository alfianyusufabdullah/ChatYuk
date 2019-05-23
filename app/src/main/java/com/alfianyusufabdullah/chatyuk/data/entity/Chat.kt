package com.alfianyusufabdullah.chatyuk.data.entity

/**
 * Created by jonesrandom on 12/26/17.
 *
 * @site www.androidexample.web.id
 * @github @alfianyusufabdullah
 */

data class Chat(var user: String? = null,
                var messageId: String? = null,
                var message: String? = null,
                var time: String? = null,
                var isSameUser: Boolean? = null,
                var isDeleted: Boolean? = false)

