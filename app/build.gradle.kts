plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("org.jetbrains.kotlin.kapt")
}

apply(from = "../ktlint.gradle")

android {
    namespace = "com.example.meal_planners_app"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.meal_planners_app"
        minSdk = 24
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
        buildConfig = true
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.timber)

    //RxJava
    implementation(libs.rxjava)
    implementation(libs.rxjava.android)

    //Dagger
    implementation(libs.dagger)
    kapt(libs.dagger.compiler)

    //Navigation
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)

    implementation(project(":data:recipe"))
    implementation(project(":data:product"))
    implementation(project(":data:category"))
    implementation(project(":data:mealtime"))
    implementation(project(":feature:library"))
    implementation(project(":feature:weekplan"))
    implementation(project(":feature:search"))
    implementation(project(":feature:shoppinglist"))
    implementation(project(":core:utils"))
    implementation(project(":core:database"))
    implementation(project(":common:ui"))
}