package com.braintreepayments.testpaypal

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.annotation.RequiresApi
import com.braintreepayments.api.BraintreeClient
import com.braintreepayments.api.PayPalCheckoutRequest
import com.braintreepayments.api.PayPalClient

class MainActivity : AppCompatActivity() {

    private lateinit var button: Button
    private lateinit var braintreeClient: BraintreeClient
    private lateinit var payPalClient: PayPalClient

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("TAG", "flag" + intent.flags)
        Log.d("TAG", "extract flag" + getIntentFlag(intent.flags))
        setContentView(R.layout.activity_main)

        button = findViewById(R.id.button);
        button.setOnClickListener {
            launchPayPal(it)
        }

        braintreeClient = BraintreeClient(this, "sandbox_f252zhq7_hh4cpc39zq4rgjcg")
        payPalClient = PayPalClient(braintreeClient)
    }

    private fun launchPayPal(it: View?) {
        val request = PayPalCheckoutRequest("1.00")
        request.currencyCode = "USD"

        payPalClient.tokenizePayPalAccount(this, request) { error ->
            error?.let {
                // handle error
            }
        }
    }

    override fun onResume() {
        super.onResume()

        val result = braintreeClient.deliverBrowserSwitchResult(this)
        result?.let {
            payPalClient.onBrowserSwitchResult(it) { payPalAccountNonce, error ->
                // send paypalAccountNonce.string to server
                payPalAccountNonce?.let { nonce ->
                    Log.d("NONCE", nonce.string)
                }
                error?.let { error ->
                    Log.d("ERROR", error.message!!)
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    fun getIntentFlag(flag: Int) : String{
        val flags = listOf<Pair<String, Int>>(
            Pair("Intent.FLAG_GRANT_READ_URI_PERMISSION", Intent.FLAG_GRANT_READ_URI_PERMISSION),
            Pair("Intent.FLAG_GRANT_WRITE_URI_PERMISSION",Intent.FLAG_GRANT_WRITE_URI_PERMISSION),
            Pair("Intent.FLAG_FROM_BACKGROUND",Intent.FLAG_FROM_BACKGROUND),
            Pair("Intent.FLAG_DEBUG_LOG_RESOLUTION",Intent.FLAG_DEBUG_LOG_RESOLUTION),
            Pair("Intent.FLAG_EXCLUDE_STOPPED_PACKAGES",Intent.FLAG_EXCLUDE_STOPPED_PACKAGES),
            Pair("Intent.FLAG_INCLUDE_STOPPED_PACKAGES",Intent.FLAG_INCLUDE_STOPPED_PACKAGES),
            Pair("Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION",Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION),
            Pair("Intent.FLAG_GRANT_PREFIX_URI_PERMISSION",Intent.FLAG_GRANT_PREFIX_URI_PERMISSION),
            Pair("android.content.Intent.FLAG_DEBUG_TRIAGED_MISSING (system)",256), // android.content.Intent.FLAG_DEBUG_TRIAGED_MISSING
            Pair("Intent.FLAG_IGNORE_EPHEMERAL (system)",512), // Intent.FLAG_IGNORE_EPHEMERAL
            Pair("Intent.FLAG_ACTIVITY_MATCH_EXTERNAL",Intent.FLAG_ACTIVITY_MATCH_EXTERNAL),
            Pair("Intent.FLAG_ACTIVITY_NO_HISTORY",Intent.FLAG_ACTIVITY_NO_HISTORY),
            Pair("Intent.FLAG_ACTIVITY_SINGLE_TOP",Intent.FLAG_ACTIVITY_SINGLE_TOP),
            Pair("Intent.FLAG_ACTIVITY_NEW_TASK",Intent.FLAG_ACTIVITY_NEW_TASK),
            Pair("Intent.FLAG_ACTIVITY_MULTIPLE_TASK",Intent.FLAG_ACTIVITY_MULTIPLE_TASK),
            Pair("Intent.FLAG_ACTIVITY_CLEAR_TOP",Intent.FLAG_ACTIVITY_CLEAR_TOP),
            Pair("Intent.FLAG_ACTIVITY_FORWARD_RESULT",Intent.FLAG_ACTIVITY_FORWARD_RESULT),
            Pair("Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP",Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP),
            Pair("Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS",Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS),
            Pair("Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT",Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT),
            Pair("Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED",Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED),
            Pair("Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY",Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY),
            Pair("Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET (deprecated)",Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET),
            Pair("Intent.FLAG_ACTIVITY_NEW_DOCUMENT",Intent.FLAG_ACTIVITY_NEW_DOCUMENT),
            Pair("Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET (deprecated)",Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET),
            Pair("Intent.FLAG_ACTIVITY_NO_USER_ACTION",Intent.FLAG_ACTIVITY_NO_USER_ACTION),
            Pair("Intent.FLAG_ACTIVITY_REORDER_TO_FRONT",Intent.FLAG_ACTIVITY_REORDER_TO_FRONT),
            Pair("Intent.FLAG_ACTIVITY_NO_ANIMATION",Intent.FLAG_ACTIVITY_NO_ANIMATION),
            Pair("Intent.FLAG_ACTIVITY_CLEAR_TASK",Intent.FLAG_ACTIVITY_CLEAR_TASK),
            Pair("Intent.FLAG_ACTIVITY_TASK_ON_HOME",Intent.FLAG_ACTIVITY_TASK_ON_HOME),
            Pair("Intent.FLAG_ACTIVITY_RETAIN_IN_RECENTS",Intent.FLAG_ACTIVITY_RETAIN_IN_RECENTS),
            Pair("Intent.FLAG_ACTIVITY_LAUNCH_ADJACENT",Intent.FLAG_ACTIVITY_LAUNCH_ADJACENT),
            Pair("Intent.FLAG_ACTIVITY_REQUIRE_NON_BROWSER",Intent.FLAG_ACTIVITY_REQUIRE_NON_BROWSER),
            Pair("Intent.FLAG_ACTIVITY_REQUIRE_DEFAULT",Intent.FLAG_ACTIVITY_REQUIRE_DEFAULT),
            Pair("Intent.FLAG_RECEIVER_REGISTERED_ONLY",Intent.FLAG_RECEIVER_REGISTERED_ONLY),
            Pair("Intent.FLAG_RECEIVER_REPLACE_PENDING",Intent.FLAG_RECEIVER_REPLACE_PENDING),
            Pair("Intent.FLAG_RECEIVER_FOREGROUND",Intent.FLAG_RECEIVER_FOREGROUND),
            Pair("Intent.FLAG_RECEIVER_NO_ABORT",Intent.FLAG_RECEIVER_NO_ABORT),
            Pair("Intent.FLAG_RECEIVER_REGISTERED_ONLY_BEFORE_BOOT (system)",67108864),
            Pair("Intent.FLAG_RECEIVER_BOOT_UPGRADE (system)",33554432), // Intent.FLAG_RECEIVER_BOOT_UPGRADE
            Pair("Intent.FLAG_RECEIVER_INCLUDE_BACKGROUND (system)",16777216), // Intent.FLAG_RECEIVER_INCLUDE_BACKGROUND
            Pair("Intent.FLAG_RECEIVER_EXCLUDE_BACKGROUND (system)",8388608), // Intent.FLAG_RECEIVER_EXCLUDE_BACKGROUND
            Pair("Intent.FLAG_RECEIVER_FROM_SHELL (system)",4194304), // Intent.FLAG_RECEIVER_FROM_SHELL
            Pair("Intent.FLAG_RECEIVER_VISIBLE_TO_INSTANT_APPS",Intent.FLAG_RECEIVER_VISIBLE_TO_INSTANT_APPS),
            Pair("Intent.FLAG_RECEIVER_OFFLOAD (system)", 2147483648.toInt()), // Intent.FLAG_RECEIVER_OFFLOAD
        )

        val matchFlags = mutableListOf<Pair<String, Int>>()
        flags.forEach {
            if ((flag and it.second) != 0) {
                matchFlags.add(it)
            }
        }

        var testFlag = 0
        var matchFlagsString = ""
        matchFlags.forEach {
            matchFlagsString += " " + it.first + "(" + it.second + ")"

            testFlag = it.second or testFlag
        }
        Log.i("TAG", "test flag: $testFlag")

        return matchFlagsString
    }
}