/**
 * This product currently only contains code developed by authors
 * of specific components, as identified by the source code files.
 *
 * Since product implements StAX API, it has dependencies to StAX API
 * classes.
 *
 * For additional credits (generally to people who reported problems)
 * see CREDITS file.
 */
package com.turbulence6th;

public class ComplexNumber {

    public static final ComplexNumber ZERO = new ComplexNumber(0, 0);

    private final double real;
    private final double imaginary;

    public ComplexNumber(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    public ComplexNumber add(double d) {
        return new ComplexNumber(this.real + d, this.imaginary);
    }

    public ComplexNumber add(ComplexNumber complexNumber) {
        return new ComplexNumber(this.real + complexNumber.real, this.imaginary + complexNumber.imaginary);
    }

    public ComplexNumber subtract(double d) {
        return new ComplexNumber(this.real - d, this.imaginary);
    }

    public ComplexNumber subtract(ComplexNumber complexNumber) {
        return this.add(complexNumber.multiply(-1));
    }

    public ComplexNumber multiply(double d) {
        return new ComplexNumber(this.real *d, this.imaginary * d);
    }

    public ComplexNumber multiply(ComplexNumber complexNumber) {
        return new ComplexNumber(this.real * complexNumber.real - this.imaginary * complexNumber.imaginary,
                this.real * complexNumber.imaginary + this.imaginary * complexNumber.real);
    }

    public ComplexNumber divide(double d) {
        return new ComplexNumber(this.real, this.imaginary).multiply(1 / d);
    }

    public ComplexNumber divide(ComplexNumber complexNumber) {
        return new ComplexNumber(this.real * complexNumber.real + this.imaginary * complexNumber.imaginary,
                this.imaginary * complexNumber.real - this.real * complexNumber.imaginary)
                .divide(complexNumber.real * complexNumber.real + complexNumber.imaginary * complexNumber.imaginary);
    }

    public double length() {
        return Math.sqrt(this.real * this.real + this.imaginary * this.imaginary);
    }

    public double slope() {
        return this.imaginary / this.real;
    }

    public double radient() {
        if (this.real == 0 && this.imaginary == 0) {
            return 0;
        } else if (this.real >= 0 && this.imaginary >= 0) {
            return Math.atan(slope());
        } else if (this.real <= 0 && this.imaginary >= 0) {
            return Math.PI + Math.atan(slope());
        } else if (this.real <= 0 && this.imaginary <= 0) {
            return Math.PI + Math.atan(slope());
        } else if (this.real >= 0 && this.imaginary <= 0) {
            return 2 * Math.PI + Math.atan(slope());
        }

        return 0;
    }

    public double getReal() {
        return real;
    }

    public double getImaginary() {
        return imaginary;
    }
}
