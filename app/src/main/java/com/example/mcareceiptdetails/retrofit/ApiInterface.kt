package com.example.mcareceiptdetails.retrofit

import com.example.mcareceiptdetails.model.ReceiptDetails
import retrofit2.Call
import retrofit2.http.GET


interface ApiInterface {

    @GET("qr-scanner-codes/alpha-qr-gFpwhsQ8fkY1")
    fun getReceiptDetails(): Call<List<ReceiptDetails>>?

}