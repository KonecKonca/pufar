package com.kozitski.pufar.controller.image;

import com.kozitski.pufar.command.PagePath;
import com.kozitski.pufar.util.CommonConstant;
import com.kozitski.pufar.util.LoadImageNameGenerator;
import com.kozitski.pufar.util.path.WebPathReturner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;


@WebServlet(urlPatterns = {"/upload/*"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024
        , maxFileSize = 1024 * 1024 * 5
        , maxRequestSize = 1024 * 1024 * 5 * 5)
public class FileUploadingServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileUploadingServlet.class);
    private static final String DATA_PATH = "/loadImages";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // path from tomcat
        String uploadFilePath = WebPathReturner.webPath + DATA_PATH;

        File fileSaveDir = new File(uploadFilePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdirs();
        }

        LOGGER.info("Upload File Directory = " + fileSaveDir.getAbsolutePath());

        String fileName = LoadImageNameGenerator.generateName();
        String fullName = uploadFilePath + File.separator + fileName;

        for (Part part : request.getParts()) {
            if (part.getSubmittedFileName() != null) {
                part.write(fullName);

                if(part.getSize() != 0){
                    request.getSession().setAttribute(CommonConstant.CURRENT_NOTIFICATION_IMAGE_PATH, fullName);
                }

                response.sendRedirect(PagePath.CREATE_NOTIFICATION.getJspPath());
            }
        }

    }

}