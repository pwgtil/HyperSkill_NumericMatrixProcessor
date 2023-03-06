package processor

import java.util.*

fun main() {
    Matrix.start()
}

class Matrix(private val rows: Int, private val columns: Int, init: (Int, Int) -> Double) {

    private var contents: List<List<Double>> = List(rows) { i -> List(columns) { j -> init(i, j) } }

    companion object {
        private const val MSG_MAIN_MANU_SELECTION =
            "1. Add matrices\n2. Multiply matrix by a constant\n3. Multiply matrices\n4. Transpose matrix\n5. Calculate a determinant\n6. Inverse matrix\n0. Exit\nYour choice: "
        private const val MSG_MANU_TRANSPOSE =
            "1. Main diagonal\n2. Side diagonal\n3. Vertical line\n4. Horizontal line\nYour choice: "
        private const val MSG_ENTER_CONSTANT = "Enter constant: "
        private const val MSG_RESULT_IS = "The result is:"
        private const val MSG_MATRIX_HAVE_INVERSE = "This matrix doesn't have an inverse."
        private const val MSG_OPERATION_CANNOT_BE_PERFORMED = "The operation cannot be performed."
        private const val MSG_ENTER_MATRIX_SIZE = "Enter matrix size: "
        private const val MSG_ENTER_MATRIX = "Enter matrix:"


        private fun initMatrix(): Matrix {
            MSG_ENTER_MATRIX_SIZE.let(::print)
            val (rows, cols) = readln().split(" ", limit = 2).map { it.toInt() }
            MSG_ENTER_MATRIX.let(::println)
            val scanner = Scanner(System.`in`)
            return Matrix(rows, cols) { _, _ -> scanner.nextDouble() }
        }

        fun start() {
            var choice = ""
            while (choice != "0") {
                MSG_MAIN_MANU_SELECTION.let(::println)
                choice = readln()
                try {
                    when (choice) {
                        "1" -> (initMatrix() + initMatrix()).let(::println)
                        "2" -> {
                            val firstMatrix = initMatrix()
                            MSG_ENTER_CONSTANT.let(::print)
                            (firstMatrix * readln().toDouble()).let(::println)
                        }

                        "3" -> (initMatrix() * initMatrix()).let(::println)
                        "4" -> {
                            MSG_MANU_TRANSPOSE.let(::println)
                            val choiceTranspose = readln().toInt()
                            initMatrix().transpose(choiceTranspose).let(::println)
                        }

                        "5" -> {
                            val result = initMatrix().determinant()
                            MSG_RESULT_IS.let(::println)
                            result.let(::println)
                        }

                        "6" -> {
                            val firstMatrix = initMatrix()
                            val result = firstMatrix.getInverse()
                            if (result != null) {
                                result.let(::println)
                            } else {
                                MSG_MATRIX_HAVE_INVERSE.let(::println)
                            }
                        }
                    }
                } catch (e: Exception) {
                    MSG_OPERATION_CANNOT_BE_PERFORMED.let(::println)
                }
            }
        }
    }

    operator fun plus(other: Matrix): Matrix {
        if (this.rows == other.rows && this.columns == other.columns) {
            return Matrix(this.rows, this.columns) { i, j -> this[i][j] + other[i][j] }
        } else {
            throw Exception()
        }
    }

    operator fun times(scalar: Double) = Matrix(rows, columns) { i, j -> this[i][j] * scalar }

    operator fun times(matrix: Matrix): Matrix {
        if (this.columns == matrix.rows) {
            return Matrix(this.rows, matrix.columns) { i, j ->
                var tmp = 0.0
                repeat(this.columns) {
                    tmp += this[i][it] * matrix[it][j]
                }
                tmp
            }
        } else {
            throw Exception()
        }
    }

    operator fun get(row: Int): List<Double> = contents[row]

    private fun minor(k: Int, l: Int) =
        Matrix(rows - 1, columns - 1) { i, j -> this[if (i < k) i else i + 1][if (j < l) j else j + 1] }

    private fun cofactor(k: Int, l: Int) = minor(k, l).determinant() * if ((k + l) % 2 == 0) 1.0 else -1.0

    fun determinant(): Double =
        if (rows == 2) {
            this[0][0] * this[1][1] - this[0][1] * this[1][0]
        } else {
            var tmp = 0.0
            repeat(columns) { tmp += this[0][it] * cofactor(0, it) }
            tmp
        }

    fun transpose(option: Int): Matrix {
        if (this.rows == this.columns) {
            return when (option) {
                1 -> /* Main diagonal */ Matrix(rows, columns) { i, j -> this[j][i] }
                2 -> /* Side diagonal */ Matrix(rows, columns) { i, j -> this[columns - j - 1][rows - i - 1] }
                3 -> /* Vertical line */ Matrix(rows, columns) { i, j -> this[i][columns - j - 1] }
                4 -> /* Horizont line */ Matrix(rows, columns) { i, j -> this[rows - i - 1][j] }
                else -> throw Exception()
            }
        } else {
            throw Exception()
        }
    }

    fun getInverse(): Matrix? {
        if (this.rows == this.columns) {
            val determinant = determinant()
            return if (determinant != 0.0) {
                Matrix(this.rows, this.columns) { i, j -> cofactor(i, j) }.transpose(1) * (1.0 / determinant)
            } else {
                null
            }
        } else {
            throw Exception()
        }
    }

    override fun toString() = MSG_RESULT_IS + "\n" + contents.joinToString("\n") { it.joinToString(" ") }
}
