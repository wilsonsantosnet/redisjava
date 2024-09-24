package com.example.demo.benchmark;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

public class BenchmarkRunner {
    public static void main(String[] args) throws Exception {
        // Use the system class loader instead of URLClassLoader
        ClassLoader classLoader = BenchmarkRunner.class.getClassLoader();
        StringBuilder classpath = new StringBuilder();
        if (classLoader instanceof URLClassLoader) {
            for (URL url : ((URLClassLoader) classLoader).getURLs()) {
                classpath.append(url.getPath()).append(File.pathSeparator);
            }
        } else {
            // Handle other types of class loaders if necessary
            System.err.println("ClassLoader is not an instance of URLClassLoader");
        }
        System.setProperty("java.class.path", classpath.toString());

        org.openjdk.jmh.Main.main(args);
    }
}