package utilities;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

// This class will have reusable methods to work with Excel files.
public class ExcelUtils {

    /*
     * .openExcelFile(String fileName, String sheetName); -> this method will open excel file with specific file name and sheet name -> no return
     * .getValue(int row, int cell); -> this method will return value on provided row and cell index -> return String
     * .setValue(int row, int cell, String value); -> this method will set value on provided row and cell index -> no return
     */

    private static Workbook workbook;
    private static Sheet sheet;
    private static String path;

    /**
     * This method will open excel file with specific file name and sheet name
     * @param fileName
     * @param sheetName
     */
    public static void openExcelFile(String fileName, String sheetName) {
        path = "src/test/resources/testdata/" + fileName + ".xlsx";
        try {
            FileInputStream file = new FileInputStream(path);
            workbook = new XSSFWorkbook(file);
            sheet = workbook.getSheet(sheetName);
        } catch (IOException e) {
            System.out.println("Excel file path is invalid.");
        }
    }

    /**
     * This method will return value on provided row and cell index
     * @param row
     * @param cell
     * @return value
     */
    public static String getValue(int row, int cell) {
        try {
            return sheet.getRow(row).getCell(cell).toString();
        } catch (NullPointerException e) {
            System.out.println("Null Pointer Exception please check you row and cell");
            return null;
        }
    }

    /**
     * This method will set value on provided row and cell index
     * @param row
     * @param cell
     * @param value
     */
    public static void setValue(int row, int cell, String value) {
        Row row1;
        if (row >= sheet.getPhysicalNumberOfRows()) { // it will return number of rows
            row1 = sheet.createRow(row);
        } else {
            row1 = sheet.getRow(row);
        }
        Cell cell1;
        if (cell >= row1.getPhysicalNumberOfCells()) {
            cell1 = row1.createCell(cell);
        } else {
            cell1 = row1.getCell(cell);
        }
        cell1.setCellValue(value);
        FileOutputStream output = null;
        try {
            output = new FileOutputStream(path);
            workbook.write(output);
        } catch (IOException e) {
            System.out.println("Excel file path is invalid.");
        } finally {
            try {
                output.close();
            } catch (IOException e) {
                System.out.println("Had exception while tried to close input object.");
            }
        }
    }


}
