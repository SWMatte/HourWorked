package assegnazione.ore.entity;

import assegnazione.ore.entity.dto.TableDTO;
import assegnazione.ore.repository.HourWorkedRepository;
import lombok.AllArgsConstructor;
 import lombok.Data;
 import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
 import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.pdmodel.interactive.form.PDTextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;


@Service
@AllArgsConstructor
@Data
public class Pdf {

    @Autowired
    private final HourWorkedRepository hourWorkedRepository;




    public void createPdf(String month) throws IOException {
        File pdfFile = new File("nomeFileDaLeggere");
        PDDocument pdDocument = PDDocument.load(pdfFile);
        PDAcroForm pdAcroForm = pdDocument.getDocumentCatalog().getAcroForm();
        PDField field = pdAcroForm.getField("prendiPrimoAcroform");


        field.setValue("ImpostaValore x quell'acroform");
        field.setReadOnly(true);


        PDPage page = pdDocument.getPage(0);
        PDPageContentStream contentStream = new PDPageContentStream(pdDocument,page,PDPageContentStream.AppendMode.APPEND,true);
        float startX = 100;
        float startY = 350;
        float fieldOffSetX=150;
        float lineSpacing=50;

        PDType1Font commonFont = PDType1Font.HELVETICA;

        List<TableDTO> tableDTO = hourWorkedRepository.getTableValue(month);


        for (int i=0; i < tableDTO.size();i++ ){

            float currentY= startY - (i * lineSpacing * 2);
            contentStream.setFont(commonFont,11);
            contentStream.beginText();
            contentStream.newLineAtOffset(startX,currentY);
            contentStream.showText("giorno: " );
            contentStream.endText();

            PDTextField nameField = new PDTextField(pdAcroForm);
            nameField.setPartialName("giorno"+i);
            nameField.setReadOnly(true);
//             nameField.setValue(tableDTO.get(i).);


        }
    }


}
