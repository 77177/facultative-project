package com.epam.lab.group1.facultative.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@Getter
@Setter
public class LocaleHolder {

    private Locale locale;
}
