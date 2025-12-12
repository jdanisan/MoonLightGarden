// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    dependencies {
        // Plugin de Google Services para inicializar Firebase
        classpath("com.google.gms:google-services:4.4.2")
    }
}

plugins {
    // Plugins comunes que se aplican en los módulos
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.android.library) apply false

    // Google Services plugin (se aplica en el módulo app, no aquí)
    id("com.google.gms.google-services") version "4.4.2" apply false
}
