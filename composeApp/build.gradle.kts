import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.googleGmsGoogleServices)

}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    sourceSets {
        
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(projects.shared)
        }
    }
}

android {
    namespace = "org.ilya.project"
    compileSdk = libs.versions.android.compileSdk.get().toInt()


    defaultConfig {
        applicationId = "org.ilya.project"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {

    implementation(project(":authenticationservice"))
    implementation(project(":core"))

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

    debugImplementation(compose.uiTooling)
    implementation("org.ow2.asm:asm:9.7.1") // Пример добавления зависимости
    implementation("androidx.compose.ui:ui:1.7.8") // Пример версии
}

