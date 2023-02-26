package processor

import java.lang.Exception

open class Matrix(val rows: Int, val columns: Int) {
    open val contents: MutableList<MutableList<Double>> = MutableList(rows) { MutableList(columns) { 0.0 } }
    open operator fun plus(matrix: Matrix): Matrix {
        return this
    }

    open operator fun times(matrix: Matrix): Matrix {
        return this
    }

    open operator fun get(row: Int): MutableList<Double> {
        return contents[row]
    }
}

class Scalar : Matrix(rows = 1, columns = 1) {
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

    override operator fun times(matrix: Matrix): Matrix2D {
        when {
            this.columns == matrix.rows -> {
                val result = Matrix2D(this.rows, matrix.columns)
                for (row in 0 until result.rows) {
                    for (col in 0 until result.columns) {
                        result[row][col] = 0.0
                        repeat(this.columns) {
                            result[row][col] += this[row][it] * matrix[it][col]
                        }
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