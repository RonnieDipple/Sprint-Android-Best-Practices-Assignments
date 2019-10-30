package com.example.assignmentfirebasecrash

import com.crashlytics.android.Crashlytics

fun dropBreadCrumb(className: String, methodName: String, id: Long, generalData: String){

    val breadcrumb = ("$className - $methodName - $id - $generalData")
    Crashlytics.log(breadcrumb)
}