package com.yxtt.hold;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.codec.binary.Base64;

import net.sf.json.JSONObject;

public class PriceSevlet extends HttpServlet {
	public PriceSevlet() {
		super();
	}

	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("text/html");
		request.getCharacterEncoding();
		request.setCharacterEncoding(request.getCharacterEncoding()) ;
		response.setCharacterEncoding("utf-8");
		
		String param = getBodyData(request);
        if (Base64.isBase64(param)) {
            param = new String(Base64.decodeBase64(param), StandardCharsets.UTF_8);
        }
        System.out.println("param:" + param);
		PrintWriter out = response.getWriter();
		
		
		out.write(DataProcess.dataprocess(param));
		out.flush();
		out.close();
	}

	public void init() throws ServletException {
	}
	//获取请求体中的字符串(POST)
    private static String getBodyData(HttpServletRequest request) {
        StringBuffer data = new StringBuffer();
        String line = null;
        BufferedReader reader = null;
        try {
            reader = request.getReader();
            while (null != (line = reader.readLine()))
                data.append(line);
        } catch (IOException e) {
        } finally {
        }
        return data.toString();
    }
    
    }

