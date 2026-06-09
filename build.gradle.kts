import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension

plugins {
	java
	kotlin("jvm") version "2.3.20"
	kotlin("kapt") version "2.3.20"
	kotlin("plugin.spring") version "2.3.20"
	kotlin("plugin.jpa") version "2.3.20"
	id("org.springframework.boot") version "3.5.14"
	id("io.spring.dependency-management") version "1.1.7"
	id("org.sonarqube") version "5.1.0.4882"
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
	apply(plugin = "jacoco")

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
		testImplementation("org.springframework.boot:spring-boot-starter-test")
		testImplementation("io.mockk:mockk:1.13.10")
	}

	tasks.withType<Test>().configureEach {
		useJUnitPlatform()
		finalizedBy(tasks.matching { it.name == "jacocoTestReport" })
	}
	tasks.withType<org.gradle.testing.jacoco.tasks.JacocoReport>().configureEach {
		dependsOn(tasks.withType<Test>())
		reports { xml.required.set(true) }
	}

	tasks.getByName<Jar>("jar") {
		enabled = true
	}
}

sonar {
	properties {
		property("sonar.host.url", "https://sonarcloud.io")
		property("sonar.organization", "kjylab")
		property("sonar.projectKey", "kjylab_my-msa-order")
		property("sonar.qualitygate.wait", "false")
		property("sonar.coverage.jacoco.xmlReportPaths", "**/build/reports/jacoco/test/jacocoTestReport.xml")
		property("sonar.exclusions", "**/generated/**, **/build/**")
	}
}
