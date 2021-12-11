plugins {
  kotlin("jvm") version "1.6.0"
}

repositories {
  mavenCentral()
}

tasks {
  sourceSets {
    main {
      java.srcDirs("10", "11")
    }
  }

  wrapper {
    gradleVersion = "7.3"
  }
}