plugins {
    id("java")
}

group = "hyuse98.KeePassToKaspersky"
version = "v1"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:6.0.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "hyuse98.KeePassToKaspersky.Main"
    }
}

tasks.test {
    useJUnitPlatform()
}