import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension

plugins {
	java
	kotlin("jvm") version "2.3.20"
	kotlin("kapt") version "2.3.20"
	kotlin("plugin.spring") version "2.3.20"
	kotlin("plugin.jpa") version "2.3.20"
	id("org.springframework.boot") version "3.3.0"
	id("io.spring.dependency-management") version "1.1.7"
}

kotlin {
	jvmToolchain(21)
}

subprojects {
	apply(plugin = "java")
	apply(plugin = "org.jetbrains.kotlin.jvm")
	apply(plugin = "org.jetbrains.kotlin.plugin.spring")
	apply(plugin = "org.springframework.boot")
	apply(plugin = "io.spring.dependency-management")

	java {
		toolchain {
			languageVersion.set(JavaLanguageVersion.of(21))
		}
	}

	configure<JavaPluginExtension> {
		toolchain {
			languageVersion.set(JavaLanguageVersion.of(21))
		}
	}

	configure<KotlinJvmProjectExtension> {
		compilerOptions {
			freeCompilerArgs.addAll("-Xjsr305=strict", "-Xannotation-default-target=param-property")
		}
	}

	dependencies {
		implementation("org.jetbrains.kotlin:kotlin-reflect")
		implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
		compileOnly("org.projectlombok:lombok")
		annotationProcessor("org.projectlombok:lombok")
	}

	tasks.getByName<Jar>("jar") {
		enabled = true
	}
}