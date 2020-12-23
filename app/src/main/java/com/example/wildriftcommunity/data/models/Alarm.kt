package com.example.wildriftcommunity.data.models

import com.google.api.Billing

data class Alarm(
    var destinationUid: String? = null,
    var Uid: String? = null,
    var kind: Int? = null,
    var message: String? = null,
    var timestamp: Long? = null
)