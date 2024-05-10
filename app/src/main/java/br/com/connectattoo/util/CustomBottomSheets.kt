package br.com.connectattoo.util

import androidx.fragment.app.Fragment
import br.com.connectattoo.R
import br.com.connectattoo.databinding.BottomSheetEditPhotoProfileBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

fun Fragment.showBottomSheetEditPhotoProfile(

    onClickChooseLibrary: () -> Unit = {},
    onClickTakePicture: () -> Unit = {},
    onClickRemovePhoto: () -> Unit = {},
) {
    val bottomSheetDialog =
        BottomSheetDialog(requireContext(), R.style.ThemeOverlay_App_BottomSheetDialog)
    val bottomSheetBinding: BottomSheetEditPhotoProfileBinding =
        BottomSheetEditPhotoProfileBinding.inflate(layoutInflater, null, false)


    bottomSheetBinding.ivChooseLibrary.setOnClickListener {
        onClickChooseLibrary()
    }
    bottomSheetBinding.txtChooseLibrary.setOnClickListener {
        onClickChooseLibrary()
    }
    bottomSheetBinding.ivTakePicture.setOnClickListener {
        onClickTakePicture()
    }
    bottomSheetBinding.txtTakePicture.setOnClickListener {
        onClickTakePicture()
    }
    bottomSheetBinding.ivRemovePhoto.setOnClickListener {
        onClickRemovePhoto()
    }
    bottomSheetBinding.txtRemovePhoto.setOnClickListener {
        onClickRemovePhoto()
    }

    bottomSheetDialog.setContentView(bottomSheetBinding.root)
    bottomSheetDialog.show()
}
