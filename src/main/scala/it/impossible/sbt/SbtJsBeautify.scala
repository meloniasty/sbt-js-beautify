// Copyright (C) 2011-2012 the original author or authors.
// See the LICENCE.txt file distributed with this work for additional
// information regarding copyright ownership.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package it.impossible.sbt

import sbt._
import sbt.Keys._
import sbt.File
import com.typesafe.sbt.jse.SbtJsTask
import com.typesafe.sbt.web.SbtWeb

object Import {

  object JsBeautifyKeys {
    val jsbeautify = TaskKey[Seq[File]]("jsbeautify", "Perform JavaScript beautifier.")
    val config = SettingKey[Option[File]]("jsbeautify-config", "The location of a JsBeautify configuration file.")
    val resolvedConfig = TaskKey[Option[File]](
      "jsbeautify-resolved-config",
      "The actual location of a JsBeautify configuration file if present. " +
        "If jsbeautify-config is none then the task will seek a .jsbeautifyrc in the project folder. " +
        "If that's not found then .jsbeautifyrc will be searched for in the user's home folder. "
    )
  }

}

object SbtJsBeautify extends AutoPlugin {
  override def requires: sbt.Plugins = SbtJsTask

  override def trigger: sbt.PluginTrigger = AllRequirements

  val autoImport = Import

  import SbtWeb.autoImport._
  import WebKeys._
  import SbtJsTask.autoImport.JsTaskKeys._
  import autoImport.JsBeautifyKeys._

  override def projectSettings: scala.Seq[sbt.Def.Setting[_]] = Seq(
    config := None,
    resolvedConfig := {
      config.value.orElse {
        val JsBeautifyRc = ".jsbeautifyrc"
        val projectRc = baseDirectory.value / JsBeautifyRc
        if (projectRc.exists()) {
          Some(projectRc)
        } else {
          val homeRc = file(System.getProperty("user.home")) / JsBeautifyRc
          if (homeRc.exists()) {
            Some(homeRc)
          } else {
            None
          }
        }
      }: Option[File]
    }

  ) ++ inTask(jsbeautify)(
      SbtJsTask.jsTaskSpecificUnscopedSettings ++ Seq(
        moduleName := "js-beautify",
        shellFile := getClass.getClassLoader.getResource("js-beautifier-shell.js"),
        includeFilter in Assets := (jsFilter in Assets).value,
        includeFilter in TestAssets := (jsFilter in TestAssets).value,

        jsOptions := resolvedConfig.value.fold("{}")(IO.read(_)),

        taskMessage in Assets := "JavaScript beautifier",
        taskMessage in TestAssets := "JavaScript test beautifier"

      )
    ) ++ SbtJsTask.addJsSourceFileTasks(jsbeautify)
}
