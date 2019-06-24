package com.ysdc.comet.data

import com.google.firebase.firestore.FirebaseFirestore
import com.ysdc.comet.common.exception.OperationCanceledException
import com.ysdc.comet.data.utils.DataConstants.TEAM_CODE
import com.ysdc.comet.data.utils.DataConstants.TEAM_COLLECTION
import io.reactivex.Single

class FirestoreDataManager(private val firebaseFirestore: FirebaseFirestore) : DataManager {

    override fun teamExist(code: String): Single<Boolean> {
        return Single.create { emitter ->
            firebaseFirestore.collection(TEAM_COLLECTION).whereEqualTo(TEAM_CODE, code).get()
                .addOnSuccessListener { result -> emitter.onSuccess(!result.isEmpty) }
                .addOnFailureListener { exception -> emitter.onError(exception) }
                .addOnCanceledListener { emitter.onError(OperationCanceledException()) }
        }
    }
}