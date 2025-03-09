package org.ilya.project

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform