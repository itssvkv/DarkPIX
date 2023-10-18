plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.itssvkv.darkpix"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.itssvkv.darkpix"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
    }
    dataBinding{
        enable = true
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //Retrofit
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.2")
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.2")
    //Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    //glide
    implementation("com.github.bumptech.glide:glide:4.16.0")
    implementation ("jp.wasabeef:glide-transformations:4.3.0")
    // If you want to use the GPU Filters
    implementation ("jp.co.cyberagent.android:gpuimage:2.1.0")
    //hilt
    implementation("com.google.dagger:hilt-android:2.44")
    kapt("com.google.dagger:hilt-compiler:2.44")
    implementation("androidx.hilt:hilt-navigation-fragment:1.0.0")
    kapt("org.jetbrains.kotlinx:kotlinx-metadata-jvm:0.5.0")

//    kapt ("com.google.dagger:hilt-android-compiler:2.44")
    //annotationProcessor
    //viewModel
    val lifecycle_version = "2.6.2"
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")
    //navigation
    val nav_version = "2.7.4"
    implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation("androidx.navigation:navigation-ui-ktx:$nav_version")

    //recycler
    implementation("androidx.recyclerview:recyclerview:1.3.1")

    //shared pref
    implementation("androidx.preference:preference-ktx:1.2.1")

    //viewPager2
    implementation("androidx.viewpager2:viewpager2:1.0.0")
    // dotIndicator
    implementation("com.tbuonomo:dotsindicator:5.0")

    //BottomNavigation
    implementation("np.com.susanthapa:curved_bottom_navigation:0.6.5")

    //paging
    val paging_version = "3.2.1" //current version at the time

    implementation("androidx.paging:paging-runtime-ktx:$paging_version")

    // alternatively - without Android dependencies for tests
    testImplementation("androidx.paging:paging-common-ktx:$paging_version")


}
hilt {
    enableAggregatingTask = true
}
