import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

val libraryVersion          = "0.3.0"
val libraryNamespace        = "io.github.lucasferreiramachado"
val frameworkLibraryName    = "KAppDeeplink" // execute gradle clean after changed it
val libraryArtifact         = "kapp-deeplink"
val libraryPackage          = "${libraryNamespace}.replaceme.core"
val githubRepoName          = "kapp-deeplink"

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinxSerialization)
    id("com.vanniktech.maven.publish") version "0.28.0"
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
        publishLibraryVariants("release", "debug")
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = frameworkLibraryName
            isStatic = true
            binaryOption("bundleId", libraryPackage)
            binaryOption("bundleVersion", libraryVersion)
        }
    }
    
    jvm()

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser {
            val rootDirPath = project.rootDir.path
            val projectDirPath = project.projectDir.path
            commonWebpackConfig {
                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
                    static = (static ?: mutableListOf()).apply {
                        // Serve sources to debug inside browser
                        add(rootDirPath)
                        add(projectDirPath)
                    }
                }
            }
        }
    }
    
    sourceSets {
        commonMain.dependencies {
            implementation(libs.navigation.compose)
            implementation(libs.kotlinx.serialization)

            api(libs.kdeeplink)
            api(libs.kdeeplink.navigation.compose)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "io.github.lucasferreiramachado.kapp.deeplink.core"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}

publishing {
    repositories {
        maven {
            name = "githubPackages"
            url = uri("https://maven.pkg.github.com/lucasferreiramachado/${libraryArtifact}")
            credentials(PasswordCredentials::class)
        }
    }
}

mavenPublishing {
    // Define coordinates for the published artifact
    coordinates(
        groupId = libraryNamespace,
        artifactId = libraryArtifact,
        version = libraryVersion
    )

    // Configure POM metadata for the published artifact
    pom {
        name.set(libraryArtifact)
        description.set("")
        inceptionYear.set("2025")
        url.set("https://maven.pkg.github.com/lucasferreiramachado/${githubRepoName}")

        licenses {
            license {
                name.set("Apache License 2.0")
                url.set("https://github.com/lucasferreiramachado/${githubRepoName}/blob/main/LICENSE")
            }
        }

        // Specify developers information
        developers {
            developer {
                id.set("lucasferreiramachado")
                name.set("Lucas Ferreira Machado")
                url.set("https://github.com/lucasferreiramachado")
            }
        }

        // Specify SCM information
        scm {
            url.set("https://github.com/lucasferreiramachado/${githubRepoName}")
            connection.set("scm:git:git://github.com/lucasferreiramachado/${githubRepoName}.git")
        }
    }
}