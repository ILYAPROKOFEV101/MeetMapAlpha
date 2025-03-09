plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
}

android {
    namespace = "com.example.authenticationservice"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.authenticationservice"
        minSdk = 25
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    // fierbase dependencies
    implementation("io.coil-kt:coil-compose:2.4.0")
    implementation(platform("com.google.firebase:firebase-bom:33.1.1")) // Firebase Bill of Materials (BOM)
    implementation("com.google.firebase:firebase-auth-ktx") // Firebase Authentication (Kotlin)
    implementation("com.google.android.gms:play-services-auth:21.2.0") // Google Play Services Auth
    implementation("androidx.compose.material3:material3:1.2.1") // Замените на актуальную версию


    implementation (libs.androidx.core.ktx)
    implementation ("androidx.lifecycle:lifecycle-runtime-compose-android:2.9.0-alpha10")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose-android:2.9.0-alpha07")
    implementation ("androidx.navigation:navigation-compose:2.9.0-alpha06")


    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}