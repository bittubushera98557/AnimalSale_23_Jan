package com.iww.classifiedolx.Fragments.AddAdvertise

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.example.lenovo.emptypro.Activities.MainActivity
import com.example.lenovo.emptypro.ApiCallClasses.RetrofitClasses.GetDataService
import com.example.lenovo.emptypro.ApiCallClasses.RetrofitClasses.RetrofitClientInstance
import com.example.lenovo.emptypro.Listeners.OnFragmentInteractionListener
import com.example.lenovo.emptypro.ModelClasses.AllApiResponse
import com.example.lenovo.emptypro.R
import com.example.lenovo.emptypro.Utilities.Utilities
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_advertisement_details.*
import kotlinx.android.synthetic.main.fragment_field_for_add_new_advertise.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class FieldForAddNewAdvertise : Fragment(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.tv_next -> {

                checkEditFieldValues()
                /*    utilities.enterNextReplaceFragment(
                            R.id.fl_fieldsFrag,
                            ChooseLocationForAdver.newInstance(addNewAdvertiseRequestParam!!,""),
                            (ctx as MainActivity).supportFragmentManager)*/
            }
            R.id.fl_fieldsFrag -> {


            }
            R.id.chkBox_dollar -> {
                chkBox_dollar.isChecked = true
                chkBox_dollar.setTextColor(R.color.colorAccent)
                chkBox_ruppess.isChecked = false
                chkBox_ruppess.setTextColor(R.color.colorBlack)

  }
            R.id.chkBox_ruppess-> {
                chkBox_ruppess.isChecked = true
                chkBox_ruppess.setTextColor(R.color.colorAccent)
                chkBox_dollar.isChecked = false
                chkBox_dollar.setTextColor(R.color.colorBlack)

            }
        }
    }

    private fun checkEditFieldValues() {
        var fiedlValueChk = true

        var strPetName = et_PetName.text.toString()
        var strTitle = et_Title.text.toString()
        var strPrice = et_Price.text.toString()
        var strPetType = et_petType.text.toString()
        var strBreed = et_breed.text.toString()
        var strAge = et_age.text.toString()
        var strDesc = et_desc.text.toString()

        if (et_petType.visibility == View.VISIBLE && (TextUtils.isEmpty(strPetType.replace(" ".toRegex(), "")))) {

            focusOnEditTxtView(et_petType, "Enter Pet Type")
            fiedlValueChk = false
        } else if (et_breed.visibility == View.VISIBLE && (TextUtils.isEmpty(
                        strBreed.replace(
                                " ".toRegex(),
                                ""
                        )
                ))
        ) {
            focusOnEditTxtView(et_breed, "Enter Pet Breed")
            fiedlValueChk = false
        } else if (et_age.visibility == View.VISIBLE && (TextUtils.isEmpty(
                        strAge.replace(
                                " ".toRegex(),
                                ""
                        )
                ))
        ) {
            focusOnEditTxtView(et_age, "Enter Age value")

            fiedlValueChk = false
        } else if (et_desc.visibility == View.VISIBLE && (TextUtils.isEmpty(
                        strDesc.replace(
                                " ".toRegex(),
                                ""
                        )
                ))
        ) {
            focusOnEditTxtView(et_desc, "Enter Desc value")
            fiedlValueChk = false
        } else if (TextUtils.isEmpty(et_PetName.text.toString().replace(" ".toRegex(), ""))) {
            focusOnEditTxtView(et_PetName, "Enter Pet Name")
            fiedlValueChk = false
        } else if (TextUtils.isEmpty(et_Title.text.toString().replace(" ".toRegex(), ""))) {
            fiedlValueChk = false
            focusOnEditTxtView(et_Title, "Enter Title")
        } else if (TextUtils.isEmpty(et_Price.text.toString().replace(" ".toRegex(), ""))) {
            fiedlValueChk = false
            focusOnEditTxtView(et_Price, "Enter Price")
        } else {
            addNewAdvertiseRequestParam!!.petName = strPetName
            addNewAdvertiseRequestParam!!.petTitle = strTitle
            addNewAdvertiseRequestParam!!.petPrice = strPrice
            addNewAdvertiseRequestParam!!.petType = strPetType
            addNewAdvertiseRequestParam!!.petBreed = strBreed
            addNewAdvertiseRequestParam!!.petAge = strAge
            addNewAdvertiseRequestParam!!.petDesc = strDesc

            utilities.enterNextReplaceFragment(
                    R.id.fl_fieldsFrag,
                    ChooseLocationForAdver.newInstance(addNewAdvertiseRequestParam!!, ""),
                    (ctx as MainActivity).supportFragmentManager
            )
        }


    }

    private fun focusOnEditTxtView(etView: EditText, strMsg: String) {
        scrVw_fields.post(Runnable {
            scrVw_fields.scrollTo(0, etView.top)
            etView.isFocusable = true
            etView.error = "" + strMsg

            //  txtInptLyt_email.error=" txtInptLyt Field Required"

        })
    }


    private var mainCatId: String? = null
    private var subCatId: String? = null
    private var listener: OnFragmentInteractionListener? = null
    var ctx: Context? = null
    var TAG = "FieldForAddNewAdvertise "
    var dialog: Dialog? = null
    var utilities = Utilities()
    internal var service: GetDataService? = null

    var addNewAdvertiseRequestParam: AllApiResponse.AddAdvertiseRequest? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mainCatId = addNewAdvertiseRequestParam!!.CatId
            subCatId = it.getString(ARG_PARAM2)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //   dialog = utilities.dialog(ctx!!)
        //    fetchAllSubCat("get_subcategory")
        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService::class.java)
        tv_next.setOnClickListener(this)
        fl_fieldsFrag.setOnClickListener(this)
        chkBox_dollar.setOnClickListener(this)
        chkBox_ruppess.setOnClickListener(this)
        tv_catName.text = addNewAdvertiseRequestParam!!.CatName + "    >    " + addNewAdvertiseRequestParam!!.SubCatName

        if(addNewAdvertiseRequestParam!!.CatName.toLowerCase().equals(""))
        {
             txtInptLyt_PetName.visibility=View.VISIBLE
            txtInptLyt_Title.visibility=View.VISIBLE
            //txtInptLyt_Price.visibility=View.VISIBLE
            ll_Price.visibility=View.VISIBLE
            txtInptLyt_petType.visibility=View.VISIBLE
            txtInptLyt_breed.visibility=View.VISIBLE
            txtInptLyt_age.visibility=View.VISIBLE
            txtInptLyt_desc.visibility=View.VISIBLE
        }
        else
            if(addNewAdvertiseRequestParam!!.CatName.toLowerCase().equals(""))
            {
                txtInptLyt_PetName.visibility=View.VISIBLE
                txtInptLyt_Title.visibility=View.VISIBLE
                //txtInptLyt_Price.visibility=View.VISIBLE
                ll_Price.visibility=View.VISIBLE
                txtInptLyt_petType.visibility=View.VISIBLE
                txtInptLyt_breed.visibility=View.VISIBLE
                txtInptLyt_age.visibility=View.VISIBLE
                txtInptLyt_desc.visibility=View.VISIBLE
            } else
                if(addNewAdvertiseRequestParam!!.CatName.toLowerCase().equals(""))
                {
                    txtInptLyt_PetName.visibility=View.VISIBLE
                    txtInptLyt_Title.visibility=View.VISIBLE
                    //txtInptLyt_Price.visibility=View.VISIBLE
                    ll_Price.visibility=View.VISIBLE
                    txtInptLyt_petType.visibility=View.VISIBLE
                    txtInptLyt_breed.visibility=View.VISIBLE
                    txtInptLyt_age.visibility=View.VISIBLE
                    txtInptLyt_desc.visibility=View.VISIBLE
                } else
                    if(addNewAdvertiseRequestParam!!.CatName.toLowerCase().equals(""))
                    {
                        txtInptLyt_PetName.visibility=View.VISIBLE
                        txtInptLyt_Title.visibility=View.VISIBLE
                        //txtInptLyt_Price.visibility=View.VISIBLE
                        ll_Price.visibility=View.VISIBLE
                        txtInptLyt_petType.visibility=View.VISIBLE
                        txtInptLyt_breed.visibility=View.VISIBLE
                        txtInptLyt_age.visibility=View.VISIBLE
                        txtInptLyt_desc.visibility=View.VISIBLE
                    } else
                        if(addNewAdvertiseRequestParam!!.CatName.toLowerCase().equals(""))
                        {
                            txtInptLyt_PetName.visibility=View.VISIBLE
                            txtInptLyt_Title.visibility=View.VISIBLE
                            //txtInptLyt_Price.visibility=View.VISIBLE
                            ll_Price.visibility=View.VISIBLE
                            txtInptLyt_petType.visibility=View.VISIBLE
                            txtInptLyt_breed.visibility=View.VISIBLE
                            txtInptLyt_age.visibility=View.VISIBLE
                            txtInptLyt_desc.visibility=View.VISIBLE
                        }
                        else
                            if(addNewAdvertiseRequestParam!!.CatName.toLowerCase().equals(""))
                            {
                                txtInptLyt_PetName.visibility=View.VISIBLE
                                txtInptLyt_Title.visibility=View.VISIBLE
                                //txtInptLyt_Price.visibility=View.VISIBLE
                                ll_Price.visibility=View.VISIBLE
                                txtInptLyt_petType.visibility=View.VISIBLE
                                txtInptLyt_breed.visibility=View.VISIBLE
                                txtInptLyt_age.visibility=View.VISIBLE
                                txtInptLyt_desc.visibility=View.VISIBLE
                            }
    }
