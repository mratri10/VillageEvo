plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.example.villageevo"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.example.villageevo"
        minSdk = 24
        targetSdk = 36
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
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.navigation.compose)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    implementation(libs.kotlinx.collections.immutable)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    // Pastikan versi ini ada (minimal 1.5.0 untuk Material 3)
    implementation("com.google.android.material:material:1.11.0")

    // Untuk Compose Material 3
    implementation("androidx.compose.material3:material3")
    implementation("com.github.doyaaaaaken:kotlin-csv-jvm:1.9.2")

    val roomVersion = "2.6.1"
    // Library utama Room
    implementation("androidx.room:room-runtime:$roomVersion")
    // Support untuk Kotlin Coroutines & Flow (Sangat penting untuk MVVM)
    implementation("androidx.room:room-ktx:$roomVersion")

    // Compiler untuk memproses anotasi (Entity, Dao, dll)
    // Compiler untuk memproses anotasi (Entity, Dao, dll)
    ksp("androidx.room:room-compiler:$roomVersion")
}