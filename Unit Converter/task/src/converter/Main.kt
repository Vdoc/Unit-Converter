package converter

import java.util.*
/*
Stage 5/5: Is it getting hot here?
 */
fun main() {
    val converter = Converter()
    converter.convert()
}

class Converter {
    var exit = false

    fun convert() {
        readInput()
        while (!exit) {
            print(magic())
            readInput()
        }
    }

    var n1: Unit = Unit()
    var n2: Unit = Unit()

    private fun readInput() {
        var parseError = true
        var str = ""
        val scanner = Scanner(System.`in`)
        var n = 0.0
        var m1 = ""
        var m2 = ""

        loop@while (parseError) {
            print("Enter what you want to convert (or exit): ")
            str = scanner.nextLine()
            if (str == "exit") {
                exit = true
                return
            }
            val input = str.split(" ")
            var k = 0

            n = input[0].toDouble()

            n1.value = n

            if (input[1].toLowerCase() == "degree" || input[1].toLowerCase() == "degrees") {
                if (input[2].toLowerCase() == "celsius") m1 = "degree celsius"
                else if (input[2].toLowerCase() == "fahrenheit") m1 = "degree fahrenheit"

                else m1 = "???"
                k = 3
            } else {
                m1 = input[1].toLowerCase()
                k = 2
            }

            n1.setUnitsOfMeasurement(m1)

            if (n < 0 && n1.group == "meter") {
                print("Length shouldn't be negative\n\n")
                continue@loop
            }
            if (n < 0 && n1.group == "gram") {
                print("Weight shouldn't be negative\n\n")
                continue@loop
            }
            if (n1.value == 1.0) n1.out = n1.unitOfMeasurement
            else n1.out = n1.unitOfMeasurementS

            k++

            if (input[k].toLowerCase() == "degree" || input[k].toLowerCase() == "degrees") {
                if (input[k+1].toLowerCase() == "celsius") m2 = "degree celsius"
                else if (input[k+1].toLowerCase() == "fahrenheit") m2 = "degree fahrenheit"

                else m2 = "???"
                k = k + 2
            } else {
                k++
                m2 = input[k-1].toLowerCase()
            }

            if (input.size > k) {
                print("Parse error\n\n")
                continue@loop
            }

            parseError = false
        }
        n2.setUnitsOfMeasurement(m2)
    }

    private fun magic(): String {
        if (n1.unitOfMeasurement == "???")
            return "Conversion from ??? to ${n2.unitOfMeasurementS} is impossible\n\n"
        if (n1.group != n2.group)
            return "Conversion from ${n1.unitOfMeasurementS} to ${n2.unitOfMeasurementS} is impossible\n\n"

        if (n1.group != "heat") n2.setValue(n1.value, n1.kt, n2.kt)
        else n2.cfk(n1)

        return "${n1.value} ${n1.out} is ${n2.value} ${n2.out}\n\n"
    }
}

class Unit {
    var value: Double = 0.0

    fun setValue(number: Double, kt1: Double = 1.0, kt2: Double = 1.0) {
        if (group != "heat") {
            value = number * kt1 / kt2
            if (value == 1.0) out = unitOfMeasurement
            else out = unitOfMeasurementS
        }
    }

    var arr: Array<String> = arrayOf()
    var unitOfMeasurement: String = ""
    var unitOfMeasurementS: String = ""
    var group: String = ""
    var out = ""
    var kt: Double = 1.0

    fun setUnitsOfMeasurement(unitOfMeasurement: String) {
        arr = when (unitOfMeasurement) {
            "m", "meter", "meters" -> arrayOf("meter", "meters", "meter", "1.0")
            "km", "kilometer", "kilometers" -> arrayOf("kilometer", "kilometers", "meter", "1000.0")
            "cm", "centimeter", "centimeters" -> arrayOf("centimeter", "centimeters", "meter", "0.01")
            "mm", "millimeter", "millimeters" -> arrayOf("millimeter", "millimeters", "meter", "0.001")
            "mi", "mile", "miles" -> arrayOf("mile", "miles", "meter", "1609.35")
            "yd", "yard", "yards" -> arrayOf("yard", "yards", "meter", "0.9144")
            "ft", "foot", "feet" -> arrayOf("foot", "feet", "meter", "0.3048")
            "in", "inch", "inches" -> arrayOf("inch", "inches", "meter", "0.0254")

            "g", "gram", "grams" -> arrayOf("gram", "grams", "gram", "1.0")
            "kg", "kilogram", "kilograms" -> arrayOf("kilogram", "kilograms", "gram", "1000.0")
            "mg", "milligram", "milligrams" -> arrayOf("milligram", "milligrams", "gram", "0.001")
            "lb", "pound", "pounds" -> arrayOf("pound", "pounds", "gram", "453.592")
            "oz", "ounce", "ounces" -> arrayOf("ounce", "ounces", "gram", "28.3495")

            "degree celsius", "degrees celsius", "celsius", "dc", "c" -> arrayOf("degree Celsius", "degrees Celsius", "heat", "1.0")
            "degree fahrenheit", "degrees fahrenheit", "fahrenheit", "df", "f" -> arrayOf("degree Fahrenheit", "degrees Fahrenheit", "heat", "1.0")
            "kelvin", "kelvins", "k" -> arrayOf("kelvin", "kelvins", "heat", "1.0")
            else -> arrayOf("???", "???", "???", "0.0")
        }
        this.unitOfMeasurement = arr[0]
        this.unitOfMeasurementS = arr[1]
        this.group = arr[2]
        this.kt = arr[3].toDouble()
    }

