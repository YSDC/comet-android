package com.ysdc.comet.authentication.manager

import android.app.Activity
import com.github.ajalt.timberkt.Timber
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.ysdc.comet.authentication.model.PhoneAuthenticationStatus
import com.ysdc.comet.common.utils.AppConstants.AUTHENTICATION_TIMOUT
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit

class PhoneAuthenticationManager {

    private val authenticationStatus = BehaviorSubject.createDefault(PhoneAuthenticationStatus.STATE_EMPTY)
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var verificationInProgress = false
    private var storedVerificationId: String? = ""
    private lateinit var activity: Activity
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken

    fun setActivity(activity: Activity){
        this.activity = activity
    }

    fun initialize(phoneNumber: String) {
        if (auth.currentUser != null) {
            authenticationStatus.onNext(PhoneAuthenticationStatus.STATE_SIGNIN_SUCCESS)
        } else {
            authenticationStatus.onNext(PhoneAuthenticationStatus.STATE_INITIALIZED)
        }
        if (verificationInProgress) {
            startAuthentication(phoneNumber)
        }
    }

    fun startAuthentication(phoneNumber: String) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            phoneNumber, // Phone number to verify
            AUTHENTICATION_TIMOUT, // Timeout duration
            TimeUnit.SECONDS, // Unit of timeout
            activity, // Activity (for callback binding)
            callbacks
        )
        verificationInProgress = true
    }

    fun verifyPhoneNumberWithCode(verificationId: String?, code: String) {
        val credential = PhoneAuthProvider.getCredential(verificationId!!, code)
        signInWithPhoneAuthCredential(credential)
    }

    fun getAuthenticationStatus() : Observable<PhoneAuthenticationStatus>{
        return authenticationStatus
    }

    fun signOut() {
        auth.signOut()
        authenticationStatus.onNext(PhoneAuthenticationStatus.STATE_INITIALIZED)
    }

    private var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            // This callback will be invoked in two situations:
            // 1 - Instant verification. In some cases the phone number can be instantly
            //     verified without needing to send or enter a verification code.
            // 2 - Auto-retrieval. On some devices Google Play services can automatically
            //     detect the incoming verification SMS and perform verification without
            //     user action.
            Timber.d { "onVerificationCompleted:$credential" }
            verificationInProgress = false

            authenticationStatus.onNext(PhoneAuthenticationStatus.STATE_VERIFY_SUCCESS)
            signInWithPhoneAuthCredential(credential)
        }

        override fun onVerificationFailed(throwable: FirebaseException) {
            // This callback is invoked in an invalid request for verification is made,
            // for instance if the the phone number format is not valid.
            Timber.e(throwable)

            verificationInProgress = false

            when (throwable) {
                is FirebaseAuthInvalidCredentialsException -> authenticationStatus.onNext(PhoneAuthenticationStatus.STATE_VERIFY_FAILED_PHONE)
                is FirebaseTooManyRequestsException -> authenticationStatus.onNext(PhoneAuthenticationStatus.STATE_VERIFY_FAILED_QUOTA)
                else -> authenticationStatus.onNext(PhoneAuthenticationStatus.STATE_VERIFY_FAILED)
            }
        }

        override fun onCodeSent(verificationId: String?, token: PhoneAuthProvider.ForceResendingToken) {
            // The SMS verification code has been sent to the provided phone number, we
            // now need to ask the user to enter the code and then construct a credential
            // by combining the code with a verification ID.
            Timber.d { "onCodeSent:" + verificationId ?: "unknown verification id" }

            // Save verification ID and resending token so we can use them later
            storedVerificationId = verificationId
            resendToken = token

            authenticationStatus.onNext(PhoneAuthenticationStatus.STATE_CODE_SENT)
        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Timber.d { "signInWithCredential:success" }

                    val user = task.result?.user
                    authenticationStatus.onNext(PhoneAuthenticationStatus.STATE_SIGNIN_SUCCESS)
                } else {
                    // Sign in failed, display a message and update the UI
                    Timber.e(task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        authenticationStatus.onNext(PhoneAuthenticationStatus.STATE_VERIFY_FAILED_CODE)
                    } else {
                        authenticationStatus.onNext(PhoneAuthenticationStatus.STATE_VERIFY_FAILED)
                    }
                }
            }
    }
    private fun resendVerificationCode(phoneNumber: String) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            phoneNumber, // Phone number to verify
            AUTHENTICATION_TIMOUT, // Timeout duration
            TimeUnit.SECONDS, // Unit of timeout
            activity, // Activity (for callback binding)
            callbacks, // OnVerificationStateChangedCallbacks
            resendToken
        )
    }
}