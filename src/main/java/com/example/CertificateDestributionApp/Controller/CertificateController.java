package com.example.CertificateDestributionApp.Controller;

import com.example.CertificateDestributionApp.Entity.CertificateData;
import com.example.CertificateDestributionApp.Repo.CertificateDataRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.util.List;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@CrossOrigin(origins = "*") // <-- important if calling from browser
@RestController
public class CertificateController {

    @Autowired
    private CertificateDataRepo participantRepository;

    @PostMapping("/generate-certificate")
    public ResponseEntity<?> generateCertificates(@RequestBody Map<String, String> request) {
        try {
            String email = request.getOrDefault("email", "").trim();
            String name = request.getOrDefault("name", "").trim();

            if (email.isEmpty() && name.isEmpty()) {
                return ResponseEntity.badRequest().body("Please provide email or name");
            }

            // Fetch participants based on email or name
            List<CertificateData> participants = email.isEmpty()
                    ? participantRepository.findAllByNameIgnoreCase(name)
                    : participantRepository.findAllByEmailIgnoreCase(email);

            if (participants == null || participants.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No certificates found for the given email or name");
            }

            // Prepare ZIP stream
            ByteArrayOutputStream zipBaos = new ByteArrayOutputStream();
            ZipOutputStream zipOut = new ZipOutputStream(zipBaos);

            for (CertificateData participant : participants) {
                String eventType = participant.getType();
                String templatePath;
                String filename = participant.getName().replaceAll("[^a-zA-Z0-9 ]", "").replaceAll(" ", "_");
                String safeEventType = eventType.replaceAll("[^a-zA-Z0-9 ]", "").replaceAll(" ", "_");

                if ("Techathon(Project Competition)".equalsIgnoreCase(eventType)) {
                    templatePath = "static/images/Techathon_Certificate.jpg";
                    filename = "techathon_" + participant.getName()+ "_" + safeEventType  + ".jpg";
                }
                if ("volunteer".equalsIgnoreCase(eventType)) {
                    templatePath = "static/images/VolentiersList.jpg";
                    filename = "techocrats" + participant.getName()+ "_" + safeEventType  + ".jpg";
                
                }
                } else {
                    templatePath = "static/images/CodeFiesta_Certificate.jpg";
                    filename = "codefiesta_" + participant.getName()+ "_" + safeEventType   + ".jpg";
                }

                try (InputStream templateStream = getClass().getClassLoader().getResourceAsStream(templatePath)) {
                    if (templateStream == null) continue;

                    BufferedImage image = ImageIO.read(templateStream);
                    Graphics2D g = image.createGraphics();

                    g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                    g.setFont(new Font("Arial", Font.BOLD, 30));
                    g.setColor(Color.BLACK);

                    // Adjust text placement
                    if ("Techathon(Project Competition)".equalsIgnoreCase(eventType)) {
                        g.drawString(participant.getName().toUpperCase(), 590, 490);
                    } else {
                        g.drawString(participant.getName().toUpperCase(), 590, 448);
                        g.drawString(eventType, 650, 490);
                    }

                    g.dispose();

                    ByteArrayOutputStream imageBaos = new ByteArrayOutputStream();
                    ImageIO.write(image, "jpg", imageBaos);
                    imageBaos.flush();

                    zipOut.putNextEntry(new ZipEntry(filename));
                    zipOut.write(imageBaos.toByteArray());
                    zipOut.closeEntry();
                    imageBaos.close();
                }
            }

            zipOut.close();

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"certificates.zip\"")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(new InputStreamResource(new ByteArrayInputStream(zipBaos.toByteArray())));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error generating certificates: " + e.getMessage());
        }
    }



}
