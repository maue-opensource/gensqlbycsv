package cn.rofs.excel;

import cn.rofs.excel.constant.SysConstant;
import cn.rofs.excel.dto.ResultDTO;
import cn.rofs.excel.enums.ModelTypeEnum;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import static cn.rofs.excel.constant.SysConstant.DEFAULT_DATA_DIR_PATH;
import static cn.rofs.excel.constant.SysConstant.DEFAULT_RESULT_DIR_PATH;

/**
 * @author rainofsilence
 * @date 2022/8/3 周三
 * @desc sql生成工具类
 */
public class GenSqlUtils {

    /**
     * @param dataFileName 文件名称(xx.csv)
     * @return
     */
    public static ResultDTO<Object> defaultGenerate(String dataFileName) {
        return defaultGenerate(dataFileName, ModelTypeEnum.DEFAULT);
    }

    /**
     * @param dataFileName 文件名称(xx.csv)
     * @param modelType    模版类型
     * @return
     */
    public static ResultDTO<Object> defaultGenerate(String dataFileName, ModelTypeEnum modelType) {
        return generate(dataFileName, DEFAULT_DATA_DIR_PATH, DEFAULT_RESULT_DIR_PATH, modelType);
    }

    /**
     * @param dataFileName  csv文件名称
     * @param dataDirPath   csv文件夹路径(xx/...)
     * @param resultDirPath sql文件夹路径(xx/...)
     * @param modelType     模版类型
     * @return
     */
    public static ResultDTO<Object> generate(String dataFileName, String dataDirPath, String resultDirPath, ModelTypeEnum modelType) {
        if (!checkDataFileNameIsCSV(dataFileName)) {
            return ResultDTO.FAIL("dataFileName不符合规范");
        }
        String[] dataFileName_ = dataFileName.split("[.]");
        String dataFullPath = dataDirPath + File.separator + dataFileName;
        String resultFullPath = resultDirPath + File.separator + dataFileName_[0] + ".sql";

        File csvFile = new File(dataFullPath);
        if (!csvFile.exists()) return ResultDTO.FAIL("csv文件不存在");
        BufferedReader br = null;
        StringBuffer resultSql = new StringBuffer();
        Map<String, Object> headerMap = new HashMap<>();
        try {
            br = new BufferedReader(new FileReader(csvFile));
            String curLine;
            int lineCount = 0;
            while ((curLine = br.readLine()) != null) {
                // TODO handle data
                if (lineCount == 0) {
                    headerMap = handleHeaderData(curLine, modelType);
                }
                resultSql.append(generateSql(curLine, headerMap, modelType));
            }

        } catch (IOException e) {
            e.printStackTrace();
            return ResultDTO.FAIL("读取csv文件出错");
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return ResultDTO.FAIL("读取csv文件出错");
                }
            }
        }
        return ResultDTO.SUCCESS();
    }

    /**
     * 检查数据文件名称是否符合规范
     *
     * @param dataFileName
     * @return
     */
    private static boolean checkDataFileNameIsCSV(String dataFileName) {
        if (dataFileName == null || dataFileName.length() < 1) return false;
        String PATTERN_REGEX_CSV = "[a-zA-Z0-9_@-]+\\.(csv|CSV)$";
        return Pattern.matches(PATTERN_REGEX_CSV, dataFileName);
    }

    /**
     * 处理首行数据
     *
     * @param curLine   行内容
     * @param modelType 模版类型
     * @return
     */
    private static Map<String, Object> handleHeaderData(String curLine, ModelTypeEnum modelType) {
        Map<String, Object> resultMap = new HashMap<>();
        if (ModelTypeEnum.DEFAULT.equals(modelType)) {
            String[] arr = curLine.split(",");
            for (String s : arr) {
                resultMap.put(s.split("::")[0], s.split("::")[1]);
            }
        }
        return resultMap;
    }


    /**
     * 生成sql
     *
     * @param curLine
     * @param headerMap
     * @param modelType
     * @return
     */
    private static StringBuffer generateSql(String curLine, Map<String, Object> headerMap, ModelTypeEnum modelType) {
        if (ModelTypeEnum.DEFAULT.equals(modelType)) {
            String tableName = String.valueOf(headerMap.get(SysConstant.MODEL_DEFAULT_TN));
            String optType = String.valueOf(headerMap.get(SysConstant.MODEL_DEFAULT_OT));
            Integer pkCounts = Integer.parseInt(headerMap.get(SysConstant.MODEL_DEFAULT_PKC).toString());

        }
        return null;
    }
}
