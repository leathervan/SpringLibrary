package com.serhiiostapenko.OnlineLibrary.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Controller
public class ImageController {
    @ResponseBody
    @RequestMapping(value = "/image/{imageName}")
    public void getImage(@PathVariable(value = "imageName") String imageName, HttpServletRequest request, HttpServletResponse response) throws IOException {
        /*response.setContentType("image/jpeg");
        FileInputStream fis = new FileInputStream(request.getServletContext().getRealPath("") + "load\\pic" + File.separator + imageName);
        fis.transferTo(response.getOutputStream());*/
    }
}
