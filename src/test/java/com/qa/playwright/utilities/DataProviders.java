package com.qa.playwright.utilities;

import com.qa.playwright.base.BaseTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class DataProviders extends BaseTest {
//    Properties prop;
//    @BeforeClass
//    public void setUpProperty() throws IOException {
//        FileInputStream ip = new FileInputStream("./src/test/resources/config/config.properties");
//        prop = new Properties();
//        prop.load(ip);
//    }

    @DataProvider
    public Object[][] getProductData() {
        return new Object[][] {
                { "Macbook" },
                { "iMac" },
                { "Samsung" }
        };
    }

    @DataProvider(name = "swagLabData")
    public Object[][] getCredentials() throws IOException {
        String fileName = prop.getProperty("excelFileName");
        String excelFilePath = System.getProperty("user.dir")+"\\src\\test\\resources\\testData\\"+fileName;
        String sheetName = prop.getProperty("sheetName");
        ExcelUtilities excel = new ExcelUtilities(excelFilePath, sheetName);
        int rowCount = excel.getRowCount();
        int cellCount = excel.getCellCount(0);
        Object[][] data = new Object[rowCount-1][cellCount];
        for (int row = 1 ; row < rowCount ; row++) {
            for (int cell = 0 ; cell < cellCount ; cell++) {
                data[row - 1][cell] = excel.getCellValue(row, cell);
            }
        }
        return data;
    }
}
