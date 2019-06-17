package com.ysdc.comet.common.ui.utils

import com.ysdc.comet.common.R
import com.ysdc.comet.common.ui.base.MvpView
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog

class DialogBuilder {

    fun displayPermissionDialog(
        context: Context, @StringRes resource: Int?, @StringRes positiveBtnRes: Int?,
        @DrawableRes drawableRes: Int?, onPositiveActionListener: View.OnClickListener,
        onNegativeListener: View.OnClickListener?
    ) {
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.dialog_permission)

        val contentTv: TextView = dialog.findViewById(R.id.dialog_content)
        contentTv.setText(resource!!)

        val negativeButton: Button = dialog.findViewById(R.id.negative_btn)
        negativeButton.setOnClickListener { view ->
            onNegativeListener?.onClick(view)
            dialog.dismiss()
        }

        val positiveButton: Button = dialog.findViewById(R.id.positive_btn)
        if (positiveBtnRes != null) {
            positiveButton.setText(positiveBtnRes)
        }
        positiveButton.setOnClickListener { view ->
            onPositiveActionListener.onClick(view)
            dialog.dismiss()
        }

        val imageView: ImageView = dialog.findViewById(R.id.dialog_image)
        imageView.setImageResource(drawableRes!!)

        dialog.show()
    }


    fun displayVersionDialog(context: Context, title: String, content: String, cancelable: Boolean, storeUrl: String): AlertDialog {
        val versionDialog = AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(content)
            .setCancelable(false)
            .setNegativeButton(
                context.getString(R.string.action_download)
            ) { _, _ -> context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(storeUrl))) }
        if (cancelable) {
            versionDialog.setPositiveButton(R.string.action_ok) { _, _ ->
                if (context is MvpView) {
                    (context as MvpView).onVersionDialogClosed()
                }
            }
        }
        return versionDialog.show()
    }
}