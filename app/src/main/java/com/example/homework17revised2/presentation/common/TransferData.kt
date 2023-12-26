package com.example.homework17revised2.presentation.common

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TransferData(
    val email : String,
    val password : String
) : Parcelable