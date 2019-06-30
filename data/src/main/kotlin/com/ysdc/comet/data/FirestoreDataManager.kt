package com.ysdc.comet.data

import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.FirebaseFirestore
import com.ysdc.comet.common.application.GeneralConfig
import com.ysdc.comet.common.exception.OperationCanceledException
import com.ysdc.comet.data.utils.DataConstants.CLUB_ID
import com.ysdc.comet.data.utils.DataConstants.CLUB_TOKEN
import com.ysdc.comet.data.utils.DataConstants.COLLECTION_CLUBS
import com.ysdc.comet.data.utils.DataConstants.COLLECTION_USERS
import com.ysdc.comet.data.utils.DataConstants.USER_PHONE
import com.ysdc.comet.data.utils.DataConstants.USER_TEAM
import com.ysdc.comet.model.User
import io.reactivex.Single

class FirestoreDataManager(
    private val firebaseFirestore: FirebaseFirestore,
    private val generalConfig: GeneralConfig
) : DataManager {

    override fun isClubTokenValid(token: String): Single<Boolean> {
        return Single.create { emitter ->
            firebaseFirestore.collection(COLLECTION_CLUBS)
                .whereEqualTo(CLUB_ID, generalConfig.clubId())
                .whereEqualTo(CLUB_TOKEN, token).get()
                .addOnSuccessListener { result -> emitter.onSuccess(!result.isEmpty) }
                .addOnFailureListener { exception -> emitter.onError(exception) }
                .addOnCanceledListener { emitter.onError(OperationCanceledException()) }
        }
    }

    override fun addOrUpdateUser(user: User): Single<User> {
        return Single.defer {
            val query = firebaseFirestore.collection(COLLECTION_USERS)
                .whereEqualTo(USER_TEAM, user.teamId)
                .whereEqualTo(USER_PHONE, user.phone)

            val result = Tasks.await(query.get())
            if (!result.isEmpty) {
                Single.just(result.documents.first().toObject(User::class.java))
            } else {
                createUser(user)
            }
        }
    }

    private fun createUser(user: User): Single<User> {
        return Single.create { emitter ->
            val newUser = firebaseFirestore.collection(COLLECTION_USERS).document()
            val newUserId = newUser.
        }
    }


}