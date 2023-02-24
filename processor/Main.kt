package processor

import java.lang.Exception

fun main() {
    View().start()
}

class View {
    companion object {

    }

    fun start() {
        val (mat1Rows, mat1Cols) = readln()
            .split(" ", limit = 2)
            .map { it.toInt() }

        val mat1 = get2DMatrix(mat1Rows, mat1Cols)

//        val (mat2Rows, mat2Cols) = readln()
//            .split(" ", limit = 2)
//            .map { it.toInt() }

        val mat2 = getScalar()

        try {
            val result = mat2 * mat1
            printMatrix(result)
        } catch (e: Exception) {
            println("ERROR")
        }


    }

    private fun printMatrix(matrix: Matrix) {
        for (row in 0 until matrix.rows) {
            matrix[row].joinToString(" ").let(::println)
        }
    }

    private fun getScalar(): Scalar{
        val result = Scalar()
        result[0][0] = readln().toInt()
        return result
    }

    private fun get2DMatrix(rows: Int, cols: Int): Matrix2D {
        val result = Matrix2D(rows, cols)
        for (row in 0 until rows) {
            readln().split(" ", limit = cols)
                .forEachIndexed { col, value -> result[row][col] = value.toInt() }
        }
        return result
    }
}


@Suppress("UNREACHABLE_CODE")
open class Matrix(val rows: Int, val columns: Int) {
    open val contents: MutableList<MutableList<Int>> = MutableList(rows) { MutableList(columns) { 0 } }
    open operator fun plus(matrix: Matrix): Matrix {
        return this
    }

    open operator fun times(matrix: Matrix): Matrix {
        return this
    }

    open operator fun get(row: Int): MutableList<Int> {
        return contents[row]
    }
}

class Scalar(): Matrix(rows = 1, columns = 1) {
    override operator fun times(matrix: Matrix): Matrix {
        when {
            matrix is Matrix2D -> {
                val result = Matrix2D(matrix.rows, matrix.columns)
                for (row in 0 until result.rows) {
                    for (col in 0 until result.columns) {
                        result[row][col] = this[0][0] * matrix[row][col]
                    }
                }
                return result
            }

            else -> {
                throw Exception()
            }
        }
    }
}

class Matrix1D(length: Int) : Matrix(rows = 1, columns = length) {

    override operator fun plus(matrix: Matrix): Matrix1D {
        when {
            this.rows == matrix.rows && this.columns == matrix.columns -> {
                val result = Matrix1D(this.columns)
                for (row in 0 until result.rows) {
                    for (col in 0 until result.columns) {
                        result[row][col] = this[row][col] + matrix[row][col]
                    }
                }
                return result
            }

            else -> {
                throw Exception()
            }
        }
    }
}

class Matrix2D(rows: Int, columns: Int) : Matrix(rows, columns) {

    override operator fun plus(matrix: Matrix): Matrix2D {
        when {
            this.rows == matrix.rows && this.columns == matrix.columns -> {
                val result = Matrix2D(this.rows, this.columns)
                for (row in 0 until result.rows) {
                    for (col in 0 until result.columns) {
                        result[row][col] = this[row][col] + matrix[row][col]
                    }
                }
                return result
            }

            else -> {
                throw Exception()
            }
        }
    }
}



