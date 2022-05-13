package com.programmers.kmooc.models

import org.json.JSONObject
import java.io.Serializable
import java.lang.Exception

data class LectureList(
    val count: Int,
    val numPages: Int,
    val previous: String,
    val next: String,
    var lectures: List<Lecture>
) : Serializable {
    companion object {
        val EMPTY = LectureList(0, 0, "", "", emptyList())

        fun from(jsonObject: JSONObject): LectureList?{
            return try{
                jsonObject.run {
                    LectureList(
                        getJSONObject("pagination").getInt("count"),
                        getJSONObject("pagination").getInt("num_pages"),
                        getJSONObject("pagination").getString("previous"),
                        getJSONObject("pagination").getString("next"),
                        getJSONArray("results").run {
                            mutableListOf<Lecture>().apply {
                                for(i in 0 until length()){
                                    add(Lecture.from(getJSONObject(i)) ?: continue)
                                }
                            }
                        }
                    )
                }
            }catch (e: Exception){
                e.printStackTrace()
                null
            }
        }
    }
}

/*
{"pagination":{"count":2164,"previous":null,"num_pages":217,"next":"http:\/\/www.kmooc.kr\/api\/courses\/v1\/course\/list\/?Mobile=1&SG_APIM=2ug8Dm9qNBfD32JLZGPN64f3EoTlkpD8kSOHWfXpyrY&page=2&serviceKey=w%2BZFjxSH9DF5oVD0e6TxJTL7F95XsTL4N8ap2Q4ontDMEbIKr0odCjOxfSfen3ooWxMYov4L3yCLswXBJNBnGA%3D%3D"},
"results":[{}]
 */