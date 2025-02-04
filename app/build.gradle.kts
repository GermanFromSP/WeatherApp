@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.parcelize)
}

android {
    namespace = "com.bignerdranch.android.weatherapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.bignerdranch.android.weatherapp"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        val key = property("apiKey")?.toString() ?: error(
            "You should add apikey into gradle.properties"
        )
        buildConfigField("String", "WEATHER_API_KEY", "\"$key\"")
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
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.10"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.accompanist.insets)
    implementation(libs.accompanist.permissions)
    implementation(libs.accompanist.systemui)

    implementation (libs.androidx.lifecycle.viewmodel.ktx)

    implementation (libs.datastore.rxjava2)

    implementation (libs.androidx.lifecycle.viewmodel.compose)

    implementation(libs.play.services.location)

    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)

    implementation (libs.androidx.fragment.ktx)

    implementation(libs.kotlinx.serialization)
    implementation(libs.datastore.rxjava2)

    implementation(libs.mvikotlin.core)
    implementation(libs.mvikotlin.main)
    implementation(libs.mvikotlin.coroutine)

    implementation(libs.decompose.core)
    implementation(libs.decompose.jetpack)

    implementation(libs.room.core)

    implementation(libs.androidsvg)
    implementation(libs.androidx.datastore.core.android)
    implementation(libs.androidx.runtime.livedata)

    ksp(libs.room.compiler)

    implementation(libs.dagger.core)
    ksp(libs.dagger.compiler)

    implementation(libs.glide)

    implementation(libs.retrofit.core)
    implementation(libs.retrofit.converter)
    implementation(libs.interceptor)
    implementation(libs.icons)

    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
}