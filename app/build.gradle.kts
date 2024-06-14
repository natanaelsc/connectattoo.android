plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id ("kotlin-kapt")
}

apply(from = "../config/detekt/detekt.gradle")


android {
    namespace = "br.com.connectattoo"
    compileSdk = 34

    defaultConfig {
        applicationId = "br.com.connectattoo"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "BASE_URL", "\"${System.getenv("API_URL")}\"")
        }
        debug {
            buildConfigField("String", "BASE_URL", "\"${System.getenv("API_URL") ?: "http://localhost:3000/api/v1/"}\"")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        buildConfig = true
        viewBinding = true
    }

    buildToolsVersion = "34.0.0"
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.android.gms:play-services-tasks:18.1.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation ("com.github.rtoshiro.mflibrary:mflibrary:1.0.0")

    implementation("androidx.navigation:navigation-fragment-ktx:2.5.3")
    implementation("androidx.navigation:navigation-ui-ktx:2.5.3")

    //glide
    implementation ("com.github.bumptech.glide:glide:4.12.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.12.0")


    implementation("androidx.cardview:cardview:1.0.0")

    //Data Store
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    //retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.6.4")
    implementation ("com.squareup.retrofit2:converter-gson:2.6.4")
    implementation ("com.squareup.retrofit2:converter-scalars:2.4.0")
    implementation ("com.squareup.okhttp3:okhttp:4.9.0")

    //Swipe Refresh
    implementation ("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")

    //gson
    implementation ("com.google.code.gson:gson:2.8.6")


    implementation ("de.hdodenhof:circleimageview:3.1.0")

    // GSM Location
    implementation ("com.google.android.gms:play-services-location:21.2.0")

    //Room DB
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")

    //Masks
    implementation ("com.github.santalu:maskara:1.0.0")

    //Flexbox
    implementation ("com.google.android.flexbox:flexbox:3.0.0")
}
kapt {
    correctErrorTypes = true
    useBuildCache = true
}
