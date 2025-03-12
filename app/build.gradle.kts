plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlinAndroidKsp)
    alias(libs.plugins.hiltAndroid)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.google.services)
    alias(libs.plugins.google.jupiter.plugin)
}

kotlin {
    jvmToolchain(21)
}

android {
    namespace = "renard.remi.ping"
    compileSdk = 35


    buildFeatures {
        compose = true
        buildConfig = true
    }

    defaultConfig {
        applicationId = "renard.remi.ping"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            isDebuggable = true
            buildConfigField("String", "API_URL", "\"http://192.168.1.61:8080/api/\"")
        }
        release {
            isMinifyEnabled = false
            buildConfigField("String", "API_URL", "\"http://192.168.1.61:8080/api/prod/\"")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.navigation.compose)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.retrofit)
    implementation(libs.gson)
    implementation(libs.okHttp)
    implementation(libs.okHttpLogs)
    implementation(libs.datastore)
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation.compose)
    implementation(libs.biometric)
    implementation(platform(libs.firebaseBom))
    implementation(libs.firebase.messaging.ktx)
    implementation(libs.room.ktx)
    implementation(libs.room.runtime)

    // The first time you build this project you should use implementation instead of ksp,
    // then rebuild with ksp
    ksp(libs.hilt.compiler)
    ksp(libs.room.compiler)

    testImplementation(libs.jupiter)
    testImplementation(libs.mockito)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.mockk)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}