
plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.doan"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.doan"
        minSdk = 33
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures{
        dataBinding = true
    }
}

dependencies {
    implementation ("com.cepheuen.elegant-number-button:lib:1.0.2")

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-analytics-ktx:21.5.0")
    implementation("com.google.firebase:firebase-analytics:21.5.0")
    implementation("com.google.firebase:firebase-auth:22.2.0")
    implementation("com.google.firebase:firebase-database:20.3.0")

    implementation("com.google.firebase:firebase-firestore:24.9.1")
    implementation("com.google.firebase:firebase-firestore-ktx:24.9.1")
    implementation("com.google.firebase:firebase-storage:20.3.0")
    implementation("com.google.firebase:firebase-functions:20.4.0")
    implementation("com.google.firebase:firebase-auth-ktx:22.2.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    //noinspection BomWithoutPlatform
    implementation("com.google.firebase:firebase-bom:32.5.0")
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.android.gms:play-services-auth:20.7.0")
    implementation("com.google.gms:google-services:4.3.0")

    /// load from url ////
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    /// Circle ImageView ////
    implementation ("de.hdodenhof:circleimageview:3.1.0")
    /// lotie///
    implementation ("com.airbnb.android:lottie:5.2.0")
    ///retrofit///
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    //viewmodel LiveData
    implementation ("androidx.lifecycle:lifecycle-viewmodel")
    implementation ("androidx.lifecycle:lifecycle-livedata")
    //// elegant number button////
    implementation ("com.cepheuen.elegant-number-button:lib:1.0.2")
    //// picasso
    implementation ("com.squareup.picasso:picasso:2.71828")


}