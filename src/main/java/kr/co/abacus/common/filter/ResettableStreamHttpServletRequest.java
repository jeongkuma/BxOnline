package kr.co.abacus.common.filter;

import kr.co.abacus.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.Part;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class ResettableStreamHttpServletRequest extends HttpServletRequestWrapper {

	private final Charset encoding;
	private byte[] rawData;

	protected Map<String, String> parameterParts = new HashMap<String, String>();

	public ResettableStreamHttpServletRequest(HttpServletRequest request) throws IOException, ServletException {
		super(request);

		// Character
		String characterEncoding = request.getCharacterEncoding();
		if (StringUtils.isBlank(characterEncoding)) {
			characterEncoding = StandardCharsets.UTF_8.name();
		}
		this.encoding = Charset.forName(characterEncoding);

		// Multipart
		String contentType = request.getContentType();
		boolean isPost = "POST".equalsIgnoreCase(request.getMethod());
		if (isPost && contentType != null && contentType.startsWith("multipart/form-data")) {
			try {
				for (Part i : getParts()) {
					if (i.getContentType() == null && i.getSize() < 300) {
						parameterParts.put(i.getName(), getData(i.getInputStream()));
					}
				}
			} catch (IOException e) {
				log.error(e.getMessage());
				throw e;
			} catch (ServletException e) {
				log.error(e.getMessage());
				throw e;
			}
		}

		// Convert InputStream data to byte array and store it to this wrapper instance.
		try {
			InputStream inputStream = request.getInputStream();
			this.rawData = IOUtils.toByteArray(inputStream);
		} catch (IOException e) {
			log.error(e.getMessage());
			throw e;
		}

	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.rawData);
		ServletInputStream servletInputStream = new ServletInputStream() {
			public int read() throws IOException {
				return byteArrayInputStream.read();
			}

			@Override
			public boolean isFinished() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean isReady() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void setReadListener(ReadListener listener) {
				// TODO Auto-generated method stub

			}
		};
		return servletInputStream;
	}

	@Override
	public BufferedReader getReader() throws IOException {
		return new BufferedReader(new InputStreamReader(this.getInputStream(), this.encoding));
	}

	@Override
	public ServletRequest getRequest() {
		return super.getRequest();
	}

	private static String getData(InputStream input) throws IOException {
		String data = "";
		String line = "";
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		while ((line = reader.readLine()) != null) {
			data += line;
		}
		return data;
	}

	@Override
	public String getParameter(String name) {
		String result = super.getParameter(name);
		if (result == null) {
			return parameterParts.get(name);
		}
		return result;
	}

}
