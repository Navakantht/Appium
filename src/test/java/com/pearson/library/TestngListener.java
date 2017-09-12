/**
* @description		: Implementation of ITestListener for generating reports, method to send mail after execution of suite
*/
package com.pearson.library;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.io.FileUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
public class TestngListener implements ITestListener {
	public static File sHtmlReports;
	public static File sTestngReports;
	public static File sPdfReports;
	public static String sdateTime;
	public static String sdate;
	public static int iPassCount=0;
	public static int iFailCount=0;
	public static int iSkippedCount=0;
	public String kirwaFile=GenericLib.sDirPath+"\\KIRWA.properties";
	public String kirwalogopath=GenericLib.sDirPath+"\\src\\test\\resources\\images\\Pearson.PNG";
	public static ArrayList sTestName= new ArrayList<String>();
	public static ArrayList sStatus= new ArrayList<String>();
	public static ArrayList sDescription= new ArrayList<String>();
	public TestngListener() throws IOException 
	{
		Date date = new Date();
        SimpleDateFormat sdtf=new SimpleDateFormat("dd-MM-yyyy_hh_mm_ss");
        SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
        sdateTime = sdtf.format(date);
		sdate = sdf.format(date);
		sTestngReports= new File(GenericLib.sDirPath+"//..//Reports//TestNGReports");
		sPdfReports = new File(GenericLib.sDirPath+"//..//Reports//PDFReports");
		if(!sTestngReports.exists())
		{
			FileUtils.forceMkdir(sTestngReports);
		}
		if(!sPdfReports.exists())
		{
			FileUtils.forceMkdir(sPdfReports);
		}
		String path="file:///"+kirwalogopath;
		GenericLib.setConfigValue(kirwaFile, "KIRWA.proj.logo",path);
		System.setProperty("KIRWA.reporter.config", "KIRWA.properties");
	}
	
