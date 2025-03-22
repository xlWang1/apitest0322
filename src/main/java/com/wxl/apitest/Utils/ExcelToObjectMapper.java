package com.wxl.apitest.Utils;
import com.wxl.apitest.model.RequestDTO;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelToObjectMapper {
    public static List<RequestDTO> readExcel(String filePath) throws Exception {
        List<RequestDTO> requestDTOs = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            Row headerRow = sheet.getRow(0); // 获取标题行
            List<String> columnNames = new ArrayList<>();
            // 提取标题行内容
            for (Cell cell : headerRow) {
                cell.setCellType(CellType.STRING);
                columnNames.add(cell.getStringCellValue().trim());
            }
            // 遍历数据行（从第二行开始）
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row dataRow = sheet.getRow(i);
                RequestDTO RequestDTO = new RequestDTO();

                // 动态映射字段
                for (int j = 0; j < columnNames.size(); j++) {
                    Cell cell = dataRow.getCell(j);
                    String columnName = columnNames.get(j);
                    String cellValue = getCellValueAsString(cell);
                    // 根据列名手动映射
                    switch (columnName) {
                        case "用例编号" -> RequestDTO.setId(Integer.parseInt(cellValue));
                        case "用例说明" -> RequestDTO.setDescription(cellValue);
                        case "所属模块" -> RequestDTO.setOwnModel(cellValue);
                        case "请求方式" -> RequestDTO.setRequestMethod(cellValue);
                        case "请求地址" -> RequestDTO.setUrl(cellValue);
                        case "请求参数" -> RequestDTO.setParameters(cellValue);
                        case "连接类型" -> RequestDTO.setContentType(cellValue);
                        case "预期结果" -> RequestDTO.setExceptString(cellValue);
                        case "实际结果" -> RequestDTO.setActual(cellValue);
                        case "提取参数" -> RequestDTO.setExtractParameter(cellValue);
                    }
                }
                requestDTOs.add(RequestDTO);
            }
        }
        return requestDTOs;
    }
    public static String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    return String.valueOf((int) cell.getNumericCellValue()); // 根据需求调整数值格式
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }
}
