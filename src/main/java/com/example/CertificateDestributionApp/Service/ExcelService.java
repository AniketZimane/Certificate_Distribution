package com.example.CertificateDestributionApp.Service;

import com.example.CertificateDestributionApp.Entity.CertificateData;
import com.example.CertificateDestributionApp.Repo.CertificateDataRepo;
import org.springframework.stereotype.Service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Service
public class ExcelService {

    @Autowired
    private CertificateDataRepo repository;

    public void saveCertificateData(MultipartFile file) {
        try (InputStream is = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(is)) {

            Sheet sheet = workbook.getSheetAt(0);
            boolean isHeader = true;

            for (Row row : sheet) {
                if (isHeader) {
                    isHeader = false; // skip header row
                    continue;
                }

                String email = row.getCell(0).getStringCellValue();
                String name = row.getCell(1).getStringCellValue();
                String type = row.getCell(2).getStringCellValue();
                System.out.println("Email: " + email + ", Name: " + name + ", Type: " + type); // DEBUG

                CertificateData certificate = new CertificateData(email, name, type);
                repository.save(certificate);
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to store Excel data: " + e.getMessage());
        }
    }
}
