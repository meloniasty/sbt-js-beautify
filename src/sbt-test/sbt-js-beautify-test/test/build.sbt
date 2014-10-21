import sbt._
import sbt.Keys._
import scala.io._
import com.typesafe.sbt.web.SbtWeb
import com.typesafe.sbt.web.SbtWeb.autoImport._

lazy val root = Project(
  id = "test-build",
  base = file(".")
).enablePlugins(SbtWeb)

lazy val check4space = taskKey[Unit]("Checks proper js-beautify with 4 space indent")

lazy val check8space = taskKey[Unit]("Checks proper js-beautify with 8 space indent")

lazy val backToRoots = taskKey[Unit]("Back to roots")

check4space := {
  val out = Source.fromFile(file("src/main/assets/plugin-test.js")).getLines().mkString("\n")
  val expected = "(function() {\n    \"use strict\";\n\n    function testPlugin() {\n        var a = 1;\n        console.log(\"plugin test\", a);\n    }\n}());"
  if (!out.equals(expected)) sys.error("file was not proper beautified")
  ()
}

check8space := {
  val out = Source.fromFile(file("src/main/assets/plugin-test.js")).getLines().mkString("\n")
  val expected = "(function() {\n        \"use strict\";\n\n        function testPlugin() {\n                var a = 1;\n                console.log(\"plugin test\", a);\n        }\n}());"
  if (!out.equals(expected)) sys.error("file was not proper beautified")
  ()
}

backToRoots := {
  val content = "(function() { \"use strict\"; function testPlugin() { var a = 1; console.log(\"plugin test\", a);  } }());"
  val f = file("src/main/assets/plugin-test.js")
  IO.write(f, content)
}