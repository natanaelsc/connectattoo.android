package br.com.connectattoo.ui.profile.tattoclientditprofile

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import br.com.connectattoo.ConnectattooApplication
import br.com.connectattoo.R
import br.com.connectattoo.databinding.FragmentTattooClientEditProfileBinding
import br.com.connectattoo.repository.ProfileRepository
import br.com.connectattoo.ui.BaseFragment
import br.com.connectattoo.util.Constants
import br.com.connectattoo.util.DataStoreManager
import br.com.connectattoo.util.PermissionUtils.requestMediaImagesPermission
import br.com.connectattoo.util.showBottomSheetEditPhotoProfile
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

@Suppress("TooManyFunctions")
@RequiresApi(Build.VERSION_CODES.O)
class TattooClientEditProfileFragment : BaseFragment<FragmentTattooClientEditProfileBinding>() {
    private lateinit var profileRepository: ProfileRepository
    private val viewModel: TattooClientEditProfileViewModel by viewModels()
    private var isMediaImagesPermissionGranted: Boolean = false
    override fun setupViews() {
        requestPermissionImages()
        getInitialInformationClientProfile()
        observerViewModel()
        setupListeners()
        observerAndValidateField()
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTattooClientEditProfileBinding {
        return FragmentTattooClientEditProfileBinding.inflate(inflater, container, false)
    }

    private fun observerAndValidateField() {
        onTextChanged(binding.etClientEmail) { validateEmail() }
        onTextChanged(binding.etBirthDate) { validateBirthDate() }
    }

    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                viewModel.setImageUri(uri)
                Glide.with(this)
                    .load(uri)
                    .circleCrop()
                    .placeholder(R.drawable.icon_person_profile_black)
                    .into(binding.ivPhotoClient)
            }
        }
    private val takePicture =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) {
                val image = createImageFileUri()
                viewModel.setImageUri(image)

                Glide.with(this)
                    .load(image)
                    .circleCrop()
                    .placeholder(R.drawable.icon_person_profile_black)
                    .into(binding.ivPhotoClient)
            }
        }

    private fun getInitialInformationClientProfile() {
        val database = (requireActivity().application as ConnectattooApplication).database
        val clientProfileDao = database.tattooClientProfileDao()
        profileRepository = ProfileRepository(clientProfileDao)
        viewModel.getInitialInformationTattooClientProfile(profileRepository)
    }


    private fun uploadProfilePhoto() {
        val database = (requireActivity().application as ConnectattooApplication).database
        val clientProfileDao = database.tattooClientProfileDao()
        profileRepository = ProfileRepository(clientProfileDao)
        if (isMediaImagesPermissionGranted) {
            val fileUri: Uri? = viewModel.imageUri.value
            val imagePart = fileUri?.let { getFilePartFromUri(requireContext(), it) }
            if (imagePart != null) {
                viewLifecycleOwner.lifecycleScope.launch {
                    val token =
                        DataStoreManager.getUserSettings(requireContext(), Constants.API_TOKEN)
                    viewModel.uploadClientProfilePhoto(profileRepository, token, imagePart)
                }
            }
        }

    }

    private fun observerViewModel() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiStateFlow.collect { uiState ->
                    when (uiState) {
                        TattooClientEditProfileViewModel.UiState.Success -> {
                            insertInformationTattooClientProfile()
                        }

                        TattooClientEditProfileViewModel.UiState.Error -> {
                            val message = viewModel.dataState.stateErrorDeleteImage
                            if (message != null) {
                                Log.i("ErrorDeleteImage", message)
                            }
                        }

                        TattooClientEditProfileViewModel.UiState.Loading -> {

                        }

                        else -> {
                        }
                    }
                }
            }
        }
    }

    private fun insertInformationTattooClientProfile() {
        val image = viewModel.dataState.imageProfile
        binding.run {
            Glide.with(ivPhotoClient)
                .load(
                    if (image.isNullOrEmpty()) R.drawable.icon_person_profile_black
                   else image
                )
                .circleCrop()
                .placeholder(R.drawable.icon_person_profile)
                .into(ivPhotoClient)
            etDisplayName.setText(viewModel.dataState.displayName)
            etUserName.setText(viewModel.dataState.username)
            etClientEmail.setText(viewModel.dataState.email)
            etBirthDate.setText(viewModel.dataState.birthDate)
        }
    }


    private fun validateDate(birthDate: String): String? {
        if (birthDate.length != 8) {
            return null
        }

        val day = birthDate.substring(0, 2)
        val month = birthDate.substring(2, 4)
        val year = birthDate.substring(4)

        val formattedDate = "$year-$month-$day"
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        format.isLenient = false

        return try {
            format.parse(formattedDate)
            formattedDate
        } catch (error: ParseException) {
            Log.i("TAG", error.message.toString())
            null
        }
    }

    private fun setupListeners() {
        binding.ivBack.setOnClickListener {
            findNavController().navigate(R.id.action_tattooClientEditProfileFragment_to_clientUserProfileFragment)
        }
        binding.btnEditClientPhoto.setOnClickListener {
            showBottomSheetEditPhotoProfile(
                onClickChooseLibrary = {
                    if (isMediaImagesPermissionGranted) {
                        getContent.launch("image/*")
                    } else {
                        showSnackBarPermissionImages()
                    }
                },
                onClickTakePicture = {
                    if (isMediaImagesPermissionGranted) {
                        takePicture()
                    } else {
                        showSnackBarPermissionImages()
                    }
                },
                enableBtnRemovePhoto = !viewModel.dataState.imageProfile.isNullOrEmpty(),
                onClickRemovePhoto = {
                    removeClientPhoto()

                }

            )
        }
        binding.btnUpload.setOnClickListener {
            uploadProfilePhoto()
        }
    }


    private fun validateEmail() {
        val email = binding.etClientEmail.text.toString()
        val checkEmail = email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
        if (checkEmail) {
            binding.tilClientEmail.error = null
        } else {
            binding.tilClientEmail.setError(R.string.error_field_email)
        }
    }

    private fun validateBirthDate() {
        val clientBirthDate = binding.etBirthDate.unMasked
        val check = validateDate(clientBirthDate)
        if (check != null) {
            binding.tilBirthDate.error = null
        } else {
            binding.tilBirthDate.setError(R.string.error_field_birth_date)
        }

    }

    private fun onTextChanged(editText: EditText, function: () -> Unit) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                return
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                function()
            }

            override fun afterTextChanged(s: Editable?) {
                return
            }
        })
    }

    private fun removeClientPhoto() {
        viewLifecycleOwner.lifecycleScope.launch {
            val token = DataStoreManager.getUserSettings(requireContext(), Constants.API_TOKEN)
            viewModel.deleteClientProfilePhoto(profileRepository, token)
        }
    }

    private fun TextInputLayout.setError(stringResId: Int?) {
        error = if (stringResId != null) {
            getString(stringResId)
        } else null
    }

    private fun takePicture() {
        val photoURI = createImageFileUri()
        takePicture.launch(photoURI)
        viewModel.setImageUri(photoURI)
    }

    private fun createImageFileUri(): Uri {
        val image = File(requireContext().filesDir, "photo.jpg")
        return FileProvider.getUriForFile(
            requireContext(),
            "br.com.connectattoo.fileprovider",
            image
        )

    }

    private fun getFilePartFromUri(
        context: Context,
        uri: Uri,
    ): MultipartBody.Part {
        val file = createFileFromUri(context, uri)
        val mediaType = context.contentResolver.getType(uri)?.toMediaTypeOrNull()
        val requestBody = file.asRequestBody(mediaType)
        return MultipartBody.Part.createFormData("image", file.name, requestBody)
    }

    private fun createFileFromUri(context: Context, uri: Uri): File {
        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
        val tempFile = File.createTempFile("upload", null, context.cacheDir)
        inputStream?.use { input ->
            FileOutputStream(tempFile).use { output ->
                input.copyTo(output)
            }
        }
        return tempFile
    }

    private fun requestPermissionImages() {
        requestMediaImagesPermission(requireContext(), this, requireActivity()) { isGranted ->
            if (isGranted) {
                isMediaImagesPermissionGranted = true
            }
        }
    }

    private fun showSnackBarPermissionImages() {
        val snackbar = Snackbar.make(
            binding.ivPhotoClient,
            getString(R.string.you_have_denied_permission_for_photos_please_grant_permission_in_settings_to_continue),
            Snackbar.ANIMATION_MODE_SLIDE
        ).setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.orange))
        snackbar.setAction(getString(R.string.open_settings)) {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri = Uri.fromParts("package", context?.packageName, null)
            intent.data = uri
            startActivity(intent)
            snackbar.dismiss()
        }
        snackbar.show()
    }
}
