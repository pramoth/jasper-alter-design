package th.co.geniustree.dev.jasper.alter.design;

import java.awt.Color;
import java.io.File;
import java.lang.reflect.Field;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRRectangle;
import net.sf.jasperreports.engine.JRStyle;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.util.JRSaver;

/**
 * @author Pramoth Suwanpech
 * Note some part of code copy from jasper report example.
 */
public class AlterDesignApp {

    public static void main(String[] args) throws Exception {
        new AlterDesignApp().fill(20);
        new AlterDesignApp().fill(160);
    }

    public void fill(int leftMargin) throws Exception {
        long start = System.currentTimeMillis();
        File sourceFile = new File("AlterDesignReport.jasper");
        System.err.println("jasper path : " + sourceFile.getAbsolutePath());
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(getClass().getResource("AlterDesignReport.jasper"));
        /**
         * **************I emphasize to you by dedicated to another method ***************************
         */
        setJasperReportFieldAtRuntime("leftMargin",jasperReport, leftMargin);
        /**
         * *****************************************
         */

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, (JRDataSource) null);

        File destFile = new File(sourceFile.getParent(), jasperReport.getName() + ".jrprint");
        System.out.println("save to:" + destFile.getAbsolutePath());
        JRSaver.saveObject(jasperPrint, destFile);

        System.err.println("Filling time : " + (System.currentTimeMillis() - start));
        JasperExportManager.exportReportToPdfFile(destFile.getAbsolutePath(), leftMargin + ".pdf");
        System.err.println("PDF creation time : " + (System.currentTimeMillis() - start));
    }

    private void setJasperReportFieldAtRuntime(String feild,JasperReport jasperReport, int leftMargin) throws IllegalAccessException, SecurityException, NoSuchFieldException, IllegalArgumentException {
        // see all of feild at http://jasperreports.sourceforge.net/api/net/sf/jasperreports/engine/base/JRBaseReport.html
        Field leftMarginField = JasperReport.class.getSuperclass().getDeclaredField(feild);
        leftMarginField.setAccessible(true);
        leftMarginField.set(jasperReport, leftMargin);
    }
}
