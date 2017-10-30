package com.code.yashladha.android_user.Portal.Fragments

import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.support.annotation.RequiresApi
import android.support.design.widget.FloatingActionButton
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.*
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import com.code.yashladha.android_user.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.mikhaellopez.circularimageview.CircularImageView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.account_address_popup.*
import kotlinx.android.synthetic.main.account_address_popup.view.*
import kotlinx.android.synthetic.main.account_contact_popup.*
import kotlinx.android.synthetic.main.account_contact_popup.view.*
import kotlinx.android.synthetic.main.account_email_popup.*
import kotlinx.android.synthetic.main.account_email_popup.view.*
import kotlinx.android.synthetic.main.account_name_popup.*
import kotlinx.android.synthetic.main.account_name_popup.view.*
import kotlinx.android.synthetic.main.fragment_accounts.*
import kotlinx.android.synthetic.main.fragment_accounts.view.*
import java.io.File

/**
 * Created by yashladha on 17/10/17.
 * Fragment for accounts configuration of the user
 */

class AccountsFragment : Fragment() {

    companion object {
        val TAG = "AccountsFragment"
    }

    private val PICK_PHOTO = 1
    private var name: ImageView? = null
    private var email: ImageView? = null
    private var contactNo: TextView? = null
    private var address: TextView? = null
    private var profileCircular: CircularImageView? = null
    private var imageSelect: FloatingActionButton? = null
    private val user = FirebaseAuth.getInstance().currentUser
    private val firestore = FirebaseFirestore.getInstance()
    private val userLoc = "users/" + user!!.uid
    private val userInfoRef = firestore.document(userLoc)
    private val storageRef = FirebaseStorage.getInstance().getReference()

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater!!.inflate(R.layout.fragment_accounts, container, false)

        name = view.account_iv_name
        email = view.account_iv_email
        contactNo = view.account_tv_contact
        address = view.account_tv_address
        imageSelect = view.imageSelectionFab
        profileCircular = view.circularProfileImage

        updateUI(view)

        name!!.setOnClickListener {
            initiateNamePopup(view)
        }

        email!!.setOnClickListener {
            initiateEmailPopup(view)
        }

        contactNo!!.setOnClickListener {
            initiateContactPopup(view)
        }

        address!!.setOnClickListener {
            initiateAddressPopup(view)
        }

        imageSelect!!.setOnClickListener {
            selectImage(view)
        }

