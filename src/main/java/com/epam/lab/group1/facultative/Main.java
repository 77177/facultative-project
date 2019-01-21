package com.epam.lab.group1.facultative;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.apache.log4j.Logger;

import java.io.File;
import java.nio.file.Paths;

public class Main {

    private final Logger logger = Logger.getLogger(this.getClass());

    private void startTomcat() throws LifecycleException {
        String docBase = "src/main/webapp/";

        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);
        tomcat.addWebapp("/", Paths.get(docBase).toAbsolutePath().toString());

        tomcat.start();
        tomcat.getServer().await();
    }

    public static void main(String[] args) throws LifecycleException {
        Main tomcat = new Main();
        tomcat.startTomcat();
    }
}





