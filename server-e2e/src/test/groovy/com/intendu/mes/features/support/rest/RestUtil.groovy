package com.intendu.mes.features.support.rest

import org.apache.http.client.utils.URLEncodedUtils
import org.apache.http.message.BasicNameValuePair

class RestUtil {

    static String createParamsStringFrom(query) {
        def params = []
        query.each { it -> params.add(new BasicNameValuePair(it.key, it.value))}
        return URLEncodedUtils.format(params, "utf-8")
    }

    static Object findByTitle(Object objWithItems , String title) {
        def foundItem = null
        objWithItems.each { currentItem ->
            if (currentItem.title == title)
                foundItem = currentItem
        }
        return foundItem
    }

    static Object findById(Object objWithItems , String id) {
        def foundItem = null
        objWithItems.each { currentItem ->
            if (currentItem.id == id)
                foundItem = currentItem
        }
        return foundItem
    }
}
