package org.z1god.selap.Notification

import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_verfication.*
import org.z1god.selap.Auth.LoginActivity
import org.z1god.selap.R


/**
 * A simple [Fragment] subclass.
 */
class VerificationFragment : BottomSheetDialogFragment() {
    companion object {
        const val KEY_MESSAGE = "KEY_MESSAGE"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_verfication, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme);
    }

    //prevent when scrolldown dialog
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog;

        bottomSheetDialog.setOnShowListener {
            val bottomSheet =
                bottomSheetDialog.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet);
            if (null != bottomSheet) {
                val behavior: BottomSheetBehavior<*> =
                    BottomSheetBehavior.from(bottomSheet)
                behavior.isHideable = false
            }
        }
        return bottomSheetDialog
    }

    //prevent when touch outsidedddc
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val touchOutside  = dialog?.window?.decorView?.findViewById<View>(com.google.android.material.R.id.touch_outside);
        touchOutside?.setOnClickListener(null)

        arguments?.let {
            tv_message_notif.text = it.getString(KEY_MESSAGE)
            btn_back_login.visibility = GONE
        }

        btn_back_login.setOnClickListener {
            startActivity(Intent(context,LoginActivity::class.java))
        }
    }



}
