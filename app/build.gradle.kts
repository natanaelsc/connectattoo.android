plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
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
            buildConfigField("String", "BASE_URL", "\"${System.getenv("API_URL") ?: "https://connectattoo-api-staging.up.railway.app/api/v1/"}\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
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
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation ("com.github.rtoshiro.mflibrary:mflibrary:1.0.0")

    implementation("androidx.navigation:navigation-fragment-ktx:2.5.3")
    implementation("androidx.navigation:navigation-ui-ktx:2.5.3")

    implementation ("com.github.bumptech.glide:glide:4.12.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0")

    implementation ("androidx.room:room-runtime:2.4.1")
    annotationProcessor ("androidx.room:room-compiler:2.4.1")
    implementation("androidx.cardview:cardview:1.0.0")

    //Data Store
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    //retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.6.4")
    implementation ("com.squareup.retrofit2:converter-gson:2.6.4")
    implementation ("com.squareup.retrofit2:converter-scalars:2.4.0")

    //Swipe Refresh
    implementation ("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")

    //gson
    implementation ("com.google.code.gson:gson:2.8.6")

    //glide
    implementation ("com.github.bumptech.glide:glide:4.12.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0")

    implementation ("de.hdodenhof:circleimageview:3.1.0")

}
