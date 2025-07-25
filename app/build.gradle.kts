plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.oliohtdarts"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.oliohtdarts"
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
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.recyclerview)
    implementation(libs.cardview)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation ("com.fasterxml.jackson.core:jackson-core:2.16.0")
    implementation ("com.fasterxml.jackson.core:jackson-annotations:2.16.0")
    implementation ("com.fasterxml.jackson.core:jackson-databind:2.16.0")
    implementation ("com.android.volley:volley:1.2.1")
}