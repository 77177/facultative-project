package com.epam.lab.group1.facultative.exception;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import java.io.PrintWriter;
import java.io.StringWriter;

import static com.epam.lab.group1.facultative.view.ViewType.ERROR;

@Data
@Component
@Scope(value = "request")
public class ExceptionModelAndViewBuilder {

    private Exception e;
    private String message;
    private String userMessage;
    private ModelAndView modelAndView;

    public ExceptionModelAndViewBuilder setException(Exception e) {
        this.e = e;
        this.message = "";
        this.userMessage = "";
        this.modelAndView = new ModelAndView(ERROR.viewName);
        return this;
    }

    public ExceptionModelAndViewBuilder replaceUserMessage(String message) {
        this.userMessage = message;
        return this;
    }

    public ExceptionModelAndViewBuilder addUserMessage(String message) {
        this.userMessage = this.userMessage.concat(" " + message);
        return this;
    }

    public ExceptionModelAndViewBuilder addErrorMessage(String message) {
        this.message = message.concat(" " + message);
        return this;
    }

    public ExceptionModelAndViewBuilder replaceErrorMessage(String message) {
        this.message = message;
        return this;
    }

    public ModelAndView build() {
        modelAndView.addObject("userMessage", this.userMessage);
        modelAndView.addObject("messagesdfsdf", e.getMessage());
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        e.printStackTrace(printWriter);
        modelAndView.addObject("stackTrace", stringWriter.toString());
        return modelAndView;
    }
}
