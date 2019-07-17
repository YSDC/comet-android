package com.ysdc.comet.data

import com.github.ajalt.timberkt.Timber
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.FirebaseFirestore
import com.ysdc.comet.common.application.GeneralConfig
import com.ysdc.comet.common.exception.ValidationException
import com.ysdc.comet.data.utils.DataConstants.CLUB_ID
import com.ysdc.comet.data.utils.DataConstants.CLUB_TOKEN
import com.ysdc.comet.data.utils.DataConstants.COLLECTION_CLUBS
import com.ysdc.comet.data.utils.DataConstants.COLLECTION_TEAMS
import com.ysdc.comet.data.utils.DataConstants.COLLECTION_USERS
import com.ysdc.comet.data.utils.DataConstants.TEAM_ID
import com.ysdc.comet.data.utils.DataConstants.USER_PHONE
import com.ysdc.comet.data.utils.DataConstants.USER_TEAM
import com.ysdc.comet.model.Team
import com.ysdc.comet.model.User
import io.reactivex.Completable
import io.reactivex.Single

class FirestoreDataManager(
    private val firebaseFirestore: FirebaseFirestore,
    private val generalConfig: GeneralConfig
) : DataManager {

    override fun addUser(user: User): Single<User> {
        return Single.defer {
            val query = firebaseFirestore.collection(COLLECTION_USERS)
                .whereEqualTo(USER_TEAM, user.teamId)
                .whereEqualTo(USER_PHONE, user.phone)

            val result = Tasks.await(query.get())
            if (!result.isEmpty) {
                Timber.d { "User exist" }
                Single.just(result.documents.first().toObject(User::class.java))
            } else {
                Timber.d { "New User" }
                createUser(user)
            }
        }
    }

    override fun updateUser(user: User): Completable {
        return Completable.defer {
            if (user.id == null) {
                //TODO: Add error message
                Completable.error(ValidationException(""))
            }
            val userRef = firebaseFirestore.collection(COLLECTION_USERS).document(user.id!!)
            userRef.set(user)
            Completable.complete()
        }
    }

    private fun createUser(user: User): Single<User> {
        return Single.defer {
            val newUser = firebaseFirestore.collection(COLLECTION_USERS).document()
            val newUserId = newUser.id
            Tasks.await(newUser.set(user))
            user.id = newUserId
            Single.just(user)
        }
    }


    override fun isClubTokenValid(token: String): Single<Boolean> {
        return Single.defer {
            val query = firebaseFirestore.collection(COLLECTION_CLUBS)
                .whereEqualTo(CLUB_ID, generalConfig.clubId())
                .whereEqualTo(CLUB_TOKEN, token)
            val result = Tasks.await(query.get())
            Single.just(!result.isEmpty)
        }
    }

    override fun registerTeam(team: Team): Completable {
        return Completable.defer {
            val query = firebaseFirestore.collection(COLLECTION_TEAMS)
                .whereEqualTo(CLUB_ID, generalConfig.clubId())
                .whereEqualTo(TEAM_ID, team.teamId)
            val result = Tasks.await(query.get())
            if (result.isEmpty) {
                val newTeam = firebaseFirestore.collection(COLLECTION_TEAMS).document()
                Tasks.await(newTeam.set(team))
            }
            Completable.complete()
        }
    }
}