        return view
    }

    private fun selectImage(view: View?) {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_PHOTO)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_PHOTO && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                val result = data.data
                val filePath = getPathFromURI(result)
                val fileUri = Uri.fromFile(File(filePath))

                val profileImageRef = storageRef.child("/users/" + fileUri.lastPathSegment)
                profileImageRef.putFile(fileUri)
                        .addOnSuccessListener { taskSnapshot ->
                            val downloadUrl = taskSnapshot.downloadUrl
                            userInfoRef
                                    .update("profileImage", downloadUrl.toString())
                                    .addOnSuccessListener {
                                        Log.i("profileImageRef", "Image uploaded successfully")
                                    }
                                    .addOnFailureListener {
                                        Log.e("profileImageRef", "Error occurred")
                                    }
                        }
                        .addOnFailureListener {
                            Toast.makeText(activity.applicationContext,
                                    "Error while updating image",
                                    Toast.LENGTH_SHORT
                            ).show()
                        }

            }
        }
    }

    private fun getPathFromURI(data: Uri?): String? {
        val wholeId = DocumentsContract.getDocumentId(data)
        val id = wholeId.split(":")[1]
        val filePathColumn = Array(1, { MediaStore.Images.Media.DATA })
        val sel = MediaStore.Images.Media._ID + "=?"
        val cursor = activity.contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, filePathColumn, sel, Array(1, { id }), null)
        val columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        if (cursor.moveToFirst()) {
            val filePath = cursor.getString(columnIndex)
            cursor.close()
            return filePath
        }
        return null
    }

    private fun updateUI(view: View) {
        userInfoRef.get().addOnCompleteListener { task ->
            val result = task.result.data
            view.account_name_text.text = result["name"] as CharSequence
            view.account_email_text.text = result["email"] as CharSequence
            contactNo!!.text = result["contact"].toString()
            address!!.text = result["address"] as CharSequence
            val downloadUri = result["profileImage"]!!.toString()
            Picasso.with(view.context).load(downloadUri).into(profileCircular)
        }
    }

    private fun initiateAddressPopup(view: View) {
        try {
            val (viewPopup, popupWindow) = popUpCreator(view, R.layout.account_address_popup, account_address_popup)

            viewPopup.account_address_popup_address.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                    validateAddress()
                }

                private fun validateAddress() {
                    val address = viewPopup.account_address_popup_address.text.toString().trim()
                    if (address.isEmpty()) {
                        viewPopup.account_address_layout.error = "Address is invalid"
                        viewPopup.account_address_popup_submit.isClickable = false
                    } else {
                        viewPopup.account_address_popup_submit.isClickable = true
                        viewPopup.account_address_layout.isErrorEnabled = false
                    }
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }
            })

            viewPopup.account_address_popup_submit.setOnClickListener {
                val addressText: String = viewPopup.account_address_popup_address.text.toString()
                userInfoRef.update("address", addressText).addOnCompleteListener {
                    accounts_main_layout.alpha = 1.0F
                    popupWindow.dismiss()
                }.addOnSuccessListener {
                    Toast.makeText(view.context, "Address updated!", Toast.LENGTH_SHORT).show()
                    address!!.text = addressText
                }.addOnFailureListener {
                    Log.e(TAG, "Error occurred while updating address")
                }
            }

            viewPopup.account_address_cancel.setOnClickListener {
                accounts_main_layout.alpha = 1.0F
                popupWindow.dismiss()
            }
        } catch (e: Exception) {
            Log.e(TAG, e.message)
        }
    }

    private fun initiateContactPopup(view: View) {
        try {
            val (viewPopup, popupWindow) = popUpCreator(view, R.layout.account_contact_popup, account_contact_popup)

            viewPopup.account_contact_popup_contact.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                    validateContact()
                }

                private fun validateContact() {
                    val phone = viewPopup.account_contact_popup_contact.text.toString().trim()
                    if (phone.isEmpty() || !isValidPhone(phone)) {
                        viewPopup.account_contact_layout.error = "Phone is invalid"
                        viewPopup.account_contact_popup_submit.isClickable = false
                    } else {
                        viewPopup.account_contact_popup_submit.isClickable = true
                        viewPopup.account_contact_layout.isErrorEnabled = false
                    }
                }

                private fun isValidPhone(phone: String): Boolean =
                        !TextUtils.isEmpty(phone) && Patterns.PHONE.matcher(phone).matches()

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }
            })

            viewPopup.account_contact_popup_submit.setOnClickListener {
                val contactnumber = viewPopup.account_contact_popup_contact.text.toString()
                userInfoRef.update("contact", contactnumber.toLong()).addOnCompleteListener {
                    accounts_main_layout.alpha = 1.0F
                    popupWindow.dismiss()
                }.addOnSuccessListener {
                    Toast.makeText(view.context, "Contact updated!", Toast.LENGTH_SHORT).show()
                    contactNo!!.text = contactnumber
                }.addOnFailureListener {
                    Log.e(TAG, "Error occurred while updating contact number")
                }
            }

            viewPopup.account_contact_cancel.setOnClickListener {
                popupWindow.dismiss()
                accounts_main_layout.alpha = 1.0F
            }

        } catch (e: Exception) {
            Log.e(TAG, e.message)
        }
    }

    private fun initiateEmailPopup(view: View) {
        try {
            val (viewPopup, popupWindow) = popUpCreator(view, R.layout.account_email_popup, account_email_popup)

            viewPopup.account_email_popup_email.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                    validateEmail()
                }

                private fun validateEmail() {
                    val email = viewPopup.account_email_popup_email.text.toString().trim()
                    if (email.isEmpty() || !isValidEmail(email)) {
                        viewPopup.account_email_popup_layout.error = "Email is not Valid"
                        viewPopup.account_email_popup_submit.isClickable = false
                    } else {
                        viewPopup.account_email_popup_layout.isErrorEnabled = false
                        viewPopup.account_email_popup_submit.isClickable = true
                    }
                }

                private fun isValidEmail(email: String): Boolean =
                        !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }
            })

            viewPopup.account_email_popup_cancel.setOnClickListener {
                popupWindow.dismiss()
                accounts_main_layout.alpha = 1.0F
            }

            viewPopup.account_email_popup_submit.setOnClickListener {
                val tempEmail = viewPopup.account_email_popup_email.text.toString()
                userInfoRef.update("email", tempEmail).addOnCompleteListener {
                    accounts_main_layout.alpha = 1.0F
                    popupWindow.dismiss()
                }.addOnSuccessListener {
                    Toast.makeText(view.context, "Email updated!", Toast.LENGTH_SHORT).show()
                    view.account_email_text.text = tempEmail
                }.addOnFailureListener {
                    Log.e(TAG, "Error occurred while updating Email")
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, e.message)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun initiateNamePopup(view: View) {
        try {
            val (viewPopup, popupWindow) = popUpCreator(view, R.layout.account_name_popup, account_name_popup)

            viewPopup.account_name_popup_name.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                    validateName()
                }

                private fun validateName() {
                    val name = viewPopup.account_name_popup_name.text.toString().trim()
                    if (name.isEmpty()) {
                        viewPopup.account_name_layout.error = "Name is not valid"
                        viewPopup.account_name_popup_submit.isClickable = false
                    } else {
                        viewPopup.account_name_layout.isErrorEnabled = false
                        viewPopup.account_name_popup_submit.isClickable = true
                    }
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }
            })

            viewPopup.account_name_cancel.setOnClickListener {
                popupWindow.dismiss()
                accounts_main_layout.alpha = 1.0F
            }

            viewPopup.account_name_popup_submit.setOnClickListener {
                val tempName = viewPopup.account_name_popup_name.text.toString()
                userInfoRef.update("name", tempName).addOnCompleteListener {
                    accounts_main_layout.alpha = 1.0F
                    popupWindow.dismiss()
                }.addOnSuccessListener {
                    Toast.makeText(view.context, "Name updated!", Toast.LENGTH_SHORT).show()
                    view.account_name_text.text = tempName
                }.addOnFailureListener {
                    Log.e(TAG, "Error occurred while updating Name")
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, e.message)
        }
    }

    private fun popUpCreator(view: View, layout: Int, group: ViewGroup?): Pair<View, PopupWindow> {
        accounts_main_layout.alpha = 0.1F
        val inflater: LayoutInflater = view.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val viewPopup = inflater.inflate(layout, group)

        val window = view.context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val width = window.defaultDisplay.width * 0.80
        val height: Double?
        height = if (resources.configuration.orientation == 1)
            window.defaultDisplay.height * 0.40
        else
            window.defaultDisplay.height * 0.55
        val focusable = true
        val popupWindow = PopupWindow(viewPopup, width.toInt(), height.toInt(), focusable)

        popupWindow.animationStyle = R.style.PopupAnimation
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)

        return Pair(viewPopup, popupWindow)
    }

    override fun onPause() {
        super.onPause()
    }
}