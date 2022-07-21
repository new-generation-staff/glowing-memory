# EasyExcel https://github.com/alibaba/easyexcel

#1、导入依赖
```xml
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>easyexcel</artifactId>
    <version>2.2.10</version>
</dependency>
```
#2、创建导入实体
```java
//准备实体表头
@Data
public class ImportContent {
    /**
    * value:表示表头名称
    * index:表示位置
    **/
    @ExcelProperty(value= "字符串标题",index = 0)
    private String string;
    @ExcelProperty("日期标题",index = 1)
    private Date date;
    @ExcelProperty("数字标题",index = 2)
    private Double doubleData;
    /**
     * 忽略这个字段
     */
    @ExcelIgnore
    private String ignore;
}
```

#3、创建监听器
```java
/**
 * 导入demo监听器
 */
public class DemoTestListener extends AnalysisEventListener<ImportContent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(DemoTestListener.class);
    /**
     * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 5;
    List<ImportContent> list = new ArrayList<ImportContent>();
 
    /**
     * 读取时，每条数据都会从这里解析
     */
    @Override
    public void invoke(ImportContent data, AnalysisContext context) {
        Gson gson = new Gson();
        LOGGER.info("解析到一条数据:{}", gson.toJson(data));
        list.add(data);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (list.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            list.clear();
        }
    }
 
    /**
     * 所有数据解析完成了 都会来调用
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveData();
        LOGGER.info("所有数据解析完成！");
    }
    /**
     * 加上存储数据库
     */
    private void saveData() {
        LOGGER.info("{}条数据，开始存储数据库！", list.size());
        //这里真实的保存数据。
        LOGGER.info("存储数据库成功！");
    }
 
    /**
     * 读取表头数据
     */
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        Gson gson = new Gson();
        LOGGER.info("解析到一条头数据:{}", gson.toJson(headMap));
    }
 
    /**
     * 读取额外信息
     * @param extra
     * @param context
     */
    @Override
    public void extra(CellExtra extra, AnalysisContext context) {
        Gson gson = new Gson();
        LOGGER.info("读取到了一条额外信息:{}", gson.toJson(extra));
        switch (extra.getType()) {
            case COMMENT:
                LOGGER.info("额外信息是批注,在rowIndex:{},columnIndex;{},内容是:{}", extra.getRowIndex(), extra.getColumnIndex(),
                        extra.getText());
                break;
            case HYPERLINK:
                if ("Sheet1!A1".equals(extra.getText())) {
                    LOGGER.info("额外信息是超链接,在rowIndex:{},columnIndex;{},内容是:{}", extra.getRowIndex(),
                            extra.getColumnIndex(), extra.getText());
                } else if ("Sheet2!A1".equals(extra.getText())) {
                    LOGGER.info(
                            "额外信息是超链接,而且覆盖了一个区间,在firstRowIndex:{},firstColumnIndex;{},lastRowIndex:{},lastColumnIndex:{},"
                                    + "内容是:{}",
                            extra.getFirstRowIndex(), extra.getFirstColumnIndex(), extra.getLastRowIndex(),
                            extra.getLastColumnIndex(), extra.getText());
                } else {
                    LOGGER.info("Unknown hyperlink!");
                }
                break;
            case MERGE:
                LOGGER.info(
                        "额外信息是超链接,而且覆盖了一个区间,在firstRowIndex:{},firstColumnIndex;{},lastRowIndex:{},lastColumnIndex:{}",
                        extra.getFirstRowIndex(), extra.getFirstColumnIndex(), extra.getLastRowIndex(),
                        extra.getLastColumnIndex());
                break;
            default:
        }
    }
 
    /**
     * 用日期去接字符串 肯定报错，此时需要用到异常处理
     * 在转换异常获取其他异常下会调用本接口。
     * 抛出异常则停止读取。如果这里不抛出异常则 继续读取下一行。
     */
    @Override
    public void onException(Exception exception, AnalysisContext context) {
        LOGGER.error("解析失败，但是继续解析下一行:{}", exception.getMessage());
        // 如果是某一个单元格的转换异常 能获取到具体行号
        // 如果要获取头的信息 配合invokeHeadMap使用
        if (exception instanceof ExcelDataConvertException) {
            ExcelDataConvertException excelDataConvertException = (ExcelDataConvertException)exception;
            LOGGER.error("第{}行，第{}列解析异常", excelDataConvertException.getRowIndex(),
                    excelDataConvertException.getColumnIndex());
        }
    }
}
```

#4、测试  特殊处理的字段可以在Listener中的invoke方法中处理
```java
public class DemoMain {
    public static void main(String[] args) {
        String fileName = "D:\\excel.xlsx";
        //读取单个sheet
        EasyExcel.read(fileName, ImportContent.class, new DemoTestListener())
                // 需要读取批注 默认不读取
                .extraRead(CellExtraTypeEnum.COMMENT)
                // 需要读取超链接 默认不读取
                .extraRead(CellExtraTypeEnum.HYPERLINK)
                // 需要读取合并单元格信息 默认不读取
                .extraRead(CellExtraTypeEnum.MERGE).sheet()
                .doRead();
        //多个sheet，读取全部
        // 这里需要注意 DemoDataListener的doAfterAllAnalysed 会在每个sheet读取完毕后调用一次。然后所有sheet都会往同一个DemoDataListener里面写。
        EasyExcel.read(fileName, ImportContent.class, new DemoTestListener()).doReadAll();
 
        //以下定义自定义格式转换
        EasyExcel.read(fileName, ImportContent.class, new DemoTestListener())
                // 若全局使用自定义转换可以用registerConverter，所有java为string,excel为string的都会用这个转换器。
                // 如果就想单个字段使用请使用@ExcelProperty 指定converter
                // .registerConverter(new CustomStringStringConverter())
                .sheet()
                //如果多行头，可以设置其他值。默认第一行为行头。
                .headRowNumber(1)
                .doRead();
 
        //可以同步返回进行数据处理，但是不推荐，数据量大会放到内存里。
        List<ImportContent> list = EasyExcel.read(fileName).head(ImportContent.class).sheet().doReadSync();
        List<ImportContent> list = EasyExcel.read(new File(fileName), ImportContent.class, new DemoTestListener()).sheet().doReadSync();
    }

    //写数据
    private List<DemoData> data() {
        List<ImportContent> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ImportContent data = new ImportContent();
            data.setString("字符串" + i);
            data.setDate(new Date());
            data.setDoubleData(0.56);
            list.add(data);
        }
        return list;
    }
    
    public void simpleWrite() {
        String fileName = PATH + "EasyTest.xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // 如果这里想使用03 则 传入excelType参数即可
        EasyExcel.write(fileName, ImportContent.class).sheet("模板").doWrite(data());

    }
}
```