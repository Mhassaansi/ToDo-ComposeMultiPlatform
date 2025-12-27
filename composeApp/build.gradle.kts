import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    //alias(libs.plugins.composeHotReload)
    alias(libs.plugins.ksp)
    alias(libs.plugins.androidx.room)
    alias(libs.plugins.kotlinx.serialization)
}

repositories {
    google()
    mavenCentral()
}

kotlin {

    sourceSets.commonMain {
        // This allows the IDE to see the generated code for all platforms
        kotlin.srcDir("build/generated/ksp/metadata/kotlin")
    }

    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
            linkerOpts.add("-lsqlite3")
        }
    }

    jvm()

    sourceSets {
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation(compose.material)
            implementation(libs.koin.android)
            implementation(libs.koin.androidx.compose)
        }

        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.materialIconsExtended)
            implementation(compose.material)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.kotlinx.datetime)

            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation(libs.navigation.compose)
            implementation(libs.androidx.room.runtime)
            implementation(libs.androidx.sqlite.bundled)
            implementation(libs.androidx.annotation)

            implementation(libs.koin.core)
            implementation(libs.koin.compose.viewmodel)
           // implementation(libs.androidx.room.runtime.jvm)
        }
        iosMain.dependencies {}
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        jvmMain.dependencies {
            implementation(compose.desktop.currentOs) {
                exclude(group = "org.jetbrains.skiko", module = "skiko-awt")
            }
            implementation("org.jetbrains.skiko:skiko-awt:0.9.4.2")
            implementation(libs.kotlinx.coroutinesSwing)
        }
    }
}

room {
    schemaDirectory("$projectDir/schemas")
}

android {
    namespace = "com.ubl.todolist"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.ubl.todolist"
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    dependencies {
        debugImplementation(compose.uiTooling)
    }
}


dependencies {
    val roomCompiler = libs.androidx.room.compiler

    add("kspAndroid", roomCompiler)
    add("kspJvm", roomCompiler)


    add("kspIosX64", roomCompiler)
    add("kspIosArm64", roomCompiler)
    add("kspIosSimulatorArm64", roomCompiler)


    add("kspCommonMainMetadata", roomCompiler)
}

ksp {
    arg("room.generateKotlin", "true")
}

compose.desktop {
    application {
        mainClass = "com.ubl.todolist.MainKt"


        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.ubl.todolist"
            packageVersion = "1.0.0"
        }
    }
}
configurations.all {
    resolutionStrategy {
        force("org.jetbrains.skiko:skiko-awt:0.9.4.2")
    }
}

// Source - https://stackoverflow.com/a
// Posted by SwHeroCat, modified by community. See post 'Timeline' for change history
// Retrieved 2025-12-27, License - CC BY-SA 4.0

/*tasks.withType<com.google.devtools.ksp.gradle.KspAATask>().configureEach {
    // This ensures that the resource generation task runs before KSP
    dependsOn(
        // Android
        "generateActualResourceCollectorsForAndroidMain",
        "generateResourceAccessorsForAndroidMain",
        "generateActualResourceCollectorsForAndroidMain",
        "generateComposeResClass",
        "generateResourceAccessorsForCommonMain",
        "generateExpectResourceCollectorsForCommonMain",
        "generateResourceAccessorsForAndroidDebug",
        // iOS
        "generateResourceAccessorsForIosArm64Main",
        "generateActualResourceCollectorsForIosArm64Main",
        "generateResourceAccessorsForIosMain",
        "generateResourceAccessorsForAppleMain",
        "generateResourceAccessorsForNativeMain",
    )
}*/




