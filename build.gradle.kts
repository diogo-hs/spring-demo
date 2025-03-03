import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES

// versions referenced from "libs" catalog at "dependencies.gradle.kts"
buildscript {
	repositories {
		mavenCentral()
		maven {
			url = uri("https://plugins.gradle.org/m2/")
		}
	}

	dependencies {
        classpath(libs.kotlin.allopen)
        classpath(libs.kotlin.gradle.plugin)
        classpath(libs.kotlin.noarg)
        classpath(libs.module.dependency.graph)
        classpath(libs.spring.boot.gradle)
        classpath(libs.task.tree)
	}
}

plugins {
	java
	jacoco
    kotlin("jvm") version libs.versions.kotlin
    kotlin("plugin.spring") version libs.versions.kotlin
    kotlin("plugin.jpa") version libs.versions.kotlin

    alias(libs.plugins.asciidoctor)
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.task.tree)
    alias(libs.plugins.module.dependency.graph)
}

apply(plugin = "org.jetbrains.kotlin.jvm")
apply(plugin = "org.jetbrains.kotlin.plugin.allopen")
apply(plugin = "org.jetbrains.kotlin.plugin.jpa")
apply(plugin = "org.jetbrains.kotlin.plugin.spring")

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
	google()
	maven {
		url = uri("https://prepo.sumoci.net/artifactory/libs-release-local")
	}
}

extra["snippetsDir"] = file("build/generated-snippets")
extra["testcontainersVersion"] = "1.17.2"

dependencies {
    implementation(enforcedPlatform(BOM_COORDINATES))
    testImplementation(enforcedPlatform(BOM_COORDINATES))
    annotationProcessor(enforcedPlatform(BOM_COORDINATES))
    testAnnotationProcessor(enforcedPlatform(BOM_COORDINATES))

    annotationProcessor(libs.lombok)

    implementation(libs.guava)
    implementation(libs.jackson.module.kotlin)
    implementation(libs.jsoup)
    implementation(libs.kotlin.stdlib.jdk8)
    implementation(libs.lombok)
    implementation(libs.orika)
    implementation(libs.aws.s3)
    implementation(libs.spring.boot.starter.data.jpa)
    implementation(libs.spring.boot.starter.validation)
    implementation(libs.spring.boot.starter.web)
    implementation(libs.vavr)
    implementation(libs.java.inject)
    implementation(libs.log4j)

    runtimeOnly(libs.groovy)
    runtimeOnly(libs.mysql.connector.java)

    testAnnotationProcessor(libs.lombok)

    testImplementation(libs.archunit)
    testImplementation(libs.bytebuddy)
    testImplementation(libs.junit.vintage) {
        exclude(group = "org.hamcrest", module = "hamcrest-core")
    }
    testImplementation(libs.mockito.inline)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.pact.jvm.spring)
    testImplementation(libs.rest.assured)
    testImplementation(libs.rest.assured.json.path)
    testImplementation(libs.rest.assured.kotlin)
    testImplementation(libs.spring.boot.starter.test)
    testImplementation(libs.spring.restdocs.core)
    testImplementation(libs.spring.restdocs.restassured)
    testImplementation(libs.wiremock)
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
