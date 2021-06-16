package examples;

import utilities.ExcelUtils;

public class ExcelTest {

    public static void main(String[] args) {

//        String path = "src/test/resources/testdata/TestData.xlsx";
//
//        try {
//            FileInputStream file = new FileInputStream(path);
//            Workbook workbook = new XSSFWorkbook(file);
//            Sheet sheet1 = workbook.getSheet("Page1");
//            String firstName = sheet1.getRow(1).getCell(0).toString();
//            System.out.println(firstName);
//            System.out.println(sheet1.getLastRowNum()); // it will give the index of last row
//            System.out.println(sheet1.getRow(1).getCell(2));
//            sheet1.createRow(3).createCell(0).setCellValue("Srikkanth");
//            System.out.println(sheet1.getRow(3).getCell(0));
//            sheet1.getRow(3).createCell(1).setCellType(CellType.NUMERIC);
//            sheet1.getRow(3).getCell(1).setCellValue(123);
//
//
//            FileOutputStream output = new FileOutputStream(path);
//            workbook.write(output);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        ExcelUtils.openExcelFile("TestData","Page1");
        System.out.println(ExcelUtils.getValue(1,0));
        ExcelUtils.setValue(4,0,"I love it");
        System.out.println(ExcelUtils.getValue(4,0));
    }
}
