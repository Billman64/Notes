import java.util.regex.Pattern.compile

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("kotlin-kapt")
}

android {
    namespace = "com.github.billman64.notes"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.github.billman64.notes"
        minSdk = 23
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // ViewModel
    val lifecycle_version = "2.3.1"
//    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")

    // Fragment
    implementation("androidx.fragment:fragment-ktx:1.6.2")

    // Dagger
    implementation("com.google.dagger:dagger:2.51")
    annotationProcessor("com.google.dagger:dagger-compiler:2.51")
    
    // JUnit
    androidTestImplementation("junit:junit:4.13.2")

    // Mockito
    testImplementation("junit:junit:4.12")
    testImplementation("org.mockito:mockito-core:3.11.2")
    androidTestImplementation("org.mockito-inline:3.11.2")

    // Mockk
    val mockkVersion = "1.13.10"
    testImplementation("io.mockk:mockk-android:${mockkVersion}")
    testImplementation("io.mockk:mockk-agent:${mockkVersion}")

}