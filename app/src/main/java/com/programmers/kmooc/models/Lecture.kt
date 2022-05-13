package com.programmers.kmooc.models

import com.programmers.kmooc.utils.DateUtil
import org.json.JSONObject
import java.io.Serializable
import java.util.*

data class Lecture(
    val id: String,                 // 아이디
    val number: String,             // 강좌번호
    val name: String,               // 강좌명
    val classfyName: String,        // 강좌분류
    val middleClassfyName: String,  // 강좌분류2
    val courseImage: String,        // 강좌 썸네일 (media>image>small)
    val courseImageLarge: String,   // 강좌 이미지 (media>image>large)
    val shortDescription: String,   // 짧은 설명
    val orgName: String,            // 운영기관
    val start: Date,                // 운영기간 시작
    val end: Date,                  // 운영기간 종료
    val teachers: String?,          // 교수진
    val overview: String?           // 상제정보(html)
) : Serializable {
    companion object {
        val EMPTY = Lecture(
            "", "", "", "", "",
            "", "", "", "", Date(), Date(), null, null
        )

        fun from(jsonObject: JSONObject): Lecture?{
            jsonObject.run {
                return try {
                    Lecture(
                        getString("id"),
                        getString("number"),
                        getString("name"),
                        getString("classfy_name"),
                        getString("middle_classfy_name"),
                        getJSONObject("media").getJSONObject("image").getString("small"),
                        getJSONObject("media").getJSONObject("image").getString("large"),
                        getString("short_description"),
                        getString("org_name"),
                        DateUtil.parseDate(getString("start")),
                        DateUtil.parseDate(getString("end")),
                        if(has("teachers"))getString("teachers") else null,
                        if(has("overview"))getString("overview") else null
                    )
                }catch (e : Exception){
                    e.printStackTrace()
                    null
                }
            }
        }
    }
}

/*
{
    "blocks_url":"http:\/\/www.kmooc.kr\/api\/courses\/v1\/blocks\/?course_id=course-v1%3AACRCEDU%2BACRC01%2B2021_01",
    "effort":"00:15@07#01:40$07:00",
    "end":"2021-07-26T15:00:00Z",
    "enrollment_start":"2021-06-29T00:00:00Z",
    "enrollment_end":"2021-07-26T15:00:00Z",
    "id":"course-v1:ACRCEDU+ACRC01+2021_01",
    "media":{
        "course_image":{"uri":"\/asset-v1:ACRCEDU+ACRC01+2021_01+type@asset+block@2021-1_대표이미지.PNG"},
        "course_video":{"uri":null},
        "image":{
            "raw":"http:\/\/www.kmooc.kr\/asset-v1:ACRCEDU+ACRC01+2021_01+type@asset+block@2021-1_%EB%8C%80%ED%91%9C%EC%9D%B4%EB%AF%B8%EC%A7%80.PNG",
            "small":"http:\/\/www.kmooc.kr\/asset-v1:ACRCEDU+ACRC01+2021_01+type@asset+block@2021-1_%EB%8C%80%ED%91%9C%EC%9D%B4%EB%AF%B8%EC%A7%80.PNG",
            "large":"http:\/\/www.kmooc.kr\/asset-v1:ACRCEDU+ACRC01+2021_01+type@asset+block@2021-1_%EB%8C%80%ED%91%9C%EC%9D%B4%EB%AF%B8%EC%A7%80.PNG"}
        },
    "name":"문화와 생활 속 청렴",
    "number":"ACRC01",
    "org":"ACRCEDU",
    "short_description":"문화와 생활 속 청렴강좌는 인문학 속 역사 이야기와 생활 속 청렴 정보통으로 구성되어 있습니다\n인문학 속 역사 이야기에서는 역사 속 인물, 영화 속 주인공, 대중문화의 사례를 통해 쉽고 재미있게 청렴에 대해 다시 한 번 생각해볼 수 있는 시간을 드립니다\n또한 생활 속 청렴 정보통에서는 어렵게만 느껴졌던 청탁금지법, 부패 및 공익신고, 우리나라 청렴지수 등 국민들이 알아야 할 생활 속 반부패 법령, 제도의 정보를 쉽고 재미있게 풀어드리겠습니다\n",
    "start":"2021-06-29T00:00:00Z",
    "start_display":"June 29, 2021",
    "start_type":"timestamp",
    "pacing":"instructor",
    "mobile_available":true,
    "hidden":false,
    "invitation_only":false,
    "teachers":"신병주, 윤성은, 하재근, 오수진, 이정수",
    "classfy":"hum",
    "middle_classfy":"husc",
    "classfy_plus":"hum_and_social",
    "course_period":"M",
    "level":"1",
    "passing_grade":"0.70",
    "audit_yn":"N",
    "fourth_industry_yn":"N",
    "home_course_yn":"N",
    "home_course_step":"",
    "ribbon_yn":"N",
    "job_edu_yn":"N",
    "linguistics":"",
    "created":"2021-06-25T00:44:47Z",
    "modified":"2022-01-20T01:46:57Z",
    "ai_sec_yn":"N",
    "basic_science_sec_yn":"N",
    "org_name":"국민권익위원회 청렴연수원",
    "classfy_name":"인문",
    "middle_classfy_name":"인문과학",
    "language_name":"한국어",
    "effort_time":"07:00",
    "video_time":"01:40",
    "week":"07",
    "learning_time":"00:15",
    "preview_video":"",
    "course_id":"course-v1:ACRCEDU+ACRC01+2021_01"
    },
    {"blocks_url":"http:\/\/www.kmooc.kr\/api\/courses\/v1\/blocks\/?course_id=course-v1%3AACRCEDU%2BACRC01%2B2022_01","effort":"00:15@07#01:40$02:00","end":"2022-02-18T15:00:00Z","enrollment_start":"2022-02-07T00:00:00Z","enrollment_end":"2022-02-18T15:00:00Z","id":"course-v1:ACRCEDU+ACRC01+2022_01","media":{"course_image":{"uri":"\/asset-v1:ACRCEDU+ACRC01+2022_01+type@asset+block@2022-1기_시즌1.PNG"},"course_video":{"uri":null},"image":{"raw":"http:\/\/www.kmooc.kr\/asset-v1:ACRCEDU+ACRC01+2022_01+type@asset+block@2022-1%EA%B8%B0_%EC%8B%9C%EC%A6%8C1.PNG","small":"http:\/\/www.kmooc.kr\/asset-v1:ACRCEDU+ACRC01+2022_01+type@asset+block@2022-1%EA%B8%B0_%EC%8B%9C%EC%A6%8C1.PNG","large":"http:\/\/www.kmooc.kr\/asset-v1:ACRCEDU+ACRC01+2022_01+type@asset+block@2022-1%EA%B8%B0_%EC%8B%9C%EC%A6%8C1.PNG"}},"name":"문화와 생활 속 청렴","number":"ACRC01","org":"ACRCEDU","short_description":"문화와 생활 속 청렴강좌는 인문학 속 역사 이야기와 생활 속 청렴 정보통으로 구성되어 있습니다\n인문학 속 역사 이야기에서는 역사 속 인물, 영화 속 주인공, 대중문화의 사례를 통해 쉽고 재미있게 청렴에 대해 다시 한 번 생각해볼 수 있는 시간을 드립니다\n또한 생

 */