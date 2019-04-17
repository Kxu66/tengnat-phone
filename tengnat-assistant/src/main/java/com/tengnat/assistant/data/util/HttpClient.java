package com.tengnat.assistant.data.util;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

/**
 * @author loren
 * @description 发送GET和POST请求
 */
public class HttpClient {

	private static final Logger LOGGER = LogManager.getLogger(HttpClient.class.getName());
	
	private static final int TIME_OUT = 1000 * 5;
	
	/**
	 * 无参数的GET请求
	 * @param url 请求的URL
	 * @return String
	 */
	public static String doGet(String url) {
		String result = getDataFromUrl(url);
		return result;
	}

	/**
	 * 带参数的GET请求
	 * @param url 请求的URL
	 * @param params key为参数名，value为参数值
	 * @return String
	 */
	public static String doGet(String url, Map<String, Object> params) {
		String address = formatURL(url, params);
		String result = doGet(address);
		return result;
	}
	
	/**
	 * POST请求
	 * @param url 请求的URL
	 * @param datas POST的数据
	 * @param contentType 内容类型 例如：application/json, application/x-www-form-urlencoded
	 * @param headerMap header头信息
	 * @return String
	 */
	public static String doPost(String url, String datas, String contentType, Map<String, String> headerMap) {
		InputStream inputStream = null;
		InputStreamReader reader = null;
		BufferedReader bufferedReader = null;
		try {
			HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
			connection.setConnectTimeout(TIME_OUT);
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setUseCaches(false);
			connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", contentType);
            if(headerMap != null) {
            	for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                    connection.setRequestProperty(entry.getKey(), entry.getValue());
				}
            }
            DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
			outputStream.write(datas.getBytes("UTF-8"));
			outputStream.flush();
			outputStream.close();

		    int responseCode = connection.getResponseCode();
		    System.out.print(responseCode);
			if (responseCode == HttpURLConnection.HTTP_OK||responseCode == HttpURLConnection.HTTP_CREATED) {
				inputStream = connection.getInputStream();
				reader = new InputStreamReader(inputStream, "UTF-8");
				bufferedReader = new BufferedReader(reader);
				String inputLine = null;
				StringBuffer buffer = new StringBuffer();
				while ((inputLine = bufferedReader.readLine()) != null) {
					buffer.append(inputLine + "\n");
				}
				connection.disconnect();
				if (StringUtils.isEmpty(buffer.toString())){
					return buffer.toString();
				};
				return buffer.substring(0, buffer.length() - 1);
			} else if (responseCode == HttpURLConnection.HTTP_NOT_FOUND) {
				return "HTTP Not Found 404";
			} else if (responseCode == HttpURLConnection.HTTP_CLIENT_TIMEOUT) {
				return "Request Timeout 408";
			} else if (responseCode == HttpURLConnection.HTTP_INTERNAL_ERROR) {
				return "Internal Server Error 500";
			} else {
				return "Request Failure " + responseCode; 
			}
		} catch (Exception e) {
			if(e instanceof SocketTimeoutException) {
				LOGGER.error("请求超时");
			} else {
				LOGGER.error("POST请求失败", e);
			}
		} finally {
			try {
				if(bufferedReader != null) {
					bufferedReader.close();
				}
				if(reader != null) {
					reader.close();
				}
				if(inputStream != null) {
					inputStream.close();
				}
			} catch (Exception e2) {
				LOGGER.error("IO流关闭失败", e2);
			}
		}
		return "Request Failure";
	}
	
	/**
	 * POST请求
	 * @param url 请求的URL
	 * @param jsonString POST的json数据
	 * @return String
	 */
	public static String doJsonPost(String url, String jsonString) {
		return doPost(url, jsonString, "application/json", null);
	}
	
	/**
	 * POST请求
	 * @param url 请求的URL
	 * @param formDatas POST的表单数据
	 * @return String
	 */
	public static String doFormPost(String url, String formDatas) {
		return doPost(url, formDatas, "application/x-www-form-urlencoded", null);
	}
	
	/**
	 * 获取POST过来的数据
	 * @return String
	 */
	public static String getPost(InputStream inputStream) {
		InputStreamReader reader = null;
		BufferedReader buffReader = null;
		try {
			reader = new InputStreamReader(inputStream, "UTF-8");
			buffReader = new BufferedReader(reader);
			StringBuffer buffer = new StringBuffer();
			String inputLine = null;
			while ((inputLine = buffReader.readLine()) != null) {
				buffer.append(inputLine + "\n");
			}
			return buffer.substring(0, buffer.length() - 1);
		} catch (Exception e) {
			LOGGER.error("获取POST过来的数据出错", e);
		} finally {
			try {
				if(buffReader != null) {
					buffReader.close();
				}
				if(reader != null) {
					reader.close();
				}
				if(inputStream != null) {
					inputStream.close();
				}
			} catch (Exception e2) {
				LOGGER.error("IO流关闭失败", e2);
			}
		}
		return null;
	}
	
	/**
	 * 上传文件
	 * @param uploadURL 上传的URL
	 * @param file 文件
	 * @return String
	 */
	public static String upload(String uploadURL, File file, String inputName) {
		if(!file.exists() || !file.isFile()) { return "文件不存在"; }
		
		InputStream inputStream = null;
		InputStreamReader reader = null;
		BufferedReader bufferedReader = null;
		try {
			long filelength = file.length();
	        String fileName=file.getName();
			URL url = new URL(uploadURL);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setConnectTimeout(TIME_OUT);
	        connection.setRequestMethod("POST");
	        connection.setDoInput(true);
	        connection.setDoOutput(true);
	        connection.setUseCaches(false);
	        connection.setRequestProperty("Connection", "Keep-Alive");
	        connection.setRequestProperty("Charset", "UTF-8");
	        String BOUNDARY = "----------" + System.currentTimeMillis();
	        connection.setRequestProperty("Content-Type", "multipart/form-data; boundary="+ BOUNDARY);
	        StringBuffer outBuffer = new StringBuffer();
	        outBuffer.append("--");
	        outBuffer.append(BOUNDARY);
	        outBuffer.append("\r\n");
	        outBuffer.append("Content-Disposition: form-data;name=\"" + inputName + "\";filename=\"" + fileName + "\";filelength=\"" + filelength + "\" \r\n");
	        outBuffer.append("Content-Type:application/octet-stream\r\n\r\n");
	        byte[] head = outBuffer.toString().getBytes("utf-8");
	        OutputStream out = new DataOutputStream(connection.getOutputStream());
	        out.write(head);
	        DataInputStream dataStream = new DataInputStream(new FileInputStream(file));
	        int bytes = 0;
	        byte[] bufferOut = new byte[1024];
	        while ((bytes = dataStream.read(bufferOut)) != -1) {
	        	out.write(bufferOut, 0, bytes);
	        }
	        dataStream.close();
	        out.write(("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8"));  
	        out.flush();  
	        out.close();
	        StringBuffer inBuffer = new StringBuffer();
	        inputStream = connection.getInputStream();
	        reader = new InputStreamReader(inputStream);
	        bufferedReader = new BufferedReader(reader);  
	        String line = null;  
	        while((line = bufferedReader.readLine()) != null) {  
	        	inBuffer.append(line + "\n");  
	        }
			connection.disconnect();
	        return inBuffer.substring(0, inBuffer.length() - 1);
		} catch (Exception e) {
			LOGGER.error("上传请求失败", e);
		} finally {
			try {
				if(bufferedReader != null) {
					bufferedReader.close();
				}
				if(reader != null) {
					reader.close();
				}
				if(inputStream != null) {
					inputStream.close();
				}
			} catch (Exception e2) {
				LOGGER.error("IO流关闭失败", e2);
			}
		}
		return null;
	}
	
	/**
	 * 下载文件
	 * @param downloadURL 下载的URL
	 * @param savePath 保存的路径(绝对路径, 例如： d:/wechat/images)
	 * @param fileName 文件名(包含后缀)
	 * @return String
	 */
	public static String download(String downloadURL, String savePath, String fileName) {
		InputStream inputStream = null;
		OutputStream outputStream = null;
		try {
			URL url = new URL(downloadURL);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			inputStream = connection.getInputStream();
			if(!savePath.endsWith("/")) { savePath = savePath + "/"; }
			String filePath = savePath + fileName;
			outputStream = new FileOutputStream(new File(filePath));
			byte[] buff = new byte[1024];
			int len = 0;
			while((len = inputStream.read(buff)) != -1) {
				outputStream.write(buff, 0, len);
			}
			connection.disconnect();
			return "download success, path = " + filePath;
		} catch (Exception e) {
			LOGGER.error("下载请求失败", e);
		} finally {
			try {
				if(outputStream != null) {
					outputStream.close();
				}
				if(inputStream != null) {
					inputStream.close();
				}
			} catch (Exception e2) {
				LOGGER.error("IO流关闭失败", e2);
			}
		}
		return null;
	}
	
	/**
	 * @description 将URL与参数拼成GET请求的URL
	 * @param url 不带参数的URL
	 * @param params 存着参数的Map<String, Object>
	 * @return String
	 */
	private static String formatURL(String url, Map<String, Object> params) {
		String address = url;
		if (params != null) {
			StringBuffer buffer = new StringBuffer("?");
			boolean flag = false;
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				String paramKey = entry.getKey();
				Object paramValue = entry.getValue();
				if (flag) {
					buffer.append("&" + paramKey + "=" + paramValue);
				} else {
					buffer.append(paramKey + "=" + paramValue);
					flag = true;
				}
			}
			address += buffer.toString();
		}
		return address;
	}

	/**
	 * 获取address里的信息
	 * @param address URL地址
	 * @return String
	 */
	private static String getDataFromUrl(String address) {
		InputStream inputStream = null;
		InputStreamReader reader = null;
		BufferedReader bufferedReader = null;
		try {
			URL url = new URL(address);
			URLConnection connection = url.openConnection();
			connection.setConnectTimeout(TIME_OUT);
			inputStream = connection.getInputStream();
			reader = new InputStreamReader(inputStream, "UTF-8");
			bufferedReader = new BufferedReader(reader);
			String inputLine = null;
			StringBuffer buffer = new StringBuffer();
			while ((inputLine = bufferedReader.readLine()) != null) {
				buffer.append(inputLine + "\n");
			}
			return buffer.substring(0, buffer.length() - 1);
		} catch (Exception e) {
			LOGGER.error("GET请求失败", e);
		} finally {
			try {
				if(bufferedReader != null) {
					bufferedReader.close();
				}
				if(reader != null) {
					reader.close();
				}
				if(inputStream != null) {
					inputStream.close();
				}
			} catch (Exception e2) {
				LOGGER.error("IO流关闭失败", e2);
			}
		}
		return null;
	}
}
