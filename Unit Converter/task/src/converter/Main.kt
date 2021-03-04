package converter

import java.util.*
/*
Stage 3/5: Distances
 */
fun main() {

    var s = "Enter a number and a measure of length: "
    print(s)

    val scanner = Scanner(System.`in`)
    val input = scanner.nextLine().split(" ")
    val number: Double = input[0].toDouble()
    val unitOfMeasurement = input[1].toLowerCase()
    print(converter(number, unitOfMeasurement))
}

fun converter(number: Double, unitOfMeasurement: String): String {
    val isSingular = if (number == 1.0) 0 else 1
    val fullUnitOfMeasurement = convertMeasurement(unitOfMeasurement)
    val convertedNumber: Double = convertNumber(number, fullUnitOfMeasurement[0])
    val isSingular2 = if (convertedNumber == 1.0) 0 else 1

    val message: String = "$number ${fullUnitOfMeasurement[isSingular]} is $convertedNumber ${convertMeasurement("meter")[isSingular2]}"
    return message
}

fun convertNumber(number: Double, unitOfMeasurement: String): Double {
    return when (unitOfMeasurement) {
        "meter" -> number
        "kilometer" -> number * 1000
        "centimeter" -> number / 100
        "millimeter" -> number / 1000
        "mile" -> number * 1609.35
        "yard" -> number * 0.9144
        "foot" -> number * 0.3048
        "inch" -> number * 0.0254
        else -> 0.0
    }
}

fun convertMeasurement(unitOfMeasurement: String): Array<String> = when (unitOfMeasurement) {
    "m", "meter", "meters" -> arrayOf("meter" , "meters")
    "km", "kilometer", "kilometers" -> arrayOf("kilometer", "kilometers")
    "cm", "centimeter", "centimeters" -> arrayOf("centimeter", "centimeters")
    "mm", "millimeter", "millimeters" -> arrayOf("millimeter", "millimeters")
    "mi", "mile", "miles" -> arrayOf("mile", "miles")
    "yd", "yard", "yards" -> arrayOf("yard", "yards")
    "ft", "foot", "feet" -> arrayOf("foot", "feet")
    "in", "inch", "inches" -> arrayOf("inch", "inches")
    else -> arrayOf("%&@#","%&@#s")
}




/*
Stage 2/5: Let’s get metric

fun main() {
    print("Enter a number of kilometers: ")
    val scanner = Scanner(System.`in`)
    val km = scanner.nextInt()
    val m = km * 1000
    print("$km kilometers is $m meters")
}
*/


/*
Stage 1/5: Standard output
fun main() {
    println("145 centimeters is 1.45 meters\n" +
            "2 miles is 3.2187 kilometers\n" +
            "5.5 inches is 139.7 millimeters\n" +
            "12 degrees Celsius is 53.6 degrees Fahrenheit\n" +
            "3 pounds is 1.360776 kilograms")
}
*/