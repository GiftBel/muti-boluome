package com.cxu.web.controller;

import com.cxu.web.component.MyLoginInterceptor;
import com.cxu.web.exception.BusinessException;
import com.cxu.web.service.AsyncService;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.*;
import org.apache.commons.io.IOUtils;
import org.krysalis.barcode4j.impl.code39.Code39Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import sun.net.www.protocol.http.HttpURLConnection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

@Controller
public class HelloController {


    @Autowired
    AsyncService asyncService;
    @PostMapping(value = "/user/login")
    public String login(@RequestParam("inputEmail")String inputEmail,
                         @RequestParam("inputPassword")String inputPassword,
                         Map<String,Object> errorMsg,HttpSession session){
        if (!StringUtils.isEmpty(inputEmail) && "123456".equals(inputPassword)){
            session.setAttribute("loginUser","inputEmail");
            return "redirect:/main.html";
        }else{
             errorMsg.put("errorMsg","用户名或密码错误");
            return "login";
        }

    }
    @ResponseBody
    @RequestMapping("/hello")
    public String Hello(@RequestParam("user") String userName){
        asyncService.asyncHello();
        if ("aaa".equals(userName)){
            throw new BusinessException("用户名不可用");
        }
//        try {
////            showPdf(request,response);
////            downloadPDF();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return "hello";
    }

    @RequestMapping("/success")
    public String success(Map<String,Object> map){
        map.put("hello","你好");
        return "success";
    }

    public void downloadPDF() throws FileNotFoundException {
        Document document =new Document(PageSize.A4,50, 50, 50, 50);
        try {
            PdfWriter wtiter = PdfWriter.getInstance(document,new FileOutputStream("E:\\POI_text\\HelloItext133.pdf"));
            Image image= null;
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Code39Bean bean =new Code39Bean();
            //精细度 每英寸所打印的点数或线数,用来表示打印机打印分辨率
            final int dpi = 200;
            final double moduleWidth = UnitConv.in2mm(1.0f / dpi);// module宽度
            bean.setModuleWidth(moduleWidth); // 配置对象
            bean.doQuietZone(false);
            bean.setFontName("1234567"); //设置不显示条形码下边的数字
            bean.setFontSize(0);
            String format = "image/png";
            String val="sdasds";
            //输出到流
            BitmapCanvasProvider provider = new BitmapCanvasProvider(outputStream, format, dpi, BufferedImage.TYPE_BYTE_BINARY, false, 0);
           bean.generateBarcode(provider,val);
            //绘制结束
            provider.finish();
            outputStream.close();
            image= Image.getInstance(outputStream.toByteArray());
            image.setAlignment(1);
            image.scalePercent(100);
            document.open();
            PdfPTable table =new PdfPTable(1);
            table.setWidthPercentage(100); // 宽度100%填充
            table.setSpacingBefore(0); // 前间距
            table.setSpacingAfter(0); // 后间距

            List<PdfPRow> listRow = table.getRows();
            //设置列宽
            float[] columnWidths = {1f};
            table.setWidths(columnWidths);
            //行1

            PdfPCell cells1[]= new PdfPCell[1];
            PdfPRow row1 = new PdfPRow(cells1);
            cells1[0] = new PdfPCell(image);//单元格内容
            listRow.add(row1);
            document.add(table);
            document.close();
            wtiter.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void showPdf(HttpServletRequest request, HttpServletResponse response) {

        String pdfUrl="http://www.962121.net/hmfmstest/filecenter/files/2020/02/1581401580338007.pdf";
        try {
            
            URL url =new URL(pdfUrl);
            String name ="业主结存单.pdf";
            HttpURLConnection urlconnection =  (HttpURLConnection) url.openConnection();
            urlconnection.setConnectTimeout(5*1000);
            InputStream inputStream = urlconnection.getInputStream();
            response.setContentType("application/pdf");
            response.addHeader("Content-Disposition", "attachment;filename="+new String(name.getBytes("utf-8"),"ISO-8859-1"));
            response.setContentType("multipart/form-data");
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

            byte[] by =new byte[1024*3];
            OutputStream outputStream= response.getOutputStream();
            int i =0;
            while((i=bufferedInputStream.read(by,0,by.length))!=-1){
                outputStream.write(by,0,i);
            }
            outputStream.flush(); bufferedInputStream.close(); inputStream.close();outputStream.close();


//            IOUtils.write(IOUtils.toByteArray(inputStream), response.getOutputStream());


        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


}
