import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
}

val localProperties = Properties().apply {
    val localPropertiesFile = rootProject.file("local.properties")
    if (localPropertiesFile.exists()) {
        load(localPropertiesFile.inputStream())
    }
}

val weatherApiKey = localProperties.getProperty("OPENWEATHER_API_KEY")

android {
    namespace = "com.example.weatherapp"
    compileSdk {
        version = release(37)

        buildFeatures {
            buildConfig = true
        }
    }

    defaultConfig {
        applicationId = "com.example.weatherapp"
        minSdk = 24
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField(
            "String",
            "OPENWEATHER_API_KEY",
            "\"$weatherApiKey\""
        )
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.core.ktx)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.12.0")

   // Moshi Converter
    implementation("com.squareup.retrofit2:converter-moshi:2.12.0")

   // Moshi Kotlin Reflection
    implementation("com.squareup.moshi:moshi-kotlin:1.15.2")

   // Glide
    implementation("com.github.bumptech.glide:glide:4.16.0")
}