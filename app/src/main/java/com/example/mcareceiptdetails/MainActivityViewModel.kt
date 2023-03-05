package com.example.mcareceiptdetails

import android.util.Log
import android.widget.TextView
import androidx.lifecycle.ViewModel
import com.example.mcareceiptdetails.model.ReceiptDetails
import com.example.mcareceiptdetails.retrofit.ApiInterface
import com.example.mcareceiptdetails.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivityViewModel : ViewModel() {

    fun fetchData(textView: TextView) {
        var receiptDetailsList: ArrayList<ReceiptDetails>
        val apiInterface: ApiInterface? =
            RetrofitClient().getClient()?.create(ApiInterface::class.java)
        val call: Call<List<ReceiptDetails>>? = apiInterface?.getReceiptDetails()
        call?.enqueue(object : Callback<List<ReceiptDetails>> {
            override fun onResponse(
                call: Call<List<ReceiptDetails>>,
                response: Response<List<ReceiptDetails>>
            ) {
                if (response.body() != null) {
                    receiptDetailsList = response.body() as java.util.ArrayList<ReceiptDetails>
                    Log.d("TAG", "Response = $receiptDetailsList")
                    printReceipt(receiptDetailsList)
                    textView.text = receiptDetailsText(receiptDetailsList)
                }
            }

            override fun onFailure(
                call: Call<List<ReceiptDetails>>,
                t: Throwable
            ) {
                Log.d("TAG", "Response = $t")
            }
        })
    }

    fun printReceipt(arrayList: ArrayList<ReceiptDetails>) {
        val sortedArray = arrayList.sortedBy { it.name }
        val domesticCost = arrayListOf<Float>()
        val importedCost = arrayListOf<Float>()

        println(".Domestic")
        for (i in sortedArray.iterator()) {
            if (i.domestic) {
                println(products(i))
                domesticCost.add(i.price)
            }
        }
        println(".Imported")
        for (i in sortedArray.iterator()) {
            if (!i.domestic) {
                println(products(i))
                importedCost.add(i.price)
            }
        }

        println("Domestic Cost: $" + domesticCost.sum())
        println("Imported Cost: $" + importedCost.sum())
        println("Domestic Count: " + domesticCost.size)
        println("Imported Count: " + importedCost.size)

    }

    fun receiptDetailsText(arrayList: ArrayList<ReceiptDetails>): String {
        val displayText: String
        val domesticProducts = mutableListOf<String>()
        val importedProducts = mutableListOf<String>()
        val sortedArray = arrayList.sortedBy { it.name }
        val domesticCost = arrayListOf<Float>()
        val importedCost = arrayListOf<Float>()

        for (i in sortedArray.iterator()) {
            if (i.domestic) {
                domesticProducts.add(products(i))
                domesticCost.add(i.price)
            }
        }
        for (i in sortedArray.iterator()) {
            if (!i.domestic) {
                importedProducts.add(products(i))
                importedCost.add(i.price)
            }
        }

        displayText =
            ".Domestic \n" +
                    "${domesticProducts.toString().replace("[", "").replace("]", "")} \n" +
                    ".Imported \n" +
                    "${importedProducts.toString().replace("[", "").replace("]", "")} \n" +
                    "Domestic Cost: $${domesticCost.sum()} \n" +
                    "Imported Cost: $${importedCost.sum()} \n" +
                    "Domestic Count: ${domesticCost.size} \n" +
                    "Imported Count: ${importedCost.size}"

        return displayText
    }

    private fun products(i: ReceiptDetails): String {
        return if (i.weight > 0){
            "...${i.name} \n   $${i.price} \n   ${i.description.substring(0,10)}... \n   ${i.weight}\n"
        } else {
            "...${i.name} \n   $${i.price} \n   ${i.description.substring(0,10)}... \n   N/A\n"
        }
    }
}