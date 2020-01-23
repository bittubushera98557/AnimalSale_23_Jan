package com.example.lenovo.emptypro.Activities

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.lenovo.emptypro.ApiCallClasses.RetrofitClasses.GetDataService
import com.example.lenovo.emptypro.ModelClasses.AllApiResponse
import com.example.lenovo.emptypro.R
import com.example.lenovo.emptypro.Utilities.Utilities
import com.example.lenovo.emptypro.Utils.GlobalData
import com.example.lenovo.emptypro.Utils.SharedPrefUtil
import com.google.firebase.database.FirebaseDatabase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class P2PChatView : AppCompatActivity() {
    internal var service: GetDataService? = null 
    var TAG = "P2PChatView "
    internal var utilities = Utilities()
    var petId=""
    var ownerId=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_p2_pchat_view)
 
            var loggeInRef = FirebaseDatabase.getInstance().getReference().child("/chat")
            val userId = loggeInRef.push().key
            loggeInRef.child("" + userId).child("name").setValue("strName")
            loggeInRef.child("" + userId).child("email").setValue("strEmail")
            loggeInRef.child("" + userId).child("mobile").setValue("strMob")
            loggeInRef.child("" + userId).child("password").setValue("strPsd")
            loggeInRef.child("" + userId).child("userType").setValue("User")
            loggeInRef.child("" + userId).child("status").setValue("active")
        getPetDetail()
        try {
            getOldIntentData()
        } catch (exp: Exception) {

        }
    }
    private fun getOldIntentData() {
        petId = intent.extras!!.getString("oldPetId")
        if (petId.equals(""))
            getPetDetail()
    }


    private fun getPetDetail() {
        Log.e(TAG + " getPetDetail", "single-pet/?userID=" + SharedPrefUtil.getUserId(this@P2PChatView) + "&petID=" + petId)
        val call = service!!.getPetDetailsApi(SharedPrefUtil.getUserId(this@P2PChatView), "" + petId)

        call.enqueue(object : Callback<AllApiResponse.PetDetailRes> {
            override fun onResponse(call: Call<AllApiResponse.PetDetailRes>, response: Response<AllApiResponse.PetDetailRes>) {
                Log.e(TAG + " getPetDetail", "response   $response")
                if (response.body()!!.status.equals("200") && response.body()!!.data.size > 0) {
                     Log.e(TAG + " getPetDetail", "size=" + response.body()!!.data.size)
ownerId=response.body()!!.data[0].userID
                } else {
                    GlobalData.showSnackbar(this@P2PChatView as Activity, response.body()!!.messageType)
                }
            }

            override fun onFailure(call: Call<AllApiResponse.PetDetailRes>, t: Throwable) {
                // progress_bar.setVisibility(View.GONE);
                Toast.makeText(this@P2PChatView, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show()
            }
        })

    }

}
