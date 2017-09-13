package com.example.springbootdownloadexample.serialization;

import com.example.springbootdownloadexample.model.Person;
import com.example.springbootdownloadexample.model.PersonList;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class PdfSerializer {

    public static ByteArrayInputStream toPdf(PersonList people) throws Exception {
        PDDocument document = new PDDocument();
        PDPage singlePage = new PDPage();
        PDFont courierBoldFont = PDType1Font.COURIER_BOLD;
        int fontSize = 12;
        document.addPage(singlePage);

        PDPageContentStream contentStream = new PDPageContentStream(document, singlePage);
        contentStream.setFont(courierBoldFont, fontSize);
        int x = 10;
        int y = 750;
        for (Person person : people.getList()) {
            contentStream.beginText();
            contentStream.newLineAtOffset(x, y);
            contentStream.showText(person.toString());
            contentStream.endText();
            y -= 20;
        }

        contentStream.close();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        document.save(out);
        return new ByteArrayInputStream(out.toByteArray());
    }
}
