import com.baizhi.dao.BannerDao;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.FontFormatting;
import org.apache.poi.ss.util.CellRangeAddress;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.util.Date;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestPoi {

    @Autowired
    BannerDao bannerDao;

    @Test
    public void test01(){
        //创建Excle文档
        HSSFWorkbook workbook=new HSSFWorkbook();
        //创建一个工作簿
        HSSFSheet sheet=workbook.createSheet();
        //设置列宽度
        sheet.setColumnWidth(3,15*256);
        //创建行对象
        HSSFRow row=sheet.createRow(0);
        //设置行高
        row.setHeight((short)2000);
        //创建单元格
        //设置样式
        HSSFFont font=workbook.createFont();
        font.setBold(true);
        font.setColor((short)20);
        font.setFontHeightInPoints((short)20);
        //需要计算机支持该字体
        font.setFontName("楷体");
        font.setItalic(true);
        font.setUnderline(FontFormatting.U_SINGLE);//下划线
        //5起始行  10结束行  3起始列  7结束列
        //合并单元格之后  单元格的值  使用(5,3)值
        CellRangeAddress cellRangeAddress=new CellRangeAddress(5,10,3,7);
        sheet.addMergedRegion(cellRangeAddress);
        //将字体样式放入HSSFCellStyle中
        HSSFCellStyle cellStyle=workbook.createCellStyle();
        cellStyle.setFont(font);
        HSSFCell cell=row.createCell(1);
        cell.setCellStyle(cellStyle);
        //为单元格赋值
        cell.setCellValue("解鑫虎");
        //将Excle文档做本地输出
        try{
            workbook.write(new File("D:\\办公\\java第三阶段\\后期项目\\示例\\"+new Date().getTime()+".xls"));
            workbook.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

}
