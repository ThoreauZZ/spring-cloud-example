package com.gomeoversea;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Swagger extends HttpServlet {
    @Override()
    protected void doPost(HttpServletRequest request,
                         HttpServletResponse response) {
         String swaggerContent = request.getParameter("swaggerContent");
        try {
            String path=request.getSession().getServletContext().getRealPath("/")+"spec-files/apiDoc.json";
            System.out.println(path+"spec-files");
            writeFile(path, swaggerContent);
            System.out.println(swaggerContent);
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public static void writeFile(String filePath, String content)
            throws IOException {
        FileWriter fw = new FileWriter(filePath);
        PrintWriter out = new PrintWriter(fw);
        out.write(content);
        out.println();
        fw.close();
        out.close();
    }
}
