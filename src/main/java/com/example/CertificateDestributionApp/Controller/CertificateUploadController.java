package com.example.CertificateDestributionApp.Controller;

import com.example.CertificateDestributionApp.Service.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/excel")
@CrossOrigin(origins = "*") // Allow requests from frontend
public class CertificateUploadController {

    @Autowired
    private ExcelService excelService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadExcel(@RequestParam("file") MultipartFile file) {
        String message = "";

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("No file uploaded.");
        }

        try {
            excelService.saveCertificateData(file);
            message = "Excel file uploaded and certificate data saved successfully!";
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            e.printStackTrace();
            message = "An error occurred while processing the Excel file.";
            return ResponseEntity.status(500).body(message);
        }
    }
}

