package br.com.connectattoo.ui.profile.tattoclientditprofile

import android.Manifest
import android.content.Context
import android.net.Uri
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import androidx.activity.result.ActivityResultLauncher
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
import br.com.connectattoo.utils.Constants
import br.com.connectattoo.utils.DataStoreManager
import br.com.connectattoo.utils.permissions.PermissionImage.shouldRequestPermission
import br.com.connectattoo.utils.showBottomSheetEditPhotoProfile
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
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

    private lateinit var fileChooserPermissionLauncher: ActivityResultLauncher<Array<String>>
    private val fileChooserPermissions = arrayOf(Manifest.permission.READ_MEDIA_IMAGES)
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTattooClientEditProfileBinding {
        return FragmentTattooClientEditProfileBinding.inflate(inflater, container, false)
    }

    override fun setupViews() {
        startChooserPermissionLauncher()
        getInitialInformationClientProfile()
        observerViewModel()
        setupListeners()
        observerAndValidateField()

    }

    private fun startChooserPermissionLauncher() {
        fileChooserPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissionResult ->
            val permissionsIdentified = permissionResult.all { it.key in fileChooserPermissions }
            val permissionsGrant = permissionResult.all { it.value }
            if (permissionsIdentified && permissionsGrant) {
                showBottomSheetProfilePhoto()
            } else {
                showSnackBarAlert(
                    getString(
                        R.string
                            .you_have_denied_permission_for_photos_please_grant_permission_in_settings_to_continue
                    )
                )
            }
        }
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


    private fun uploadProfileData() {
        val database = (requireActivity().application as ConnectattooApplication).database
        val clientProfileDao = database.tattooClientProfileDao()
        profileRepository = ProfileRepository(clientProfileDao)

        viewLifecycleOwner.lifecycleScope.launch {
            val token = DataStoreManager.getUserSettings(requireContext(), Constants.API_TOKEN)
            uploadProfilePhoto(token)
            updateClientProfile(profileRepository, token)
        }
    }

    private fun updateClientProfile(profileRepository: ProfileRepository, token: String) {
        val name = binding.etName.text.toString()
        //val email = binding.etClientEmail.text.toString()
        val userName = binding.etUserName.text.toString()
        val birthDate = binding.etBirthDate.unMasked

        val checkName = validateFields(name, binding.etName)
        val checkUserName = validateFields(userName, binding.etUserName)
        val checkBirthDate = validateBirthDate()
        if (checkName && checkUserName && checkBirthDate) {
            viewModel.updateClientProfile(
                profileRepository = profileRepository,
                token = token,
                name = name,
                username = userName,
                birthDate = birthDate
            )
        }


    }

    private fun uploadProfilePhoto(token: String) {
        val fileUri: Uri? = viewModel.imageUri.value
        val imagePart = fileUri?.let { getFilePartFromUri(requireContext(), it) }
        if (imagePart != null) {
            viewModel.uploadClientProfilePhoto(profileRepository, token, imagePart)
        }
    }

    private fun observerViewModel() {
        viewModel.message.observe(viewLifecycleOwner) { message ->
            if (message == "Sucesso no upload da foto de perfil" || message == "Sucesso na atualização do perfil") {
                findNavController().navigate(R.id.action_tattooClientEditProfileFragment_to_clientUserProfileFragment)
            }
        }
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiStateFlow.collect { uiState ->
                    when (uiState) {
                        TattooClientEditProfileViewModel.UiState.Success -> {
                            insertInformationTattooClientProfile()
                            binding.btnUpload.isEnabled = true
                        }

                        TattooClientEditProfileViewModel.UiState.Error -> {
                            binding.btnUpload.isEnabled = true
                            val message = viewModel.dataState.stateErrorDeleteImage
                            if (message != null) {
                                Log.i("ErrorDeleteImage", message)
                            }
                        }

                        TattooClientEditProfileViewModel.UiState.Loading -> {
                            binding.btnUpload.isEnabled = false
                        }

                        else -> {
                            binding.btnUpload.isEnabled = true
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
            etName.setText(viewModel.dataState.name)
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
            showBottomSheetProfilePhoto()

        }
        binding.btnUpload.setOnClickListener {
            validateEmail()
            uploadProfileData()
        }
    }

    private fun showBottomSheetProfilePhoto() {
        showBottomSheetEditPhotoProfile(
            onClickChooseLibrary = {
                if (shouldRequestPermission(fileChooserPermissions)) {
                    fileChooserPermissionLauncher.launch(fileChooserPermissions)
                } else {
                    getContent.launch("image/*")
                }
            },
            onClickTakePicture = {
                if (shouldRequestPermission(fileChooserPermissions)) {
                    fileChooserPermissionLauncher.launch(fileChooserPermissions)
                } else {
                    takePicture()
                }
            },
            enableBtnRemovePhoto = !viewModel.dataState.imageProfile.isNullOrEmpty(),
            onClickRemovePhoto = {
                removeClientPhoto()
            }
        )
    }

    private fun validateEmail() {
        val email = binding.etClientEmail.text.toString()
        val checkEmail = email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
        if (checkEmail) {
            setBackgroundValid(binding.etClientEmail)
        } else {
            setBackgroundInvalid(binding.etClientEmail)
        }
    }

    private fun validateFields(value: String, textInputEditText: TextInputEditText): Boolean {
        return if (value.isNotEmpty()) {
            setBackgroundValid(textInputEditText)
            true
        } else {
            setBackgroundInvalid(textInputEditText)
            false
        }
    }

    private fun validateBirthDate(): Boolean {
        val clientBirthDate = binding.etBirthDate.unMasked
        val check = validateDate(clientBirthDate)
        return if (check != null) {
            setBackgroundValid(binding.etBirthDate)
            true
        } else {
            setBackgroundInvalid(binding.etBirthDate)
            false
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

    private fun setBackgroundInvalid(editText: EditText) {
        editText.setBackgroundResource(R.drawable.bg_edit_input_invalid)
    }

    private fun setBackgroundValid(editText: EditText) {
        editText.setBackgroundResource(R.drawable.bg_text_input_layout_backgroud)
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
    ): MultipartBody.Part? {
        val mimeType = context.contentResolver.getType(uri)
        var filePart: MultipartBody.Part? = null

        mimeType?.let {
            if (isImageMimeType(it)) {
                val file = createFileFromUri(context, uri)
                val mediaType = it.toMediaTypeOrNull()
                val requestBody = file.asRequestBody(mediaType)
                filePart = MultipartBody.Part.createFormData(
                    "image",
                    viewModel.dataState.name.toString(),
                    requestBody
                )

            } else {
                showSnackBarAlert(getString(R.string.snack_please_select_an_image_in_JPEG_JPG_or_PNG_format))
            }
        }

        return filePart
    }

    private fun isImageMimeType(mimeType: String): Boolean {
        return mimeType.startsWith("image/jpeg") ||
            mimeType.startsWith("image/png") ||
            mimeType.startsWith("image/jpg")
    }

    private fun createFileFromUri(context: Context, uri: Uri): File {
        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
        val mimeType = context.contentResolver.getType(uri)
        val extension = when (mimeType) {
            "image/jpeg" -> ".jpeg"
            "image/png" -> ".png"
            "image/jpg" -> ".jpg"
            else -> ""
        }

        val tempFile = File.createTempFile(
            viewModel.dataState.name.toString(),
            extension,
            context.cacheDir
        )
        inputStream?.use { input ->
            FileOutputStream(tempFile).use { output ->
                input.copyTo(output)
            }
        }
        return tempFile
    }

    private fun showSnackBarAlert(
        title: String
    ) {
        val snackBar = Snackbar.make(
            binding.ivPhotoClient,
            title,
            Snackbar.ANIMATION_MODE_SLIDE
        ).setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.orange))
        snackBar.show()
    }

}
