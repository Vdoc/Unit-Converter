package converter

import java.util.*
/*
Stage 2/5: Let’s get metric
 */
fun main() {
    print("Enter a number of kilometers: ")
    val scanner = Scanner(System.`in`)
    val km = scanner.nextInt()
    val m = km * 1000
    print("$km kilometers is $m meters")
}



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