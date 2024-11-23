package ru.erdc.acts.controllers;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Phrase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.erdc.acts.entities.Workstation;
import ru.erdc.acts.repositories.WorkstationRepository;
import ru.erdc.acts.repositories.specifications.ProductSpecifications;
import ru.erdc.acts.services.FileStorageService;
import ru.erdc.acts.services.WorkstationsService;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Controller
public class WorkstationController {
    @Autowired
    private WorkstationsService workstationsService;
    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    public void setWorkstationService(WorkstationsService workstationsService) {
        this.workstationsService = workstationsService;
    }

    @Autowired
    private WorkstationRepository workstationRepository;

    @Autowired
    public void setWorkstationsService(WorkstationsService workstationsService) {
        this.workstationsService = workstationsService;
    }

    @Autowired
    public WorkstationController(WorkstationsService workstationsService, FileStorageService fileStorageService) {
        this.workstationsService = workstationsService;
        this.fileStorageService = fileStorageService;
    }
//    @GetMapping("/")
//    public String index() {
//        return "index";
//    }

    @GetMapping("/comps")
    public String showWorkstationsPage(Model model, @RequestParam(name = "word", required = false) String word) {
        Specification<Workstation> spec = Specification.where(null);
        StringBuilder filtersBuilder = new StringBuilder();
        if (word != null && !word.isEmpty()) {
            spec = spec.and(ProductSpecifications.titleContains(word));
            filtersBuilder.append("&word=" + word);
        }

        model.addAttribute("word", word);
        List<Workstation> workstationsList = workstationsService.findAllBySpec(spec);
        model.addAttribute("workstations", workstationsList);
        return "comps";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(Model model, @PathVariable(name = "id", required = false) Long id) {
        Workstation workstation = workstationsService.findById(id);
        model.addAttribute("workstation", workstation);
        return "edit_comp";
    }

    @PostMapping("/edit")
    public String saveModifiedWorkstation(@ModelAttribute(name = "workstation") Workstation workstation) {
        workstationsService.save(workstation);
        return "redirect:/comps";
    }

    @GetMapping("/delete/{id}")
    public String deleteWorkstation(@PathVariable(name = "id") Long id, Model model) {
        Workstation workstation = workstationsService.findById(id);
        workstationRepository.delete(workstation);
        return "redirect:/comps";
    }

    // Генератор PDF ------------------------
    @GetMapping("/pdf/{id}")
    public String pdfWorkstation(@PathVariable(name = "id") Long id, Model model) {
        String fontPath = "times.ttf";
        Font russianFont1 = FontFactory.getFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font russianFont2 = FontFactory.getFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        russianFont1.setColor(BaseColor.DARK_GRAY);
        russianFont1.setStyle(Font.BOLD);

        Document document = new Document();
        Workstation workstation = workstationsService.findById(id);
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu");
        String nowDate = now.format(formatter);
        PdfPTable table = new PdfPTable(4);
        float[] widths = {1f, 3f, 2f, 2f};

        try {
            table.setWidths(widths);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }

        table.setTotalWidth(500);
        table.setLockedWidth(true);

        try {
            PdfWriter.getInstance(document, new FileOutputStream(workstation.getId() + "_act.pdf"));
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        document.open();

        String para0 = "                                                Акт приема-передачи материальных ценностей работнику \n \n";
        String block1 = "                                                                                                                               г. " + workstation.getLocation() + "\n " +
                        "                                                                                                                     от " + nowDate + " № " + workstation.getId() + "\n\n";
        String para1 = "        Акционерное общество _вставить наименование организации_, именуемое в дальнейшем «Работодатель», в лице эксперта Иванова Иван Иваныча, с одной стороны и " + workstation.getOwner() + " именуемым в дальнейшем «Работник», с другой стороны, составили настоящий акт о следующем: \n \n";
        String para2 = "        1.\tВ соответствии с Трудовым договором, Работодатель передал, а Работник принял следующие материальные ценности для выполнения своих должностных обязанностей: \n";
        table.setWidthPercentage(95);

        table.addCell(new Phrase("№", russianFont2));
        table.addCell(new Phrase("Наименование оборудования", russianFont2));
        table.addCell(new Phrase("Серийный", russianFont2));
        table.addCell(new Phrase("Примечание", russianFont2));
        table.addCell("1");
        table.addCell(workstation.getComputer());
        table.addCell(workstation.getSerial_number());
        table.addCell("                ");


        Paragraph paragraph0 = new Paragraph(para0, russianFont1);
        Paragraph paragraphb = new Paragraph(block1, russianFont2);
        Paragraph paragraph1 = new Paragraph(para1, russianFont2);
        Paragraph paragraph2 = new Paragraph(para2, russianFont2);
        try {
            document.add(paragraph0);
            document.add(paragraphb);
            document.add(paragraph1);
            document.add(paragraph2);
            document.add(table);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
        document.close();
        return "redirect:/comps";
    }

    @GetMapping("/add/{id}")
    public String showAddFileForm(@PathVariable(name = "id") Long id, Model model) {
        model.addAttribute("workstation", workstationsService.findById(id));
        return "add-file";
    }

    @GetMapping("/edit")
    public String addNewWorkstation(Model model, @RequestParam(name = "id", required = false) Long id) {
        Workstation workstation = null;
        if (id != null) {
            workstation = workstationsService.findById(id);
        } else {
            workstation = new Workstation();
        }
        model.addAttribute("workstation", workstation);
        return "edit_comp";
    }

    @PostMapping("/add/{id}")
    public String addFileToWorkstation(@PathVariable(name = "id") Long id, @RequestParam("file") MultipartFile file) throws IOException {
        workstationsService.addFileToWorkstation(id, file);
        return "redirect:/comps";
    }
}