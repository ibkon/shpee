package com.xentn.shpee.base;

import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.IOUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.awt.image.BufferedImage;
import java.io.*;

/***
 * 文档生成
 */
public class BuildDocx {
    XWPFDocument    document;
    OutputStream    outputStream;
    public BuildDocx(){
        this(null);
    }
    public BuildDocx(OutputStream outputStream){
        this.outputStream   = outputStream;
        document = new XWPFDocument();
    }
    //添加标题
    public void addTest(String test){
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun run = paragraph.createRun();
        run.setText(test);
        run.setColor("000000");
        run.setFontSize(20);
    }
    //添加图片
    public void addImage(String path,String fileName,int type) {
        XWPFParagraph   paragraph   = document.createParagraph();
        XWPFRun         run         = paragraph.createRun();
        InputStream     inputStream = null;
        BufferedImage   image       = null;
        byte[]          bs          = null;

        paragraph.setAlignment(ParagraphAlignment.CENTER);

        try {
            inputStream = new FileInputStream(path);
            image = Thumbnails.of(path).size(500,500).asBufferedImage();
            bs = IOUtils.toByteArray(inputStream);
            run.addPicture(new ByteArrayInputStream(bs), type,"", Units.toEMU(image.getWidth()),Units.toEMU(image.getHeight()));
            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }

    }

    public void flush() throws IOException {
        document.write(new FileOutputStream("test.docx"));
        document.close();
    }
}
