sbt-js-beautify
===============

Allows [js-beautify](https://github.com/beautify-web/js-beautify) to be used in sbt. 

My implementation only beautifies javascripts files and overwrite originals.


Plugin highly inspired by [sbt-jshint](https://github.com/sbt/sbt-jshint)

To use this plugin use the addSbtPlugin command within your project's plugins.sbt (or as a global setting) i.e.:

    addSbtPlugin("it.impossible.sbt" % "sbt-js-beautify" % "<version>")

You should check original node js plugin website [https://github.com/beautify-web/js-beautify](https://github.com/beautify-web/js-beautify) for beautifier options description.

Options should be provided in .jsbeautifyrc file, i.e.

    {
        "indent_size": 4,
        "indent_char": " ",
        "indent_level": 0,
        "indent_with_tabs": false,
        "preserve_newlines": true,
        "max_preserve_newlines": 10,
        "jslint_happy": false,
        "space_after_anon_function": false,
        "brace_style": "collapse",
        "keep_array_indentation": false,
        "keep_function_indentation": false,
        "space_before_conditional": true,
        "break_chained_methods": false,
        "eval_code": false,
        "unescape_strings": false,
        "wrap_line_length": 0
    }





