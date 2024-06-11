plugins {
  id("java")
  id ("java-library")
  id("io.freefair.lombok") version "8.6"
  id("org.openjfx.javafxplugin") version "0.1.0"
}
group = "org.example"

repositories {
  mavenCentral()
}
//compile group: 'org.openjfx', name: 'javafx-web', version: '11'

dependencies {


  implementation(fileTree("C:/code/itmo/prog/bin/")
          .include("shared.jar")
          .include("client.jar"))
}
javafx {
  modules("javafx.controls", "javafx.fxml", "javafx.web")
}


tasks.withType<Jar>() {
  manifest {
    attributes["Main-Class"] = "src.ClientMain"
  }
  val dependencies = configurations
          .runtimeClasspath
          .get()
          .map(::zipTree) // OR .map { zipTree(it) }
  from(dependencies)
  duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

tasks.withType<JavaCompile> {
  options.compilerArgs.addAll(arrayOf("--release", "17"))
}

tasks.test {
  useJUnitPlatform()
}
tasks.compileJava{
  options.encoding = "UTF-8"
}

tasks.javadoc {
  options.encoding = "UTF-8"
}
