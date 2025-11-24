package com.qa.playwright.utilities;

import org.testng.annotations.DataProvider;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class DataProviders {
    private static Properties prop = new Properties();
    private static String fileName;
    private static String excelFilePath;
    private static String sheetName;
    // static block loads config once
    static {
        try {
            FileInputStream ip = new FileInputStream("./src/test/resources/config/config.properties");
            prop.load(ip);
            fileName = prop.getProperty("excelFileName");
            excelFilePath = System.getProperty("user.dir")+"\\src\\test\\resources\\testData\\"+fileName;
            sheetName = prop.getProperty("sheetName");
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties file.", e);
        }
    }

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

    @DataProvider(name = "productName")
    public String[] productName(ExcelUtilities excel) throws IOException {
        int rowCount = excel.getRowCount();
        String[] data = new String[rowCount-1];
        for (int row = 1 ; row < rowCount ; row++) {
            data[row-1]=excel.getCellValue(row, 0);
        }
        return data;
    }

    @DataProvider(name = "contactDetails")
    public Object[][] contactDetails(ExcelUtilities excel) throws IOException {
        int rowCount = excel.getRowCount();
        int cellCount = excel.getCellCount(0);
        Object[][] data = new Object[rowCount-1][cellCount+1];
        for (int row = 1 ; row < rowCount ; row++) {
            for (int cell = 0 ; cell < cellCount ; cell++) {
                data[row-1][cell] = excel.getCellValue(row, cell);
            }
        }
        return data;
    }

    @DataProvider(name = "swagLabCartData")
    public Object[][] getDetails() throws IOException {
        sheetName = "Cart Details";
        ExcelUtilities excel = new ExcelUtilities(excelFilePath, sheetName);
        Object[] products = productName(excel);
        sheetName = "Contact Details";
        excel = new ExcelUtilities(excelFilePath, sheetName);
        Object[][] data = contactDetails(excel);

        for (Object[] row : data) {
            row[row.length-1]=products;
        }
        return data;
    }

    @DataProvider(name = "swagLabLoginCartData")
    public Object[][] getLoginCartDetails() throws IOException {
        Object[][] login_data = getCredentials();
        Object[][] return_data = new Object[login_data.length][login_data[0].length+1];
        Object[][] cart_details = getDetails();
        for (int i = 0 ; i < return_data.length ; i++) {
            for (int j = 0 ; j < return_data[0].length ; j++) {
                if(j >= return_data[0].length-1) {
                    return_data[i][j] = cart_details;
                } else {
                    return_data[i][j] = login_data[i][j];
                }
            }
        }

        return return_data;
    }
}