    fun cfk(n1: Unit) {
        if (unitOfMeasurement == "degree Celsius") {
            if (n1.unitOfMeasurement == "degree Celsius") value = n1.value
            if (n1.unitOfMeasurement == "degree Fahrenheit") value = (n1.value - 32) * 5 / 9
            if (n1.unitOfMeasurement == "kelvin") value = n1.value - 273.15
        }
        if (unitOfMeasurement == "degree Fahrenheit") {
            if (n1.unitOfMeasurement == "degree Celsius") value = (n1.value * 9) / 5 +32
            if (n1.unitOfMeasurement == "degree Fahrenheit") value = n1.value
            if (n1.unitOfMeasurement == "kelvin") value = n1.value * 9 / 5 - 459.67
        }
        if (unitOfMeasurement == "kelvin") {
            if (n1.unitOfMeasurement == "degree Celsius") value = n1.value + 273.15
            if (n1.unitOfMeasurement == "degree Fahrenheit") value = (n1.value + 459.67) * 5 / 9
            if (n1.unitOfMeasurement == "kelvin") value = n1.value
        }
        if (value == 1.0) out = unitOfMeasurement
        else out = unitOfMeasurementS
    }
}





/*
Stage 4/5: Heavy duty

fun main() {
    val converter = Converter()
    converter.convert()
}

class Converter {
    var exit = false

    fun convert() {
        readInput()
        while (!exit) {
            print(magic())
            readInput()
        }
    }

    var n1: Unit = Unit()
    var n2: Unit = Unit()

    private fun readInput() {
        print("Enter what you want to convert (or exit): ")
        val scanner = Scanner(System.`in`)
        val input = scanner.nextLine().split(" ")
        if (input.size == 4) {
            n1.setValue(input[0].toDouble())
            n1.setUnitsOfMeasurement(input[1].toLowerCase())
            n1.setValue(n1.value)
            n2.setUnitsOfMeasurement(input[3].toLowerCase())
        }
        if (input.size == 1 && input[0] == "exit") exit = true
    }

    private fun magic(): String {
        if (n1.unitOfMeasurement == "???")
            return "Conversion from ??? to ${n2.unitOfMeasurementS} is impossible\n\n"
        if (n1.group != n2.group)
            return "Conversion from ${n1.unitOfMeasurementS} to ${n2.unitOfMeasurementS} is impossible\n\n"
        n2.setValue(n1.value, n1.kt , n2.kt)
        return "${n1.value} ${n1.out} is ${n2.value} ${n2.out}\n\n"
    }
}

class Unit {
    var value: Double = 0.0

    fun setValue(number: Double, kt1: Double = 1.0, kt2: Double = 1.0) {
        value = number * kt1 / kt2
        if (value == 1.0) out = unitOfMeasurement
        else out = unitOfMeasurementS
    }

    var arr: Array<String> = arrayOf()
    var unitOfMeasurement: String = ""
    var unitOfMeasurementS: String = ""
    var group: String = ""
    var out = ""
    var kt: Double = 1.0

    fun setUnitsOfMeasurement(unitOfMeasurement: String) {
        arr = when (unitOfMeasurement) {
            "m", "meter", "meters" -> arrayOf("meter", "meters", "meter", "1.0")
            "km", "kilometer", "kilometers" -> arrayOf("kilometer", "kilometers", "meter", "1000.0")
            "cm", "centimeter", "centimeters" -> arrayOf("centimeter", "centimeters", "meter", "0.01")
            "mm", "millimeter", "millimeters" -> arrayOf("millimeter", "millimeters", "meter", "0.001")
            "mi", "mile", "miles" -> arrayOf("mile", "miles", "meter", "1609.35")
            "yd", "yard", "yards" -> arrayOf("yard", "yards", "meter", "0.9144")
            "ft", "foot", "feet" -> arrayOf("foot", "feet", "meter", "0.3048")
            "in", "inch", "inches" -> arrayOf("inch", "inches", "meter", "0.0254")

            "g", "gram", "grams" -> arrayOf("gram", "grams", "gram", "1.0")
            "kg", "kilogram", "kilograms" -> arrayOf("kilogram", "kilograms", "gram", "1000.0")
            "mg", "milligram", "milligrams" -> arrayOf("milligram", "milligrams", "gram", "0.001")
            "lb", "pound", "pounds" -> arrayOf("pound", "pounds", "gram", "453.592")
            "oz", "ounce", "ounces" -> arrayOf("ounce", "ounces", "gram", "28.3495")
            else -> arrayOf("???", "???", "???", "0.0")
        }
        this.unitOfMeasurement = arr[0]
        this.unitOfMeasurementS = arr[1]
        this.group = arr[2]
        this.kt = arr[3].toDouble()
    }
}
*/




/*
Stage 3/5: Distances

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
*/



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