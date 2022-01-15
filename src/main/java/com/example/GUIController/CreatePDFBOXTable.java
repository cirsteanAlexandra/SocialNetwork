package com.example.GUIController;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;

/**
 * @author Bharathi Raja
 */
public class CreatePDFBOXTable {

    public static void main(String[] args) {
        try {
            createTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createTable() throws Exception {
        String path = "d:\\tablepdf.pdf";  //location to store the pdf file
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);
        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        PDRectangle mediabox = page.getArtBox();
        float margin = 50;
        float startX = mediabox.getLowerLeftX() + margin;
        float startY = mediabox.getUpperRightY() - margin;
        final float rowHeight = 20f;



        //SPECIFY THE NUMBER OF ROWS TO BE CREATED
        //example: use list.size() to get the number of elements
        final int rows = 3;



        final float tableWidth = page.getArtBox().getWidth() - (2 * margin);

        //draw the rows
        float nexty = 650;
        for (int i = 0; i <= rows; i++) {
            if (i == 0 || i == 1) {
                contentStream.drawLine(margin, nexty, margin + tableWidth, nexty);
            }
            nexty -= rowHeight;
        }

        contentStream.drawLine(margin, 300, margin + tableWidth, 300);
        int y = 650;

        //drawing columns the columns
        float nextx = 50;

        int h = 300;
        contentStream.drawLine(nextx, y, nextx, h);
        nextx = 100;
        contentStream.drawLine(nextx, y, nextx, h);
        nextx = 350;
        contentStream.drawLine(nextx, y, nextx, h);
        nextx = 420;
        contentStream.drawLine(nextx, y, nextx, h);
        nextx = 475;
        contentStream.drawLine(nextx, y, nextx, h);
        nextx = 545;
        contentStream.drawLine(nextx, y, nextx, h);

        //now add the text

        contentStream.close();

        document.save(path);
        document.close();
    }
}

