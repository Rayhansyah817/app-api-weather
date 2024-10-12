plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.main.appweather"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.main.appweather"
        minSdk = 24
        targetSdk = 34
        versionCode = 2
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            buildConfigField("String", "BASE_URL", "\"https://api.weatherapi.com/v1/\"")
            buildConfigField("String", "API_KEY", "\"bf0b3703f9a54994a4e45249240710\"")
        }
        release {
            buildConfigField("String", "BASE_URL", "\"https://api.weatherapi.com/v1/\"")
            buildConfigField("String", "API_KEY", "\"bf0b3703f9a54994a4e45249240710\"")

            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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
        viewBinding = true
        buildConfig = true
        dataBinding = true
    }
}

dependencies {

    // Retrofit
    implementation (libs.retrofit)

    // Converter for JSON (Gson)
    implementation (libs.converter.gson)

    // Rename Response Api
    implementation (libs.gson)

    // OkHttp
    implementation (libs.okhttp)

    // Logging Interceptor
    implementation (libs.logging.interceptor)

    // Coroutines
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.0")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")

    implementation ("com.github.bumptech.glide:glide:4.15.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.15.0")

    // Gps
    implementation(libs.play.services.location)

    // Swipe Refresh
    implementation ("androidx.swiperefreshlayout:swiperefreshlayout:1.2.0-alpha01")

    // Lifecycle
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.5.1") //liveData
    implementation ("androidx.room:room-ktx:2.4.3")


//    // Koin Module
//    implementation ("io.insert-koin:koin-android:3.4.0")
//    implementation ("io.insert-koin:koin-androidx-viewmodel:2.2.2")
//    implementation ("io.insert-koin:koin-core:3.4.0")
//
//    implementation ("androidx.fragment:fragment-ktx:1.5.7")
//    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.0")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}