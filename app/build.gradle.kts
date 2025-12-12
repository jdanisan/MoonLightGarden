plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.moonlightgarden"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.moonlightgarden"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        buildConfigField("String", "WEATHER_API_KEY", "\"6fb0876a5d8144f6b30100754252205\"")
        manifestPlaceholders["ASTRONOMY_API_KEY"] = "6fb0876a5d8144f6b30100754252205"

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
        buildConfig = true
    }
}

dependencies {
    // AndroidX + Compose
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // UI
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.cardview:cardview:1.0.0")

    // Navigation
    implementation("androidx.navigation:navigation-fragment:2.6.0")
    implementation("androidx.navigation:navigation-ui:2.6.0")
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    // Firebase (usando BOM para alinear versiones)
    implementation(platform("com.google.firebase:firebase-bom:33.13.0"))
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-database")
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-common")

    // Google Play Services
    implementation("com.google.android.gms:play-services-auth:20.7.0")
    implementation("com.google.android.gms:play-services-safetynet:18.0.1") // o Play Integrity API

    // Networking
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.picasso:picasso:2.8")
    implementation(libs.volley)

    // Charts
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")

    // Tests
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
