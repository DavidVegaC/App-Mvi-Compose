import utils.addDataStorageDependencies
import utils.addKotlinCoroutinesDependencies
import utils.addRetrofitDependencies
import utils.addSecurity
import utils.addTimberDependencies

plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.comGoogleDevtoolsKsp)
    alias(libs.plugins.comGoogleDaggerHiltAndroid)
}

android {
    namespace = "com.davega.data"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        getByName("debug"){
            buildConfigField(
                type = "String",
                name = "BASE_URL",
                value = "\"https://b74c7b25-2c11-4eb9-89b1-cb0009f1710b.mock.pstmn.io\""
            )
        }

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

    android.buildFeatures.buildConfig = true
}

dependencies {

    // hilt
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation.compose)
    ksp(libs.hilt.compiler)

    addKotlinCoroutinesDependencies()
    addRetrofitDependencies()
    addTimberDependencies()
    addDataStorageDependencies()
    addSecurity()

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    api(project(":domain"))
}