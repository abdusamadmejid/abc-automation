package com.uyghur.utility;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class TestUtility {
    WebDriver driver;
    public void sleep(int seconds){
        try {
            Thread.sleep(seconds*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void waitForElementPresent(WebElement element,int timeOut){
        WebDriverWait wait=new WebDriverWait(driver,timeOut);
        wait.until(ExpectedConditions.visibilityOf(element));
    }
    public String readFromExel(String fileName,String sheetName,int rowNumber,int columnNumber){
      String cellValue=null;
        FileInputStream fileInputStream=null;
        try {
            fileInputStream=new FileInputStream(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        XSSFWorkbook workbook=null;
        try {
            workbook=new XSSFWorkbook(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        XSSFSheet sheet=workbook.getSheet(sheetName);
        XSSFRow row=sheet.getRow(rowNumber);
        if (row==null){
            System.out.println("Empty row,there is no any data");
        }else {
            XSSFCell cell=row.getCell(columnNumber);
            CellType cellType=cell.getCellTypeEnum();
            switch (cellType)
            {
                case NUMERIC:
                    cellValue=String.valueOf(cell.getNumericCellValue());
                    break;
                case STRING:
                    cellValue=cell.getStringCellValue();
                    break;
                default:
                    cellValue=cell.getStringCellValue();
                    break;
            }

        }
        System.out.println(cellValue);
        return cellValue;
    }
    public static String takeScreenShot(WebDriver driver){
        TakesScreenshot ts=(TakesScreenshot) driver;
        File src=ts.getScreenshotAs(OutputType.FILE);
        String path=System.getProperty("user.dir")+"/Screenshot"+System.currentTimeMillis()+".png";
        File destination=new File(path);
        try {
            FileUtils.copyFile(src,destination);
        } catch (IOException e) {
            System.out.println("Capture Failed "+e.getMessage());
        }
        return path;
    }

}
