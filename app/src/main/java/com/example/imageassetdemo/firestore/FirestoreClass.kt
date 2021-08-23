package com.example.imageassetdemo.firestore

import android.util.Log
import com.example.imageassetdemo.activities.RegisterActivity
import com.example.imageassetdemo.models.User
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class FirestoreClass {
    private val mFireStore = FirebaseFirestore.getInstance()


    /**
     * A function to make an entry of the registered user in the FireStore database.
     */
    fun registerUser(activity: RegisterActivity, userInfo: User) {
        Log.d(
            activity.javaClass.simpleName,
            "Adding user in Firestore: ${userInfo.id}"
        )

        // The "users" is collection name. If the collection is already created then it will not create the same one again.
        mFireStore.collection("users")
            // Document ID for users fields. Here the document it is the User ID.
            .document(userInfo.id)
            // Here the userInfo are Field and the SetOption is set to merge. It is for if we wants to merge later on instead of replacing the fields.
            .set(userInfo, SetOptions.merge())
            .addOnSuccessListener {

                // Here call a function of base activity for transferring the result to it.
                activity.userRegistrationSuccess()
                Log.d(
                    activity.javaClass.simpleName,
                    "User added in Firestore: ${userInfo.id}"
                )
            }
            .addOnFailureListener { e ->
                activity.hideProgressDialog()
                Log.e(
                    activity.javaClass.simpleName,
                    "Error while registering the user.",
                    e
                )
            }
    }
    // END

}