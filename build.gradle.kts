plugins {
    id("java")
    id("application")
}

group = "org.example"
version = "1.0"

application {
    mainClass = "cariperbedaan.main.Main"
}

repositories {
    mavenCentral()
}


dependencies {
    // Gson untuk baca levels.json
    implementation("com.google.code.gson:gson:2.10.1")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "cariperbedaan.main.Main"
    }
    from(configurations.runtimeClasspath.get().map {
        if (it.isDirectory) it else zipTree(it)
    })
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}