plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id ("kotlin-kapt")
    id ("com.google.dagger.hilt.android")
    id("com.google.gms.google-services")
}

android {

    namespace = "com.example.travelsouvenir"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.travelsouvenir"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation ("com.google.code.gson:gson:2.10.1")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.6.2")
    implementation ("io.coil-kt:coil-compose:2.2.1")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.2")
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.2")
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.play.services.location)
    implementation(libs.androidx.room.common)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.junit.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    //HILT
    implementation ("com.google.dagger:hilt-android:2.51.1")
    kapt ("com.google.dagger:hilt-compiler:2.51.1")
    implementation ("androidx.hilt:hilt-navigation-compose:1.0.0")
    //ROOM
    val room_version = "2.6.1"

    implementation ("androidx.room:room-runtime:$room_version")
    kapt("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")
    //pager
    implementation ("com.google.accompanist:accompanist-pager:0.36.0")
    implementation ("com.google.accompanist:accompanist-pager-indicators:0.36.0")
    //TESTING
    testImplementation ("org.mockito:mockito-core:5.0.0")
    testImplementation ("org.mockito.kotlin:mockito-kotlin:5.0.0")
    androidTestImplementation ("org.mockito:mockito-android:5.0.0")
    androidTestImplementation ("androidx.test.ext:junit:1.1.5")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.1")
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.1")
    testImplementation ("org.robolectric:robolectric:4.10")
    implementation ("ch.qos.logback:logback-classic:1.2.3")
    testImplementation ("io.mockk:mockk:1.13.4")
    testImplementation ("androidx.arch.core:core-testing:2.1.0")
    //FIREBASE
    implementation(platform("com.google.firebase:firebase-bom:33.5.0"))
    implementation("com.google.firebase:firebase-messaging")
}

kapt {
    correctErrorTypes=true
}
