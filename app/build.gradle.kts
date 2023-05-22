import org.jetbrains.kotlin.storage.CacheResetOnProcessCanceled.enabled

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.hilt.gradle)
    alias(libs.plugins.kotlinter)
}

android {
    namespace = "denise.mendez.meli"
    compileSdk = 33

    defaultConfig {
        applicationId = "denise.mendez.meli"
        minSdk = 26
        targetSdk = 33
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

}

dependencies {
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(projects.data)
    implementation(projects.domain)
    implementation(libs.moshi.converter)
    implementation(libs.moshi.codegen)
    implementation(libs.moshi.adapters)
    implementation(libs.moshi.kotlin)
    implementation(libs.okhttp.okhttp)
    implementation(libs.okhttp.urlconnection)
    implementation(libs.okhttp.interceptor)
    implementation(libs.okhttp.tls)
    implementation(libs.moshi.converter.factory)
    implementation(libs.retrofit2)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)

}

kapt {
    correctErrorTypes = true
}