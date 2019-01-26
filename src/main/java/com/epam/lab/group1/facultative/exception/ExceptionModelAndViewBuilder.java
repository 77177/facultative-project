package com.epam.lab.group1.facultative.exception;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import static com.epam.lab.group1.facultative.controller.ViewName.ERROR;

@Data
@Component
public class ExceptionModelAndViewBuilder {

    private Exception e;
    private String message;
    private ModelAndView modelAndView;

    public ExceptionModelAndViewBuilder setException(Exception e) {
        this.e = e;
        this.modelAndView = new ModelAndView(ERROR);
        return this;
    }

    public ExceptionModelAndViewBuilder addMessage(String message) {
        message.concat(" " + message);
        return this;
    }

    public ExceptionModelAndViewBuilder replaceMessageWith(String message) {
        this.message = message;
        return this;
    }

    public ModelAndView build() {
        modelAndView.addObject("message", this.message);
        modelAndView.addObject("exceptionName", this.e.getClass().getSimpleName());
        return modelAndView;
    }
}
