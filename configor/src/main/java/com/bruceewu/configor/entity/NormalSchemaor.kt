package com.bruceewu.configor.entity

import android.text.TextUtils

//常用的跳转逻辑
open class NormalSchemaor : ISchemaor {
    private var title: String = ""
    var subTitle: String = ""
    var img: String = ""
    var action: String = ""
    var url: String = ""
    var login: Int = 0

    companion object {
        fun newInstance(title: String?, subTitle: String?, img: String?, action: String?, url: String?, login: Int?): NormalSchemaor = NormalSchemaor().apply {
            this.title = title ?: ""
            this.subTitle = subTitle ?: ""
            this.img = img ?: ""
            this.action = action ?: ""
            this.url = url ?: ""
            this.login = login ?: 0
        }
    }

    override fun getId() = 0L

    override fun getName() = ""

    override fun getLmNumber() = ""

    override fun getAdId() = ""

    override fun isNative() = TextUtils.equals(action, "native")

    override fun needLogin() = login == 1

    override fun isCredit() = false

    override fun getTitle() = title

    override fun getLink() = url
}

//跳转H5页面
class H5Schemaor(val url: String?) : ISchemaor {

    override fun getLink() = url

    override fun getName() = ""

    override fun getId() = 0L

    override fun getLmNumber() = ""

    override fun isNative() = false

    override fun getTitle() = ""

    override fun needLogin() = true

    override fun isCredit() = false

    override fun getAdId() = ""
}

//跳转本地页面
class NativeSchemaor(val url: String) : ISchemaor {
    override fun getLink() = url

    override fun getName() = ""

    override fun getId() = 0L

    override fun getLmNumber() = ""

    override fun isNative() = true

    override fun getTitle() = ""

    override fun needLogin() = true

    override fun isCredit() = false

    override fun getAdId() = ""
}

//当前页面直接交互操作
class ActionSchemaor(val url: String?) : ISchemaor {
    override fun getId() = 0L

    override fun getName() = ""

    override fun getLmNumber() = ""

    override fun getAdId() = ""

    override fun isNative() = true

    override fun needLogin() = true

    override fun isCredit() = false

    override fun getTitle() = ""

    override fun getLink() = url
}

class NoneSchemaor : ISchemaor {
    override fun getLink() = ""

    override fun getName() = ""

    override fun getId() = 0L

    override fun getLmNumber() = ""

    override fun isNative() = true

    override fun getTitle() = ""

    override fun needLogin() = false

    override fun isCredit() = false

    override fun getAdId() = ""
}

class NotificationSchemaor(private val isH5: String, private val url: String, private val needLogin: String) : ISchemaor {

    override fun getLink() = url

    override fun getName() = ""

    override fun getId() = 0L

    override fun getLmNumber() = ""

    override fun isNative() = isH5 != "1"

    override fun getTitle() = ""

    override fun needLogin() = needLogin == "1"

    override fun isCredit() = false

    override fun getAdId() = ""
}