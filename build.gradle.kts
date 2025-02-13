plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
        vendor.set(JvmVendorSpec.AMAZON)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    // Database stuff
    // JPA interfaces
    implementation("jakarta.persistence:jakarta.persistence-api:3.1.0")
    // Hibernate: JPA provider. Needed only at runtime
    runtimeOnly("org.hibernate:hibernate-core:6.6.7.Final")
    // Database driver. Needed only at runtime
    runtimeOnly("com.h2database:h2:2.2.220")
    // Maybe not needed?
    //    runtimeOnly("org.hibernate:hibernate-entitymanager:6.6.7.Final")
    //    implementation("javax.xml.bind:jaxb-api:2.3.1")

}

tasks.test {
    useJUnitPlatform()
}