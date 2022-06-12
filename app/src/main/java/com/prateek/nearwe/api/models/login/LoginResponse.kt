package com.prateek.nearwe.api.models.login

import com.google.gson.annotations.SerializedName


data class LoginResponse (

  @SerializedName("IsError"      ) var IsError      : Boolean? = null,
  @SerializedName("ErrorMessage" ) var ErrorMessage : String?  = null,
  @SerializedName("Message"      ) var Message      : String?  = null,
  @SerializedName("Result"       ) var Result       : Result?  = Result()

)