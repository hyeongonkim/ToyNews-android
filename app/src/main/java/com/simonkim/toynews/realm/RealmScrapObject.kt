package com.simonkim.toynews.realm

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class RealmScrapObject : RealmObject() {
    @PrimaryKey
    open var url : String = ""
    open var urlToImage : String = ""
    open var title : String = ""
    open var description : String = ""
}