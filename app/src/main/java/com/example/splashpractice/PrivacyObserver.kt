package com.example.splashpractice

import android.R
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams

/**
 * An Observer that can overlay a PrivacyView when the Activity moves to the background.
 * Activity がバッググラウンドに移動したとき、PrivacyView を被せることができる Observer
 */
class PrivacyObserver {
    lateinit var privacyView: View

    /**
     * このメソッドは Activity#onCreate で実行する
     * ex: addObserver(this, R.layout.privacy)
     *
     * @param activity　PrivacyViewを表示させたい Activity
     * @param layoutResID PrivacyView にしたいレイアウトID
     */
    fun addObserver(activity: Activity, layoutResID: Int) {
        println(((activity.findViewById(R.id.content) as ViewGroup).getChildAt(0) as ViewGroup).childCount)
        val rootViewGroup = (activity.findViewById(R.id.content) as ViewGroup).getChildAt(0) as ViewGroup
        val inflater = LayoutInflater.from(activity)
        privacyView = inflater.inflate(layoutResID, rootViewGroup, false)
        privacyView.id = View.generateViewId()
        activity.addContentView(privacyView, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT))
        privacyView.visibility = View.GONE
    }


    /**
     * Set PrivacyView
     * use over Android10 (for GestureNavigation)
     * at Activity#onUserInteraction
     */
    fun onUserInteraction() {
        println("onUserInteraction")
        if (privacyView.visibility == View.GONE) {
            privacyView.visibility = View.VISIBLE
        }
    }

    /**
     * Set PrivacyView
     * at Activity#onUserLeaveHint
     */
    fun onUserLeaveHint() {
        println("onUserLeaveHint")
        if (privacyView.visibility == View.GONE) {
            privacyView.visibility = View.VISIBLE
        }
    }

    /**
     * Remove PrivacyView
     * at Activity#onResume
     */
    fun onResume() {
        println("onResume")
        privacyView.visibility = View.GONE
    }
}