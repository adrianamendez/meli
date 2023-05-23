import org.jetbrains.kotlin.storage.CacheResetOnProcessCanceled.enabled

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.hilt.gradle)
    alias(libs.plugins.kotlinter)
    alias(libs.plugins.androidx.navigation.args)
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

    packagingOptions {
        resources.excludes.add("META-INF/*")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        dataBinding = true
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
    implementation(libs.lottie)
    implementation(libs.androidx.material)
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.glide)

    implementation(libs.mockito.kotlin)
    implementation(libs.coroutines.test)
    implementation(libs.junit)
    implementation(libs.io.mockk)
    implementation(libs.kotlinx.coroutines.test)
    implementation(libs.androidx.test.core)
    implementation(libs.mockito.core)
    implementation(libs.androidx.test.runner)
    implementation(libs.core.testing)
    implementation(libs.mockitokotlin2)
    implementation(libs.kotlin.reflect)

}

kapt {
    correctErrorTypes = true
}