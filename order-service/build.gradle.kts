object Versions {
    val GRPC = "4.34.1"
    val GRPC_KOTLIN = "1.4.1"
    val GRPC_PROTO = "1.80.0"
}

plugins {
    kotlin("jvm")
    id("com.google.protobuf") version "0.10.0"
}

dependencies {
    implementation(project(":order"))

    implementation("org.springframework.boot:spring-boot-starter-web")

    implementation("org.springframework.boot:spring-boot-starter-actuator")

    implementation("org.springframework.boot:spring-boot-starter-jdbc")

    implementation("net.devh:grpc-server-spring-boot-starter:3.1.0.RELEASE")

    implementation("com.google.protobuf:protobuf-kotlin:${Versions.GRPC}")
    implementation("io.grpc:grpc-kotlin-stub:${Versions.GRPC_KOTLIN}")

    implementation("io.grpc:grpc-protobuf:${Versions.GRPC_PROTO}")

    implementation("io.grpc:grpc-stub:${Versions.GRPC_PROTO}")
    implementation("io.grpc:grpc-netty-shaded:${Versions.GRPC_PROTO}")

    runtimeOnly("org.postgresql:postgresql")
}

sourceSets{
    getByName("main"){
        java {
            srcDirs(
                "build/generated/source/proto/main/java",
                "build/generated/source/proto/main/kotlin"
            )
        }
    }
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:${Versions.GRPC}"
    }

    plugins {
        register("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:${Versions.GRPC_PROTO}"
        }
        register("grpckt") {
            artifact = "io.grpc:protoc-gen-grpc-kotlin:${Versions.GRPC_KOTLIN}:jdk8@jar"
        }
    }
    generateProtoTasks {
        all().forEach {
            it.plugins {
                register("grpc")
                register("grpckt")
            }
            it.builtins {
                register("kotlin")
            }
        }
    }
}