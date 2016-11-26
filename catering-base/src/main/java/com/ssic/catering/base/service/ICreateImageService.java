package com.ssic.catering.base.service;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ssic.catering.common.image.ImageInfoDto;

public interface ICreateImageService {

	public Map<String, Object> createImage(ImageInfoDto image, @RequestParam("image")MultipartFile myfile, HttpServletRequest request, HttpServletResponse response);

	public void showPicture(HttpServletResponse response,String imageUrl) throws Exception;
}
