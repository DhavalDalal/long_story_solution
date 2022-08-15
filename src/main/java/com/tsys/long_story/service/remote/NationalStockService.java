package com.tsys.long_story.service.remote;

import java.io.*;
import java.net.*;
import java.security.*;
import java.util.logging.Logger;
import javax.net.ssl.*;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class NationalStockService {
  private static final Logger LOG = Logger.getLogger(NationalStockService.class.getName());
  private final String urlTemplate;
  private final boolean allowCalls;

  public NationalStockService(@Value("${stock_service.national.url}") String urlTemplate,
                              @Value("${stock_service.allow.calls}") boolean allowCalls) {
    this.urlTemplate = urlTemplate + "/stocks/";
    this.allowCalls = allowCalls;
    LOG.info(String.format("==> urlTemplate = %s", this.urlTemplate));
    LOG.info(String.format("==> allowCalls = %s", allowCalls));
  }

  public double getPrice(final String ticker) throws Exception {
    if (!allowCalls)
      throw new UnsupportedOperationException("Operation Not Allowed At This Time!");

    setupSSLContext();
    final String httpsUrl = urlTemplate + ticker;
    LOG.info(String.format("==> Getting Price For = %s%s", urlTemplate, ticker));
    final HttpsURLConnection connection = openSSLConnectionTo(httpsUrl);
    final String data = getData(connection);
    LOG.info(String.format("==> Got Price = %s", data));

    final JSONObject stockDetails = new JSONObject(data);
    return stockDetails.getDouble("price");
  }

  private void setupSSLContext() throws NoSuchAlgorithmException, KeyManagementException {
    SSLContext ctx = SSLContext.getInstance("TLS");
    ctx.init(new KeyManager[0], new TrustManager[] { new Empty() }, new SecureRandom());
    SSLContext.setDefault(ctx);
  }

  private String getData(final HttpsURLConnection connection) throws IOException {
    InputStream is = connection.getInputStream();
    InputStreamReader isr = new InputStreamReader(is);
    BufferedReader reader = new BufferedReader(isr);
    String line = null;
    StringBuilder data = new StringBuilder();
    while ((line = reader.readLine()) != null) {
      data.append(line);
    }
    return data.toString();
  }

  private HttpsURLConnection openSSLConnectionTo(String url) throws MalformedURLException, IOException {
    final URL https = new URL(url);
    HttpsURLConnection connection = (HttpsURLConnection) https.openConnection();
    connection.setHostnameVerifier(new VerifiedOK());
    return connection;
  }

  private static class VerifiedOK implements HostnameVerifier {
    @Override
    public boolean verify(String arg0, SSLSession arg1) { return true; }
  }

  private static class Empty implements X509TrustManager {
    @Override
    public void checkClientTrusted(java.security.cert.X509Certificate[] arg0, String arg1)
            throws java.security.cert.CertificateException { }

    @Override
    public void checkServerTrusted(java.security.cert.X509Certificate[] arg0, String arg1)
            throws java.security.cert.CertificateException { }

    @Override
    public java.security.cert.X509Certificate[] getAcceptedIssuers() { return null; }
  }
}