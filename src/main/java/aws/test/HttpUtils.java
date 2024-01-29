package aws.test;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.util.Set;
import java.util.zip.GZIPInputStream;

import lombok.Builder;

@Builder
public class HttpUtils {

	public static final String REQ_METHOD_POST = "POST";
	public static final String REQ_METHOD_GET = "GET";
	public static final String REQ_METHOD_PUT = "PUT";
	public static final String API_HEADER_KEY = "apikey";

	protected Map<String, String> headerParams;
	protected String urlString;
	protected String postData;
	protected String requestMethod;

	public static final String HEADER_CHANNEL = "ChannelType";
	public static final String SYNC_HEADER = "sync";
	protected long responseTime;

	public String getResponse() throws IOException {
		HttpURLConnection httpUrlCon = null;
		try {
			long startTime = System.currentTimeMillis();
			httpUrlCon = getURLConnection();
			String responseString = getContent(httpUrlCon);
			long endTime = System.currentTimeMillis();
			responseTime = endTime - startTime;
			return responseString;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public <T> HttpURLConnection getURLConnection() throws MalformedURLException, Exception, IOException {
		URL url = new URL(urlString);
		HttpURLConnection httpUrlCon = getUrlConnection(url);
		setHeaderParams(httpUrlCon);
		setURLConnection(httpUrlCon);
		httpUrlCon.connect();
		return httpUrlCon;
	}

	public HttpURLConnection getUrlConnection(URL url) throws Exception {
		return (HttpURLConnection) url.openConnection();
	}

	private HttpURLConnection setURLConnection(HttpURLConnection httpUrlCon) throws Exception {
		httpUrlCon.setDoInput(true);
		httpUrlCon.setInstanceFollowRedirects(true);
		httpUrlCon.setRequestMethod(requestMethod);
		if (postData != null) {
			setPostDataForUrlCon(httpUrlCon, postData);
		}
		return httpUrlCon;
	}

	public void setPostDataForUrlCon(URLConnection conn, String postData) throws Exception {
		conn.setDoOutput(true);
		OutputStream outStream = conn.getOutputStream();
		OutputStreamWriter osw = new OutputStreamWriter(outStream);
		osw.write(postData.toString(), 0, postData.toString().length());
		osw.flush();
		osw.close();
	}

	private void setHeaderParams(HttpURLConnection httpUrlCon) {
		if (null == headerParams || headerParams.isEmpty()) {
			return;
		}
		Set<String> keys = headerParams.keySet();
		for (String key : keys) {
			httpUrlCon.setRequestProperty(key, headerParams.get(key));
		}
	}

	public String getContent(URLConnection urlCon) throws IOException {
		if (urlCon == null) {
			return null;
		}
		HttpURLConnection httpUrlCon = (HttpURLConnection) urlCon;
		String encoding = urlCon.getContentEncoding();
		InputStream ip = null;
		StringBuffer strBuf = new StringBuffer();
		try {
			if (httpUrlCon.getResponseCode() >= HttpURLConnection.HTTP_BAD_REQUEST
					&& httpUrlCon.getResponseCode() <= HttpURLConnection.HTTP_INTERNAL_ERROR) {
				if (encoding != null && encoding.equalsIgnoreCase("gzip")) {
					ip = new GZIPInputStream(new BufferedInputStream(httpUrlCon.getErrorStream()));
				} else {
					ip = new BufferedInputStream(httpUrlCon.getErrorStream());
				}
			} else if (encoding != null && encoding.equalsIgnoreCase("gzip")) {
				ip = new GZIPInputStream(new BufferedInputStream(urlCon.getInputStream()));
			} else {
				ip = new BufferedInputStream(urlCon.getInputStream());
			}
			byte[] b = new byte[4096];
			for (int n = 0; (n = ip.read(b)) != -1;) {
				strBuf.append(new String(b, 0, n));
			}
		} finally {
			try {
				if (ip != null) {
					ip.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return strBuf.toString();
	}
}