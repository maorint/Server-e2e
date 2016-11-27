package com.intendu.mes.features.support.rest

import org.apache.http.HttpResponse

class RestResponse {

    String body
    int statusCode

    RestResponse(HttpResponse response) {
        this.body = getStringFrom(response.getEntity().getContent())
        this.statusCode = response.getStatusLine().getStatusCode()
    }

    private String getStringFrom(InputStream inputStream) {
        BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream))

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        return result.toString()
    }
}
