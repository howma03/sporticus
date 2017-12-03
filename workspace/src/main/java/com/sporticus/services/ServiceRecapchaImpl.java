package com.sporticus.services;

import com.sporticus.interfaces.IServiceRecapcha;
import com.sporticus.util.logging.LogFactory;
import com.sporticus.util.logging.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;

@Component
public class ServiceRecapchaImpl implements IServiceRecapcha {

	private static final Logger LOGGER = LogFactory.getLogger(ServiceRecapchaImpl.class.getName());
	private static final String url = "https://www.google.com/recaptcha/api/siteverify";
	private static final String USER_AGENT = "Mozilla/5.0";

	@Value("${recaptcha.site.key}")
	private String sitekey = "";

	// @Value("${recaptcha.private.key}")
	private String secret = "6Lc5gzoUAAAAANJBFqqbzNS0m-MzOtTUZkfPuVhE";

	public ServiceRecapchaImpl() {

	}

	@Override
	public void verify(String gRecaptchaResponse) throws RecaptchaException {
		if (gRecaptchaResponse == null || "".equals(gRecaptchaResponse)) {
			throw new RecaptchaException("Recapcha failed");
		}

		try {
			URL obj = new URL(url);
			HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

			// add request header
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", USER_AGENT);
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

			String postParams = "secret=" + secret + "&response="
					+ gRecaptchaResponse;

			// Send post request
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(postParams);
			wr.flush();
			wr.close();

			int responseCode = con.getResponseCode();
			System.out.println("\nSending 'POST' request to URL : " + url);
			System.out.println("Post parameters : " + postParams);
			System.out.println("Response Code : " + responseCode);

			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			// print result
			System.out.println(response.toString());

			//parse JSON response and return 'success' value
			JsonReader jsonReader = Json.createReader(new StringReader(response.toString()));
			JsonObject jsonObject = jsonReader.readObject();
			jsonReader.close();

			if (!jsonObject.getBoolean("success")) {
				throw new RecaptchaException("Recapcha failed");
			}

		} catch (Exception e) {
			throw new RecaptchaException("Recaptcha failed", e);
		}
	}

	@PostConstruct
	private void onInit() {
		LOGGER.info(() -> "Constructed - recaptcha.site.key=" + sitekey);
		LOGGER.info(() -> "Constructed - recaptcha.private.key=" + secret);
	}
}