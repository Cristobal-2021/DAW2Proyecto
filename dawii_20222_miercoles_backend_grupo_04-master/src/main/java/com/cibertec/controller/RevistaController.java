package com.cibertec.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cibertec.util.AppSettings;

@RestController
@RequestMapping("/url/revista")
@CrossOrigin(origins = AppSettings.URL_CROSS_ORIGIN)
public class RevistaController {

}
