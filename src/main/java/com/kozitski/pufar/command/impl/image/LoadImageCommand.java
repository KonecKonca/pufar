package com.kozitski.pufar.command.impl.image;

import com.kozitski.pufar.command.PagePath;
import com.kozitski.pufar.command.Router;
import com.kozitski.pufar.command.response.ResponseCommand;
import com.kozitski.pufar.util.CommonConstant;
import com.kozitski.pufar.util.LoadImageNameGenerator;
import com.kozitski.pufar.util.path.WebPathReturner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;

// TODO: Auto-generated Javadoc
/**
 * The Class LoadImageCommand.
 */
public class LoadImageCommand implements ResponseCommand {
    
    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(LoadImageCommand.class);
    
    /** The Constant DATA_PATH. */
    private static final String DATA_PATH = "/loadImages";

    /**
     * Execute.
     *
     * @param request the request
     * @param response the response
     * @return the router
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws ServletException the servlet exception
     */
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Router router = new Router();
        router.setPagePath(PagePath.CREATE_NOTIFICATION.getJspPath());

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

                if (part.getSize() != 0) {
                    request.getSession().setAttribute(CommonConstant.CURRENT_NOTIFICATION_IMAGE_PATH, fullName);
                }

                router.setRouteType(Router.RouteType.REDIRECT);
            }
        }

        return router;
    }


}
