package klaxon.klaxon

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap
import java.io.BufferedReader
import java.io.File
import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.Path
import kotlin.io.path.bufferedReader
import kotlin.io.path.bufferedWriter
import kotlin.io.path.createDirectory
import kotlin.io.path.deleteIfExists
import kotlin.io.path.exists
import kotlin.io.path.fileVisitor
import kotlin.io.path.isDirectory
import kotlin.io.path.isRegularFile
import kotlin.io.path.relativeTo
import kotlin.io.path.walk

@OptIn(ExperimentalPathApi::class)
fun main() {
    println("Hello World!")

    // Load the mappings
    val mappings = Object2ObjectOpenHashMap<String, String>()
    csvReader().open(File("methods.csv")) {
        readAllWithHeaderAsSequence().forEach {
            mappings[it["searge"]] = it["name"]
        }
    }

    // Remap files
    val inDir = Path("input")
    val outDir = Path("output")

    if (!inDir.isDirectory()) {
        inDir.deleteIfExists()
        inDir.createDirectory()
    }
    if (!outDir.isDirectory()) {
        outDir.deleteIfExists()
        outDir.createDirectory()
    }

    // Remap every file
    val funcFinder = Regex("func_[0-9]+_[a-z]")
    inDir.walk().forEach { it ->
        if (!it.isRegularFile()) return

        val reader = it.bufferedReader()
        val out = outDir.resolve(it.relativeTo(inDir))
        out.deleteIfExists()
        val writer = out.bufferedWriter()

        reader.lines().forEach { line ->
            writer.appendLine(line.replace(funcFinder, transform = { match ->
                println(match.value)
                mappings.getOrDefault(match.value, match.value)
            }))
        }
    }
}