package com.epam.lab.group1.facultative;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import java.io.File;

public class Main {

    private final Logger logger = Logger.getLogger(this.getClass());

    private void startTomcat() throws LifecycleException, ServletException {
        String docBase = "src/main/webapp/";

        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);
        tomcat.addWebapp("/", new File(docBase).getAbsolutePath());

        tomcat.start();
        tomcat.getServer().await();
    }

    public static void main(String[] args) throws LifecycleException, ServletException {
        Main tomcat = new Main();
        tomcat.startTomcat();
    }
}





