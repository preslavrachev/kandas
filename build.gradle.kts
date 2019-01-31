import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "com.preslavrachev"
version = "0.1-SNAPSHOT"

buildscript {
    var kotlin_version: String by extra
    kotlin_version = "1.2.21"

    repositories {
        mavenCentral()
    }
    
    dependencies {
        classpath(kotlinModule("gradle-plugin", kotlin_version))
    }
    
}

apply {
    plugin("kotlin")
}

val kotlin_version: String by extra

repositories {
    mavenCentral()
}

dependencies {
    compile(kotlinModule("stdlib-jdk8", kotlin_version))
    testCompile("org.junit.jupiter:junit-jupiter-api:5.3.0")
    testCompile("com.willowtreeapps.assertk:assertk-jvm:0.13")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

