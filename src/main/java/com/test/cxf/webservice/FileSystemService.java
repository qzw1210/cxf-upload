package com.test.cxf.webservice;

import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.util.List;

/**
 * Created by quzhiwen on 2016/7/26.
 */
@Path("/")
public class FileSystemService {
    @GET
    @Path("dlText")
    @Produces("text/plain")
    public Response downloadText() {
        File file = new File("D:\\ftpserver\\1.txt");
        Response.ResponseBuilder response = Response.ok(file);
        response.header("Content-Disposition", "attachment;filename='1.txt'");
        return response.build();
    }

    @POST
    @Path("/upload")
    public Response uploadFileByForm(String id, String name, Attachment image) {
        System.out.println("id:" + id);
        System.out.println("name:" + name);
        DataHandler dh = image.getDataHandler();

        try {
            InputStream ins = dh.getInputStream();
            writeToFile(ins, "D:\\ftpserver\\" + dh.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Response.ok().entity("ok").build();
    }

    /**
     * 多文件上传
     *
     * @param attchments
     * @param request
     * @return
     */
    @POST
    @Path("/uploadlist")
    //@Consumes("multipart/form-data")
    public Response uploadFileList(List<Attachment> attachments, @Context HttpServletRequest request){
        if (attachments.size() > 0)
            System.out.println("ok");

        for (Attachment attach : attachments) {
            DataHandler dh = attach.getDataHandler();
            System.out.println(attach.getContentType().toString());

            if (attach.getContentType().toString().equals("text/plain")) {
                try {
                    System.out.println(dh.getName());
                    System.out.println(writeToString(dh.getInputStream()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    writeToFile(dh.getInputStream(),
                            "D:\\ftpserver\\" + dh.getName());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return Response.ok().entity("ok").build();
    }

    private void writeToFile(InputStream ins, String path) {
        try {
            OutputStream out = new FileOutputStream(new File(path));
            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = ins.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String writeToString(InputStream ins) throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] b = new byte[1024];
        int i = -1;
        while ((i = ins.read(b)) != -1) {
            out.write(b, 0, i);
        }
        ins.close();
        return new String(out.toByteArray(), "UTF-8");
    }
}
