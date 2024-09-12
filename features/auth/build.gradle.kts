import utils.addBiometric
import utils.addRetrofitDependencies

plugins {
    alias(libs.plugins.androidDynamicFeature)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.navigationSafeargsKotlin)
    alias(libs.plugins.comGoogleDevtoolsKsp)
    alias(libs.plugins.comGoogleDaggerHiltAndroid)
}

android {
    namespace = "com.davega.auth"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
}

dependencies {
    implementation(project(":app"))
    implementation(project(":domain"))
    implementation(project(":data"))
    implementation(project(":ui"))

    // hilt
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation.compose)
    ksp(libs.hilt.compiler)

    addRetrofitDependencies()
    addBiometric()

    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    debugImplementation(libs.androidx.ui.tooling)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation)
    implementation(libs.androidx.icons)
    implementation(libs.androidx.constraint)

    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)


    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.annotation)
}