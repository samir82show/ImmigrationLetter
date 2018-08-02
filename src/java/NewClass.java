
import java.io.File;
import java.io.IOException;
import org.apache.fontbox.ttf.TrueTypeFont;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDTrueTypeFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDTextField;

public class NewClass {

    public static void main(String arg[]) throws IOException {
        System.setProperty("sun.java2d.cmm", "sun.java2d.cmm.kcms.KcmsServiceProvider");
        PDDocument doc = PDDocument.load(new File("c:\\data\\immigration_letters\\EmployeeTransfer.pdf"));
        PDAcroForm acroForm = doc.getDocumentCatalog().getAcroForm();
        PDResources formResources = acroForm.getDefaultResources();
        PDTrueTypeFont font = (PDTrueTypeFont) formResources.getFont(COSName.getPDFName("ddddd"));
        System.out.println("COSName.getPDFName(\"Ubuntu\").............." + formResources.getFont(COSName.getPDFName("Ubuntu")));
// here is the 'magic' to reuse the font as a new font resource
        TrueTypeFont ttFont = font.getTrueTypeFont();

        PDFont font2 = PDType0Font.load(doc, ttFont, true);
        ttFont.close();

        formResources.put(COSName.getPDFName("F0"), font2);

        PDTextField formField = (PDTextField) acroForm.getField("Text2");
        formField.setDefaultAppearance("/F0 0 Tf 0 g");
        formField.setValue("aaas");

        doc.save("c:\\data\\immigration_letters\\filling.pdf");
        doc.close();

    }

}
