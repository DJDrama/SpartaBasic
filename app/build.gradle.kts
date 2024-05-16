import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
}

android {

    namespace = "com.spartabasic.www"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.spartabasic.www"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        val gradleLocalProperties = gradleLocalProperties(
            projectRootDir = rootDir,
            providers = providers
        )
        val apiKey = gradleLocalProperties.getProperty("REST_API_KEY")
        val baseUrl = gradleLocalProperties.getProperty("KAKAO_BASE_URL")
        debug {
            buildConfigField("String", "REST_API_KEY", "\"$apiKey\"")
            buildConfigField("String", "KAKAO_BASE_URL", "\"$baseUrl\"")
            isDebuggable = true
            isMinifyEnabled = false
        }
        release {
            buildConfigField("String", "REST_API_KEY", "\"$apiKey\"")
            buildConfigField("String", "KAKAO_BASE_URL", "\"$baseUrl\"")
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
        buildConfig = true
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

    // implementation("androidx.fragment:fragment-ktx:1.2.0")
    implementation(libs.androidx.fragment.ktx)

    implementation(libs.retrofit)
    implementation(platform(libs.okHttpBom))
    implementation(libs.logging.interceptor)
    implementation(libs.converter.moshi)
    implementation(libs.moshi.kotlin)
    implementation(libs.moshi.adapters)
}