plugins {
    application
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("io.freefair.lombok") version "8.6"
}

group = "hexlet.code"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

application {
    mainClass = "hexlet.code.App"
}

dependencies {
    implementation("io.javalin:javalin:6.3.0")
    implementation("io.javalin:javalin-rendering:6.1.3")
    implementation("gg.jte:jte:3.1.12")
    implementation("io.javalin:javalin-bundle:6.1.3")

    implementation("com.h2database:h2:2.2.224")
    implementation("com.zaxxer:HikariCP:5.1.0")
    implementation ("org.postgresql:postgresql:42.2.23")

    implementation("com.konghq:unirest-java:3.11.09")
    implementation("org.jsoup:jsoup:1.14.3")

    implementation("io.ebean:ebean:12.15.1")
    implementation("io.ebean:ebean-ddl-generator:12.15.1")
    implementation("io.ebean:ebean-migration:12.15.1")
    implementation("io.ebean:ebean-querybean:12.15.1")
    implementation("io.ebean:ebean-annotation:7.7")

    implementation("org.glassfish.jaxb:jaxb-runtime:2.3.5")
    implementation("javax.activation:activation:1.1.1")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}