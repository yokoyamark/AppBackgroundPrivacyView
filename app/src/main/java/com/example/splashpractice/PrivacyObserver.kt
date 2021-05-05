package com.example.splashpractice

import android.R
import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import androidx.core.view.ViewCompat.animate
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

/**
 * An LifecycleObserver that can overlay a PrivacyView when the Activity moves to the background.<br>
 * This class must initialize after setContentView().<br>
 * Activity がバッググラウンドに移動したとき、PrivacyView を被せることができる Observer。
 *
 * @param context Activity Context
 * @param layoutResID layout resource id for privacy
 */
class PrivacyObserver(context: Context, layoutResID: Int) : LifecycleObserver {
    private var context: Context? = null
    private var privacyView: View? = null
    private var backgroundReceiver: BroadcastReceiver? = null

    init {
        this.context = context
        initObserver(context as Activity, layoutResID)
    }

    /**
     * layoutResID から view を生成して activity 最上位に被せる。
     *
     * @param activity　PrivacyViewを表示させたい Activity
     * @param layoutResID PrivacyView にしたいレイアウトID
     */
    private fun initObserver(activity: Activity, layoutResID: Int) {
        println(((activity.findViewById(R.id.content) as ViewGroup).getChildAt(0) as ViewGroup).childCount)
        val rootViewGroup =
            (activity.findViewById(R.id.content) as ViewGroup).getChildAt(0) as ViewGroup
        val inflater = LayoutInflater.from(activity)
        privacyView = inflater.inflate(layoutResID, rootViewGroup, false)
        privacyView?.id = View.generateViewId()
        activity.addContentView(
            privacyView,
            LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        )
        privacyView?.visibility = View.GONE
    }

    /**
     * AUTO SET
     * Set BroadcastReceiver for show PrivacyView
     * at Activity#onStart
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        println("onStart")
        registerReceiver()
    }

    /**
     * Remove PrivacyView
     * at Activity#onResume
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        println("onResume")
        gone()
    }

    /**
     * DO NOT USE
     * Show PrivacyView
     * use over Android10 (for GestureNavigation)
     * but react any touch ...
     * at Activity#onUserInteraction
     */
    fun onUserInteraction() {
        println("onUserInteraction")
        visible()
    }

    /**
     * Please use this at onUserLeaveHint
     * Show PrivacyView
     * at Activity#onUserLeaveHint
     */
    fun onUserLeaveHint() {
        println("onUserLeaveHint")
        visible()
    }

    /**
     * AUTO SET
     * Remove BroadcastReceiver for show PrivacyView
     * at Activity#onPause
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        println("onPause")
        unregisterReceiver()
    }

    fun visible() {
        if (privacyView != null) {
            if (privacyView?.visibility == View.GONE) {
                privacyView?.alpha = 1f
                privacyView?.visibility = View.VISIBLE
                privacyView?.postDelayed({
                    animate(privacyView!!).alpha(0f).setDuration(300).withEndAction {
                        privacyView?.visibility = View.GONE
                    }
                }, 2000)
            }
        }
    }

    fun gone() {
        if (privacyView != null) {
            if (privacyView?.visibility == View.GONE) {
                privacyView?.alpha = 1f
                privacyView?.visibility = View.VISIBLE
                privacyView?.postDelayed({
                    animate(privacyView!!).alpha(0f).setDuration(300).withEndAction {
                        privacyView?.visibility = View.GONE
                    }
                }, 200)
            }
        }
    }

    private fun registerReceiver() {
        backgroundReceiver = BackgroundReceiver(this)
        context?.registerReceiver(
            backgroundReceiver,
            IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)
        )
    }

    private fun unregisterReceiver() {
        context?.unregisterReceiver(backgroundReceiver)
    }

    class BackgroundReceiver(private val privacyObserver: PrivacyObserver) : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            println("BackgroundReceiver_onReceive")
            privacyObserver.visible()
        }
    }
}
