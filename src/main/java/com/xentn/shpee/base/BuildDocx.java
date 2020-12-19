package com.xentn.shpee.base;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

/***
 * 文档生成
 */
public class BuildDocx {

    private XWPFDocument document;
    XWPFParagraph   paragraph;

    public  BuildDocx(){
        this(null);
    }
    public  BuildDocx(String outFile){
        document = new XWPFDocument();
        paragraph = document.createParagraph();
    }
    //添加节点
    public void addNote(String text){
        XWPFRun run = paragraph.createRun();
        run.setText(text);
        run.setFontSize(11);
    }
    //添加图片
    public void addImage(String images){
        XWPFRun run = paragraph.createRun();
        paragraph.setAlignment(ParagraphAlignment.CENTER);

    }
}