	public void onTestStart(ITestResult result) 
	{
		GenericLib.setConfigValue(GenericLib.sConfigFile, "DATE", sdateTime);
	}
	public void onTestSuccess(ITestResult result) 
	{
		sDescription.add(result.getMethod().getDescription());
		iPassCount = iPassCount+1;
		GenericLib.setStatus(result.getName().toString(), "Passed",sTestName,sStatus);
	}
	public void onTestFailure(ITestResult result) 
	{
		sDescription.add(result.getMethod().getDescription());
		iFailCount = iFailCount+1;
		 GenericLib.setStatus(result.getName().toString(), "Failed",sTestName,sStatus);
	}
	public void onTestSkipped(ITestResult result) 
	{
		sDescription.add(result.getMethod().getDescription());
		iSkippedCount = iSkippedCount+1;
		GenericLib.setStatus(result.getName(), "Skipped",sTestName,sStatus);
	}
	public void onTestFailedButWithinSuccessPercentage(ITestResult result)
	{
	}
	public void onStart(ITestContext context) 
	{
	}
	public void onFinish(ITestContext context) 
	{
		 File testOuput = new File(GenericLib.sDirPath+"\\test-output");    
		 String sTestngReports= GenericLib.sDirPath+"\\..\\Reports\\TestNGReports\\TestNG_"+sdateTime;	
		 File pdfReports = new File(sPdfReports+"\\PDFReports"+sdateTime+".pdf");
         getPieChart();
         getBarChart();
        PdfGenerater pdf = new PdfGenerater();
        pdf.toExecute(sTestName, sDescription, sStatus, iPassCount, iFailCount, iSkippedCount, pdfReports);
         iPassCount=context.getPassedTests().size(); 
     	 iFailCount=context.getFailedTests().size(); 
     	 iSkippedCount=context.getSkippedTests().size(); 
     	 int iTotal_Executed = iPassCount+iFailCount+iSkippedCount;
     	 getPieChart();
     	 //sendMail(iPassCount, iFailCount, iSkippedCount, iTotal_Executed, pdfReports);
     	try
        {
       	 FileUtils.copyDirectoryToDirectory(testOuput,new File(sTestngReports));
          System.out.println("testoutput is moved");
        }
        catch(Exception e)
        {
          System.out.println("testoutput is not moved");
        }
	}
	public static void getPieChart()
	{
		DefaultPieDataset pieDataset = new DefaultPieDataset();
		pieDataset.setValue("FAIL", new Integer(iFailCount));
		pieDataset.setValue("SKIP", new Integer(iSkippedCount));
		pieDataset.setValue("PASS", new Integer(iPassCount));
		
		JFreeChart piechart = ChartFactory.createPieChart("Pie Chart", pieDataset, true, true, false);
		PiePlot plot = (PiePlot) piechart.getPlot();
		 
		plot.setSectionPaint("FAIL", Color.RED);
		plot.setSectionPaint("SKIP", Color.ORANGE);
		plot.setSectionPaint("PASS", new Color(192*85+192*104+192*47));
		plot.setBackgroundPaint(new Color(192*65536+192*256+192));
		plot.setExplodePercent("FAIL", 0.05);
	    plot.setSimpleLabels(true);
	    plot.setSectionOutlinesVisible(true);
	    
	    PieSectionLabelGenerator gen = new StandardPieSectionLabelGenerator("{0}: {1} ({2})", new DecimalFormat("0"), new DecimalFormat("0%"));
	    plot.setLabelGenerator(gen);
	    plot.setLabelFont(new Font("SansSerif", Font.BOLD, 12));
		 try {
				ChartUtilities.saveChartAsJPEG(new File(System.getProperty("user.dir")+"\\src\\test\\resources\\images\\PieChart.png"), piechart, 400, 400);
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	public static void getBarChart()
	{
		  final String series1 = "First";
	       final String series2 = "Second";
	       final String series3 = "Third";
	       final String category1 = "Status";

	       DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
	       dataSet.addValue(iPassCount, series1, "Status");
	       dataSet.addValue(iFailCount, series2, "Status");
	       dataSet.addValue(iSkippedCount,series3, "Status");
	 		
	 		
	 		JFreeChart chart = ChartFactory.createBarChart("Bar Graph", "Execution Status", "Testcases",dataSet, PlotOrientation.VERTICAL, false, true, false);
	 		CategoryPlot barplot = chart.getCategoryPlot();
	 		barplot.setBackgroundPaint(Color.white);
	 		barplot.setBackgroundPaint(new Color(192*65536+192*256+192));
	 		
	 		barplot.setDomainGridlinePaint(Color.white);
	 		
	 		NumberAxis rangeAxis = (NumberAxis) barplot.getRangeAxis();
	 		rangeAxis.setRange(0.0, 70.0);
	        rangeAxis.setTickUnit(new NumberTickUnit(10));
	        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
	        rangeAxis.setAutoRangeIncludesZero(true);
	 	    
	 		 final BarRenderer renderer = (BarRenderer) barplot.getRenderer();
	         renderer.setDrawBarOutline(false);
	         renderer.setMaximumBarWidth(0.20);
	         
	         final GradientPaint gp0 = new GradientPaint(
	                 0.0f, 0.0f, new Color(192*85+192*104+192*47), 
	                 0.0f, 0.0f, Color.lightGray
	             );
	         final GradientPaint gp1 = new GradientPaint(
	        		 
	             0.0f, 0.0f, Color.red, 
	             0.0f, 0.0f, Color.lightGray
	         );
	        
	         final GradientPaint gp2 = new GradientPaint(
	             0.0f, 0.0f, Color.orange, 
	             0.0f, 0.0f, Color.lightGray
	         );
	         renderer.setSeriesPaint(0, gp0);
	         renderer.setSeriesPaint(1, gp1);
	         renderer.setSeriesPaint(2, gp2);

	 		
	 		 try {
					ChartUtilities.saveChartAsJPEG(new File(System.getProperty("user.dir")+"\\src\\test\\resources\\images\\BarChart.png"), chart, 400, 400);
				} catch (IOException e) {
					e.printStackTrace();
				}
	}
	
	public void sendMail(int iPassCount, int iFailCount, int skippedCount, int iTotal_Executed,File pdfReports)
	{
		
		Properties props = new Properties();
		String message = "<p>Hi Team,</p><div style=\"font-family:Verdana;\">Find the test automation execution status as below. For detail information, find the attached pdf file.</div><p></p><p></p><p></p><p></p>"
				+ "<p><div style=\"font-family:Verdana;\"><b> EXECUTION SUMMARY : </b></div></p>"
				+ "<p><div style=\"font-family:Verdana;\"><a href='ftp://111.93.145.49/HTMLReports/index.html'>AbofReport</a></div></p>"
				+"<p style=\"font-family:Verdana;\"><b>Note:</b> To Access Abof Report Please Click On The Above AbofReport Link Use The Below Credentials<br><br><i>UserName: <b>Abof</b></i><br><i>Password:<b>Pa$$w0rd!%#927</b></i><i></p>"
				+ "<table bgcolor=\"#BDE4F6\" style=\"border-radius: 20px; padding: 25px;\"><tr><td>&nbsp;&nbsp;&nbsp;<table style=\"height:180px; width:200px; border-width:2px; border-style:groove; float: left\"><tbody>"
				+ "<tr style=\"outline: thin solid; font-family:Verdana; color: #000000; text-align: left; border-width:2px; \"><th style=\"outline: thin solid;\">Total Executed</th><td style=\"outline: thin solid; font-weight: bold;\">&nbsp;&nbsp;"+iTotal_Executed+"&nbsp;&nbsp;</td></tr>"
	        	+ "<tr style=\"outline: thin solid;  font-family:Verdana; color: #000000; text-align: left; border-width:2px; \"><th style=\"outline: thin solid;\">Passed</th><td style=\"outline: thin solid; font-weight: bold;\">&nbsp;&nbsp;"+iPassCount+"&nbsp;&nbsp;</td></tr>"
	        	+ "<tr style=\"outline: thin solid; font-family:Verdana; color: #000000; text-align: left; border-width:2px; \"><th style=\"outline: thin solid;\">Failed</th><td style=\"color: Red; outline: thin solid; font-weight: bold;\">&nbsp;&nbsp;"+iFailCount+"&nbsp;&nbsp;</td></tr>"
	        	+ "<tr style=\"outline: thin solid; font-family:Verdana; color: #000000; text-align: left; border-width:2px; \"><th style=\"outline: thin solid;\">Skipped</th><td style=\"outline: thin solid; font-weight: bold;\">&nbsp;&nbsp;"+skippedCount+"&nbsp;&nbsp;</td></tr>"
	        	+ "</tbody></table></td>"
	        	+ "&nbsp;&nbsp;&nbsp;"
	        	+ "<td><img src=\"cid:image\" style=\"height:200px; width: 200px; outline: thin solid;\"></td></tr></table>"
	        	+ "<p></p><div style=\"font-family:Verdana;\">Regards,</div><p></p>"
	        	+ "<div style=\"font-family:Verdana;\">CrowdBetaTesters Team</div>";
				
		props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.starttls.enable", "true");
	    props.put("mail.smtp.host", "smtp.gmail.com");
	    props.put("mail.smtp.port", "587");
	    props.put("mail.debug", "true");
	    Session session = Session.getInstance(props,new Authenticator() 
	    {
	    	protected PasswordAuthentication getPasswordAuthentication() 
	    	{
	    		return new PasswordAuthentication(GenericLib.getConfigValue(GenericLib.sConfigFile,"FROM_EMAILID"), GenericLib.getConfigValue(GenericLib.sConfigFile,"FROM_PWD"));
	 		}
	 	});
	   try 
	   {
		   MimeMessage msg = new MimeMessage(session);
		   msg.setFrom(new InternetAddress(GenericLib.getConfigValue(GenericLib.sConfigFile,"FROM_EMAILID")));
		   msg.setRecipients(Message.RecipientType.TO, GenericLib.getConfigValue(GenericLib.sConfigFile,"TO_EMAILID"));
		   msg.setSubject("Abof Execution Report on "+sdate);
		   msg.setSentDate(new Date());
		   Multipart multipart = new MimeMultipart();
		   MimeBodyPart textPart = new MimeBodyPart();
		   textPart.setContent(message, "text/html");
		   MimeBodyPart messageBodyPart = new MimeBodyPart();
	       DataSource fds = new FileDataSource(System.getProperty("user.dir")+"\\src\\test\\resources\\images\\PieChart.png");
	       messageBodyPart.setDataHandler(new DataHandler(fds));
	       messageBodyPart.setHeader("Content-ID", "<image>");
	       multipart.addBodyPart(textPart);
	       multipart.addBodyPart(messageBodyPart);
		   MimeBodyPart attachementPart = new MimeBodyPart();
		   attachementPart.attachFile(pdfReports);
		   multipart.addBodyPart(attachementPart);
		   msg.setContent(multipart);
		   Transport.send(msg);
		   System.out.println("Mail Sent Successfully");
	   	} 
	    catch (Exception ex)
	    {
	   	    ex.printStackTrace();
	   	}
	}

}
