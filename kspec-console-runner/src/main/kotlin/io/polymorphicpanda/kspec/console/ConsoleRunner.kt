package io.polymorphicpanda.kspec.console

import com.intellij.openapi.util.Disposer
import io.polymorphicpanda.kspec.console.reporter.ConsoleReporter
import io.polymorphicpanda.kspec.launcher.KSpecLauncher
import io.polymorphicpanda.kspec.launcher.LaunchConfiguration
import joptsimple.OptionException
import joptsimple.OptionParser
import joptsimple.OptionSet
import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys
import org.jetbrains.kotlin.cli.common.messages.PrintingMessageCollector
import org.jetbrains.kotlin.cli.jvm.compiler.EnvironmentConfigFiles
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinToJVMBytecodeCompiler
import org.jetbrains.kotlin.cli.jvm.config.JVMConfigurationKeys
import org.jetbrains.kotlin.cli.jvm.config.addJvmClasspathRoots
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.config.addKotlinSourceRoot
import org.jetbrains.kotlin.utils.KotlinPaths
import org.jetbrains.kotlin.utils.PathUtil
import org.slf4j.LoggerFactory
import java.io.ByteArrayOutputStream
import java.io.File
import java.net.URLClassLoader
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

class ConsoleRunner(val launcher: KSpecLauncher) {

    private val logger by lazy(LazyThreadSafetyMode.NONE) {
        LoggerFactory.getLogger("io.polymorphicpanda.kspec.console.info")
    }
    private val reporter = ConsoleReporter()

    init {
        launcher.addReporter(reporter)
    }

    private val parser by lazy(LazyThreadSafetyMode.NONE) {
        OptionParser().apply {
            acceptsAll(listOf("h", "help"), "Prints this message.")

            acceptsAll(listOf("v", "version"), "Prints version info.")

            acceptsAll(listOf("s", "specs"), "Single spec (.kt) or a directory containg spec files.")
                .requiredUnless("help", "version")
                .withRequiredArg()

            acceptsAll(listOf("classpath", "cp"), "Contains the classes required to run the specs.")
                .withRequiredArg()
                .withValuesSeparatedBy(CLASSPATH_SEPARATOR)

            acceptsAll(listOf("f", "filter"), "Filter out specific spec classes.")
                .withRequiredArg()

            acceptsAll(listOf("q", "query"), "Specify xpath-like query.")
                .withRequiredArg()
        }
    }

    inline fun <reified T> OptionSet.getOrDefault(option: String, default: T): T {
        return if (has(option)) valueOf(option) as T else default
    }

    inline fun <reified T> OptionSet.get(option: String): T {
        return valueOf(option) as T
    }

    fun run(vararg args: String) {
        try {
            val optionSet = parser.parse(*args)

            if (optionSet.has("help")) {
                printHelpInfo()
            } else if (optionSet.has("version")) {
                printVersionInfo()
            } else {
                val filter = optionSet.getOrDefault("filter", "")
                val query = optionSet.getOrDefault("query", "")
                val target = Paths.get(optionSet.get<String>("specs"))
                val cp = optionSet.valuesOf("cp")
                    .map { it as String }
                    .map { Paths.get(it) }

                val configuration = LaunchConfiguration(compileSpec(target, cp), cp, filter, query)

                launcher.launch(configuration)
            }
        } catch(e: OptionException) {
            println(e.message)
        }
    }

    companion object {
        val CLASSPATH_SEPARATOR = File.pathSeparator
    }

    private fun printVersionInfo() {
        logger.trace("KSpec v${Version.KSPEC_VERSION} (Kotlin v${Version.KOTLIN_VERSION})")
    }

    private fun printHelpInfo() {
        val buffer = ByteArrayOutputStream()
        parser.printHelpOn(buffer)
        logger.trace(buffer.toString(Charsets.UTF_8.name()))
    }

    private fun compileSpec(source: Path, classpath: List<Path>): Path {
        val outDir = Files.createTempDirectory("kspec-")

        val rootDisposable = Disposer.newDisposable()

        val configuration = CompilerConfiguration()

        val messageCollector = PrintingMessageCollector.PLAIN_TEXT_TO_SYSTEM_ERR

        configuration.addKotlinSourceRoot(
            source.toString()
        )

        configuration.put(JVMConfigurationKeys.MODULE_NAME, outDir.fileName.toString())
        configuration.put(CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY, messageCollector)
        configuration.addJvmClasspathRoots(
            getClassPath(PathUtil.getKotlinPathsForCompiler(), classpath)
        )

        val environment = KotlinCoreEnvironment.createForProduction(
            rootDisposable,
            configuration,
            EnvironmentConfigFiles.JVM_CONFIG_FILES
        )

        KotlinToJVMBytecodeCompiler.compileBunchOfSources(
            environment,
            null,
            outDir.toFile(),
            emptyList(),
            false
        )

        return outDir
    }

    private fun getClassPath(paths: KotlinPaths, classpath: List<Path>): List<File> {
        val result = arrayListOf<File>()
        result.addAll(PathUtil.getJdkClassesRoots())

        val cl = javaClass.classLoader as URLClassLoader

        result.addAll(classpath.map { it.toFile() })
        result.addAll(cl.urLs.map { Paths.get(it.toURI()).toFile() })
        return result
    }
}
