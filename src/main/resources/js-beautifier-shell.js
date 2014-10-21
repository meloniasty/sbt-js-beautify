/*global process, require */

/*
 * Beutify JS files.
 *
 * Arguments:
 * 0 - name given to the command invoking the script (unused)
 * 1 - filepath of this script (unused)
 * 2 - array of file paths to the files to lint
 * 3 - the target folder to write to (unused - not required)
 * 4 - jsbeutifier options as a Json object
 *
 */

(function () {

  "use strict";

  var args = process.argv;
  var console = require("console");
  var fs = require("fs");
  var beautify = require('js-beautify').js_beautify;

  var SOURCE_FILE_MAPPINGS_ARG = 2;
  var OPTIONS_ARG = 4;
  var options = JSON.parse(args[OPTIONS_ARG]);

  var sourceFileMappings = JSON.parse(args[SOURCE_FILE_MAPPINGS_ARG]);
  var sourceFilesToProcess = sourceFileMappings.length;
  var results = [];
  var problems = [];

  sourceFileMappings.forEach(function (sourceFilePath) {
    var sourceFile = sourceFilePath[0];

    fs.readFile(sourceFile, "utf8", function (e, source) {

      if (e) {
        console.error("Error while trying to read " + source, e);
      } else {
        var actualErrors = 0;
        var pretty = beautify(source, options);

        fs.writeFile(sourceFile, pretty, 'utf8', function (e) {

          if (e !== undefined && e !== null) {
            ++actualErrors;

            problems.push({
              message: e.toString(),
              source: sourceFile
            });
          }
        });

        results.push({
          source: sourceFile,
          result: (actualErrors === 0 ? {filesRead: [sourceFile], filesWritten: []} : null)
        });
      }
      if (--sourceFilesToProcess === 0) {
        console.log("\u0010" + JSON.stringify({results: results, problems: problems}));
      }
    });
  });
}());
