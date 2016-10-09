package com.github.wesleyegberto.doitapp.business.logging.boundary;

/**
 *
 * @author wesley
 */
// just to show us will be used with function style
// the compiler will threat it as functional interface even without the annotation
@FunctionalInterface
public interface LogSink {
    void log(String message);
}
