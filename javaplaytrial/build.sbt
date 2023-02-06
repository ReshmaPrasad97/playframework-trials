name := """javaplaytrial"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.13.10"

libraryDependencies += guice
libraryDependencies += "org.elasticsearch" % "elasticsearch" % "7.10.0"
libraryDependencies += "org.elasticsearch.client" % "elasticsearch-rest-high-level-client" % "7.9.3"
libraryDependencies ++= Seq(javaJdbc)
libraryDependencies += jdbc

// https://mvnrepository.com/artifact/mysql/mysql-connector-java
libraryDependencies += "mysql" % "mysql-connector-java" % "8.0.32"
