package com.example.CertificateDestributionApp.Controller;


import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


@Controller
public class ClientController {
    @GetMapping("/")
    public String getHomePage()
    {
        return "index";
    }
    @GetMapping("/upload/data/")
    public String setCertificateData()
    {
        return "uploaddata";
    }


}
