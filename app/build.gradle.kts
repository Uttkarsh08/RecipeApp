plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    kotlin("kapt")
}

android {
    namespace = "com.example.recipeapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.recipeapp"
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
    buildFeatures {
        compose = true
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
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation("androidx.compose.material:material:1.7.5") // Replace with actual version of libs.androidx.material
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.7") // Replace with actual version of libs.androidx.lifecycle.viewmodel.ktx
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.7") // Replace with actual version of libs.androidx.lifecycle.viewmodel.compose

    implementation("com.squareup.retrofit2:retrofit:2.11.0") // Replace with actual version of libs.retrofit
    implementation("com.squareup.retrofit2:converter-gson:2.11.0") // Replace with actual version of libs.converter.gson

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.9.0") // Replace with actual version of libs.kotlinx.coroutines.android
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0") // Replace with actual version of libs.kotlinx.coroutines.core

// Room dependencies
    implementation("androidx.room:room-runtime:2.6.1") // Replace with actual version of libs.androidx.room.runtime
    annotationProcessor("androidx.room:room-compiler:2.6.1") // Replace with actual version of libs.androidx.room.compiler
    kapt("androidx.room:room-compiler:2.6.1") // Replace with actual version of libs.androidx.room.compiler
    implementation("androidx.room:room-ktx:2.6.1") // Replace with actual version of libs.androidx.room.ktx

    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.7") // Replace with actual version of libs.androidx.lifecycle.runtime.compose

    implementation("androidx.compose.material:material-icons-extended:1.7.5") // Replace with actual version of libs.androidx.material.icons.extended
    implementation("org.jetbrains.kotlin:kotlin-script-runtime:2.0.20") // Replace with actual version of kotlin("script-runtime")

    implementation("io.coil-kt:coil-compose:2.7.0") // Replace with actual version of libs.coil.compose

    implementation("androidx.paging:paging-runtime-ktx:3.3.4")
    implementation("androidx.navigation:navigation-compose:2.8.4")

    implementation("androidx.paging:paging-compose:3.3.4")
}
