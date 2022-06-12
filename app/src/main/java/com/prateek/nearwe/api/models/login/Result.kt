package com.prateek.nearwe.api.models.login

import com.google.gson.annotations.SerializedName
import com.prateek.nearwe.api.models.User.UserModel


data class Result (

  @SerializedName("Status"  ) var Status  : Int?    = null,
  @SerializedName("message" ) var message : String? = null,
  @SerializedName("User"    ) var User    : UserModel   = UserModel()

)