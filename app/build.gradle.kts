plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")

}

android {
    namespace = "com.example.mymovie"
    compileSdk = 35


    // ... другие настройки ...

    buildFeatures {
        viewBinding = true
    }


    defaultConfig {
        applicationId = "com.example.mymovie"
        minSdk = 24
        targetSdk = 35
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
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }


    dependencies {
        implementation(libs.retrofit)
        implementation(libs.retrofit.gson)
        implementation(libs.okhttp.logging)

        // Coroutines
        implementation(libs.coroutines.android)

        // ViewModel и LiveData
        implementation(libs.dagger)
        kapt(libs.dagger.compiler)

        implementation(libs.androidx.viewbinding)
        implementation(libs.volley)
        implementation(libs.okhttp)
        implementation(libs.easypermissions)
        implementation(libs.play.services.location)
        implementation(libs.navigation.fragment.ktx)
        implementation(libs.picasso)
        implementation(libs.androidx.core.ktx)
        implementation(libs.androidx.appcompat)
        implementation(libs.material)
        implementation(libs.androidx.activity)
        implementation(libs.androidx.constraintlayout)
        testImplementation(libs.junit)
        androidTestImplementation(libs.androidx.junit)
        androidTestImplementation(libs.androidx.espresso.core)
    }
}