/*
    private fun fetchAllSubCat(strApi: String) {
        showHideDialog(true)
        val call = service!!.allCateListApi()

        call!!.fetchSubCategory(strApi, mainCatId).enqueue(object : Callback<AllApiResponse.SubCategoryRes> {
            override fun onResponse(
                call: Call<AllApiResponse.SubCategoryRes>,
                response: Response<AllApiResponse.SubCategoryRes>
            ) {
                dialog!!.hide()
                Log.e(TAG + "fetchAllSubCat res", "" + Gson().toJson(response.body()))
                if (response.isSuccessful && (response.body()!!.status.equals("1"))) {
                    var subCatData = response.body()!!.data
                    for (i in 0..(subCatData.size - 1)) {
                        if (subCatData[i].id == subCatId) {
                            var field1 = subCatData[i].field1
                            if (!field1.equals("")) {
                                txtInptLyt_field1.visibility = View.VISIBLE
                                txtInptLyt_field1.hint = field1
                            }

                            var field2 = subCatData[i].field2
                            if (!field2.equals("")) {
                                txtInptLyt_field2.visibility = View.VISIBLE
                                txtInptLyt_field2.hint = field2
                            }

                            var field3 = subCatData[i].field3
                            if (!field3.equals("")) {
                                txtInptLyt_field3.visibility = View.VISIBLE
                                txtInptLyt_field3.hint = field3
                            }

                            var field4 = subCatData[i].field4
                            if (!field4.equals("")) {
                                txtInptLyt_field4.visibility = View.VISIBLE
                                txtInptLyt_field4.hint = field4
                            }

                            var field5 = subCatData[i].field5
                            if (!field5.equals("")) {
                                txtInptLyt_field5.visibility = View.VISIBLE
                                txtInptLyt_field5.hint = field5
                            }

                            var field6 = subCatData[i].field6
                            if (!field6.equals("")) {
                                txtInptLyt_field6.visibility = View.VISIBLE
                                txtInptLyt_field6.hint = field6
                            }

                            var field7 = subCatData[i].field7
                            if (!field7.equals("")) {
                                txtInptLyt_field7.visibility = View.VISIBLE
                                txtInptLyt_field7.hint = field7
                            }

                            var field8 = subCatData[i].field8
                            if (!field8.equals("")) {
                                txtInptLyt_field8.visibility = View.VISIBLE
                                txtInptLyt_field8.hint = field8
                            }

                            var field9 = subCatData[i].field9
                            if (!field9.equals("")) {
                                txtInptLyt_field9.visibility = View.VISIBLE
                                txtInptLyt_field9.hint = field9
                            }

                            var field10 = subCatData[i].field10
                            if (!field10.equals("")) {
                                txtInptLyt_field10.visibility = View.VISIBLE
                                txtInptLyt_field10.hint = field10
                            }
                        }
                    }
                } else {
                    Utility.snackBar(tv_next, "" + response.body()!!.message)
                }
            }

            override fun onFailure(call: Call<AllApiResponse.SubCategoryRes>, t: Throwable) {
                t.printStackTrace()
                dialog!!.hide()

            }
        })
    }*/

    private fun showHideDialog(dialogStatus: Boolean) {
        if (dialogStatus == true && dialog != null) {
            dialog!!.hide()
            dialog!!.cancel()
            dialog!!.dismiss()

            dialog!!.show()

        }
        if (dialogStatus == false && dialog != null) {
            dialog!!.hide()
            dialog!!.cancel()
            dialog!!.dismiss()
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_field_for_add_new_advertise, container, false)


    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        ctx = context
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }

    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }


    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: AllApiResponse.AddAdvertiseRequest, param2: String) =
                FieldForAddNewAdvertise().apply {
                    arguments = Bundle().apply {
                        addNewAdvertiseRequestParam = param1;
                        //    putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
