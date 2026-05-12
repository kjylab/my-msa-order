plugins {
    kotlin("jvm")
    kotlin("kapt")
    kotlin("plugin.jpa")
}


dependencies {
    implementation("com.github.kjylab:my-msa-common:v1.+")

    implementation("org.springframework.boot:spring-boot-starter-web")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    implementation("org.springframework.kafka:spring-kafka")

    runtimeOnly("com.h2database:h2")

    implementation("com.querydsl:querydsl-jpa:5.1.0:jakarta")
}

sourceSets {
    main {
        kotlin.srcDir("build/generated/source/kapt/main")
    }
}