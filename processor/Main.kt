package processor

import java.lang.Exception

fun main() {
    View().start()
}

class View {
    companion object {
        private const val MSG_ADD_MATRICES = "1. Add matrices"
        private const val MSG_MULTIPLY_BY_CONSTANT = "2. Multiply matrix by a constant"
        private const val MSG_MULTIPLY_MATRICES = "3. Multiply matrices"
        private const val MSG_EXIT = "0. Exit"
        const val MSG_MAIN_MANU_SELECTION =
            MSG_ADD_MATRICES + "\n" + MSG_MULTIPLY_BY_CONSTANT + "\n" + MSG_MULTIPLY_MATRICES + "\n" + MSG_EXIT
        const val MSG_YOUR_CHOICE = "Your choice: "
        const val MSG_ENTER_SIZE_FIRST_MATRIX = "Enter size of first matrix: "
        const val MSG_ENTER_FIRST_MATRIX = "Enter first matrix:"
        const val MSG_ENTER_SIZE_SECOND_MATRIX = "Enter size of second matrix: "
        const val MSG_ENTER_SECOND_MATRIX = "Enter second matrix:"
        const val MSG_ENTER_MATRIX = "Enter matrix:"
        const val MSG_ENTER_CONSTANT = "Enter constant: "
        const val MSG_THE_RESULT_IS = "The result is:"
        const val MSG_OPERATION_CANNOT_BE_PERFORMED = "The operation cannot be performed."
    }

    // PROPERTIES
    private var firstMatrix: Matrix? = null
    private var secondMatrix: Matrix? = null

    // METHODS
    private fun mainManuSelection(): Int {
        var choice: Int = Int.MIN_VALUE
        while (choice !in 0..4) {
            println(MSG_MAIN_MANU_SELECTION)
            println(MSG_YOUR_CHOICE)
            choice = try {
                readln().toInt()
            } catch (e: NumberFormatException) {
                println()
                Int.MIN_VALUE
            }
        }
        return choice
    }

    fun start() {
        var choice: Int = Int.MIN_VALUE
        while (choice != 0) {
            choice = mainManuSelection()
            when (choice) {
                1 -> {
                    try {
                        addMatrices()
                    } catch (e: Exception) {
                        MSG_OPERATION_CANNOT_BE_PERFORMED.let(::println)
                    }
                }

                2 -> {
                    try {
                        multiplyMatrixByConstant()
                    } catch (e: Exception) {
                        MSG_OPERATION_CANNOT_BE_PERFORMED.let(::println)
                    }
                }

                3 -> {
                    try {
                        multiplyMatrices()
                    } catch (e: Exception) {
                        MSG_OPERATION_CANNOT_BE_PERFORMED.let(::println)
                    }
                }

                else -> {
                    // nothing for now
                }
            }
        }
    }

    private fun multiplyMatrices() {
        MSG_ENTER_SIZE_FIRST_MATRIX.let(::print)
        val (mat1Rows, mat1Cols) = readln().split(" ", limit = 2).map { it.toInt() }

        MSG_ENTER_FIRST_MATRIX.let(::println)
        firstMatrix = get2DMatrix(mat1Rows, mat1Cols)

        MSG_ENTER_SIZE_SECOND_MATRIX.let(::print)
        val (mat2Rows, mat2Cols) = readln().split(" ", limit = 2).map { it.toInt() }

        MSG_ENTER_SECOND_MATRIX.let(::println)
        secondMatrix = get2DMatrix(mat2Rows, mat2Cols)

        val result = firstMatrix as Matrix2D * secondMatrix as Matrix2D
        MSG_THE_RESULT_IS.let(::println)
        printMatrix(result)
    }

    private fun multiplyMatrixByConstant() {
        MSG_ENTER_SIZE_FIRST_MATRIX.let(::print)
        val (mat1Rows, mat1Cols) = readln().split(" ", limit = 2).map { it.toInt() }

        MSG_ENTER_MATRIX.let(::println)
        secondMatrix = get2DMatrix(mat1Rows, mat1Cols)

        MSG_ENTER_CONSTANT.let(::print)
        firstMatrix = getScalar()

        val result = firstMatrix as Scalar * secondMatrix as Matrix2D
        MSG_THE_RESULT_IS.let(::println)
        printMatrix(result)
    }

    private fun addMatrices() {
        MSG_ENTER_SIZE_FIRST_MATRIX.let(::print)
        val (mat1Rows, mat1Cols) = readln().split(" ", limit = 2).map { it.toInt() }

        MSG_ENTER_FIRST_MATRIX.let(::println)
        firstMatrix = get2DMatrix(mat1Rows, mat1Cols)

        MSG_ENTER_SIZE_SECOND_MATRIX.let(::print)
        val (mat2Rows, mat2Cols) = readln().split(" ", limit = 2).map { it.toInt() }

        MSG_ENTER_SECOND_MATRIX.let(::println)
        secondMatrix = get2DMatrix(mat2Rows, mat2Cols)

        val result = firstMatrix as Matrix2D + secondMatrix as Matrix2D
        MSG_THE_RESULT_IS.let(::println)
        printMatrix(result)
    }

    private fun printMatrix(matrix: Matrix) {
        for (row in 0 until matrix.rows) {
            matrix[row].joinToString(" ").let(::println)
        }
        println()
    }

    private fun getScalar(): Scalar {
        val result = Scalar()
        result[0][0] = readln().toDouble()
        return result
    }

    private fun get2DMatrix(rows: Int, cols: Int): Matrix2D {
        val result = Matrix2D(rows, cols)
        for (row in 0 until rows) {
            readln().split(" ", limit = cols)
                .forEachIndexed { col, value -> result[row][col] = value.toDouble() }
        }
        return result
    }
}



